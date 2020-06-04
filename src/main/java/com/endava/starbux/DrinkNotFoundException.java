package com.endava.starbux;

public class DrinkNotFoundException extends RuntimeException {
    public DrinkNotFoundException(String message) {
        super(message);
    }
}
