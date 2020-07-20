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

public class JsonParser {

    static protected class Origin {

        private final String data;
        private int index;
        private final int maxIndex;

        Origin(String data) {
            this.data = data;
            index = 0;
            maxIndex = data != null ? data.length() : 0;
        }

        public char get() {
            if (index < maxIndex)
                return data.charAt(index);
            else
                return 0;
        }

        public char getSkippingSpace() {
            char c = get();
            while (isSpace(c))
                c = next();
            return c;
        }

        public char next() {
            if (index < maxIndex) {
                index++;
                return get();
            }
            else
                return 0;
        }

        public char nextSkippingSpace() {
            char c = next();
            while (isSpace(c))
                c = next();
            return c;
        }

        public int getIndex() {
            return index;
        }

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

    static public JsonElement parse(String data) throws JsonParserException {
        return parse(new Origin(data), null);
    }

    static public JsonElement parseNoThrow(String data) {
        return parseNoThrow(new Origin(data), null);
    }

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

    static protected boolean isSingleNumber(Origin origin, boolean neg) {
        int index = origin.getIndex();
        if (neg)
            return (index == 1);
        else
            return ( index == 0);
    }
    static protected boolean isLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_';
    }

    static protected boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    static protected boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

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
