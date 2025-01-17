package org.example;

import java.util.concurrent.Semaphore;

public class FloorBathroom {

    final static int BATHROOM_STALLS = 6;
    final static int NUM_EMPLOYEES = 6;
    Semaphore bathroomStalls = new Semaphore(6);
    boolean [] stallBooker ;


    public static void main(String[] args) {
        System.out.println("Floor Bathroom Booking System");



    }

    static class Person implements Runnable {
        @Override
        public void run() {}
    }
}
