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

package uk.ltd.getahead.dwr.create.test;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Bram Smeets
 */
public class DummyDataManager extends DummyManagerParent {
    public void doTest() {
        // do nothing
    }

    public void doTest(String s) {
        // do nothing
    }

    public void debugger() {
        // do nothing
    }

    public String testArguments(String s, int i, long l, boolean b, float f, List list, Map map, HttpServletRequest request) {
        return "testString";
    }

    public void doException() throws Exception {
        throw new Exception("testing");
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj != null && obj instanceof DummyDataManager) {
            DummyDataManager mgr = (DummyDataManager) obj;
            return true;
        }
        return false;
    }
}