package com.hertz.service.hertzlibrary.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.hertz.service.hertzlibrary.util.CollectionsUtils.convert;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CollectionUtilsTest {

    @Test
    void testConversionWilNullSource() {
        List<String> nums = null;
        var results = convert(nums, Integer::valueOf);

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    void testConvertWithEmptySource() {
        List<String> nums = emptyList();
        var results = convert(nums, Integer::valueOf);

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    void testNormalConversion() {
        List<String> nums = List.of("1", "2", "3");
        var results = convert(nums, Integer::valueOf);

        assertNotNull(results);
        assertEquals(3, results.size());
        assertEquals(6, results.stream().mapToInt(Integer::intValue).sum());
    }
}
