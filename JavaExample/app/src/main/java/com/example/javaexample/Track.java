package com.example.javaexample;

public class Track {
    public String name;
    public String instructor;

    Track(String name, String instructor){
        this.name = name;
        this.instructor = instructor;
    }

    public String getName(){
        return name;
    }

    public String getInstructor(){
        return instructor;
    }
}
