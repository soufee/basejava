package ru.shoma.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoma on 09.05.2018.
 */
public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
//        Thread thread0 = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i + " с первого");
//            }
//        });
//        Thread thread1 = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                System.out.println(i + " со второго");
//                           }
//        });
//        thread0.start();
//        thread1.start();

        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
    }

    private static synchronized void inc() {
        counter++;
    }

}
