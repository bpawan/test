package com.devk.webclient.service.util;

import com.devk.webclient.service.exception.UnsupportedInsuranceTypeException;
import com.devk.webclient.service.model.ProductType;
import com.devk.webclient.service.model.ProductTypeCandidate;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ProductTypeMapper {
    public String map(String needle) throws UnsupportedInsuranceTypeException {
        String cleanedNeedle = this.cleanupIncomingProductType(needle);

        if (ProductTypeCandidate.PENSION.contains(cleanedNeedle)) return ProductType.PENSION;
        if (ProductTypeCandidate.RESIDUAL_DEBT.contains(cleanedNeedle)) return ProductType.RESIDUAL_DEBT;
        if (ProductTypeCandidate.CAPITAL.contains(cleanedNeedle)) return ProductType.CAPITAL;
        if (ProductTypeCandidate.BIOMERTY.contains(cleanedNeedle)) return ProductType.BIOMETRIC;
        if (ProductTypeCandidate.RESITER.contains(cleanedNeedle)) return ProductType.RESITER;
        if (ProductTypeCandidate.FUNDS.contains(cleanedNeedle)) return ProductType.FUNDS;
        if (ProductTypeCandidate.CAPITAL_FUNDS.contains(cleanedNeedle)) return ProductType.CAPITAL_FUNDS;
        if (ProductTypeCandidate.RISK.contains(cleanedNeedle)) return ProductType.RISK;

        throw new UnsupportedInsuranceTypeException(needle);
    }

    private String cleanupIncomingProductType(String rawProductType) throws UnsupportedInsuranceTypeException {

        return ProductTypeCandidate
                .ALL_CANDIDATES
                .filter(candidate -> Pattern.compile("^" + candidate).matcher(rawProductType).find())
                .getOrElseThrow(() -> new UnsupportedInsuranceTypeException(rawProductType));
    }
}
