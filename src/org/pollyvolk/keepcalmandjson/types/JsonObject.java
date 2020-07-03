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

import java.util.Map;
import java.util.TreeMap;

public final class JsonObject extends JsonContainer {

    private final Map<String, JsonElement> elements;

    public JsonObject(JsonElement parent) {
        super(parent);
        elements = new TreeMap<>();
    }

    protected void buildString(StringBuilder sb) {
        sb.append('{');
        boolean flag = false;
        for (Map.Entry<String, JsonElement> entry : elements.entrySet()) {
            if (flag)
                sb.append(',');
            String name = entry.getKey();
            JsonElement elem = entry.getValue();
            sb.append('"');
            sb.append(name);
            sb.append("\":");
            elem.buildString(sb);
            flag = true;
        }
        sb.append('}');
    }

    protected void buildString(StringBuilder sb, int indent) {
        if (elements.isEmpty()) {
            sb.append("{ }");
            return;
        }
        sb.append('{');
        boolean flag = false;
        for (Map.Entry<String, JsonElement> entry : elements.entrySet()) {
            if (flag)
                sb.append(',');
            sb.append('\n');
            buildIndent(sb, indent + 1);
            String name = entry.getKey();
            JsonElement elem = entry.getValue();
            buildJsonString(sb, name);
            JsonContainer jc = elem.toJsonContainer();
            if (jc != null && !jc.isEmpty()) {
                sb.append(" :\n");
                buildIndent(sb, indent + 1);
            }
            else {
                sb.append(" : ");
            }
            elem.buildString(sb, indent + 1);
            flag = true;
        }
        sb.append('\n');
        buildIndent(sb, indent);
        sb.append('}');
    }

    public JsonObject toJsonObject() {
        return this;
    }

    public int size() {
        return elements.size();
    }

    public boolean containsKey(String key) {
        return elements.containsKey(key);
    }

    public JsonElement getElementByKey(String key) {
        return elements.get(key);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public JsonString createJsonString(String key, String value) {
        JsonString elem = new JsonString(this, value);
        elements.put(key, elem);
        return elem;
    }

    public JsonNumber createJsonNumber(String key, double value) {
        JsonNumber elem = new JsonNumber(this, value);
        elements.put(key, elem);
        return elem;
    }

    public JsonBoolean createJsonBoolean(String key, boolean value) {
        JsonBoolean elem = new JsonBoolean(this, value);
        elements.put(key, elem);
        return elem;
    }

    public JsonNull createJsonNull(String key) {
        JsonNull elem = new JsonNull(this);
        elements.put(key, elem);
        return elem;
    }

    public JsonObject createJsonObject(String key) {
        JsonObject elem = new JsonObject(this);
        elements.put(key, elem);
        return elem;
    }

    public JsonArray createJsonArray(String key) {
        JsonArray elem = new JsonArray(this);
        elements.put(key, elem);
        return elem;
    }

    public void addElement(String key, JsonElement elem) {
        elements.put(key, elem);
    }

    public Map<String, JsonElement> getElements() {
        return elements;
    }
}
