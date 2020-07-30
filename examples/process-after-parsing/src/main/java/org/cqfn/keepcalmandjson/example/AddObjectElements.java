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
package org.cqfn.keepcalmandjson.example;

import org.cqfn.keepcalmandjson.parser.JsonParser;
import org.cqfn.keepcalmandjson.parser.exceptions.JsonParserException;
import org.cqfn.keepcalmandjson.types.JsonArray;
import org.cqfn.keepcalmandjson.types.JsonElement;
import org.cqfn.keepcalmandjson.types.JsonNumber;
import org.cqfn.keepcalmandjson.types.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddObjectElements {

    public static void main(String[] args) throws JsonParserException {
        String data =
                "{\n" +
                        "  name : \"John P.\",\n" +
                        "  age : 25,\n" +
                        "  score : 82.75,\n" +
                        "  hasDiploma : true\n" +
                        "}";
        JsonElement jsonData = JsonParser.parse(data);
        // Convert abstract element to JSON object instance
        JsonObject employee = jsonData.toJsonObject();
        // Add JsonElement created before
        JsonNumber years = new JsonNumber(null,3);
        employee.addElement("years of experience", years);
        // Create new content
        // Create array and fill it
        JsonArray languages = employee.createJsonArray("languages");
        languages.createStringElement("Java");
        languages.createStringElement("Kotlin");
        // Create object and fill it
        JsonObject projects = employee.createJsonObject("personal projects");
        projects.createJsonNull("scientific papers");
        projects.createJsonString("GitHub", "https://github.com/...");
        projects.createJsonNumber("articles amount", 2);
        // Print updated JSON version without indention
        System.out.println(employee.toString());
        // Print updated JSON version with indention
        System.out.println(employee.toStringWithIndents());
        testOutput(employee);
    }

    public static void testOutput(JsonElement element) {
        String jsonObject = "{\"age\":25,\"hasDiploma\":true,\"languages\":[\"Java\",\"Kotlin\"],\"name\":\"John P.\"," +
                "\"personal projects\":{\"GitHub\":\"https://github.com/...\",\"articles amount\":2," +
                "\"scientific papers\":null},\"score\":82.75,\"years of experience\":3}";
        String jsonObjectWithIndent =
                "{\n" +
                "  \"age\" : 25,\n" +
                "  \"hasDiploma\" : true,\n" +
                "  \"languages\" :\n" +
                "  [\n" +
                "    \"Java\",\n" +
                "    \"Kotlin\"\n" +
                "  ],\n" +
                "  \"name\" : \"John P.\",\n" +
                "  \"personal projects\" :\n" +
                "  {\n" +
                "    \"GitHub\" : \"https://github.com/...\",\n" +
                "    \"articles amount\" : 2,\n" +
                "    \"scientific papers\" : null\n" +
                "  },\n" +
                "  \"score\" : 82.75,\n" +
                "  \"years of experience\" : 3\n" +
                "}";
        // Check object content
        assertEquals(element.toString(), jsonObject);
        // Check object content with indention
        assertEquals(element.toStringWithIndents(), jsonObjectWithIndent);
    }
}
