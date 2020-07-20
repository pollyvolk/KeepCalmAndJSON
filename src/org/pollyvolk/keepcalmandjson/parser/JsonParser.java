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

import org.pollyvolk.keepcalmandjson.parser.exceptions.*;

import org.pollyvolk.keepcalmandjson.types.*;

/**
 * JSON parser of String source.
 */
public class JsonParser {

    /**
     * Origin data.
     */
    static protected class Origin {

        /**
         * String value of source data.
         */
        private final String data;

        /**
         * Index of current char being processed.
         */
        private int index;

        /**
         * Index of the last char.
         */
        private final int maxIndex;

        /**
         * Constructor.
         * @param data String value of source data.
         */
        Origin(String data) {
            this.data = data;
            index = 0;
            maxIndex = data != null ? data.length() : 0;
        }

        /**
         * Get char at the current position.
         * @return Char at the index position.
         */
        public char get() {
            if (index < maxIndex)
                return data.charAt(index);
            else
                return 0;
        }

        /**
         * Get char at the current position, but skip spaces.
         * @return Current char or next char that is not a space.
         */
        public char getSkippingSpace() {
            char c = get();
            while (isSpace(c))
                c = next();
            return c;
        }

        /**
         * Get next char.
         * @return Char at the next after index position.
         */
        public char next() {
            if (index < maxIndex) {
                index++;
                return get();
            }
            else
                return 0;
        }

        /**
         * Get char at the next position, but skip spaces.
         * @return Next char after index position or next char that is not a space.
         */
        public char nextSkippingSpace() {
            char c = next();
            while (isSpace(c))
                c = next();
            return c;
        }

        /**
         * Get current index.
         * @return index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Check if char is a space.
         * @return TRUE if char is a space, newline, carriage return or tab escape sequence.
         */
        static protected boolean isSpace(char c) {
            switch(c) {
                case ' ':
                case '\n':
                case '\r':
                case '\t':
                    return true;
                default:
                    return false;
            }
        }
    }

    /**
     * Parse origin data.
     * @param data String data.
     * @return JsonElement element.
     * @throws JsonParserException if fails.
     */
    static public JsonElement parse(String data) throws JsonParserException {
        return parse(new Origin(data), null);
    }

    /**
     * Parse origin data.
     * @param data String data.
     * @return JsonElement element.
     */
    static public JsonElement parseNoThrow(String data) {
        return parseNoThrow(new Origin(data), null);
    }

    /**
     * Parse JSON element in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @return JsonElement element.
     * @throws JsonParserException if fails.
     */
    static protected JsonElement parse(Origin origin, JsonElement parent) throws JsonParserException{
        char c = origin.getSkippingSpace();

        switch(c) {
            case 0:
                throw new ExpectedJsonElementException();
            case '{':
                origin.next();
                return parseObject(origin, parent);
            case '[':
                origin.next();
                return parseArray(origin, parent);
            case '"': {
                origin.next();
                String value = parseString(origin);
                return new JsonString(parent, value);
            }
            case '-':
                c = origin.next();
                if (isDigit(c))
                    return parseNumber(origin, parent, true);
                break;
        }

        if (isDigit(c)) {
            return parseNumber(origin, parent, false);
        }

        if (isLetter(c)) {
            StringBuilder b = new StringBuilder();
            do {
                b.append(c);
                c = origin.next();
            } while(isLetter(c));
            switch(b.toString()) {
                case "true":
                    return new JsonBoolean(parent, true);
                case "false":
                    return new JsonBoolean(parent, false);
                case "null":
                    return new JsonNull(parent);
                default:
                    throw new InvalidJsonException();
            }
        }

        throw new ExpectedJsonElementException();
    }

    /**
     * Parse JSON element in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @return JsonElement element.
     */
    static protected JsonElement parseNoThrow(Origin origin, JsonElement parent) {
        char c = origin.getSkippingSpace();

        switch(c) {
            case 0:
                return null;
            case '{':
                origin.next();
                return parseObjectNoThrow(origin, parent);
            case '[':
                origin.next();
                return parseArrayNoThrow(origin, parent);
            case '"': {
                origin.next();
                String value = parseStringNoThrow(origin);
                return value != null ? new JsonString(parent, value) : null;
            }
            case '-':
                c = origin.next();
                if (isDigit(c))
                    return parseNumberNoThrow(origin, parent, true);
                break;
        }

        if (isDigit(c)) {
            return parseNumberNoThrow(origin, parent, false);
        }

        if (isLetter(c)) {
            StringBuilder b = new StringBuilder();
            do {
                b.append(c);
                c = origin.next();
            } while(isLetter(c));
            switch(b.toString()) {
                case "true":
                    return new JsonBoolean(parent, true);
                case "false":
                    return new JsonBoolean(parent, false);
                case "null":
                    return new JsonNull(parent);
                default:
                    return null;
            }
        }

        return null;
    }

    /**
     * Parse JSON object in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @return JsonObject element.
     * @throws JsonParserException if fails.
     */
    static protected JsonObject parseObject(Origin origin, JsonElement parent) throws JsonParserException {
        JsonObject obj = new JsonObject(parent);
        int count = 0;

        while(true) {
            char c = origin.getSkippingSpace();

            if (c == 0)
                throw new InvalidJsonException();
            if (c == '}') {
                origin.next();
                return obj;
            }
            if (count > 0) {
                if (c != ',')
                    throw new InvalidJsonException();
                c = origin.nextSkippingSpace();
                if (c == 0)
                    throw new InvalidJsonException();
            }
            String name = null;
            if (c == '\"') {
                origin.next();
                name = parseString(origin);
            }
            else if (isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(c);
                    c = origin.next();
                } while(isLetter(c) || isDigit(c));
                name = sb.toString();
            }
            if (c == '}')
                continue;
            if (name == null)
                throw new InvalidJsonException();
            c = origin.getSkippingSpace();
            if (c != ':')
                throw new InvalidJsonException();
            c = origin.nextSkippingSpace();
            if (c == 0)
                throw new InvalidJsonException();
            try {
                JsonElement element = parse(origin, obj);
                obj.addElement(name, element);
            } catch (JsonParserException e) {
                throw new ExpectedJsonElementException();
            }
            count++;
        }
    }

    /**
     * Parse JSON object in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @return JsonObject element.
     */
    static protected JsonObject parseObjectNoThrow(Origin origin, JsonElement parent) {
        JsonObject obj = new JsonObject(parent);
        int count = 0;

        while(true) {
            char c = origin.getSkippingSpace();

            if (c == 0)
                return null;
            if (c == '}') {
                origin.next();
                return obj;
            }
            if (count > 0) {
                if (c != ',')
                    return null;
                c = origin.nextSkippingSpace();
                if (c == 0)
                    return null;
            }
            String name = null;
            if (c == '\"') {
                origin.next();
                name = parseStringNoThrow(origin);
            }
            else if (isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                do {
                    sb.append(c);
                    c = origin.next();
                } while(isLetter(c) || isDigit(c));
                name = sb.toString();
            }
            if (c == '}')
                continue;
            if (name == null)
                return null;
            c = origin.getSkippingSpace();
            if (c != ':')
                return null;
            c = origin.nextSkippingSpace();
            if (c == 0)
                return null;
            JsonElement element = parseNoThrow(origin, obj);
            if (element == null)
                return null;
            obj.addElement(name, element);
            count++;
        }
    }

    /**
     * Parse JSON array in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @return JsonArray element.
     * @throws JsonParserException if fails.
     */
    static protected JsonArray parseArray(Origin origin, JsonElement parent) throws JsonParserException {
        JsonArray arr = new JsonArray(parent);
        int count = 0;

        while(true) {
            char c = origin.getSkippingSpace();
            if (c == ']') {
                origin.next();
                return arr;
            }
            if (c == 0)
                throw new ExpectedArrayException();
            if (count > 0) {
                if (c != ',')
                    throw new InvalidJsonException();
                c = origin.nextSkippingSpace();
                if (c == 0)
                    throw new ExpectedArrayException();
            }
            if (c == ']')
                continue;
            try {
                JsonElement element = parse(origin, arr);
                arr.addElement(element);
            } catch (JsonParserException e) {
                throw new ExpectedArrayException();
            }
            count++;
        }
    }

    /**
     * Parse JSON array in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @return JsonArray element.
     */
    static protected JsonArray parseArrayNoThrow(Origin origin, JsonElement parent) {
        JsonArray arr = new JsonArray(parent);
        int count = 0;

        while(true) {
            char c = origin.getSkippingSpace();
            if (c == ']') {
                origin.next();
                return arr;
            }
            if (c == 0)
                return null;
            if (count > 0) {
                if (c != ',')
                    return null;
                c = origin.nextSkippingSpace();
                if (c == 0)
                    return null;
            }
            if (c == ']')
                continue;
            JsonElement element = parseNoThrow(origin, arr);
            if (element == null)
                return null;
            arr.addElement(element);
            count++;
        }
    }

    /**
     * Parse JSON string in origin data.
     * @param origin Origin data object.
     * @return JsonString element.
     * @throws ExpectedStringException if fails.
     */
    static protected String parseString(Origin origin) throws ExpectedStringException {
        StringBuilder sb = new StringBuilder();
        char c = origin.get();
        while (c != '\"' && c != 0) {
            if (c == '\\') {
                c = origin.next();
                switch(c) {
                    case '"':
                        sb.append('"');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    case '/':
                        sb.append('/');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'u': {
                        int h = 0;
                        for (int i = 0; i < 4; i++) {
                            c = origin.next();
                            if (!isHexDigit(c))
                                throw new ExpectedStringException();
                            h = (h << 4) | convertHexDigit(c);
                        }
                        c = (char)h;
                        sb.append(c);
                        break;
                    }
                    default:
                        throw new ExpectedStringException();
                }
            }
            else
                sb.append(c);
            c = origin.next();
        }
        if (c == 0)
            throw new ExpectedStringException();
        origin.next();
        return sb.toString();
    }

    /**
     * Parse JSON string in origin data.
     * @param origin Origin data object.
     * @return JsonString element.
     */
    static protected String parseStringNoThrow(Origin origin) {
        StringBuilder sb = new StringBuilder();
        char c = origin.get();
        while (c != '\"' && c != 0) {
            if (c == '\\') {
                c = origin.next();
                switch(c) {
                    case '"':
                        sb.append('"');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    case '/':
                        sb.append('/');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'u': {
                        int h = 0;
                        for (int i = 0; i < 4; i++) {
                            c = origin.next();
                            if (!isHexDigit(c))
                                return null;
                            h = (h << 4) | convertHexDigit(c);
                        }
                        c = (char)h;
                        sb.append(c);
                        break;
                    }
                    default:
                        return null;
                }
            }
            else
                sb.append(c);
            c = origin.next();
        }
        if (c == 0)
            return null;
        origin.next();
        return sb.toString();
    }

    /**
     * Parse JSON number in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @param neg TRUE if number is negative.
     * @return JsonNumber element.
     * @throws ExpectedNumberException if fails.
     */
    static protected JsonNumber parseNumber(Origin origin, JsonElement parent, boolean neg) throws ExpectedNumberException {
        boolean isSingleNumber = isSingleNumber(origin, neg);
        StringBuilder sb = new StringBuilder();
        char c = origin.get();
        do {
            sb.append(c);
            c = origin.next();
        } while(isDigit(c));
        if (c == '.') {
            sb.append(c);
            c = origin.next();
            if (isDigit(c)) {
                do {
                    sb.append(c);
                    c = origin.next();
                } while(isDigit(c));
            }
        }
        c = origin.getSkippingSpace();
        if (isSingleNumber) {
            if (c != 0)
                throw new ExpectedNumberException();
        } else if (c != ',' && c != '}' && c != ']')
            throw new ExpectedNumberException();
        try {
            double value = Double.parseDouble(sb.toString());
            return new JsonNumber(parent, neg ? -value : value);
        }
        catch (NumberFormatException e) {
            throw new ExpectedNumberException();
        }
    }

    /**
     * Parse JSON number in origin data.
     * @param origin Origin data object.
     * @param parent Parent JsonElement.
     * @param neg TRUE if number is negative.
     * @return JsonNumber element.
     */
    static protected JsonNumber parseNumberNoThrow(Origin origin, JsonElement parent, boolean neg) {
        boolean isSingleNumber = isSingleNumber(origin, neg);
        StringBuilder sb = new StringBuilder();
        char c = origin.get();
        do {
            sb.append(c);
            c = origin.next();
        } while(isDigit(c));
        if (c == '.') {
            sb.append(c);
            c = origin.next();
            if (isDigit(c)) {
                do {
                    sb.append(c);
                    c = origin.next();
                } while(isDigit(c));
            }
        }
        c = origin.getSkippingSpace();
        if (isSingleNumber) {
            if (c != 0)
                return null;
        } else if (c != ',' && c != '}' && c != ']')
            return null;
        try {
            double value = Double.parseDouble(sb.toString());
            return new JsonNumber(parent, neg ? -value : value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Check if all origin data is a single number.
     * @param origin Origin data object.
     * @param neg TRUE if number is negative.
     * @return TRUE if origin data is a single number.
     */
    static protected boolean isSingleNumber(Origin origin, boolean neg) {
        int index = origin.getIndex();
        if (neg)
            return (index == 1);
        else
            return ( index == 0);
    }

    /**
     * Check if the specified char is a letter.
     * @param c Char.
     * @return TRUE if specified char is a letter.
     */
    static protected boolean isLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
    }

    /**
     * Check if the specified char is a digit.
     * @param c Char.
     * @return TRUE if specified char is a digit.
     */
    static protected boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * Check if the specified char is a hex digit.
     * @param c Char.
     * @return TRUE if specified char is a hex digit.
     */
    static protected boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    /**
     * Convert hex digit to decimal value.
     * @param c Char.
     * @return Decimal value of the specified hex digit.
     */
    static protected int convertHexDigit(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'A' && c <= 'F')
            return c - 'A' + 10;
        if (c >= 'a' && c <= 'f')
            return c - 'a' + 10;
        return -1;
    }
}
