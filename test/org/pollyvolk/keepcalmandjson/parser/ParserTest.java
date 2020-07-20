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
package org.pollyvolk.keepcalmandjson.parser;

import org.junit.Test;
import org.pollyvolk.keepcalmandjson.types.*;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test case for {@link JsonParser} methods without exceptions.
 */
public class ParserTest {

    @Test
    public void testObjectParsing() {
        String input =
                "{\n" +
                "  \"name\" : \"Ivan Ivanov\",\n" +
                "  \"years\" : 25,\n" +
                "  \"PhD\" : null,\n" +
                "  \"skills\" :\n" +
                "  [\n" +
                "    \"Java\",\n" +
                "    \"C++\",\n" +
                "    \"Assembler\",\n" +
                "    \"Linux\"\n" +
                "  ],\n" +
                "  \"testPassed\" : true,\n" +
                "  \"research work\" :\n" +
                "  {\n" +
                "  \"papers\" : 3,\n" +
                "  \"publications\" : 1\n" +
                "  }\n" +
                "}";

        JsonElement element = JsonParser.parseNoThrow(input);
        JsonObject object = element.toJsonObject();
        assertNotNull(object);
        assertFalse(object.isEmpty());

        JsonElement string = object.getElementByKey("name");
        assertNotNull(string);
        assertTrue(string.isString());
        assertEquals("Ivan Ivanov", string.getStringValue());

        JsonElement number = object.getElementByKey("years");
        assertNotNull(number);
        assertTrue(number.isNumber());
        assertTrue(number.isInteger());
        assertEquals(25, number.getIntValue());

        JsonElement jsonNull = object.getElementByKey("PhD");
        assertNotNull(jsonNull);
        assertTrue(jsonNull.isNull());

        JsonArray array = object.getElementByKey("skills").toJsonArray();
        assertNotNull(array);
        assertEquals(4, array.size());
        assertEquals("Java", array.getElementAt(0).getStringValue());
        assertEquals("C++", array.getElementAt(1).getStringValue());
        assertEquals("Assembler", array.getElementAt(2).getStringValue());
        assertEquals("Linux", array.getElementAt(3).getStringValue());

        JsonElement bool = object.getElementByKey("testPassed");
        assertNotNull(bool);
        assertTrue(bool.getBooleanValue());

        JsonObject internalObject = object.getElementByKey("research work").toJsonObject();
        assertNotNull(internalObject);
        Map<String, JsonElement> objElements = internalObject.getElements();
        assertEquals(2, objElements.size());
        assertEquals(3, objElements.get("papers").getIntValue());
        assertEquals(1, objElements.get("publications").getIntValue());
    }

    @Test
    public void testIntNumberParsing() {
        String input = "123";
        int expected = 123;
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getIntValue(), 0);
    }

    @Test
    public void testNegativeNumberParsing() {
        String input = "-123";
        int expected = -123;
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getIntValue(), 0);
    }
    @Test
    public void testFloatNumberParsing() {
        String input = "12.370";
        double expected = 12.37;
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getDoubleValue(), 0);
    }

    @Test
    public void testLongIntNumberParsing() {
        String input = "922337203685";
        long expected = 922337203685L;
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getLongValue(), 0);
    }

    @Test
    public void testHexDigitParsing() {
        String input = "\"\\u000F \\u001e\"";
        String expected = "\u000F \u001e";
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getStringValue());
    }

    @Test
    public void testJsonStringParsing() {
        String input = "\"Parser\"";
        String expected = "Parser";
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getStringValue());
    }


    @Test
    public void testJsonNullParsing() {
        String input = "null";
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertTrue(element.isNull());
    }

    @Test
    public void testBoolTrueParsing() {
        String input = "true";
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertTrue(element.isBoolean());
        assertTrue(element.getBooleanValue());
    }

    @Test
    public void testBoolFalseParsing() {
        String input = "false";
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertTrue(element.isBoolean());
        assertFalse(element.getBooleanValue());
    }

    @Test
    public void testArrayParsing() {
        String input =
                "[\n" +
                "  \"str1\",\n" +
                "  \"str2\",\n" +
                "  1,\n" +
                "  2\n" +
                "]";
        JsonElement element = JsonParser.parseNoThrow(input);
        JsonArray array = element.toJsonArray();
        assertNotNull(array);
        assertEquals(4, array.size());
    }

    @Test
    public void testArrayWithTrailingComaParsing() {
        String input =
                "[\n" +
                "  \"str1\",\n" +
                "  \"str2\",\n" +
                "  1,\n" +
                "  2,\n" +
                "]";
        JsonElement element = JsonParser.parseNoThrow(input);
        JsonArray array = element.toJsonArray();
        assertNotNull(array);
        assertEquals(4, array.size());
    }

    @Test
    public void testObjectWithTrailingComaParsing() {
        String input = "{\n" +
                "\t\"key1\" : \"value1\",\n" +
                "\t\"key2\" : \"value2\",\n" +
                "}";
        JsonElement element = JsonParser.parseNoThrow(input);
        JsonObject object = element.toJsonObject();
        assertNotNull(object);
        assertEquals(2, object.size());
    }

    public void testEscapeSequencesParsing(String expected, String input) {
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNotNull(element);
        assertEquals(expected, element.getStringValue());
    }

    @Test
    public void testBackspaceEscapeSequence() {
        String input = "\"test\\bsymbols\"";
        String expected = "test\bsymbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testTabEscapeSequence() {
        String input = "\"test\\tsymbols\"";
        String expected = "test\tsymbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testNewLineEscapeSequence() {
        String input = "\"test\\nsymbols\"";
        String expected = "test\nsymbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testFormFeedEscapeSequence() {
        String input = "\"test\\fsymbols\"";
        String expected = "test\fsymbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testCarriageReturnEscapeSequence() {
        String input = "\"test\\rsymbols\"";
        String expected = "test\rsymbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testDoubleQuoteEscapeSequence() {
        String input = "\"test\\\"symbols\"";
        String expected = "test\"symbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testBackslashEscapeSequence() {
        String input = "\"test\\\\symbols\"";
        String expected = "test\\symbols";
        testEscapeSequencesParsing(expected, input);
    }

    @Test
    public void testSlashEscapeSequence() {
        String input = "\"test\\/symbols\"";
        String expected = "test/symbols";
        testEscapeSequencesParsing(expected, input);
    }
}
