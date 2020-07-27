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
 * JSON abstract container of JSON elements, e.g. JSON array and JSON object types.
 */
public abstract class JsonContainer extends JsonElement {

    /**
     * Constructor.
     * @param parent Parent JsonElement.
     */
    public JsonContainer(JsonElement parent) {
        super(parent);
    }

    /**
     * Convert JsonElement object to JsonContainer.
     * @return Self.
     */
    public JsonContainer toJsonContainer() {
        return this;
    }

    /**
     * Get an amount of container elements.
     * @return Int value of container elements amount.
     */
    public abstract int size();

    /**
     * Check if the container is empty.
     * @return TRUE if container is empty.
     */
    public abstract boolean isEmpty();
}
