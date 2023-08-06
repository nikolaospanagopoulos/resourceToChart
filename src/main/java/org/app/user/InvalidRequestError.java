package org.app.user;

public class InvalidRequestError extends Exception{
    public InvalidRequestError(String message){
        super(message);
    }
}
