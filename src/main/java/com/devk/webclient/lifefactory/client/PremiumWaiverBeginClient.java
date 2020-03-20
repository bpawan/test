package com.devk.webclient.lifefactory.client;

import com.devk.webclient.lifefactory.util.exception.DateConversionFailedException;
import com.devk.webclient.lifefactory.client.exception.InvalidResponseFromLifeFactoryException;
import com.devk.webclient.lifefactory.client.exception.PremiumWaiverBeginClientException;
import com.devk.webclient.lifefactory.util.JAXBElementFactory;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoProcessPremiumWaiverBeginRequest;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoProcessPremiumWaiverBeginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Slf4j
public class PremiumWaiverBeginClient extends BaseTransactionalServiceClient<SoProcessPremiumWaiverBeginRequest> {

    private static final String REQUEST_LOCAL_PART = "soProcessPremiumWaiverBeginRequest";

    @Autowired
    public PremiumWaiverBeginClient(InvocationContext560 invocationContext, JAXBElementFactory elementFactory) {
        super(invocationContext, elementFactory);
    }

    public SoProcessPremiumWaiverBeginResponse sendRequestToWaivePremium(
            String contractNumber,
            LocalDate effectiveDate,
            String modificationReason
    ) throws PremiumWaiverBeginClientException, InvalidResponseFromLifeFactoryException {
        return (SoProcessPremiumWaiverBeginResponse) this.send(
                this.createRequestToSend(effectiveDate, contractNumber, modificationReason),
                SoProcessPremiumWaiverBeginRequest.class,
                REQUEST_LOCAL_PART
        );
    }

    private SoProcessPremiumWaiverBeginRequest createRequestToSend(
            LocalDate effectiveDate,
            String contractNumber,
            String modificationReason
    ) throws PremiumWaiverBeginClientException {
        try {
            SoProcessPremiumWaiverBeginRequest request = new SoProcessPremiumWaiverBeginRequest();
            request.setInvocationBusinessContextTransactional(this.createBusinessContext(contractNumber, effectiveDate, modificationReason));
            request.setInvocationContext(this.invocationContext);

            return request;
        } catch (DateConversionFailedException exception) {
            throw new PremiumWaiverBeginClientException(exception);
        }
    }
}
