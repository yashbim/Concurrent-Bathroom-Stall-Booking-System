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

        private int EMPLOYEE_ID;
        private FloorBathroom bathroom;

        public Person(int EMPLOYEE_ID, FloorBathroom bathroom) {
            this.EMPLOYEE_ID = EMPLOYEE_ID;
            this.bathroom = bathroom;
        }

        @Override
        public void run() {}
    }
}
