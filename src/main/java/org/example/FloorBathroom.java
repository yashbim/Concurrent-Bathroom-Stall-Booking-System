// FInd source code at https://github.com/yashbim/Concurrent-Bathroom-Stall-Booking-System

package org.example;

import java.util.concurrent.Semaphore;

public class FloorBathroom {

    final static int BATHROOM_STALLS = 6;
    final static int NUM_EMPLOYEES = 6;
    Semaphore bathroomStalls;
    boolean [] stallBooker ;


    public static void main(String[] args) {
        System.out.println("Floor Bathroom Booking System");

        FloorBathroom bathroom = new FloorBathroom();
        Thread[] people = new Thread[NUM_EMPLOYEES];

        //starting threads

        for (int i = 0; i < NUM_EMPLOYEES; i++) {
            people[i] = new Thread(new Person(i+1, bathroom));

            try{
                Thread.sleep(1000); //will simulate time taken in the stall
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }

        }

        //Waiting for threads to complete

        for (Thread person : people) {
            try {
                person.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            }
        }
    }

    public FloorBathroom() {

        bathroomStalls = new Semaphore(BATHROOM_STALLS,true);
        stallBooker = new boolean[BATHROOM_STALLS];

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
