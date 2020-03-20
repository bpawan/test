package com.devk.webclient.service.util;

import com.devk.webclient.service.exception.UnsupportedInsuranceTypeException;
import com.devk.webclient.service.model.LifeFactoryInsuranceType;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Type Mapper")
public class ProductTypeMapperTest {

    private ProductTypeMapper mapper;

    @BeforeEach
    public void setup() {
        this.mapper = new ProductTypeMapper();
    }

    @Test
    @DisplayName("Throw Exception for unsupported insurance type")
    public void throwException() {
        UnsupportedInsuranceTypeException exception = assertThrows(UnsupportedInsuranceTypeException.class, () -> {
            this.mapper.map("Non Existing Insurance Type");
        });

        assertEquals(
                "Provided insurance type (Non Existing Insurance Type) is not supported",
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("Clean up the raw input data before mapping it.")
    public void cleanup() {

        // Tuple2<Input String, Expected Outcome>
        List<Tuple2<String, String>> dataToTest = List.of(
               new Tuple2<>("AUFREN_N_2015_12", "Rente"),
               new Tuple2<>("AUFREN_N_2020_06", "Rente"),
               new Tuple2<>("AUFREN_L_2020_06", "Rente"),
               new Tuple2<>("SOFREN_N_2017_01", "Rente"),
               new Tuple2<>("DIRREN_N_2017_01", "Rente"),
               new Tuple2<>("AUFREN_SP_N_2017_01", "Rente"),
               new Tuple2<>("AUFREN_SP_L_2017_01", "Rente"),
               new Tuple2<>("SOFREN_SP_N_2017_01", "Rente"),
               new Tuple2<>("AUFRENSOND_N_2015_12", "Rente"),
               new Tuple2<>("AUFREN_N_2015_12", "Rente"),
               new Tuple2<>("FONDSREN_N_2015_12", "Fonds"),
               new Tuple2<>("RIS_L_2015_12", "Risiko"),
                new Tuple2<>("FONDSKAP_L_2015_12", "Fondskap"),
                new Tuple2<>("DIRFONDSKAP_L_2015_12", "Fondskap")
        );

        dataToTest.forEach(testDataTuple -> {
            try {
                assertEquals(
                        testDataTuple._2,
                        this.mapper.map(testDataTuple._1),
                        String.format(
                                "Failed: [%s: %s]",
                                testDataTuple._1,
                                testDataTuple._2
                        )
                );
            } catch (UnsupportedInsuranceTypeException e) {
                fail(
                        String.format(
                                "Failed: [%s: %s] with exception message: %s",
                                testDataTuple._1,
                                testDataTuple._2,
                                e.getMessage()
                        )
                );
            }
        });
    }

    @Test
    @DisplayName("Will map all valid insurance type")
    public void map() {
        this.performAssertion(
                "Rente",
                List.of(
                        LifeFactoryInsuranceType.AUFREN,
                        LifeFactoryInsuranceType.SOFREN,
                        LifeFactoryInsuranceType.BASREN,
                        LifeFactoryInsuranceType.DIRREN,
                        LifeFactoryInsuranceType.AUFRENSOND,
                        LifeFactoryInsuranceType.AUFREN_SP,
                        LifeFactoryInsuranceType.SOFREN_SP,
                        LifeFactoryInsuranceType.SOFRENSOND
                )
        );

        this.performAssertion(
                "Kapital",
                List.of(
                        LifeFactoryInsuranceType.KAP,
                        LifeFactoryInsuranceType.DIRKAP,
                        LifeFactoryInsuranceType.KAPVW
                )
        );


        this.performAssertion(
                "Biometrie",
                List.of(
                        LifeFactoryInsuranceType.SBUEU,
                        LifeFactoryInsuranceType.DIRSBU
                )
        );


        this.performAssertion(
                "Riester",
                List.of(LifeFactoryInsuranceType.RIESREN)
        );

        this.performAssertion(
                "Fonds",
                List.of(LifeFactoryInsuranceType.FONDSREN)
        );

        this.performAssertion(
                "Fondskap",
                List.of(
                        LifeFactoryInsuranceType.FONDSKAP,
                        LifeFactoryInsuranceType.DIRFONDSKAP
                )
        );
        this.performAssertion(
                "Risiko",
                List.of(
                        LifeFactoryInsuranceType.SKV,
                        LifeFactoryInsuranceType.RIS
                )
        );
    }

    private void performAssertion(String expected, List<String> inputValues) {
        inputValues.forEach(inputValue -> {
            try {
                assertEquals(expected, this.mapper.map(inputValue));
            } catch (UnsupportedInsuranceTypeException e) {
                fail(e);
            }
        });
    }
}
