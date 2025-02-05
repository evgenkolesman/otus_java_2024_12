package ru.otus.junit.test;

import lombok.extern.slf4j.Slf4j;
import ru.otus.junit.annotations.After;
import ru.otus.junit.annotations.Before;
import ru.otus.junit.annotations.Test;

@Slf4j
@SuppressWarnings("unused")
public class TestClass {


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



    @After
    void clear() {
        log.info("CLEAR DATA");
    }


}
