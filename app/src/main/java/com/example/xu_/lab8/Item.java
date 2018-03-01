package com.example.xu_.lab8;

/**
 * Created by xu_ on 2017/12/20.
 */

public class Item {
    int id;
    String name;
    String birth;
    String gift;

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setBirth(String birth){
        this.birth = birth;
    }
    public void setGift(String gift){
        this.gift = gift;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getBirth(){
        return birth;
    }
    public String getGift(){
        return gift;
    }
}
