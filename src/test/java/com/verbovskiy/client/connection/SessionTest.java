package com.verbovskiy.client.connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SessionTest {

    @Test
    void getInstance() {
        ThreadLocal<Session> actual = Session.getInstance();
        Assertions.assertNotNull(actual);
    }
}