package com.example;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class ThreadExample {
    private final List<Integer> lessThenFifty = new CopyOnWriteArrayList<>();
    private final List<Integer> greaterThenFifty = new CopyOnWriteArrayList<>();

    private final Semaphore sem;
    private final Event event;

    private ExecutorService supplierExecutor;

    public ThreadExample(Event event, Semaphore sem) {
        this.event = event;
        this.sem = sem;
    }

    public void run(List<Supplier<Integer>> input) {
        event.addEvent(this::getSummFromTwoLists);

        supplierExecutor = Executors.newFixedThreadPool(input.size());

        input.forEach(s -> CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Integer integer = s.get();
                    System.out.println("thread id: " + Thread.currentThread().getId() + " значение supplier: " + integer);
                    return integer;
                }, supplierExecutor)
                .thenAccept(res -> new SummThread(lessThenFifty, greaterThenFifty, res, sem, event).run()));

    }

    private void getSummFromTwoLists() {
        int ls = lessThenFifty.stream().mapToInt(Integer::intValue).sum();
        int gs = greaterThenFifty.stream().mapToInt(Integer::intValue).sum();
        System.out.printf("event ----------> lessThenFifty - %s,  greaterThenFifty - %s%n", ls, gs);
    }
}
