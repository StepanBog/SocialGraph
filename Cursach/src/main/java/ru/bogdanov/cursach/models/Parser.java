package ru.bogdanov.cursach.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Parser {
    private String str;
    public Parser() {
    }
    public Response authPars(String resp) {
        Response response = new Response();
        String[] sArray = resp.split("\"");
        response.setAccess_token(sArray[3]);
        response.setExpires_in(Integer.parseInt((sArray[6].substring(1,sArray[6].length()-1))));
        response.setUser_id(Integer.parseInt((sArray[8].substring(1,sArray[8].length()-1))));
        return response;
    }


    public ArrayList<Person> parseFriendsData(String responsePersonsData, String responsePersonsFriends) {
        ArrayList<Person> l = new ArrayList<>();
        responsePersonsData = responsePersonsData.substring(responsePersonsData.indexOf('['),responsePersonsData.length()-2);
        responsePersonsFriends = responsePersonsFriends.substring(responsePersonsFriends.indexOf('['),responsePersonsFriends.length()-2);
        String[] dataStr = responsePersonsData.split("\\.\\.");
        String[] friendsStr = responsePersonsFriends.split("\\.");
        int i = 0;
        while (i < 25 && i < dataStr.length) {
            if (!friendsStr[i].equals("[]") && !friendsStr[i].contains("error_code") &&  !dataStr[i].contains("error_code") && !dataStr[i].contains("DELETED") && !dataStr[i].contains("banned")) {
                String[] str = dataStr[i].substring(1, dataStr[i].length() - 1).split(",");
                String[] str2 = friendsStr[i].substring(1, friendsStr[i].length() - 1).split(",");
                Person p = new Person(str[1],str[0],str[2],str[3],str[4].replace("impg","").replace("impf",""));
                for (String s: str2) {
                    s = s.replaceAll("[\\[\\]\"]","");
                    if (!s.contains("error"))
                     //   try {
                            p.addFriend(Integer.parseInt(s));
                     //   }catch (Exception e){
                       //     System.out.println(str2.toString());
                       // }
                }
                l.add(p);
            }
            i++;

        }
        System.out.println(dataStr.length);

        return l;
    }

    public String parsePersonFriends(String request) {
        request = request.substring(request.indexOf('['),request.length()-2);
        return request;

    }
}
