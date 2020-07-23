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

import java.util.Map;

/**
 * Test case for {@link JsonObject}.
 */
public class JsonObjectTest {

    @Test
    public void testJsonSimpleObjectCreating() {
        JsonObject album = new JsonObject(null);
        album.createJsonString("album", "Dark Side Of The Moon");
        album.createJsonString("group", "Pink Floyd");
        album.createJsonNumber("year", 1973);

        String expected = "{\"album\":\"Dark Side Of The Moon\",\"group\":\"Pink Floyd\",\"year\":1973}";
        GeneralTest.testNoExceptionStringFormat(expected, album);
    }

    @Test
    public void testJsonSimpleObjectCreatingWithIndents() {
        JsonObject film = new JsonObject(null);
        film.createJsonString("name", "Big fish");
        film.createJsonNumber("year", 2003);
        film.createJsonNumber("rating", 8.1);
        JsonArray stars = film.createJsonArray("stars");
        stars.createStringElement("Ewan McGregor");
        stars.createStringElement("Albert Finney");
        stars.createStringElement("Jessica Lange");
        film.createJsonBoolean("wasNominated", true);
        film.createJsonNull("reviews");

        String expectedWithIndents =
                "{\n" +
                "  \"name\" : \"Big fish\",\n" +
                "  \"rating\" : 8.1,\n" +
                "  \"reviews\" : null,\n" +
                "  \"stars\" :\n" +
                "  [\n" +
                "    \"Ewan McGregor\",\n" +
                "    \"Albert Finney\",\n" +
                "    \"Jessica Lange\"\n" +
                "  ],\n" +
                "  \"wasNominated\" : true,\n" +
                "  \"year\" : 2003\n" +
                "}";
        GeneralTest.testNoExceptionStringFormatWithIndents(expectedWithIndents, film);
    }

    @Test
    public void testNestedJsonObjectCreating() {
        JsonObject language = new JsonObject(null);
        language.createJsonString("language", "C");
        language.createJsonString("latest standard", "C18");
        JsonObject influencedLanguage = language.createJsonObject("influenced languages");
        influencedLanguage.createJsonString("language", "C++");
        influencedLanguage.createJsonString("latest standard", "C++17");
        influencedLanguage.createJsonObject("alternatives");

        String expected =
                "{\n" +
                "  \"influenced languages\" :\n" +
                "  {\n" +
                "    \"alternatives\" : { },\n" +
                "    \"language\" : \"C++\",\n" +
                "    \"latest standard\" : \"C++17\"\n" +
                "  },\n" +
                "  \"language\" : \"C\",\n" +
                "  \"latest standard\" : \"C18\"\n" +
                "}";
        GeneralTest.testNoExceptionStringFormatWithIndents(expected, language);

        JsonObject alternatives = influencedLanguage.getElementByKey("alternatives").toJsonObject();
        assertNotNull(alternatives);
        JsonString anotherLanguage = new JsonString(null,"C#");
        alternatives.addElement("language", anotherLanguage);
        assertNull(anotherLanguage.getParent());
    }

    @Test
    public void testJsonObjectStructure() {
        JsonObject java = new JsonObject(null);
        java.createJsonString("language", "Java");
        java.createJsonNumber("latest release", 14);
        JsonArray paradigms = java.createJsonArray("paradigms");
        paradigms.createStringElement("object-oriented");
        paradigms.createStringElement("imperative");
        paradigms.createStringElement("reflective");

        assertNotNull(java);
        assertFalse(java.isEmpty());
        assertEquals(3, java.size());
        assertTrue(java.containsKey("language"));
        assertTrue(java.containsKey("latest release"));
        assertTrue(java.containsKey("paradigms"));

        Map<String, JsonElement> javaCharacteristics = java.getElements();
        assertNotNull(javaCharacteristics);
        assertEquals(3, javaCharacteristics.size());
        JsonElement paradigmsElement = java.getElementByKey("paradigms");
        assertSame(paradigms, paradigmsElement);
    }
}
