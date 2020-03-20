package com.devk.webclient.service;

import com.devk.webclient.lifefactory.client.ContractInformationClient;
import com.devk.webclient.lifefactory.client.exception.TransactionalServiceClientException;
import com.devk.webclient.service.exception.ProductDataExtractionServiceFailedException;
import com.devk.webclient.service.exception.UnsupportedInsuranceTypeException;
import com.devk.webclient.service.model.ProductData;
import com.devk.webclient.service.util.ProductTypeMapper;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoContractCalculateInformationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Product Data Extraction Service")
public class ProductDataExtractionServiceTest {

    private final ContractInformationClient contractInformationClient = mock(ContractInformationClient.class);
    private final ProductTypeMapper productTypeMapper = new ProductTypeMapper();
    private ProductDataExtractionService service;

    @BeforeEach
    public void setup() {
        this.service = new ProductDataExtractionService(this.contractInformationClient, this.productTypeMapper);

    }

    @Test
    @DisplayName("Product Data Extraction Succeeds")
    public void extractionSucceeds() throws Exception {
        // Input data
        LocalDate effectiveDate = LocalDate.now();
        String contractNumber = "123456789";

        // Expected data
        String brokerNumber1 = "11110011";
        String brokerNumber2 = "11160018";
        String currentContribution = "102.77";
        String insuranceFeature = "UNBEKANNT";
        String productType = "Rente";

        when(this.contractInformationClient.fetchContractInformation(any(), anyString(), any()))
                .thenReturn(this.createTestResponse());

        ProductData actual = this.service.extractFromContractInformation(effectiveDate, contractNumber);

        assertEquals(brokerNumber1, actual.getBrokerData().get(0));
        assertEquals(brokerNumber2, actual.getBrokerData().get(1));

        assertEquals(currentContribution, actual.getCurrentContribution());
        assertEquals(insuranceFeature, actual.getDirectInsuranceFeature());
        assertEquals(productType, actual.getProductType());
    }

    @Test
    @DisplayName("Exception while LifeFactory client fails. ")
    public void throwsExceptionDueToLF() throws Exception {
        LocalDate effectiveDate = LocalDate.now();
        String contractNumber = "123456789";

        when(this.contractInformationClient.fetchContractInformation(any(), any(), any()))
                .thenThrow(new TransactionalServiceClientException("Some message"));

        ProductDataExtractionServiceFailedException exception = assertThrows(
                ProductDataExtractionServiceFailedException.class,
                () -> this.service.extractFromContractInformation(effectiveDate, contractNumber)
        );

        assertEquals("Service failed while extracting product data.", exception.getMessage());
    }

    @Test
    @DisplayName("Exception while mapping of data fails. ")
    public void throwsExceptionDueToData() throws Exception {
        LocalDate effectiveDate = LocalDate.now();
        String contractNumber = "123456789";
        String pdId = "AUFREN_N_2017_01";

        ProductTypeMapper productTypeMapper = mock(ProductTypeMapper.class);
        when(productTypeMapper.map(pdId)).thenThrow(new UnsupportedInsuranceTypeException("some message"));

        when(this.contractInformationClient.fetchContractInformation(any(), anyString(), any()))
                .thenReturn(this.createTestResponse());

        ProductDataExtractionService extractionService = new ProductDataExtractionService(
                this.contractInformationClient,
                productTypeMapper
        );

        ProductDataExtractionServiceFailedException exception = assertThrows(
                ProductDataExtractionServiceFailedException.class,
                () -> extractionService.extractFromContractInformation(effectiveDate, contractNumber)
        );

        assertEquals("Service failed while extracting product data.", exception.getMessage());
    }

    private SoContractCalculateInformationResponse createTestResponse() throws Exception {
        String filename = "fixture/soContractCalculateInformationResponse.xml";

        File responseFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getFile());

        JAXBContext jaxbContext = JAXBContext.newInstance(SoContractCalculateInformationResponse.class);

        Unmarshaller jaxbUnmarshal = jaxbContext.createUnmarshaller();

        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, new FileInputStream(responseFile));

        return jaxbUnmarshal.unmarshal(soapMessage.getSOAPBody().extractContentAsDocument(), SoContractCalculateInformationResponse.class).getValue();
    }
}
