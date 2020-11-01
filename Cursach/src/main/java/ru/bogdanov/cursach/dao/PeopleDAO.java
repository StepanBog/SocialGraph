package ru.bogdanov.cursach.dao;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import io.vertx.core.dns.SrvRecord;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogdanov.cursach.models.Parser;
import ru.bogdanov.cursach.models.Person;
import ru.bogdanov.cursach.models.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class PeopleDAO {

    private static final int APP_ID = 7618754;
    private static final String CLIENT_SECRET = "E9zgDy1tD9Ia01yb1sQv";
    private String ACCESS_TOKEN;
    private UserActor actor;
   // public Graph graph;
    private VkApiClient vk;
    @Autowired
    private Parser parser;

    public int getRoot() {
        return root;
    }

    private int root;
    public PeopleDAO() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        this.vk = new VkApiClient(transportClient);


    }

    public String request(String req) throws IOException {// return code;
        URL obj = new URL(req);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String resp = response.toString();
        return resp;
    }

    public void init(String param) throws IOException, ClientException, ApiException, ParseException {
        // String url = "http://www.google.com/";
        String code = "https://oauth.vk.com/access_token?client_id=" + APP_ID + "&client_secret=" + CLIENT_SECRET + "&response_type=token&redirect_uri=http://localhost:8080/0/cursach/auth&code=" + param;
        String resp = request(code);
        Response res = parser.authPars(resp);
        ACCESS_TOKEN = res.getAccess_token();
        actor = new UserActor(APP_ID, ACCESS_TOKEN);
       // return actor.getId();
    }
    public ArrayList<Person> createList(String id,String fName,String lName,String sex,String src) throws IOException, InterruptedException, ClientException, ApiException {
        Person person = new Person(id, fName, lName, sex, src);
        person.setCharge(50);
        root = person.getId();
        ArrayList<Integer> friendsList;
        GetResponse getResponse = vk.friends().get(actor).execute();
        friendsList = (ArrayList<Integer>) getResponse.getItems();
        int count = 0;
        int i = 0;
        ArrayList<Person> res = new ArrayList<>();
        String friends25 = "";
        for (Integer l : friendsList) {
            person.addFriend(l);
            if (i < 25){
                friends25 = friends25 + "," + l.toString();
                i++;
            } else {
                i = 0;
                friends25 = friends25.substring(1);
                res.addAll(loadPersonsData(friends25));
                count++;
                friends25 = "";
            }
        }
        if (!friends25.isEmpty())
            friends25 = friends25.substring(1);
        res.addAll(loadPersonsData(friends25));
        res.add(person);
        return res;
    }
    public ArrayList<Person> loadAppendedData(Person p) throws IOException, InterruptedException, ClientException, ApiException {
        Integer id = p.getId();
        ArrayList<Person> friendsList =  createList(id.toString(),p.getName(),p.getSurname(),p.getGender(),p.getPhotoSRC());
       return friendsList;
    };
    public ArrayList<Person> loadData() throws ClientException, ApiException, IOException, ParseException, InterruptedException {
        List<UserXtrCounters> list = vk.users().get(actor).fields(UserField.SEX,UserField.PHOTO_200).execute();
        ArrayList<Person> res = createList(list.get(0).getId().toString(), list.get(0).getFirstName(), list.get(0).getLastName(), list.get(0).getSex().toString(),list.get(0).getPhoto200());
       return res;
    }

    private ArrayList<Person> loadPersonsData(String friends25) throws IOException, InterruptedException {
        String responsePersonsData = "";
        String responsePersonsFriends = "";
        long m = System.currentTimeMillis();
        responsePersonsData = request("https://api.vk.com/method/execute.getPersonsData?user_ids=" + friends25 + "&fields=sex,photo_200&v=5.52&access_token=" + ACCESS_TOKEN + "&lang=ru");
        responsePersonsFriends = request("https://api.vk.com/method/execute.getFriends?user_ids=" + friends25 + "&v=5.52&access_token=" + ACCESS_TOKEN + "&lang=ru");
        if(System.currentTimeMillis() - m < 1000)
            Thread.sleep(500);
        ArrayList<Person> list = parser.parseFriendsData(responsePersonsData,responsePersonsFriends);
        return list;
    }
}
