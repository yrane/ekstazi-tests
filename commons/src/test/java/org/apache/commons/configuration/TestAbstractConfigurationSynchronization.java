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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.configuration.SynchronizerTestImpl.Methods;
import org.apache.commons.configuration.io.FileHandler;
import org.apache.commons.configuration.sync.LockMode;
import org.apache.commons.configuration.sync.NoOpSynchronizer;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class for the synchronization capabilities of
 * {@code AbstractConfiguration}. This class mainly checks the collaboration
 * between a configuration object and its {@code Synchronizer}.
 *
 * @version $Id: TestAbstractConfigurationSynchronization.java 1486351 2013-05-25 19:09:41Z oheger $
 */
public class TestAbstractConfigurationSynchronization
{
    /** Constant for the test property accessed by all tests. */
    private static final String PROP = "configuration.loaded";

    /** The synchronizer used for testing. */
    private SynchronizerTestImpl sync;

    /** A test configuration. */
    private AbstractConfiguration config;

    @Before
    public void setUp() throws Exception
    {
        // any concrete class will do
        PropertiesConfiguration c = new PropertiesConfiguration();
        new FileHandler(c).load(ConfigurationAssert
                .getTestFile("test.properties"));
        sync = new SynchronizerTestImpl();
        c.setSynchronizer(sync);
        config = c;
    }

    /**
     * Tests the Synchronizer used by default.
     */
    @Test
    public void testDefaultSynchronizer()
    {
        assertSame("Wrong default synchronizer", NoOpSynchronizer.INSTANCE,
                new PropertiesConfiguration().getSynchronizer());
    }

    /**
     * Tests whether a read lock can be obtained.
     */
    @Test
    public void testLockRead()
    {
        config.lock(LockMode.READ);
        sync.verify(Methods.BEGIN_READ);
    }

    /**
     * Tests whether a write lock can be obtained.
     */
    @Test
    public void testLockWrite()
    {
        config.lock(LockMode.WRITE);
        sync.verify(Methods.BEGIN_WRITE);
    }

    /**
     * Tests lock() with a null argument.
     */
    @Test(expected = NullPointerException.class)
    public void testLockNull()
    {
        config.lock(null);
    }

    /**
     * Tests whether a read lock can be released.
     */
    @Test
    public void testUnlockRead()
    {
        config.unlock(LockMode.READ);
        sync.verify(Methods.END_READ);
    }

    /**
     * Tests whether a write lock can be released.
     */
    @Test
    public void testUnlockWrite()
    {
        config.unlock(LockMode.WRITE);
        sync.verify(Methods.END_WRITE);
    }

    /**
     * Tests the correct synchronization of addProperty().
     */
    @Test
    public void testAddPropertySynchronized()
    {
        config.addProperty(PROP, "of course");
        sync.verify(Methods.BEGIN_WRITE, Methods.END_WRITE);
    }

    /**
     * Tests the correct synchronization of setProperty().
     */
    @Test
    public void testSetPropertySynchronized()
    {
        config.setProperty(PROP, "yes");
        sync.verifyStart(Methods.BEGIN_WRITE);
        sync.verifyEnd(Methods.END_WRITE);
    }

    /**
     * Tests the correct synchronization of clearProperty().
     */
    @Test
    public void testClearPropertySynchronized()
    {
        config.clearProperty(PROP);
        sync.verify(Methods.BEGIN_WRITE, Methods.END_WRITE);
    }

    /**
     * Tests the correct synchronization of clear().
     */
    @Test
    public void testClearSynchronized()
    {
        config.clear();
        sync.verifyStart(Methods.BEGIN_WRITE);
        sync.verifyEnd(Methods.END_WRITE);
    }

    /**
     * Tests whether read access to properties is synchronized.
     */
    @Test
    public void testGetPropertySynchronized()
    {
        assertEquals("Wrong raw value", "true", config.getProperty(PROP));
        assertTrue("Wrong boolean value", config.getBoolean(PROP));
        sync.verify(Methods.BEGIN_READ, Methods.END_READ, Methods.BEGIN_READ,
                Methods.END_READ);
    }

    /**
     * Tests whether containsKey() is correctly synchronized.
     */
    @Test
    public void testContainsKeySychronized()
    {
        assertTrue("Wrong result", config.containsKey(PROP));
        sync.verify(Methods.BEGIN_READ, Methods.END_READ);
    }

    /**
     * Tests whether isEmpty() is correctly synchronized.
     */
    @Test
    public void testIsEmptySychronized()
    {
        assertFalse("Configuration is empty", config.isEmpty());
        sync.verify(Methods.BEGIN_READ, Methods.END_READ);
    }

    /**
     * Tests whether getKeys() is correctly synchronized.
     */
    @Test
    public void testGetKeysSychronized()
    {
        assertTrue("No keys", config.getKeys().hasNext());
        sync.verify(Methods.BEGIN_READ, Methods.END_READ);
    }

    /**
     * Tests whether getKeys(String prefix) is correctly synchronized.
     */
    @Test
    public void testGetKeysPrefixSynchronized()
    {
        config.getKeys("test");
        sync.verify(Methods.BEGIN_READ, Methods.END_READ);
    }

    /**
     * Tests synchronization of subset().
     */
    @Test
    public void testSubsetSynchronized()
    {
        AbstractConfiguration subset =
                (AbstractConfiguration) config.subset("configuration");
        sync.verify();
        assertEquals("Wrong synchronizer for subset",
                NoOpSynchronizer.INSTANCE, subset.getSynchronizer());
    }
}
