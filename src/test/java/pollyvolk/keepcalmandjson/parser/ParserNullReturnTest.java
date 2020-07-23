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
package pollyvolk.keepcalmandjson.parser;

import pollyvolk.keepcalmandjson.types.JsonElement;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for {@link JsonParser} null returning.
 */
public class ParserNullReturnTest {

    public void testNullReturn(String input) {
        JsonElement element = JsonParser.parseNoThrow(input);
        assertNull(element);
    }

    @Test
    public void testObjectWithElementsWithoutQuotes() {
        String input = "{ 12 : 345}";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithWrongKey() {
        String input = "{ 65 : \"value\"}";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithoutKey() {
        String input = "{ : \"value\"}";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithoutFigureBrackets() {
        String input = "key : \"value\"";
        testNullReturn(input);
    }

    @Test
    public void testObjectValueWithoutQuotes() {
        String input = "{ key : value}";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithSquareBrackets() {
        String input = "[\"test\" : 123]";
        testNullReturn(input);
    }

    @Test
    public void testArrayWithoutClosingBracket() {
        String input = "[\"test\", \"hello\"";
        testNullReturn(input);
    }

    @Test
    public void testArrayWithTrailingComaWithoutClosingBracket() {
        String input = "[\"test\", \"hello\",";
        testNullReturn(input);
    }

    @Test
    public void testArrayWithWrongElement() {
        String input = "[\"test\", FALSE]";
        testNullReturn(input);
    }

    @Test
    public void testNumberWithNotDigitChars() {
        String input = "-123F?";
        testNullReturn(input);
    }

    @Test
    public void testHexNumberWithNotHexDigitChars() {
        String input = "\"\\u000$";
        testNullReturn(input);
    }

    @Test
    public void testWrongEscapeChar() {
        String input = "\"test\\*symbols\"";
        testNullReturn(input);
    }

    @Test
    public void testStringWithoutClosingQuote() {
        String input = "\"testing";
        testNullReturn(input);
    }

   @Test
    public void testWrongNumberFormatInsideArray() {
        String input = "[1, 2 &*&, 3]";
        testNullReturn(input);
    }

    @Test
    public void testArrayWithWrongBrackets() {
        String input = "[1, 2, 3 }";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithoutClosingBracket() {
        String input = "{\"key\" : \"value\"";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithWrongFormat() {
        String input = "{\"key\" : \"value\" :";
        testNullReturn(input);
    }

    @Test
    public void testEntryFigureBracketWithoutElements() {
        String input = "{";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithOnlyKey() {
        String input = "{\"key\"}";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithKeyAndEmptyValue() {
        String input = "{\"key\" :";
        testNullReturn(input);
    }

    @Test
    public void testObjectWithTrailingComaWithoutClosingBracket() {
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
