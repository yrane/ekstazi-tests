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
package org.apache.commons.configuration2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

/**
 * A test class which tests functionality related to immutable configurations.
 *
 * @version $Id: TestImmutableConfiguration.java 1624601 2014-09-12 18:04:36Z oheger $
 */
public class TestImmutableConfiguration
{
    /** Constant for the name of a test properties file. */
    private static final String TEST_FILE = "test.properties";

    /**
     * Tries to create an immutable configuration from a null object.
     */
    @Test(expected = NullPointerException.class)
    public void testUnmodifiableConfigurationNull()
    {
        ConfigurationUtils.unmodifiableConfiguration(null);
    }

    /**
     * Creates a test configuration object filled with properties.
     *
     * @return the test configuration
     * @throws ConfigurationException if an error occurs
     */
    private static PropertiesConfiguration createTestConfig()
            throws ConfigurationException
    {
        return new FileBasedConfigurationBuilder<PropertiesConfiguration>(
                PropertiesConfiguration.class).configure(
                new FileBasedBuilderParametersImpl().setFile(ConfigurationAssert
                        .getTestFile(TEST_FILE))).getConfiguration();
    }

    /**
     * Tests whether data can be accessed from an unmodifiable configuration.
     */
    @Test
    public void testUnmodifiableConfigurationAccess()
            throws ConfigurationException
    {
        Configuration confOrg = createTestConfig();
        ImmutableConfiguration conf =
                ConfigurationUtils.unmodifiableConfiguration(confOrg);
        assertFalse("Empty", conf.isEmpty());
        for (Iterator<String> it = confOrg.getKeys(); it.hasNext();)
        {
            String key = it.next();
            assertTrue("Key not contained: " + key, conf.containsKey(key));
            assertEquals("Wrong value for " + key, confOrg.getProperty(key),
                    conf.getProperty(key));
        }
    }

    /**
     * Tests different access methods for properties.
     */
    @Test
    public void testUnmodifiableConfigurationOtherTypes()
            throws ConfigurationException
    {
        ImmutableConfiguration conf =
                ConfigurationUtils
                        .unmodifiableConfiguration(createTestConfig());
        assertEquals("Wrong byte", (byte) 10, conf.getByte("test.byte"));
        assertEquals("Wrong boolean", true, conf.getBoolean("test.boolean"));
        assertEquals("Wrong double", 10.25, conf.getDouble("test.double"), .05);
        assertEquals("Wrong float", 20.25f, conf.getFloat("test.float"), .05);
        assertEquals("Wrong int", 10, conf.getInt("test.integer"));
        assertEquals("Wrong long", 1000000L, conf.getLong("test.long"));
        assertEquals("Wrong short", (short) 1, conf.getShort("test.short"));
    }

    /**
     * Obtains all keys from the given iteration.
     *
     * @param it the iterator
     * @return a set with all keys
     */
    private static Set<String> fetchKeys(Iterator<String> it)
    {
        Set<String> keys = new HashSet<String>();
        while (it.hasNext())
        {
            keys.add(it.next());
        }
        return keys;
    }

    /**
     * Tests an iteration over the keys of the immutable configuration.
     */
    @Test
    public void testUnmodifiableConfigurationIterate()
            throws ConfigurationException
    {
        Configuration confOrg = createTestConfig();
        ImmutableConfiguration conf =
                ConfigurationUtils.unmodifiableConfiguration(confOrg);
        assertEquals("Different keys", fetchKeys(confOrg.getKeys()),
                fetchKeys(conf.getKeys()));
    }

    /**
     * Tests that it is not possible to remove keys using the iterator.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableConfigurationIteratorRemove()
            throws ConfigurationException
    {
        ImmutableConfiguration conf =
                ConfigurationUtils
                        .unmodifiableConfiguration(createTestConfig());
        Iterator<String> it = conf.getKeys();
        it.next();
        it.remove();
    }

    /**
     * Tests whether an update of the original configuration is visible for the
     * immutable view.
     */
    @Test
    public void testUnmodifiableConfigurationLiveUpdate()
            throws ConfigurationException
    {
        Configuration confOrg = createTestConfig();
        ImmutableConfiguration conf =
                ConfigurationUtils.unmodifiableConfiguration(confOrg);
        final String key = "new.property";
        String value = "new value";
        confOrg.addProperty(key, value);
        assertEquals("Value not set", value, conf.getString(key));
    }

    /**
     * Tests that a cast to a mutable configuration is not possible.
     */
    @Test(expected = ClassCastException.class)
    public void testUnmodifiableConfigurationCast()
            throws ConfigurationException
    {
        ImmutableConfiguration conf =
                ConfigurationUtils
                        .unmodifiableConfiguration(createTestConfig());
        Configuration mutableConf = (Configuration) conf;
        mutableConf.clear();
    }

    /**
     * Tests whether an immutable subset can be queried.
     */
    @Test
    public void testImmutableSubset() throws ConfigurationException
    {
        ImmutableConfiguration conf =
                ConfigurationUtils
                        .unmodifiableConfiguration(createTestConfig());
        ImmutableConfiguration subset = conf.immutableSubset("test");
        assertFalse("No content", subset.isEmpty());
        assertEquals("Wrong value", 1000000, subset.getLong("long"));
    }

    /**
     * Tests whether an unmodifiable hierarchical configuration can be created.
     */
    @Test
    public void testUnmodifiableHierarchicalConfiguration()
    {
        HierarchicalConfiguration<?> conf = new BaseHierarchicalConfiguration();
        String key = "test";
        conf.addProperty(key, Boolean.TRUE);
        ImmutableHierarchicalConfiguration ihc =
                ConfigurationUtils.unmodifiableConfiguration(conf);
        assertTrue("Property not found", ihc.getBoolean(key));
        assertEquals("Wrong max index", 0, ihc.getMaxIndex(key));
    }
}
