package com.devk.webclient.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ProductData {
    private final List<String> brokerData;
    private final String directInsuranceFeature;
    private final String currentContribution;
    private final String productType;
}
