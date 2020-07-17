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

/**
 * JSON object type extending JSON abstract container.
 */
public final class JsonObject extends JsonContainer {

    /**
     * Map of String key and JsonElement value pairs.
     */
    private final Map<String, JsonElement> elements;

    /**
     * Constructor.
     * @param parent Parent JsonElement.
     */
    public JsonObject(JsonElement parent) {
        super(parent);
        elements = new TreeMap<>();
    }

    /**
     * Convert object to a string format.
     * @param sb StringBuilder containing a string representation of JSON object.
     */
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

    /**
     * Convert object to a string format with indention.
     * @param sb StringBuilder containing a string representation of JSON object.
     * @param indent Indention value.
     */
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

    /**
     * Convert the element to a JsonObject.
     * @return Self.
     */
    public JsonObject toJsonObject() {
        return this;
    }

    /**
     * Get an amount of object elements.
     * @return Int value of object elements amount.
     */
    public int size() {
        return elements.size();
    }

    /**
     * Check if object contains a specified key.
     * @param key String value of a key.
     * @return TRUE if object contains a specified key.
     */
    public boolean containsKey(String key) {
        return elements.containsKey(key);
    }

    /**
     * Get element by a specified key.
     * @param key String value of a key.
     * @return JsonElement with the specified key.
     */
    public JsonElement getElementByKey(String key) {
        return elements.get(key);
    }

    /**
     * Check if the object is empty.
     * @return TRUE if object is empty.
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Create JsonString element and add it to object elements.
     * @param key String value of a key.
     * @param value String value.
     * @return Created JsonString object.
     */
    public JsonString createJsonString(String key, String value) {
        JsonString elem = new JsonString(this, value);
        elements.put(key, elem);
        return elem;
    }

    /**
     * Create JsonNumber element and add it to object elements.
     * @param key String value of a key.
     * @param value Double value of a number.
     * @return Created JsonNumber object.
     */
    public JsonNumber createJsonNumber(String key, double value) {
        JsonNumber elem = new JsonNumber(this, value);
        elements.put(key, elem);
        return elem;
    }

    /**
     * Create JsonBoolean element and add it to object elements.
     * @param key String value of a key.
     * @param value Boolean value.
     * @return Created JsonBoolean object.
     */
    public JsonBoolean createJsonBoolean(String key, boolean value) {
        JsonBoolean elem = new JsonBoolean(this, value);
        elements.put(key, elem);
        return elem;
    }

    /**
     * Create JsonNull element and add it to object elements.
     * @param key String value of a key.
     * @return Created JsonNull object.
     */
    public JsonNull createJsonNull(String key) {
        JsonNull elem = new JsonNull(this);
        elements.put(key, elem);
        return elem;
    }

    /**
     * Create JsonObject element and add it to object elements.
     * @param key String value of a key.
     * @return Created JsonObject object.
     */
    public JsonObject createJsonObject(String key) {
        JsonObject elem = new JsonObject(this);
        elements.put(key, elem);
        return elem;
    }

    /**
     * Create JsonArray element and add it to object elements.
     * @param key String value of a key.
     * @return Created JsonArray object.
     */
    public JsonArray createJsonArray(String key) {
        JsonArray elem = new JsonArray(this);
        elements.put(key, elem);
        return elem;
    }

    /**
     * Add the specified key and JsonElement element value to object elements.
     * @param elem JsonElement element.
     */
    public void addElement(String key, JsonElement elem) {
        elements.put(key, elem);
    }

    /**
     * Get all the object elements.
     * @return Map of object key-value pairs.
     */
    public Map<String, JsonElement> getElements() {
        return elements;
    }
}
