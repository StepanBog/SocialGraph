package ru.bogdanov.cursach.models;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogdanov.cursach.dao.PeopleDAO;

import java.io.IOException;
import java.util.*;

@Component
public class Graph {
    private int rootId;
    private Map<Integer,Person> list;
    private Map<Integer,Person> appendedList;
    private LinkedList<Pair> edges;
    private LinkedList<Pair> appendedEdges;
    @Autowired
    private PeopleDAO dao;
    public Graph() {
        this.list = new HashMap<>(); {
        }
        this.edges = new LinkedList<>();
        /*  list.put(1,new Person("1","Tom","Hanks","2"));
            list.get(1).addFriend(2);
            list.get(1).addFriend(3);
            list.get(1).addFriend(5);
            list.get(1).addFriend(6);
        list.put(2,new Person("2","Amy","Smoke","2"));
            list.get(2).addFriend(1);
            list.get(2).addFriend(4);
            list.get(2).addFriend(3);
            list.get(2).addFriend(6);
        list.put(3,new Person("3","Jon","Snow","2"));
            list.get(3).addFriend(1);
            list.get(3).addFriend(2);
            list.get(3).addFriend(4);
        list.put(4,new Person("4","Den","Cooper","2"));
            list.get(4).addFriend(3);
            list.get(4).addFriend(2);
            list.get(4).addFriend(10);
            list.get(4).addFriend(5);
        list.put(5,new Person("5","Tom","Black","2"));
            list.get(5).addFriend(4);
            list.get(5).addFriend(1);
            list.get(5).addFriend(7);
            list.get(5).addFriend(8);*/
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public void addPerson(Person person){
        this.list.put(person.getId(),person);
    }

    public void addPersons(ArrayList<Person> loadPersonsData) {
        for (Person p:
             loadPersonsData) {
            this.list.put(p.getId(),p);
        }
    }
    public void cutFriends(){
      //  Iterator<Integer> iter = list.keySet().iterator();
        for (Map.Entry<Integer, Person> pers : list.entrySet()){
            //Integer id = iter.next();
            ArrayList<Integer> friends = pers.getValue().getFriends();
            ArrayList<Integer> friends2 = new ArrayList<>();
            for (Integer i:
                 friends) {
                if (list.containsKey(i)){
                    friends2.add(i);
        //            System.out.println("sdfsdf");
                }
            }
            list.get(pers.getKey()).setFriends(friends2);
        }
    }

    public LinkedList<Pair> getEdges() {
        return edges;
    }

    public void createEdges(){
        for (Map.Entry<Integer, Person> pers : list.entrySet()){
            ArrayList<Integer> friends = pers.getValue().getFriends();
            for (Integer i : friends){
                if (!edges.contains(new Pair<Integer,Integer>(pers.getValue().getId(),i))){
                    edges.add(new Pair<Integer,Integer>(pers.getValue().getId(),i));
                }
            }
        }
    }
    public Map<Integer, Person> getList() {
        return list;
    }

    public void setList(Map<Integer, Person> list) {
        this.list = list;
    }

    public void loadData(String code) throws ApiException, ClientException, ParseException, IOException, InterruptedException {
      //  ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
       // PeopleDAO dao = context.getBean("peopleDAO",PeopleDAO.class);
        dao.init(code);
        addPersons(dao.loadData());
        rootId = dao.getRoot();

    }

    public void appendData(String id) throws IOException, InterruptedException, ClientException, ApiException {
        appendedList = new HashMap<Integer, Person>();
        ArrayList<Person> l = dao.loadAppendedData(list.get(Integer.parseInt(id)));
        addPersons(l);
        for (Person p : l) {
            appendedList.put(p.getId(),p);
        }
    }
    public void appendEdges(){
        appendedEdges = new LinkedList<>();
        for (Map.Entry<Integer, Person> pers : list.entrySet()){
            ArrayList<Integer> friends = pers.getValue().getFriends();
            for (Integer i : friends){
                if (!edges.contains(new Pair<Integer,Integer>(pers.getValue().getId(),i))){
                    appendedEdges.add(new Pair<Integer,Integer>(pers.getValue().getId(),i));
                    edges.add(new Pair<Integer,Integer>(pers.getValue().getId(),i));
                }
            }
        }

    }

    public Map<Integer, Person> getAppendedList() {
        return appendedList;
    }

    public LinkedList<Pair> getAppendedEdges() {
        return appendedEdges;
    }

    public void setCord(String list) {
        String[] arr = list.split(";");
        for (int i = 0 ;i < arr.length ; i++){
            String[] item = arr[i].split(",");
            this.list.get(Integer.parseInt(item[0])).setX(Integer.parseInt(item[1]));
            this.list.get(Integer.parseInt(item[0])).setY(Integer.parseInt(item[2]));
        }
    }
}
