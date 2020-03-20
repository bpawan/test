package com.devk.webclient.service.exception;

public class ProductDataExtractionServiceFailedException extends Exception {
    public ProductDataExtractionServiceFailedException(Throwable previous) {
        super("Service failed while extracting product data.", previous);
    }
}
