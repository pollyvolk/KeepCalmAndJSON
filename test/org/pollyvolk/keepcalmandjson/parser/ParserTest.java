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
        assertTrue(bool.isBoolean());
        assertTrue(bool.getBooleanValue());

        JsonObject internalObject = object.getElementByKey("research work").toJsonObject();
        assertNotNull(internalObject);
        Map<String, JsonElement> objElements = internalObject.getElements();
        assertEquals(2, objElements.size());
        assertTrue(objElements.containsKey("papers"));
        assertTrue(objElements.containsKey("publications"));
        assertEquals(3, objElements.get("papers").getIntValue());
        assertEquals(1, objElements.get("publications").getIntValue());
    }
}
