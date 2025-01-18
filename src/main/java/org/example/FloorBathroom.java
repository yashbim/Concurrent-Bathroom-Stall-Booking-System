// FInd source code at https://github.com/yashbim/Concurrent-Bathroom-Stall-Booking-System

package org.example;

import java.util.concurrent.Semaphore;

public class FloorBathroom {
    Semaphore stalls;
    boolean[] stallAvailability; //false means occupied
    static int NUM_EMPLOYEES = 100;
    static int BATHROOM_STALLS = 6;

    public static void main(String[] args) {
        FloorBathroom bathroom = new FloorBathroom();
        Thread[] people = new Thread[NUM_EMPLOYEES];

        // Create and start threads
        for (int i = 0; i < NUM_EMPLOYEES; i++) {
            people[i] = new Thread(new Person(i + 1, bathroom));
//            people[i] = new Thread(new Person(i + 1, bathroom));

            // Add small delay between thread starts
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            people[i].start();
        }

        // Wait for all threads to complete
        for (Thread person : people) {
            try {
                person.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public FloorBathroom() {
        stalls = new Semaphore(BATHROOM_STALLS, true); // Fair semaphore
        stallAvailability = new boolean[BATHROOM_STALLS]; // false means available
    }

    public void printStallStatus() {
        System.out.println("\nCurrent Stall Status:");
        for (int i = 0; i < BATHROOM_STALLS; i++) {
            System.out.print("Stall " + (i + 1) + ": " +
                    (stallAvailability[i] ? "Occupied" : "Available") + " \n ");
        }
        System.out.println("\n");
    }

    private synchronized int findAvailableSlot() {
        for (int i = 0; i < BATHROOM_STALLS; i++) {
            if (!stallAvailability[i]) {
                stallAvailability[i] = true;
                return i;
            }
        }
        return -1;
    }

    public void bookStall(int personId) {
        try {
            System.out.println("Person " + personId + " is waiting in line.");
            stalls.acquire();

            int assignedStall = findAvailableSlot();
            System.out.println("Person " + personId + " is using stall " + (assignedStall + 1));
            printStallStatus();

            // Simulate bathroom use time as 3 seconds
            Thread.sleep(3000);

            clearStall(assignedStall);
            System.out.println("Person " + personId + " has left stall " + (assignedStall + 1));
            printStallStatus();

            stalls.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private synchronized void clearStall(int stallNumber) {
        stallAvailability[stallNumber] = false;
    }

    static class Person implements Runnable {
        private int employee_id;
        private FloorBathroom bathroom;


        public Person(int id, FloorBathroom bathroom ) {
            this.employee_id = id;
            this.bathroom = bathroom;
        }

        @Override
        public void run() {
            bathroom.bookStall(employee_id);
        }
    }

}