package com.devk.webclient.lifefactory.client.exception;

public class FailedCreatingLifeFactoryRequestException extends TransactionalServiceClientException {
    public FailedCreatingLifeFactoryRequestException(String reason, Throwable previous) {
        super(
                String.format(
                        "Failed creating request to send to life factory. [%s] ",
                        reason
                ),
                previous
        );
    }
}
