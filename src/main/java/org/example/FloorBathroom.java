package org.example;

import java.util.concurrent.Semaphore;

public class FloorBathroom {

    Semaphore bathroomStalls = new Semaphore(6);

    public static void main(String[] args) {
        System.out.println("Floor Bathroom Booking System");



    }
}
