package com.example;

import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class SummThread implements Runnable {

    private final Semaphore sem;
    private final List<Integer> lessThenFifty;
    private final List<Integer> greaterThenFifty;
    private final Integer num;
    private final Event event;


    SummThread(List<Integer> lessThenFifty, List<Integer> greaterThenFifty, Integer num, Semaphore sem, Event event) {
        this.lessThenFifty = lessThenFifty;
        this.greaterThenFifty = greaterThenFifty;
        this.sem = sem;
        this.num = num;
        this.event = event;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId() + " entered sum ");
        try {
            sem.acquire();
            System.out.println(Thread.currentThread().getId() + " executing ");

            if (num > 50) {
                sleep(1000);
                greaterThenFifty.add(num);
                System.out.println("thread id :" + Thread.currentThread().getId() + " added to greaterThenFifty " + num);
                event.onEvent();
                sem.release();
            }

            if (num < 50) {
                sleep(1000);
                lessThenFifty.add(num);
                System.out.println("thread id :" + Thread.currentThread().getId() + " added to lessThenFifty " + num);
                event.onEvent();
                sem.release();
            }

            System.out.println("left summ " + Thread.currentThread().getId());

        } catch (InterruptedException e) {
            System.out.println("something go wrong");
        }
    }
}
