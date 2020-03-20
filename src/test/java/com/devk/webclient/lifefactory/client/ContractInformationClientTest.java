package com.devk.webclient.lifefactory.client;

import com.devk.webclient.lifefactory.util.JAXBElementFactory;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoContractCalculateInformationRequest;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoContractCalculateInformationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@DisplayName("Life Factory Contract Information Client Test")
public class ContractInformationClientTest {

    public static final String URL = "http://www.example.com";

    private final InvocationContext560 invocationContext = mock(InvocationContext560.class);

    private final JAXBElementFactory elementFactory = mock(JAXBElementFactory.class);

    private final WebServiceTemplate webServiceTemplate = mock(WebServiceTemplate.class);

    private ContractInformationClient contractInformationClient;

    @BeforeEach
    public void setup() {
        this.contractInformationClient = new ContractInformationClient(this.invocationContext, this.elementFactory);
        this.contractInformationClient.setMarshaller(mock(Jaxb2Marshaller.class));
        this.contractInformationClient.setUnmarshaller(mock(Jaxb2Marshaller.class));
        this.contractInformationClient.setWebServiceTemplate(webServiceTemplate);
        doReturn(URL).when(this.webServiceTemplate).getDefaultUri();
    }

    @Test
    public void fetchContractInformation() throws Exception {
        LocalDate effectiveDate = LocalDate.now();
        String contractNumber = "123456789";
        String modificationReason = "some modification reason";

        ArgumentCaptor<SoContractCalculateInformationRequest> requestCaptor
                = ArgumentCaptor.forClass(SoContractCalculateInformationRequest.class);

        doReturn(mock(JAXBElement.class))
                .when(this.elementFactory)
                .create(
                        requestCaptor.capture(),
                        eq(SoContractCalculateInformationRequest.class),
                        eq("http://xmlns.fja.com/ipl/ccshared/lc/sc/lifecontracttransactionalservice"),
                        eq("soProcessPremiumWaiverBeginRequest")
                );

        JAXBElement<SoContractCalculateInformationResponse> testResponse = mock(JAXBElement.class);
        doReturn(testResponse).when(this.webServiceTemplate).marshalSendAndReceive(eq(URL), eq(new Object()));

        doReturn("test").when(testResponse).getValue();

        SoContractCalculateInformationResponse actualResponse =
                this.contractInformationClient.fetchContractInformation(
                        effectiveDate,
                        contractNumber,
                        modificationReason
                );
    }
}
