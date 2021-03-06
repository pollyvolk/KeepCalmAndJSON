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
import org.cqfn.keepcalmandjson.types.JsonElement;
import org.cqfn.keepcalmandjson.types.JsonObject;

import static org.junit.jupiter.api.Assertions.*;

public class GetValueByKey {

    public static void main(String[] args) throws JsonParserException {
        int value = 0;
        String data = "{\n" +
                "\tkey1 : \"value1\",\n" +
                "\tkey2 : \"value2\",\n" +
                "\tkey3 : 123\n" +
                "}";
        JsonElement jsonData = JsonParser.parse(data);
        // Convert abstract element to JSON object instance
        JsonObject jsonObject = jsonData.toJsonObject();
        // Check if some key and value exist
        if (jsonObject.containsKey("key3"))
            // Get JsonElement by specified key and convert it to the type expected
            value = jsonObject.getElementByKey("key3").getIntValue();
        System.out.println(value);
        testOutput(value);
    }

    public static void testOutput(int value) {
        assertEquals(value, 123);
    }
}
