package com.example;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private List<Runnable> list = new ArrayList<>();

    public void onEvent(){
        list.forEach(Runnable::run);
    }

    public void addEvent(Runnable runnable){
        list.add(runnable);
    }
}
