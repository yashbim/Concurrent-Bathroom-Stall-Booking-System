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
                Thread.sleep(1000); //small delay
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

    public void printStallAvailability(){

        System.out.println("\nCurrent Stall Status:");
        for (int i = 0; i < BATHROOM_STALLS; i++) {
            System.out.println("Stall " + stallBooker[i+1] + " is " + (stallBooker[i] ? "available\n" : "unavailable\n"));
        }
        System.out.println("\n---------------------------");

    }

    private synchronized int findEmptySlot(){
        for (int i = 0; i < BATHROOM_STALLS; i++) {
            if(!stallBooker[i]){
                stallBooker[i] = true;
                return i;
            }
        }
        return -1;
    }

    public void bookBathroom(int personId){

        try{
            System.out.println("Person " + personId + " is waiting for bathroom.");
            bathroomStalls.acquire();

            int available_stall = findEmptySlot();
            System.out.println("Person " + personId + " is using stall " + available_stall);
            printStallAvailability();

            Thread.sleep(3000); //simulates bathroom time

            clearBathroom(available_stall); //empties the bathroom

            System.out.println("Person " + personId + " has emptied " + available_stall);
            printStallAvailability();

            bathroomStalls.release();

        }catch (InterruptedException e){}

    }

    public synchronized void clearBathroom(int stallId){

        stallBooker[stallId] = false;

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
