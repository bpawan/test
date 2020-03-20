package com.devk.webclient.lifefactory.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Component
public class JAXBElementFactory {

    public <T> JAXBElement<T> create(T request, Class<T> type, String namespaceUrl, String localPart) {
        return new JAXBElement<>(createQName(namespaceUrl, localPart), type, request);
    }

    private QName createQName(String namespaceUrl, String localPart) {
        return new QName(namespaceUrl, localPart);
    }
}

