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
package org.cqfn.keepcalmandjson.parser;
import org.cqfn.keepcalmandjson.parser.exceptions.JsonParserException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for {@link JsonParser} throwing {@link JsonParserException} exceptions.
 */
public class CheckParserExceptionsTest {

    public void testExceptOccurrence(String input) {
        boolean exceptionCaught = false;
        try {
            JsonParser.parse(input);
        } catch (JsonParserException e) {
            System.out.println(e.getErrorMessage() + " in " + input);
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

    @Test
    public void testObjectWithElementsWithoutQuotes() {
        String input = "{ 12 : 345}";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithWrongKey() {
        String input = "{ 65 : \"value\"}";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithoutKey() {
        String input = "{ : \"value\"}";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithoutFigureBrackets() {
        String input = "key : \"value\"";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectValueWithoutQuotes() {
        String input = "{ key : value}";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithSquareBrackets() {
        String input = "[\"test\" : 123]";
        testExceptOccurrence(input);
    }

    @Test
    public void testArrayWithoutClosingBracket() {
        String input = "[\"test\", \"hello\"";
        testExceptOccurrence(input);
    }

    @Test
    public void testArrayWithTrailingComaWithoutClosingBracket() {
        String input = "[\"test\", \"hello\",";
        testExceptOccurrence(input);
    }

    @Test
    public void testArrayWithWrongElement() {
        String input = "[\"test\", FALSE]";
        testExceptOccurrence(input);
    }

    @Test
    public void testInput10() {
        String input = "123PU";
        testExceptOccurrence(input);
    }

    @Test
    public void testHexNumberWithNotHexDigitChars() {
        String input = "\"\\u000$";
        testExceptOccurrence(input);
    }

    @Test
    public void testWrongEscapeChar() {
        String input = "\"test\\*symbols\"";
        testExceptOccurrence(input);
    }

    @Test
    public void testStringWithoutClosingQuote() {
        String input = "\"testing";
        testExceptOccurrence(input);
    }

   @Test
    public void testWrongNumberFormatInsideArray() {
        String input = "[1, 2 &*&, 3]";
       testExceptOccurrence(input);
    }

    @Test
    public void testArrayWithWrongBrackets() {
        String input = "[1, 2, 3 }";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithoutClosingBracket() {
        String input = "{\"key\" : \"value\"";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithWrongFormat() {
        String input = "{\"key\" : \"value\" :";
        testExceptOccurrence(input);
    }

    @Test
    public void testEntryFigureBracketWithoutElements() {
        String input = "{";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithOnlyKey() {
        String input = "{\"key\"}";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithKeyAndEmptyValue() {
        String input = "{\"key\" :";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithTrailingComaWithoutClosingBracket() {
        String input = "{\"key\" : \"value\",";
        testExceptOccurrence(input);
    }

    @Test
    public void testObjectWithWrongClosingBracket() {
        String input = "{\n" +
                    "\t\"key1\" : \"value1\",\n" +
                    "\t\"key2\" : \"value2\"\n" +
                    "{\n";
        testExceptOccurrence(input);
    }

    @Test
    public void testEmptyInputParsing() {
        String input = "";
        testExceptOccurrence(input);
    }

    @Test
    public void testNullInputParsing() {
        testExceptOccurrence(null);
    }

    @Test
    public void testNotJsonElementParsing() {
        String input = "#$&";
        testExceptOccurrence(input);
    }
}
