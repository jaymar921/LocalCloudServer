package com.JayMar.enums;

public enum Commands {

    EXIT(1),
    INVALID(2);

    private final int value;
    Commands(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
