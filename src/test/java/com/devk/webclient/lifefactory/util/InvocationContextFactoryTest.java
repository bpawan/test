package com.devk.webclient.lifefactory.util;

import com.devk.webclient.lifefactory.util.exception.ApplicationInitializationException;
import com.fja.xmlns.ipl.bsf.types.InvocationContext560;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class InvocationContextFactoryTest {
    @Test
    public void createsInvocationContext560() throws Exception {
        String test_username = "test_username";
        String test_user_id = "test_user_id";

        InvocationContext560 actual = InvocationContextFactory.create(
                test_username,
                test_user_id
        );

        InvocationContext560 expected = new InvocationContext560();
        expected.setUserID(test_user_id);
        expected.setUserName(test_username);

        assertEquals(InvocationContext560.class, actual.getClass());
    }

    @Test
    public void throwsExceptionForInvalidUserName() {
        ApplicationInitializationException exception = assertThrows(ApplicationInitializationException.class, () -> {
            InvocationContextFactory.create(
                    "",
                    "test_user_id"
            );
        });

        assertEquals("Failed to initialize application. (Context Username invalid)", exception.getMessage());
    }

    @Test
    public void throwsExceptionForInvalidUserId() {
        ApplicationInitializationException exception = assertThrows(ApplicationInitializationException.class, () -> {
            InvocationContextFactory.create(
                    "test_username",
                    ""
            );
        });

        assertEquals("Failed to initialize application. (Context UserID invalid)", exception.getMessage());
    }
}
