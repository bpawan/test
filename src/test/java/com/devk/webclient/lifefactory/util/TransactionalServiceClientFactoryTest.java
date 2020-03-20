package com.devk.webclient.lifefactory.util;

import com.devk.webclient.lifefactory.client.ContractInformationClient;
import com.devk.webclient.lifefactory.client.PremiumWaiverBeginClient;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TransactionalServiceClientFactoryTest {

    private final InvocationContext560 invocationContext560 = mock(InvocationContext560.class);

    private final JAXBElementFactory jaxbElementFactory = mock(JAXBElementFactory.class);

    private final Jaxb2Marshaller marshaller = mock(Jaxb2Marshaller.class);

    private TransactionalServiceClientFactory factory;

    @BeforeEach
    public void setup() {
        this.factory = new TransactionalServiceClientFactory(
                this.invocationContext560,
                this.jaxbElementFactory,
                this.marshaller
        );
    }

    @Test
    public void willCreateContractInformationClient() {
        String serviceUrl = "http://www.example.com";
        ContractInformationClient actual = this.factory.createContractInformationClient(serviceUrl);

        assertEquals(serviceUrl, actual.getDefaultUri());
        assertThat(actual.getMarshaller()).isInstanceOf(Jaxb2Marshaller.class);
        assertThat(actual.getUnmarshaller()).isInstanceOf(Jaxb2Marshaller.class);
    }

    @Test
    public void willCreatePremiumWaiverBeginClient() {
        String serviceUrl = "http://www.example.com";
        PremiumWaiverBeginClient actual = this.factory.createPremiumWaiverBeginClient(serviceUrl);

        assertEquals(serviceUrl, actual.getDefaultUri());
        assertThat(actual.getMarshaller()).isInstanceOf(Jaxb2Marshaller.class);
        assertThat(actual.getUnmarshaller()).isInstanceOf(Jaxb2Marshaller.class);
    }
}
