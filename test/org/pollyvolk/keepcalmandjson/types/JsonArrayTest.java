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
package org.pollyvolk.keepcalmandjson.types;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonArrayTest {

    @Test
    public void createSimpleJsonArray() {
        JsonArray jsonArray = new JsonArray(null);
        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");
        for (String str : stringList)
            jsonArray.createStringElement(str);

        String expected = "[\"one\",\"two\",\"three\"]";
        String expectedWithIndents =
                "[\n" +
                "  \"one\",\n" +
                "  \"two\",\n" +
                "  \"three\"\n" +
                "]";

        GeneralTest.testNoExceptionStringFormat(expected, jsonArray);
        GeneralTest.testNoExceptionStringFormatWithIndents(expectedWithIndents, jsonArray);
    }

    @Test
    public void createEmptyJsonArray() {
        JsonArray jsonArray = new JsonArray(null);
        String expected = "[]";
        String expectedWithIndents = "[ ]";

        GeneralTest.testNoExceptionStringFormat(expected, jsonArray);
        GeneralTest.testNoExceptionStringFormatWithIndents(expectedWithIndents, jsonArray);
        assertNotNull(jsonArray);
        assertTrue(jsonArray.isEmpty());
    }

    @Test
    public void createJsonArrayOfVariousElements() {
        JsonArray jsonArray = new JsonArray(null);
        JsonString jsonString = new JsonString(null,"lastItem");
        jsonArray.createStringElement("stringValue");
        jsonArray.createArrayElement();
        jsonArray.createBooleanElement(true);
        jsonArray.createNullElement();
        jsonArray.createObjectElement().createJsonString("test", "in process");
        jsonArray.createNumberElement(3.14);
        jsonArray.addArrayElement(jsonString);

        String expected = "[\"stringValue\",[],true,null,{\"test\":\"in process\"},3.14,\"lastItem\"]";
        String expectedWithIndents =
                "[\n" +
                "  \"stringValue\",\n" +
                "  [ ],\n" +
                "  true,\n" +
                "  null,\n" +
                "  {\n" +
                "    \"test\" : \"in process\"\n" +
                "  },\n" +
                "  3.14,\n" +
                "  \"lastItem\"\n" +
                "]";

        GeneralTest.testNoExceptionStringFormat(expected, jsonArray);
        GeneralTest.testNoExceptionStringFormatWithIndents(expectedWithIndents, jsonArray);

        assertEquals(7, jsonArray.size());
        JsonElement item = jsonArray.getElementAt(2);
        assertNotNull(item);
        assertTrue(item.isBoolean());
        assertEquals(true, item.getBooleanValue());
    }

    @Test
    public void getJsonArrayElements() {
        JsonString jsonString1 = new JsonString(null,"JSON");
        JsonNumber jsonNumber = new JsonNumber(null, 4);
        JsonString jsonString2 = new JsonString(null,"ever");
        JsonNull jsonNull = new JsonNull(null);

        JsonArray jsonArray = new JsonArray(null);
        jsonArray.addArrayElement(jsonString1);
        jsonArray.addArrayElement(jsonNumber);
        jsonArray.addArrayElement(jsonString2);
        jsonArray.addElement(jsonNull);

        List<JsonElement> elements = jsonArray.getArrayElements();
        JsonElement stringElement1 = elements.get(0);
        assertSame(jsonString1, stringElement1);
        JsonElement stringElement2 = elements.get(2);
        assertSame(jsonString2, stringElement2);
        JsonElement numberElement = elements.get(1);
        assertSame(jsonNumber, numberElement);
        assertNull(jsonNull.getParent());
    }

    @Test
    public void testJsonArrayInsideObject() {
        JsonObject object = new JsonObject(null);
        JsonArray array = object.createJsonArray("language");
        array.createStringElement("Java");
        array.createStringElement("Python");

        String expectedWithIndents1 =
                "{\n" +
                "  \"language\" :\n" +
                "  [\n" +
                "    \"Java\",\n" +
                "    \"Python\"\n" +
                "  ]\n" +
                "}";
        GeneralTest.testNoExceptionStringFormatWithIndents(expectedWithIndents1, object);

        object.getElementByKey("language").toJsonArray().createStringElement("C++");
        String expectedWithIndents2 =
                "{\n" +
                "  \"language\" :\n" +
                "  [\n" +
                "    \"Java\",\n" +
                "    \"Python\",\n" +
                "    \"C++\"\n" +
                "  ]\n" +
                "}";
        GeneralTest.testNoExceptionStringFormatWithIndents(expectedWithIndents2, object);
    }
}
