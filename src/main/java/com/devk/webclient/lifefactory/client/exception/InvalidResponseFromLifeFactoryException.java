package com.devk.webclient.lifefactory.client.exception;

public class InvalidResponseFromLifeFactoryException extends TransactionalServiceClientException {
    public InvalidResponseFromLifeFactoryException(String reason) {
        super(String.format("Received invalid response from Life factory [%s]", reason));
    }
}
