/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.sync.Synchronizer;

/**
 * A test implementation of Synchronizer which allows keeping track about
 * the methods called by the configuration.
 *
 * @version $Id: SynchronizerTestImpl.java 1571735 2014-02-25 16:37:52Z ggregory $
 */
public class SynchronizerTestImpl implements Synchronizer
{
    /** A buffer for registering the methods invoked by clients. */
    private final StringBuilder methods = new StringBuilder();

    /**
     * {@inheritDoc} Registers this invocation.
     */
    @Override
    public void beginRead()
    {
        append(Methods.BEGIN_READ);
    }

    /**
     * {@inheritDoc} Registers this invocation.
     */
    @Override
    public void endRead()
    {
        append(Methods.END_READ);
    }

    /**
     * {@inheritDoc} Registers this invocation.
     */
    @Override
    public void beginWrite()
    {
        append(Methods.BEGIN_WRITE);
    }

    /**
     * {@inheritDoc} Registers this invocation.
     */
    @Override
    public void endWrite()
    {
        append(Methods.END_WRITE);
    }

    /**
     * Verifies that the passed in methods were called in this order.
     *
     * @param expMethods the expected methods
     */
    public void verify(Methods... expMethods)
    {
        assertEquals("Wrong methods invoked",
                constructExpectedMethods(expMethods), methods.toString());
    }

    /**
     * Verifies that the specified methods were called at the beginning of
     * the interaction with the synchronizer.
     *
     * @param expMethods the expected methods
     */
    public void verifyStart(Methods... expMethods)
    {
        assertTrue("Wrong methods at start: " + methods, methods.toString()
                .startsWith(constructExpectedMethods(expMethods)));
    }

    /**
     * Verifies that the specified methods were called at the end of the
     * interaction with the synchronizer.
     *
     * @param expMethods the expected methods
     */
    public void verifyEnd(Methods... expMethods)
    {
        assertTrue("Wrong methods at start: " + methods, methods.toString()
                .endsWith(constructExpectedMethods(expMethods)));
    }

    /**
     * Verifies that the specified sequence of methods was called somewhere in
     * the interaction with the synchronizer.
     *
     * @param expMethods the expected methods
     */
    public void verifyContains(Methods... expMethods)
    {
        assertTrue("Expected methods not found: " + methods, methods.toString()
                .indexOf(constructExpectedMethods(expMethods)) >= 0);
    }

    /**
     * Clears the methods recorded so far.
     */
    public void clear()
    {
        methods.setLength(0);
    }

    /**
     * Generates a string with expected methods from the given array.
     *
     * @param expMethods the array with expected methods
     * @return a corresponding string representation
     */
    private String constructExpectedMethods(Methods... expMethods)
    {
        StringBuilder buf = new StringBuilder();
        for (Methods m : expMethods)
        {
            buf.append(m);
        }
        return buf.toString();
    }

    /**
     * Adds a method name to the internal buffer. Called by all interface
     * methods.
     *
     * @param m the method that was invoked
     */
    private void append(Methods m)
    {
        methods.append(m);
    }

    /**
     * An enumeration with the methods of the Synchronizer which can be called.
     */
    public static enum Methods
    {
        BEGIN_READ, END_READ, BEGIN_WRITE, END_WRITE
    }
}
