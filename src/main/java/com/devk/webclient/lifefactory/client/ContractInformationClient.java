package com.devk.webclient.lifefactory.client;

import com.devk.webclient.lifefactory.client.exception.InvalidResponseFromLifeFactoryException;
import com.devk.webclient.lifefactory.client.exception.TransactionalServiceClientException;
import com.devk.webclient.lifefactory.util.JAXBElementFactory;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoContractCalculateInformationRequest;
import com.fja.xmlns.ipl.ccshared.lc.sc.lifecontracttransactionalservice.SoContractCalculateInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class ContractInformationClient extends BaseTransactionalServiceClient<SoContractCalculateInformationRequest> {
    private static final String REQUEST_LOCAL_PART = "soProcessPremiumWaiverBeginRequest";

    @Autowired
    public ContractInformationClient(InvocationContext560 invocationContext, JAXBElementFactory elementFactory) {
        super(invocationContext, elementFactory);
    }

    public SoContractCalculateInformationResponse fetchContractInformation(
            LocalDate effectiveDate,
            String contractNumber,
            String modificationReason
    ) throws TransactionalServiceClientException {
        Object rawResponse = this.send(
                this.createRequest(effectiveDate, contractNumber, modificationReason),
                SoContractCalculateInformationRequest.class,
                REQUEST_LOCAL_PART
        );

        return this.validateAndExtractResponse(rawResponse);
    }

    private SoContractCalculateInformationRequest createRequest(
            LocalDate effectiveDate,
            String contractNumber,
            String modificationReason
    ) throws TransactionalServiceClientException {
        SoContractCalculateInformationRequest request = new SoContractCalculateInformationRequest();

        request.setInvocationBusinessContextTransactional(
                this.createBusinessContext(contractNumber, effectiveDate, modificationReason)
        );

        request.setInvocationContext(this.invocationContext);

        return request;
    }

    private SoContractCalculateInformationResponse validateAndExtractResponse(Object rawResponse)
            throws TransactionalServiceClientException {

        if (rawResponse instanceof SoContractCalculateInformationResponse) {
            SoContractCalculateInformationResponse response = (SoContractCalculateInformationResponse) rawResponse;

            if (null == response.getResult()) {
                throw new InvalidResponseFromLifeFactoryException("result must not be null");
            }

            return response;
        }

        throw new InvalidResponseFromLifeFactoryException(
                "unexpected response type. (expected: soContractCalculateInformationResponse)"
        );
    }
}
