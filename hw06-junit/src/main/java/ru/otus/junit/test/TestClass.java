package ru.otus.junit.test;

import java.util.logging.*;
import ru.otus.junit.annotations.*;

@SuppressWarnings("unused")
public class TestClass {
    Logger log = Logger.getLogger(TestClass.class.getName());

    @Before
    void setUp() {
        log.info("SET UP");
    }

    @Test
    void firstTest() {
        log.info("TEST EXEC");
    }

    @Test
    void secondTest() {
        log.info("TEST 2 EXEC");
    }

    @Test
    void thirdFailedTest() {
        log.info("TEST 3 EXEC");
        assert false;
    }

    @After
    void clear() {
        log.info("CLEAR DATA");
    }
}
