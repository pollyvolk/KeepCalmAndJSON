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
package pollyvolk.keepcalmandjson.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for {@link JsonElement}.
 */
public class JsonElementTest {

    @Test
    public void testBackspaceEscapeSequence() {
        JsonString string = new JsonString(null,"test\bsymbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\bsymbols\"", string);
    }

    @Test
    public void testTabEscapeSequence() {
        JsonString string = new JsonString(null,"test\tsymbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\tsymbols\"", string);
    }

    @Test
    public void testNewLineEscapeSequence() {
        JsonString string = new JsonString(null,"test\nsymbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\nsymbols\"", string);
    }

    @Test
    public void testFormFeedEscapeSequence() {
        JsonString string = new JsonString(null,"test\fsymbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\fsymbols\"", string);
    }

    @Test
    public void testCarriageReturnEscapeSequence() {
        JsonString string = new JsonString(null,"test\rsymbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\rsymbols\"", string);
    }

    @Test
    public void testDoubleQuoteEscapeSequence() {
        JsonString string = new JsonString(null,"test\"symbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\\"symbols\"", string);
    }

    @Test
    public void testBackslashEscapeSequence() {
        JsonString string = new JsonString(null,"test\\symbols");
        GeneralTest.testNoExceptionStringFormat("\"test\\\\symbols\"", string);
    }

    @Test
    public void testJsonElementTypes() {
        JsonObject film = new JsonObject(null);
        film.createJsonString("name", "Back to the Future");
        film.createJsonNumber("year", 1985);
        film.createJsonNumber("rating", 8.5);
        JsonArray stars = film.createJsonArray("stars");
        stars.createStringElement("Michael J. Fox");
        stars.createStringElement("Christopher Lloyd");
        stars.createStringElement("Lea Thompson");
        film.createJsonBoolean("wasNominated", true);
        film.createJsonNull("reviews");
        JsonObject sequel = film.createJsonObject("sequel");
        sequel.createJsonString("name", "Back to the Future Part II");

        JsonElement name = film.getElementByKey("name");
        String nameValue = name.getStringValue();
        assertEquals("Back to the Future", nameValue);
        assertTrue(name.isString());
        assertFalse(name.isBoolean());
        assertFalse(name.isInteger());
        assertFalse(name.isLongInteger());
        assertFalse(name.isNull());
        assertFalse(name.isNumber());
        assertEquals(0, name.getIntValue());
        assertEquals(0, name.getLongValue());
        assertEquals(0, name.getDoubleValue(), 0);
        assertFalse(name.getBooleanValue());
        assertNull(name.toJsonObject());
        assertNull(name.toJsonArray());


        JsonElement year = film.getElementByKey("year");
        assertFalse(year.isString());
        String yearValue = year.getStringValue();
        assertEquals("1985", yearValue);
        assertEquals(1985, year.getIntValue());
        assertEquals(1985, year.getLongValue());
        assertNull(year.toJsonObject());
        assertNull(year.toJsonArray());

        JsonElement rating = film.getElementByKey("rating");
        assertEquals(8.5, rating.getDoubleValue(), 0.01);
        assertNull(rating.toJsonObject());
        assertNull(rating.toJsonArray());

        JsonElement actors = film.getElementByKey("stars");
        assertNotNull(actors.toJsonArray());

        JsonElement sequelVariable  = film.getElementByKey("sequel");
        assertNotNull(sequelVariable.toJsonObject());
    }

    //@Test
    //public void testJsonElementSpecificSymbols() {
    //    JsonString string = new JsonString(null, "тест");
    //    GeneralTest.testNoExceptionStringFormat("\"\\u0442\\u0435\\u0441\\u0442\"", string);
    //}
}
