package com.boardcamp.api.exceptions;

public class GameUnprocessableEntityException extends RuntimeException {
    public GameUnprocessableEntityException(String message){
        super(message);
    }
}
