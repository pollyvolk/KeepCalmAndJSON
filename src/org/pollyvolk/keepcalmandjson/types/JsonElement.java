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
package org.pollyvolk.keepcalmandjson.types;

public abstract class JsonElement {

    private JsonElement parent;

    public JsonElement(JsonElement parent) {
        this.parent = parent;
    }

    public JsonElement getParent() {
        return parent;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(sb);
        return sb.toString();
    }

    public String toStringWithIndents() {
        StringBuilder sb = new StringBuilder();
        buildString(sb, 0);
        return sb.toString();
    }

    public String getStringValue() {
        return toString();
    }

    public int getIntValue() {
        return 0;
    }

    public long getLongValue() {
        return 0;
    }

    public double getDoubleValue() {
        return 0;
    }

    public boolean getBooleanValue() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isInteger() {
        return false;
    }

    public boolean isLongInteger() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public JsonContainer toJsonContainer() {
        return null;
    }

    public JsonObject toJsonObject() {
        return null;
    }

    public JsonArray toJsonArray() {
        return null;
    }

    protected static void buildIndent(StringBuilder sb, int indent) {
        if (indent > 0) {
            for (int i = 0; i < indent; i++)
                sb.append("  ");
        }
    }

    protected static void buildJsonString(StringBuilder sb, String value) {
        sb.append('"');
        for (int i = 0, len = value.length(); i < len; i++) {
            char c = value.charAt(i);
            switch(c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < ' ' || c > 127)
                        sb.append(String.format("\\u%04x", (int)c));
                    else
                        sb.append(c);
                    break;
            }
        }
        sb.append('"');
    }

    protected void setParent(JsonElement elem) {
        parent = elem;
    }

    protected abstract void buildString(StringBuilder sb);

    protected abstract void buildString(StringBuilder sb, int indent);
}
