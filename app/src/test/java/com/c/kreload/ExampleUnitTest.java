package com.c.kreload;

import org.junit.Test;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TestString(){

        String hallo = "08515-83838-9393";
        System.out.println(hallo.length());

    }
}