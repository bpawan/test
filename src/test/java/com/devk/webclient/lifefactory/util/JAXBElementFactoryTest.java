package com.devk.webclient.lifefactory.util;

import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JAXBElementFactoryTest {

    private JAXBElementFactory factory = new JAXBElementFactory();

    @Test
    public void willCreateJAXBElement() {

        @Data
        class TestRequest {
            private String var1;
            private String var2;
        }

        TestRequest request = new TestRequest();
        request.setVar1("test variable 1");
        request.setVar2("test variable 2");

        JAXBElement<TestRequest> actual = this.factory.create(request, TestRequest.class, "some url", "some endpoint");

        assertEquals(JAXBElement.class, actual.getClass());
    }
}
