package com.devk.webclient.lifefactory.client.exception;

public class TransactionalServiceClientException extends Exception {
    public TransactionalServiceClientException(String message) {
        super(message);
    }

    public TransactionalServiceClientException(String message, Throwable previous) {
        super(message, previous);
    }
}
