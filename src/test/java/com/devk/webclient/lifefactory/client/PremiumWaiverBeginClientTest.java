package com.devk.webclient.lifefactory.client;

import com.devk.webclient.lifefactory.util.JAXBElementFactory;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoProcessPremiumWaiverBeginRequest;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoProcessPremiumWaiverBeginResponse;
import com.fja.xmlns.ipl.ccshared.lc.sc.types.TransactionalBusinessProcessReturnContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.bind.JAXBElement;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@DisplayName("PremiumWaiverBeginClient")
@ExtendWith(MockitoExtension.class)
public class PremiumWaiverBeginClientTest {

    public static final String MODIFICATION_REASON = "CUSTOMER_REQUIREMENT";
    private static final String CONTRACT_EXT_ID = "26000024100";
    private static final LocalDate EFFECTIVE_DATE = LocalDate.of(2019, 12, 1);
    protected final InvocationContext560 invocationContext = mock(InvocationContext560.class);
    protected final JAXBElementFactory elementFactory = mock(JAXBElementFactory.class);
    private PremiumWaiverBeginClient premiumWaiverBeginClient;

    @BeforeEach
    public void setup() {
        this.premiumWaiverBeginClient = new PremiumWaiverBeginClient(this.invocationContext, this.elementFactory);
        this.premiumWaiverBeginClient.setDefaultUri("test_url");
    }

    @Test
    @DisplayName("Sending request succeeds")
    public void willSendRequest() throws Exception {

        String namespaceUrl = "http://xmlns.fja.com/ipl/ccshared/lc/sc/lifecontracttransactionalservice";
        String requestLocalPart = "soProcessPremiumWaiverBeginRequest";

        ArgumentCaptor<SoProcessPremiumWaiverBeginRequest> testRequest = ArgumentCaptor.forClass(SoProcessPremiumWaiverBeginRequest.class);
        JAXBElement<SoProcessPremiumWaiverBeginRequest> requestXml = mock(JAXBElement.class);


        when(this.elementFactory.create(
                any(), eq(SoProcessPremiumWaiverBeginRequest.class), any(), any())
        ).thenReturn(requestXml);

        SoProcessPremiumWaiverBeginResponse actualResponse = this.premiumWaiverBeginClient
                .sendRequestToWaivePremium(CONTRACT_EXT_ID, EFFECTIVE_DATE, MODIFICATION_REASON);

        assertThat(actualResponse.getResult()).isInstanceOf(TransactionalBusinessProcessReturnContainer.class);
    }
}
