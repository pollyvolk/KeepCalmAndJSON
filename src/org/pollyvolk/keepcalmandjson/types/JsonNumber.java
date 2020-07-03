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

public final class JsonNumber extends JsonElement {
    private final double value;

    public JsonNumber(JsonElement parent, double value) {
        super(parent);
        this.value = value;
    }

    protected void buildString(StringBuilder sb) {
        String fmtValue;
        if (value == (long)value)
            fmtValue = String.format("%d", (long)value);
        else
            fmtValue = String.format("%s", value);
        sb.append(fmtValue);
    }

    protected void buildString(StringBuilder sb, int indent) {
        buildString(sb);
    }

    public int getIntValue() {
        int intValue = (int)value;
        return intValue == value ? intValue : 0;
    }

    public long getLongValue() {
        long longValue = (long)value;
        return longValue == value ? longValue : 0;
    }

    public double getDoubleValue() {
        return value;
    }

    public boolean isNumber() {
        return true;
    }

    public boolean isInteger() {
        return value == (int)value;
    }

    public boolean isLongInteger() {
        return value == (long)value;
    }
}
