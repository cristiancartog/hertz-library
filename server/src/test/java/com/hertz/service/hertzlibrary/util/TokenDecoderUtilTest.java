package com.hertz.service.hertzlibrary.util;

import org.junit.jupiter.api.Test;

import static com.hertz.service.hertzlibrary.util.TokenDecoderUtil.userName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenDecoderUtilTest {

    @Test
    void testNullToken() {
        assertThrows(IllegalArgumentException.class, () -> userName(null));
    }

    @Test
    void testEmptyToken() {
        assertThrows(IllegalArgumentException.class, () -> userName("  \t\n"));
    }

    @Test
    void testEmptyUserName() {
        assertThrows(IllegalArgumentException.class, () -> userName("-token"));
    }

    @Test
    void testCorrectTokenDecoding() {
        assertEquals("aaa", userName("aaa-token"));
        assertEquals("@#$%-ad.a", userName("@#$%-ad.a-token"));
    }

}
