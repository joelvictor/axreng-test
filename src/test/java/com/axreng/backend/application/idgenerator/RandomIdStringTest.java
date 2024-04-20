package com.axreng.backend.application.idgenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class RandomIdStringTest {

    @Test
    void testDefaultConstructor() {
        RandomIdString generator = new RandomIdString(); // Default length 21
        String generatedId = generator.nextString();

        assertNotNull(generatedId);
        assertEquals(21, generatedId.length());
        assertTrue(generatedId.matches("[A-Za-z0-9]+")); // Check for alphanumeric characters
    }

    @Test
    void testConstructorWithLength() {
        int length = 10;
        RandomIdString generator = new RandomIdString(length);
        String generatedId = generator.nextString();

        assertNotNull(generatedId);
        assertEquals(length, generatedId.length());
    }

    @Test
    void testConstructorWithCustomSymbols() {
        String customSymbols = "AB12";
        RandomIdString generator = new RandomIdString(5, new SecureRandom(), customSymbols);
        String generatedId = generator.nextString();

        assertNotNull(generatedId);
        assertEquals(5, generatedId.length());
        assertTrue(generatedId.matches("[AB12]+")); // Check for only allowed characters
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, 0})
    void testInvalidLength(int length) {
        assertThrows(IllegalArgumentException.class, () -> new RandomIdString(length));
    }

    @Test
    void testNullRandom() {
        assertThrows(NullPointerException.class, () -> new RandomIdString(10, null, RandomIdString.ALPHANUMERIC));
    }
}
