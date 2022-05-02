package com.barisertakus.robotenvironment.enums;


public enum Direction {
    RIGHT, LEFT, UP, DOWN;

    public static Direction findByValue(String value) {
        String compareValue = value.toUpperCase();
        for (Direction direction : Direction.values()) {
            if (compareValue.equals(direction.toString()))
                return direction;
        }
        return null;
    }
}
