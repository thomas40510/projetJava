package com.apogee.dev.DuoVaders;

import com.apogee.dev.DuoVaders.client.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LogTest {

    private String out;
    @BeforeEach
    void setUp() {
        // redirect System.out to a String out
        System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
            public void write(int b) {
                out += (char) b;
            }
        }));
    }

    @Test
    void e() {
        Log.e("tag", "msg");
        // check if contains
        assertTrue(out.contains("[LOG/E]: (tag) msg"));
    }

    @Test
    void testE() {
        Log.e("tag", "msg", new Throwable());
        // check if contains
        assertTrue(out.contains("[LOG/E]: (tag) msg"));
    }

    @Test
    void i() {
        Log.i("tag", "msg");
        // check if contains
        assertTrue(out.contains("[LOG/I]: (tag) msg"));
    }

    @Test
    void testI() {
        Log.i("tag", "msg", new Throwable());
        // check if contains
        assertTrue(out.contains("[LOG/I]: (tag) msg"));
    }

    @Test
    void d() {
        Log.d("tag", "msg");
        // check if contains
        assertTrue(out.contains("[LOG/D]: (tag) msg"));
    }

    @Test
    void testD() {
        Log.d("tag", "msg", new Throwable());
        // check if contains
        assertTrue(out.contains("[LOG/D]: (tag) msg"));
    }
}