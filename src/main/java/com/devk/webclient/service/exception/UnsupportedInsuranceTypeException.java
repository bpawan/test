package com.devk.webclient.service.exception;

public class UnsupportedInsuranceTypeException extends Exception {
    public UnsupportedInsuranceTypeException(String insuranceType) {
        super(String.format("Provided insurance type (%s) is not supported", insuranceType));
    }
}
