/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Polina Volkhontseva
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cqfn.keepcalmandjson.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for {@link JsonNumber}.
 */
public class JsonNumberTest {

    @Test
    public void testJsonNumberCreating() {
        double input = 20.20;
        JsonNumber jsonNumber = new JsonNumber(null, input);
        String expected = "20.2";
        GeneralTest.testNoExceptionStringFormat(expected, jsonNumber);
    }

    @Test
    public void testJsonNumberCreatingWithIndents() {
        int input = 7;
        JsonNumber jsonNumber = new JsonNumber(null, input);
        String expected = "7";
        GeneralTest.testNoExceptionStringFormatWithIndents(expected, jsonNumber);
    }

    @Test
    public void testJsonNumberIntValue() {
        int input = -75;
        JsonNumber jsonNumber = new JsonNumber(null, input);
        assertNotNull(jsonNumber);
        assertEquals(input, jsonNumber.getIntValue());
        assertTrue(jsonNumber.isInteger());
    }

    @Test
    public void testJsonNumberLongValue() {
        long input = 123L;
        JsonNumber jsonNumber = new JsonNumber(null, input);
        assertNotNull(jsonNumber);
        assertEquals(input, jsonNumber.getLongValue());
        assertTrue(jsonNumber.isLongInteger());
    }

    @Test
    public void testJsonNumberDoubleValue() {
        double input = 10.0d / 3.0d;
        double result = 3.333;
        JsonNumber jsonNumber = new JsonNumber(null, input);
        assertNotNull(jsonNumber);
        assertEquals(result, jsonNumber.getDoubleValue(), 0.001);
    }

    @Test
    public void testJsonNumberFormat() {
        double input = 20.20;
        JsonNumber jsonNumber = new JsonNumber(null, input);
        assertNotNull(jsonNumber);
        GeneralTest.testNoExceptionBooleanCase(true, jsonNumber.isNumber());
    }
}
