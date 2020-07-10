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
import org.pollyvolk.keepcalmandjson.types.JsonElement;

import static org.junit.Assert.*;

public class ParserNullReturnTest {

    public void testNullReturn(String input) {
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNull(element);
    }

    @Test
    public void testInput1() {
        String input = "{ 12 : 345}";
        testNullReturn(input);
    }

    @Test
    public void testInput2() {
        String input = "{ 65 : \"value\"}";
        testNullReturn(input);
    }

    @Test
    public void testInput3() {
        String input = "{ : \"value\"}";
        testNullReturn(input);
    }

    @Test
    public void testInput4() {
        String input = "key : \"value\"";
        testNullReturn(input);
    }

    @Test
    public void testInput5() {
        String input = "{ key : value}";
        testNullReturn(input);
    }

    @Test
    public void testInput6() {
        String input = "[\"test\" : 123]";
        testNullReturn(input);
    }

    @Test
    public void testInput7() {
        String input = "[\"test\", \"hello\"";
        testNullReturn(input);
    }

    @Test
    public void testInput8() {
        String input = "[\"test\", \"hello\",";
        testNullReturn(input);
    }

    @Test
    public void testInput9() {
        String input = "[\"test\", FALSE]";
        testNullReturn(input);
    }

    @Test
    public void testInput10() {
        String input = "-123F?";
        testNullReturn(input);
    }

    @Test
    public void testInput11() {
        String input = "\"\\u000$";
        testNullReturn(input);
    }

    @Test
    public void testInput12() {
        String input = "\"test\\*symbols\"";
        testNullReturn(input);
    }

    @Test
    public void testInput13() {
        String input = "\"testing";
        testNullReturn(input);
    }

    @Test
    public void testInput14() {
        String input = "[1, 2, 3";
        testNullReturn(input);
    }

    @Test
    public void testInput15() {
        String input = "[1, 2, 3 }";
        testNullReturn(input);
    }

    @Test
    public void testInput16() {
        String input = "{\"key\" : \"value\"";
        testNullReturn(input);
    }

    @Test
    public void testInput17() {
        String input = "{\"key\" : \"value\" :";
        testNullReturn(input);
    }

    @Test
    public void testInput18() {
        String input = "{";
        testNullReturn(input);
    }

    @Test
    public void testInput19() {
        String input = "{\"key\"}";
        testNullReturn(input);
    }

    @Test
    public void testInput20() {
        String input = "{\"key\" :";
        testNullReturn(input);
    }

    @Test
    public void testInput21() {
        String input = "{\"key\" : \"value\",";
        testNullReturn(input);
    }

    @Test
    public void testNullInputParsing() {
        testNullReturn(null);
    }

    @Test
    public void testNotJsonElementParsing() {
        String input = "#$&";
        testNullReturn(input);
    }
}
