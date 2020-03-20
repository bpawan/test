package com.devk.webclient.lifefactory.client;

import com.devk.webclient.lifefactory.util.exception.DateConversionFailedException;
import com.devk.webclient.lifefactory.client.exception.InvalidResponseFromLifeFactoryException;
import com.devk.webclient.lifefactory.util.JAXBElementFactory;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import com.fja.xmlns.ipl.ccshared.lb.types.types.CVCompany;
import com.fja.xmlns.ipl.ccshared.lb.types.types.CVCompetenceLevel;
import com.fja.xmlns.ipl.ccshared.lc.sc.types.InvocationBusinessContextTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBIntrospector;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@RequiredArgsConstructor
abstract public class BaseTransactionalServiceClient<T> extends WebServiceGatewaySupport {

    private static final String NAMESPACE_URI = "http://xmlns.fja.com/ipl/ccshared/lc/sc/lifecontracttransactionalservice";

    protected final InvocationContext560 invocationContext;

    protected final JAXBElementFactory elementFactory;

    protected Object send(T request, Class<T> requestClass, String requestLocalPart) throws InvalidResponseFromLifeFactoryException {
        Object requestToSend = this.elementFactory.create(
                request,
                requestClass,
                NAMESPACE_URI,
                requestLocalPart
        );

        Object returnObject = this.getWebServiceTemplate().marshalSendAndReceive(getDefaultUri(), requestToSend);

        if(null == returnObject) {
            throw new InvalidResponseFromLifeFactoryException("null response");
        }

        return JAXBIntrospector.getValue(returnObject);
    }

    protected InvocationBusinessContextTransactional createBusinessContext(
            String contractNumber,
            LocalDate effectiveDate,
            String modificationReason
    ) throws DateConversionFailedException {
        InvocationBusinessContextTransactional businessContext = new InvocationBusinessContextTransactional();
        businessContext.setCompanyId(CVCompany.DEVK);
        businessContext.setCompetenceLevel(CVCompetenceLevel.MITARBEITER);
        businessContext.setModificationReasonCV(modificationReason);
        businessContext.setContractIdExt(contractNumber);


        businessContext.setEffectiveDate(this.convertToGregorian(effectiveDate));

        return businessContext;
    }

    private XMLGregorianCalendar convertToGregorian(LocalDate localDate) throws DateConversionFailedException {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString());
        } catch (DatatypeConfigurationException exception) {
            throw new DateConversionFailedException(exception);
        }
    }
}
