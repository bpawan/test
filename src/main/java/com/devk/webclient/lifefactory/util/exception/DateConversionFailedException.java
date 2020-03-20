package com.devk.webclient.lifefactory.util.exception;

import com.devk.webclient.lifefactory.client.exception.TransactionalServiceClientException;

public class DateConversionFailedException extends TransactionalServiceClientException {
    public DateConversionFailedException(Throwable exception) {
        super("Failed to convert the effective date to Gregorian Calender", exception);
    }
}
