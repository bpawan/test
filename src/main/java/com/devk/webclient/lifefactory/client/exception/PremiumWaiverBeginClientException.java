package com.devk.webclient.lifefactory.client.exception;

public class PremiumWaiverBeginClientException extends Exception {
    public PremiumWaiverBeginClientException(Throwable exception) {
        super("Failed making request to premium waiver process begin in Life Factory Transactional Service.", exception);
    }
}
