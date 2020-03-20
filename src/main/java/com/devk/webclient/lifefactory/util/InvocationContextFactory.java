package com.devk.webclient.lifefactory.util;

import com.devk.webclient.lifefactory.util.exception.ApplicationInitializationException;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;

public class InvocationContextFactory {

    public static InvocationContext560 create(String invocationContextUserName, String invocationContextUserId)
            throws ApplicationInitializationException {
        validateContextCredentials(invocationContextUserId, invocationContextUserName);
        InvocationContext560 context = new InvocationContext560();
        context.setUserName(invocationContextUserName);
        context.setUserID(invocationContextUserId);

        return context;
    }

    private static void validateContextCredentials(String invocationContextUserId, String invocationContextUserName)
            throws ApplicationInitializationException {
        if (invocationContextUserId.isEmpty()) {
            throw new ApplicationInitializationException("Failed to initialize application. (Context UserID invalid)");
        }

        if (invocationContextUserName.isEmpty()) {
            throw new ApplicationInitializationException("Failed to initialize application. (Context Username invalid)");
        }
    }
}
