package com.bridgelabz.exception;

public class TravelPackageNotFoundException
        extends RuntimeException {

    public TravelPackageNotFoundException(String message) {
        super(message);
    }
}