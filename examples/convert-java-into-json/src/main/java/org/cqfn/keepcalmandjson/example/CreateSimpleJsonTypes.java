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

import org.cqfn.keepcalmandjson.types.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateSimpleJsonTypes {

    public static void main(String[] args) {
        // Create JSON number
        JsonNumber number = new JsonNumber(null, 111);
        // Create empty JSON array
        JsonArray array = new JsonArray(null);
        // Create empty JSON boolean
        JsonBoolean bool = new JsonBoolean(null, true);
        // Create new elements in array or add existing ones
        array.createBooleanElement(false);
        array.addArrayElement(bool);
        array.addArrayElement(number);
        // Print result JSON array with indention
        System.out.println(array.toStringWithIndents());
        testOutput(array);
    }

    public static void testOutput(JsonElement element) {
        String jsonObject =
                "[\n" +
                "  false,\n" +
                "  true,\n" +
                "  111\n" +
                "]";
        // Check object content
        assertEquals(element.toStringWithIndents(), jsonObject);
    }
}
