package ru.bogdanov.cursach.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Person {

    private int charge;
    private int id;
    private String name;
    private String surname;
    private String gender;
    private String photoSRC;
    private int x;
    private int y;
    public ArrayList<Integer> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Integer> friends) {
        this.friends = friends;
    }

    private ArrayList<Integer> friends;

    public Person(){

    }
    public Person(String id, String name, String surname, String gender,String photoSRC) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.charge = 10;
        this.photoSRC = photoSRC;
        friends = new ArrayList<>();
        x = 0;
        y = 0;
    }

    public String getPhotoSRC() {
        return photoSRC;
    }

    public void setPhotoSRC(String photoSRC) {
        this.photoSRC = photoSRC;
    }

    public void addFriend(int id){
        friends.add(id);
    }
    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
