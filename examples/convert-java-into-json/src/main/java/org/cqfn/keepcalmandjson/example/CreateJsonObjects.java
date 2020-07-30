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

import org.cqfn.keepcalmandjson.types.JsonArray;
import org.cqfn.keepcalmandjson.types.JsonElement;
import org.cqfn.keepcalmandjson.types.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateJsonObjects {
    public static void main(String[] args) {
        // Create JSON object
        JsonObject album = new JsonObject(null);
        // Create key-value pairs
        album.createJsonString("album", "Album name");
        album.createJsonString("group", "Group name");
        album.createJsonNumber("year", 2019);
        JsonArray tracklist = album.createJsonArray("tracklist");
        tracklist.createStringElement("song 1");
        tracklist.createStringElement("song 2");
        // Create nested object
        JsonObject record = album.createJsonObject("record");
        record.createJsonNumber("year", 2020);
        record.createJsonString("country", "Russia");
        record.createJsonString("format", "LP");
        // Convert to string representation of JSON with indention
        String output = album.toStringWithIndents();
        System.out.println(output);
        testOutput(album);
    }

    public static void testOutput(JsonElement element) {
        String jsonObject =
                "{\n" +
                "  \"album\" : \"Album name\",\n" +
                "  \"group\" : \"Group name\",\n" +
                "  \"record\" :\n" +
                "  {\n" +
                "    \"country\" : \"Russia\",\n" +
                "    \"format\" : \"LP\",\n" +
                "    \"year\" : 2020\n" +
                "  },\n" +
                "  \"tracklist\" :\n" +
                "  [\n" +
                "    \"song 1\",\n" +
                "    \"song 2\"\n" +
                "  ],\n" +
                "  \"year\" : 2019\n" +
                "}";
        // Check object content
        assertEquals(element.toStringWithIndents(), jsonObject);
    }
}
