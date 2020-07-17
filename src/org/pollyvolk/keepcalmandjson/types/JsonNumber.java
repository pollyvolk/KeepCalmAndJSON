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

/**
 * JSON numeric element type extending JSON abstract element.
 */
public final class JsonNumber extends JsonElement {

    /**
     * JSON numeric element value.
     */
    private final double value;

    /**
     * Constructor.
     * @param parent Parent JsonElement.
     * @param value Double value.
     */
    public JsonNumber(JsonElement parent, double value) {
        super(parent);
        this.value = value;
    }

    /**
     * Convert JsonNumber to a string format.
     * @param sb StringBuilder containing a string representation of JSON number type.
     */
    protected void buildString(StringBuilder sb) {
        String fmtValue;
        if (value == (long)value)
            fmtValue = String.format("%d", (long)value);
        else
            fmtValue = String.format("%s", value);
        sb.append(fmtValue);
    }

    /**
     * Convert JsonNumber to a string format with indention.
     * @param sb StringBuilder containing a string representation of JSON number type.
     * @param indent Indention value.
     */
    protected void buildString(StringBuilder sb, int indent) {
        buildString(sb);
    }

    /**
     * Get an int value of the JsonNumber element.
     * @return Integer value of JsonNumber element.
     */
    public int getIntValue() {
        int intValue = (int)value;
        return intValue == value ? intValue : 0;
    }

    /**
     * Get a long value of the JsonNumber element.
     * @return Long value of JsonNumber element.
     */
    public long getLongValue() {
        long longValue = (long)value;
        return longValue == value ? longValue : 0;
    }

    /**
     * Get a double value of the JsonNumber element.
     * @return Double value of JsonNumber element.
     */
    public double getDoubleValue() {
        return value;
    }

    /**
     * Check if the element is a JsonNumber.
     * @return TRUE.
     */
    public boolean isNumber() {
        return true;
    }

    /**
     * Check if the JsonNumber element contains integer value.
     * @return TRUE if JsonNumber element has integer value.
     */
    public boolean isInteger() {
        return value == (int)value;
    }

    /**
     * Check if the JsonNumber element contains long value.
     * @return TRUE if JsonNumber element has long value.
     */
    public boolean isLongInteger() {
        return value == (long)value;
    }
}
