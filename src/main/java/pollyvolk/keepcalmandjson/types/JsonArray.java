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

import java.util.ArrayList;
import java.util.List;

/**
 * JSON array type extending JSON abstract container.
 */
public final class JsonArray extends JsonContainer {

    /**
     * List of JSON array elements.
     */
    private final List<JsonElement> elements;

    /**
     * Constructor.
     * @param parent Parent JsonElement.
     */
    public JsonArray(JsonElement parent) {
        super(parent);
        elements = new ArrayList<JsonElement>();
    }

    /**
     * Convert array to a string format.
     * @param sb StringBuilder containing a string representation of JSON array.
     */
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

    /**
     * Convert array to a string format with indention.
     * @param sb StringBuilder containing a string representation of JSON array.
     * @param indent Indention value.
     */
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

    /**
     * Convert JSON element to JSON array type.
     * @return JsonArray object.
     */
    public JsonArray toJsonArray() {
        return this;
    }

    /**
     * Get an amount of array elements.
     * @return Int value of array elements amount.
     */
    public int size() {
        return elements.size();
    }

    /**
     * Get the element at the specified position.
     * @param index Index of the element.
     * @return JsonElement at the index position.
     */
    public JsonElement getElementAt(int index) {
        return elements.get(index);
    }

    /**
     * Check if the array is empty.
     * @return TRUE if array is empty.
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Create JsonString element and add it to the array.
     * @param value String value.
     * @return Created JsonString object.
     */
    public JsonString createStringElement(String value) {
        JsonString elem = new JsonString(this, value);
        elements.add(elem);
        return elem;
    }

    /**
     * Create JsonNumber element and add it to the array.
     * @param value Double value of number.
     * @return Created JsonNumber object.
     */
    public JsonNumber createNumberElement(double value) {
        JsonNumber elem = new JsonNumber(this, value);
        elements.add(elem);
        return elem;
    }

    /**
     * Create JsonBoolean element and add it to the array.
     * @param value Boolean value.
     * @return Created JsonBoolean object.
     */
    public JsonBoolean createBooleanElement(boolean value) {
        JsonBoolean elem = new JsonBoolean(this, value);
        elements.add(elem);
        return elem;
    }

    /**
     * Create JsonNull element and add it to the array.
     * @return Created JsonNull object.
     */
    public JsonNull createNullElement() {
        JsonNull elem = new JsonNull(this);
        elements.add(elem);
        return elem;
    }

    /**
     * Create JsonObject element and add it to the array.
     * @return Created empty JsonObject object.
     */
    public JsonObject createObjectElement() {
        JsonObject elem = new JsonObject(this);
        elements.add(elem);
        return elem;
    }

    /**
     * Create JsonArray element and add it to the array.
     * @return Created empty JsonArray object.
     */
    public JsonArray createArrayElement() {
        JsonArray elem = new JsonArray(this);
        elements.add(elem);
        return elem;
    }

    /**
     * Add the specified JsonElement element to the array and set current array as its parent.
     * @param elem JsonElement element.
     */
    public void addArrayElement(JsonElement elem) {
        elem.setParent(this);
        elements.add(elem);
    }

    /**
     * Add the specified JsonElement element to the array.
     * @param elem JsonElement element.
     */
    public void addElement(JsonElement elem) {
        elements.add(elem);
    }

    /**
     * Get all the array elements.
     * @return  List of array elements.
     */
    public List<JsonElement> getArrayElements() {
        return elements;
    }
}

