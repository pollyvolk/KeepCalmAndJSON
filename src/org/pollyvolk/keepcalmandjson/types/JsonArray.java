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

import java.util.ArrayList;
import java.util.List;

public final class JsonArray extends JsonContainer {

    private final List<JsonElement> elements;

    public JsonArray(JsonElement parent) {
        super(parent);
        elements = new ArrayList<>();
    }

    protected void buildString(StringBuilder sb) {
        sb.append('[');
        boolean flag = false;
        for (JsonElement elem : elements) {
            if (flag)
            {
                sb.append(',');
            }
            elem.buildString(sb);
            flag = true;
        }
        sb.append(']');
    }

    protected void buildString(StringBuilder sb, int indent) {
        if (elements.isEmpty()) {
            sb.append("[ ]");
            return;
        }
        sb.append('[');
        boolean flag = false;
        for (JsonElement elem : elements) {
            if (flag)
                sb.append(',');
            sb.append('\n');
            buildIndent(sb, indent + 1);
            elem.buildString(sb, indent + 1);
            flag = true;
        }
        sb.append('\n');
        buildIndent(sb, indent);
        sb.append(']');
    }

    public JsonArray toJsonArray() {
        return this;
    }

    public int size() {
        return elements.size();
    }

    public JsonElement getElementAt(int index) {
        return elements.get(index);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public JsonString createStringElement(String value) {
        JsonString elem = new JsonString(this, value);
        elements.add(elem);
        return elem;
    }

    public JsonNumber createNumberElement(double value) {
        JsonNumber elem = new JsonNumber(this, value);
        elements.add(elem);
        return elem;
    }

    public JsonBoolean createBooleanElement(boolean value) {
        JsonBoolean elem = new JsonBoolean(this, value);
        elements.add(elem);
        return elem;
    }

    public JsonNull createNullElement() {
        JsonNull elem = new JsonNull(this);
        elements.add(elem);
        return elem;
    }

    public JsonObject createObjectElement() {
        JsonObject elem = new JsonObject(this);
        elements.add(elem);
        return elem;
    }

    public JsonArray createArrayElement() {
        JsonArray elem = new JsonArray(this);
        elements.add(elem);
        return elem;
    }

    public void addArrayElement(JsonElement elem) {
        elem.setParent(this);
        elements.add(elem);
    }

    public void addElement(JsonElement elem) {
        elements.add(elem);
    }

    public List<JsonElement> getArrayElements() {
        return elements;
    }
}

