package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Event event = new Event();
        Semaphore sem = new Semaphore(2);
        ThreadExample threadExample = new ThreadExample(event, sem);


        List<Supplier<Integer>> list = new ArrayList<>();

        int numberOfTimesToRandomize = 3; // random.nextInt(15);
        for (int i = 0; i <= numberOfTimesToRandomize; i++) {
            list.add(() -> random.nextInt(100));
        }

        threadExample.run(list);
    }
}
