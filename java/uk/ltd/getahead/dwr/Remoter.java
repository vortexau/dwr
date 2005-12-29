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


/**
 * The heart of DWR is a system to generate content from some requests.
 * This interface generates scripts and executes remote calls.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public interface Remoter
{
    /**
     * Generate some Javascript that forms an interface definition
     * @param scriptName The script to generate for
     * @param path The path of the requests. TODO: Think about this in terms of servlet spec
     * @return An interface javascript
     * @throws SecurityException
     */
    HttpResponse generateInterfaceScript(String scriptName, String path) throws SecurityException;

    /**
     * Execute a set of remote calls and generate a Javascript reply
     * @param calls The set of calls to execute
     * @return A reply page
     */
    HttpResponse execute(Calls calls);
}
