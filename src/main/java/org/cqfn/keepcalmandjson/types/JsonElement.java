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
package org.cqfn.keepcalmandjson.types;

/**
 * JSON abstract element.
 */
public abstract class JsonElement {

    /**
     * JsonElement parent of current element.
     */
    private JsonElement parent;

    /**
     * Constructor.
     * @param parent Parent JsonElement.
     */
    public JsonElement(JsonElement parent) {
        this.parent = parent;
    }

    /**
     * Get the parent of the element.
     * @return Parent JsonElement.
     */
    public JsonElement getParent() {
        return parent;
    }

    /**
     * Convert element to a string format.
     * @return String containing a string representation of JSON element.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        buildString(sb);
        return sb.toString();
    }

    /**
     * Convert element to a string format with indents.
     * @return String containing a string representation of JSON element with all indents.
     */
    public String toStringWithIndents() {
        StringBuilder sb = new StringBuilder();
        buildString(sb, 0);
        return sb.toString();
    }

    /**
     * Get a string value of the element.
     * @return String value of JSON element.
     */
    public String getStringValue() {
        return toString();
    }

    /**
     * Get an int value of the element.
     * @return 0.
     */
    public int getIntValue() {
        return 0;
    }

    /**
     * Get a long value of the element.
     * @return 0.
     */
    public long getLongValue() {
        return 0;
    }

    /**
     * Get a double value of the element.
     * @return 0.
     */
    public double getDoubleValue() {
        return 0;
    }

    /**
     * Get a boolean value of the element.
     * @return 0.
     */
    public boolean getBooleanValue() {
        return false;
    }

    /**
     * Check if the element is a JsonString.
     * @return FALSE.
     */
    public boolean isString() {
        return false;
    }

    /**
     * Check if the element is a JsonNumber.
     * @return FALSE.
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * Check if the JsonNumber element contains integer value.
     * @return FALSE.
     */
    public boolean isInteger() {
        return false;
    }

    /**
     * Check if the JsonNumber element contains long value.
     * @return FALSE.
     */
    public boolean isLongInteger() {
        return false;
    }

    /**
     * Check if the element is a JsonBoolean.
     * @return FALSE.
     */
    public boolean isBoolean() {
        return false;
    }

    /**
     * Check if the element is a JsonNull.
     * @return FALSE.
     */
    public boolean isNull() {
        return false;
    }

    /**
     * Convert the element to a JsonContainer.
     * @return NULL.
     */
    public JsonContainer toJsonContainer() {
        return null;
    }

    /**
     * Convert the element to a JsonObject.
     * @return NULL.
     */
    public JsonObject toJsonObject() {
        return null;
    }

    /**
     * Convert the element to a JsonArray.
     * @return NULL.
     */
    public JsonArray toJsonArray() {
        return null;
    }

    /**
     * Add indention to string representation of JSON element.
     * @param sb StringBuilder containing a string representation of JSON element.
     * @param indent Indention value.
     */
    protected static void buildIndent(StringBuilder sb, int indent) {
        if (indent > 0) {
            for (int i = 0; i < indent; i++)
                sb.append("  ");
        }
    }

    /**
     * Convert JsonString value to a string format.
     * @param sb StringBuilder containing a string representation of JSON element.
     * @param value Indention JsonString value.
     */
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

    /**
     * Convert element to a string format.
     * @param sb StringBuilder containing a string representation of JSON element.
     */
    protected abstract void buildString(StringBuilder sb);

    /**
     * Convert element to a string format with indention.
     * @param sb StringBuilder containing a string representation of JSON element.
     * @param indent Indention value.
     */
    protected abstract void buildString(StringBuilder sb, int indent);
}
