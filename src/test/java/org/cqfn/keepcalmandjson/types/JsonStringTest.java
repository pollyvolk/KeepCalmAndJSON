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
 * Test case for {@link JsonString}.
 */
public class JsonStringTest {

    @Test
    public void testJsonStringCreating() {
        String input = "testing";
        JsonString jsonString = new JsonString(null, input);
        String expected = "\"testing\"";
        GeneralTest.testNoExceptionStringFormat(expected, jsonString);
    }

    @Test
    public void testJsonStringCreatingWithIndents() {
        String input = "testing";
        JsonString jsonString = new JsonString(null, input);
        String expected = "\"testing\"";
        GeneralTest.testNoExceptionStringFormatWithIndents(expected, jsonString);
    }

    @Test
    public void testJsonStringGetValue() {
        String input = "testing";
        JsonString jsonString = new JsonString(null, input);
        String expected = "testing";
        GeneralTest.testNoExceptionElementValue(expected, jsonString);
    }

    @Test
    public void testJsonStringsFormat() {
        String input = "testingFormat";
        JsonElement jsonString = new JsonString(null, input);
        GeneralTest.testNoExceptionBooleanCase(true, jsonString.isString());
    }
}
