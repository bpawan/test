package com.devk.webclient.lifefactory.util;

import com.devk.webclient.lifefactory.client.ContractInformationClient;
import com.devk.webclient.lifefactory.client.PremiumWaiverBeginClient;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionalServiceClientFactory {
    @Autowired
    private final InvocationContext560 invocationContext560;

    @Autowired
    private final JAXBElementFactory jaxbElementFactory;

    @Autowired
    private final Jaxb2Marshaller marshaller;

    public ContractInformationClient createContractInformationClient(String serviceUrl) {
        ContractInformationClient client = new ContractInformationClient(invocationContext560, jaxbElementFactory);
        client.setDefaultUri(serviceUrl);
        client.setMarshaller(this.marshaller);
        client.setUnmarshaller(this.marshaller);

        return client;
    }

    public PremiumWaiverBeginClient createPremiumWaiverBeginClient(String serviceUrl) {
        PremiumWaiverBeginClient premiumWaiverBeginClient = new PremiumWaiverBeginClient(invocationContext560, jaxbElementFactory);
        premiumWaiverBeginClient.setDefaultUri(serviceUrl);
        premiumWaiverBeginClient.setMarshaller(marshaller);
        premiumWaiverBeginClient.setUnmarshaller(marshaller);

        return premiumWaiverBeginClient;
    }
}
