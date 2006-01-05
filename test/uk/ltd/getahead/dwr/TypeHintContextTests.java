/*
 * Copyright 2005 Joe Walker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ltd.getahead.dwr;

import junit.framework.TestCase;

/**
 * @author Bram Smeets
 */
public class TypeHintContextTests extends TestCase {
    private TypeHintContext ctx;

    protected void setUp() throws Exception {
        super.setUp();

        ctx = new TypeHintContext(getClass().getMethod("testConstructor", new Class[0]), -1);
    }

    public void testConstructor() {
        try {
            ctx = new TypeHintContext(null, -1);
            fail("an illegal argument exception was expected");
        } catch (IllegalArgumentException e) {
            // do nothing, was expected
        }
    }

    public void testToString() throws Exception {
        String s = ctx.toString();
        assertNotNull(s);

        // call it twice to get the cached value
        s = ctx.toString();
        assertNotNull(s);

        // also create a context with different parameters
        ctx = new TypeHintContext(getClass().getMethod("aComplexMethodSignature", new Class[] {
                String.class, Integer.class, Long.class}), -1);
        s = ctx.toString();
        assertNotNull(s);
    }

    public void testHashCode() {
        int hashCode = ctx.hashCode();
        assertFalse(hashCode == -1);
    }

    public void testEquals() throws Exception {
        assertFalse(ctx.equals(null));
        assertFalse(ctx.equals("Testing"));
        assertTrue(ctx.equals(ctx));

        TypeHintContext ctx2 = new TypeHintContext(getClass().getMethod("testConstructor", new Class[0]), -1);
        assertTrue(ctx.equals(ctx2));

        ctx2 = new TypeHintContext(getClass().getMethod("testConstructor", new Class[0]), 10);
        assertFalse(ctx.equals(ctx2));

        ctx2 = new TypeHintContext(getClass().getMethod("testEquals", new Class[0]), -1);
        assertFalse(ctx.equals(ctx2));
    }

    public String aComplexMethodSignature(String s, Integer i, Long l) {
        return "test";
    }
}