package com.devk.webclient.service.model;

import io.vavr.collection.List;

public final class ProductTypeCandidate {

    public static final List<String> ALL_CANDIDATES = List.of(
            LifeFactoryInsuranceType.AUFREN,
            LifeFactoryInsuranceType.SOFREN,
            LifeFactoryInsuranceType.BASREN,
            LifeFactoryInsuranceType.DIRREN,
            LifeFactoryInsuranceType.AUFRENSOND,
            LifeFactoryInsuranceType.AUFREN_SP,
            LifeFactoryInsuranceType.SOFREN_SP,
            LifeFactoryInsuranceType.SOFRENSOND,
            LifeFactoryInsuranceType.RESTS,
            LifeFactoryInsuranceType.KAP,
            LifeFactoryInsuranceType.DIRKAP,
            LifeFactoryInsuranceType.KAPVW,
            LifeFactoryInsuranceType.SBUEU,
            LifeFactoryInsuranceType.DIRSBU,
            LifeFactoryInsuranceType.RIESREN,
            LifeFactoryInsuranceType.FONDSREN,
            LifeFactoryInsuranceType.FONDSKAP,
            LifeFactoryInsuranceType.DIRFONDSKAP,
            LifeFactoryInsuranceType.SKV,
            LifeFactoryInsuranceType.RIS
    );

    public static final List<String> PENSION = List.of(
            LifeFactoryInsuranceType.AUFREN,
            LifeFactoryInsuranceType.SOFREN,
            LifeFactoryInsuranceType.BASREN,
            LifeFactoryInsuranceType.DIRREN,
            LifeFactoryInsuranceType.AUFRENSOND,
            LifeFactoryInsuranceType.AUFREN_SP,
            LifeFactoryInsuranceType.SOFREN_SP,
            LifeFactoryInsuranceType.SOFRENSOND
    );

    public static final List<String> RESIDUAL_DEBT = List.of(LifeFactoryInsuranceType.RESTS);

    public static final List<String> CAPITAL = List.of(
            LifeFactoryInsuranceType.KAP,
            LifeFactoryInsuranceType.DIRKAP,
            LifeFactoryInsuranceType.KAPVW
    );

    public static final List<String> BIOMERTY = List.of(
            LifeFactoryInsuranceType.SBUEU,
            LifeFactoryInsuranceType.DIRSBU
    );

    public static final List<String> RESITER = List.of(LifeFactoryInsuranceType.RIESREN);

    public static final List<String> FUNDS = List.of(LifeFactoryInsuranceType.FONDSREN);

    public static final List<String> CAPITAL_FUNDS = List.of(
            LifeFactoryInsuranceType.FONDSKAP,
            LifeFactoryInsuranceType.DIRFONDSKAP
    );

    public static final List<String> RISK = List.of(
            LifeFactoryInsuranceType.SKV,
            LifeFactoryInsuranceType.RIS
    );

    private ProductTypeCandidate() {
        // Prevent instantiation of the class.
    }
}
