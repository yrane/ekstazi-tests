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
import static org.junit.Assert.assertTrue;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.configuration.builder.fluent.DatabaseBuilderParameters;
import org.apache.commons.configuration.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration.ex.ConfigurationException;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for database stored configurations.  Note, when running this Unit
 * Test in Eclipse it sometimes takes a couple tries. Otherwise you may get
 * database is already in use by another process errors.
 *
 * @version $Id: TestDatabaseConfiguration.java 1554634 2014-01-01 16:41:48Z oheger $
 */
public class TestDatabaseConfiguration
{
    /** Constant for another configuration name. */
    private static final String CONFIG_NAME2 = "anotherTestConfig";

    /** An error listener for testing whether internal errors occurred.*/
    private ConfigurationErrorListenerImpl listener;

    /** The test helper. */
    private DatabaseConfigurationTestHelper helper;

    @Before
    public void setUp() throws Exception
    {
        /*
         * Thread.sleep may or may not help with the database is already in
         * use exception.
         */
        //Thread.sleep(1000);

        // set up the datasource

        helper = new DatabaseConfigurationTestHelper();
        helper.setUp();
    }

    @After
    public void tearDown() throws Exception
    {
        // if an error listener is defined, we check whether an error occurred
        if(listener != null)
        {
            assertEquals("An internal error occurred", 0, listener.getErrorCount());
        }
        helper.tearDown();
    }

    /**
     * Creates a database configuration with default values.
     *
     * @return the configuration
     * @throws ConfigurationException if an error occurs
     */
    private PotentialErrorDatabaseConfiguration setUpConfig()
            throws ConfigurationException
    {
        return helper.setUpConfig(PotentialErrorDatabaseConfiguration.class);
    }

    /**
     * Creates an error listener and adds it to the specified configuration.
     *
     * @param config the configuration
     */
    private void setUpErrorListener(PotentialErrorDatabaseConfiguration config)
    {
        // remove log listener to avoid exception longs
        config.removeErrorListener(config.getErrorListeners().iterator().next());
        listener = new ConfigurationErrorListenerImpl();
        config.addErrorListener(listener);
        config.failOnConnect = true;
    }

    /**
     * Prepares a test for a database error. Sets up a config and registers an
     * error listener.
     *
     * @return the initialized configuration
     * @throws ConfigurationException if an error occurs
     */
    private PotentialErrorDatabaseConfiguration setUpErrorConfig()
            throws ConfigurationException
    {
        PotentialErrorDatabaseConfiguration config = setUpConfig();
        setUpErrorListener(config);
        return config;
    }

    /**
     * Checks the error listener for an expected error. The properties of the
     * error event will be compared with the expected values.
     *
     * @param type the expected type of the error event
     * @param key the expected property key
     * @param value the expected property value
     */
    private void checkErrorListener(int type, String key, Object value)
    {
        listener.verify(type, key, value);
        assertTrue(
                "Wrong event source",
                listener.getLastEvent().getSource() instanceof DatabaseConfiguration);
        assertTrue("Wrong exception",
                listener.getLastEvent().getCause() instanceof SQLException);
        listener = null; // mark as checked
    }

    @Test
    public void testAddPropertyDirectSingle() throws ConfigurationException
    {
        DatabaseConfiguration config = helper.setUpConfig();
        config.addPropertyDirect("key", "value");

        assertTrue("missing property", config.containsKey("key"));
    }

    /**
     * Tests whether a commit is performed after a property was added.
     */
    @Test
    public void testAddPropertyDirectCommit() throws ConfigurationException
    {
        helper.setAutoCommit(false);
        DatabaseConfiguration config = helper.setUpConfig();
        config.addPropertyDirect("key", "value");
        assertTrue("missing property", config.containsKey("key"));
    }

    @Test
    public void testAddPropertyDirectMultiple() throws ConfigurationException
    {
        DatabaseConfiguration config = helper.setUpMultiConfig();
        config.addPropertyDirect("key", "value");

        assertTrue("missing property", config.containsKey("key"));
    }

    @Test
    public void testAddNonStringProperty() throws ConfigurationException
    {
        DatabaseConfiguration config = helper.setUpConfig();
        config.addPropertyDirect("boolean", Boolean.TRUE);

        assertTrue("missing property", config.containsKey("boolean"));
    }

    @Test
    public void testGetPropertyDirectSingle() throws ConfigurationException
    {
        Configuration config = setUpConfig();

        assertEquals("property1", "value1", config.getProperty("key1"));
        assertEquals("property2", "value2", config.getProperty("key2"));
        assertEquals("unknown property", null, config.getProperty("key3"));
    }

    @Test
    public void testGetPropertyDirectMultiple() throws ConfigurationException
    {
        Configuration config = helper.setUpMultiConfig();

        assertEquals("property1", "value1", config.getProperty("key1"));
        assertEquals("property2", "value2", config.getProperty("key2"));
        assertEquals("unknown property", null, config.getProperty("key3"));
    }

    @Test
    public void testClearPropertySingle() throws ConfigurationException
    {
        Configuration config = helper.setUpConfig();
        config.clearProperty("key1");

        assertFalse("property not cleared", config.containsKey("key1"));
    }

    @Test
    public void testClearPropertyMultiple() throws ConfigurationException
    {
        Configuration config = helper.setUpMultiConfig();
        config.clearProperty("key1");

        assertFalse("property not cleared", config.containsKey("key1"));
    }

    /**
     * Tests that another configuration is not affected when clearing
     * properties.
     */
    @Test
    public void testClearPropertyMultipleOtherConfig() throws ConfigurationException
    {
        DatabaseConfiguration config = helper.setUpMultiConfig();
        DatabaseConfiguration config2 =
                helper.setUpMultiConfig(DatabaseConfiguration.class,
                        CONFIG_NAME2);
        config2.addProperty("key1", "some test");
        config.clearProperty("key1");
        assertFalse("property not cleared", config.containsKey("key1"));
        assertTrue("Property cleared in other config", config2
                .containsKey("key1"));
    }

    /**
     * Tests whether a commit is performed after a property was cleared.
     */
    @Test
    public void testClearPropertyCommit() throws ConfigurationException
    {
        helper.setAutoCommit(false);
        Configuration config = helper.setUpConfig();
        config.clearProperty("key1");
        assertFalse("property not cleared", config.containsKey("key1"));
    }

    @Test
    public void testClearSingle() throws ConfigurationException
    {
        Configuration config = helper.setUpConfig();
        config.clear();

        assertTrue("configuration is not cleared", config.isEmpty());
    }

    @Test
    public void testClearMultiple() throws ConfigurationException
    {
        Configuration config = helper.setUpMultiConfig();
        config.clear();

        assertTrue("configuration is not cleared", config.isEmpty());
    }

    /**
     * Tests whether a commit is performed after a clear operation.
     */
    @Test
    public void testClearCommit() throws ConfigurationException
    {
        helper.setAutoCommit(false);
        Configuration config = helper.setUpConfig();
        config.clear();
        assertTrue("configuration is not cleared", config.isEmpty());
    }

    @Test
    public void testGetKeysSingle() throws ConfigurationException
    {
        Configuration config = setUpConfig();
        Iterator<String> it = config.getKeys();

        assertEquals("1st key", "key1", it.next());
        assertEquals("2nd key", "key2", it.next());
    }

    @Test
    public void testGetKeysMultiple() throws ConfigurationException
    {
        Configuration config = helper.setUpMultiConfig();
        Iterator<String> it = config.getKeys();

        assertEquals("1st key", "key1", it.next());
        assertEquals("2nd key", "key2", it.next());
    }

    @Test
    public void testContainsKeySingle() throws ConfigurationException
    {
        Configuration config = setUpConfig();
        assertTrue("missing key1", config.containsKey("key1"));
        assertTrue("missing key2", config.containsKey("key2"));
    }

    @Test
    public void testContainsKeyMultiple() throws ConfigurationException
    {
        Configuration config = helper.setUpMultiConfig();
        assertTrue("missing key1", config.containsKey("key1"));
        assertTrue("missing key2", config.containsKey("key2"));
    }

    @Test
    public void testIsEmptySingle() throws ConfigurationException
    {
        Configuration config1 = setUpConfig();
        assertFalse("The configuration is empty", config1.isEmpty());
    }

    @Test
    public void testIsEmptyMultiple() throws ConfigurationException
    {
        Configuration config1 = helper.setUpMultiConfig();
        assertFalse("The configuration named 'test' is empty", config1.isEmpty());

        Configuration config2 = helper.setUpMultiConfig(DatabaseConfiguration.class, "testIsEmpty");
        assertTrue("The configuration named 'testIsEmpty' is not empty", config2.isEmpty());
    }

    @Test
    public void testGetList() throws ConfigurationException
    {
        DatabaseBuilderParameters params = helper.setUpDefaultParameters().setTable("configurationList");
        Configuration config1 = helper.createConfig(DatabaseConfiguration.class, params);
        List<Object> list = config1.getList("key3");
        assertEquals(3,list.size());
    }

    @Test
    public void testGetKeys() throws ConfigurationException
    {
        DatabaseBuilderParameters params = helper.setUpDefaultParameters().setTable("configurationList");
        Configuration config1 = helper.createConfig(DatabaseConfiguration.class, params);
        Iterator<String> i = config1.getKeys();
        assertTrue(i.hasNext());
        Object key = i.next();
        assertEquals("key3",key.toString());
        assertFalse(i.hasNext());
    }

    @Test
    public void testClearSubset() throws ConfigurationException
    {
        Configuration config = setUpConfig();

        Configuration subset = config.subset("key1");
        subset.clear();

        assertTrue("the subset is not empty", subset.isEmpty());
        assertFalse("the parent configuration is empty", config.isEmpty());
    }

    /**
     * Tests whether the configuration has already an error listener registered
     * that is used for logging.
     */
    @Test
    public void testLogErrorListener() throws ConfigurationException
    {
        DatabaseConfiguration config = helper.setUpConfig();
        assertEquals("No error listener registered", 1, config.getErrorListeners().size());
    }

    /**
     * Tests handling of errors in getProperty().
     */
    @Test
    public void testGetPropertyError() throws ConfigurationException
    {
        setUpErrorConfig().getProperty("key1");
        checkErrorListener(AbstractConfiguration.EVENT_READ_PROPERTY, "key1", null);
    }

    /**
     * Tests handling of errors in addPropertyDirect().
     */
    @Test
    public void testAddPropertyError() throws ConfigurationException
    {
        setUpErrorConfig().addProperty("key1", "value");
        checkErrorListener(AbstractConfiguration.EVENT_ADD_PROPERTY, "key1", "value");
    }

    /**
     * Tests handling of errors in isEmpty().
     */
    @Test
    public void testIsEmptyError() throws ConfigurationException
    {
        assertTrue("Wrong return value for failure", setUpErrorConfig().isEmpty());
        checkErrorListener(AbstractConfiguration.EVENT_READ_PROPERTY, null, null);
    }

    /**
     * Tests handling of errors in containsKey().
     */
    @Test
    public void testContainsKeyError() throws ConfigurationException
    {
        assertFalse("Wrong return value for failure", setUpErrorConfig().containsKey("key1"));
        checkErrorListener(AbstractConfiguration.EVENT_READ_PROPERTY, "key1", null);
    }

    /**
     * Tests handling of errors in clearProperty().
     */
    @Test
    public void testClearPropertyError() throws ConfigurationException
    {
        setUpErrorConfig().clearProperty("key1");
        checkErrorListener(AbstractConfiguration.EVENT_CLEAR_PROPERTY, "key1", null);
    }

    /**
     * Tests handling of errors in clear().
     */
    @Test
    public void testClearError() throws ConfigurationException
    {
        setUpErrorConfig().clear();
        checkErrorListener(AbstractConfiguration.EVENT_CLEAR, null, null);
    }

    /**
     * Tests handling of errors in getKeys().
     */
    @Test
    public void testGetKeysError() throws ConfigurationException
    {
        Iterator<String> it = setUpErrorConfig().getKeys();
        checkErrorListener(AbstractConfiguration.EVENT_READ_PROPERTY, null, null);
        assertFalse("Iteration is not empty", it.hasNext());
    }

    /**
     * Tests obtaining a property as list whose value contains the list
     * delimiter. Multiple values should be returned.
     */
    @Test
    public void testGetListWithDelimiter() throws ConfigurationException
    {
        DatabaseConfiguration config = setUpConfig();
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(';'));
        List<Object> values = config.getList("keyMulti");
        assertEquals("Wrong number of list elements", 3, values.size());
        assertEquals("Wrong list element 0", "a", values.get(0));
        assertEquals("Wrong list element 2", "c", values.get(2));
    }

    /**
     * Tests obtaining a property whose value contains the list delimiter when
     * delimiter parsing is disabled.
     */
    @Test
    public void testGetListWithDelimiterParsingDisabled() throws ConfigurationException
    {
        DatabaseConfiguration config = setUpConfig();
        assertEquals("Wrong value of property", "a;b;c", config.getString("keyMulti"));
    }

    /**
     * Tests adding a property containing the list delimiter. When this property
     * is queried multiple values should be returned.
     */
    @Test
    public void testAddWithDelimiter() throws ConfigurationException
    {
        DatabaseConfiguration config = setUpConfig();
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(';'));
        config.addProperty("keyList", "1;2;3");
        String[] values = config.getStringArray("keyList");
        assertEquals("Wrong number of property values", 3, values.length);
        assertEquals("Wrong value at index 1", "2", values[1]);
    }

    /**
     * Tests setProperty() if the property value contains the list delimiter.
     */
    @Test
    public void testSetPropertyWithDelimiter() throws ConfigurationException
    {
        DatabaseConfiguration config = helper.setUpMultiConfig();
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(';'));
        config.setProperty("keyList", "1;2;3");
        String[] values = config.getStringArray("keyList");
        assertEquals("Wrong number of property values", 3, values.length);
        assertEquals("Wrong value at index 1", "2", values[1]);
    }

    /**
     * Tests whether a CLOB as a property value is handled correctly.
     */
    @Test
    public void testExtractPropertyValueCLOB() throws ConfigurationException,
            SQLException
    {
        ResultSet rs = EasyMock.createMock(ResultSet.class);
        Clob clob = EasyMock.createMock(Clob.class);
        final String content = "This is the content of the test CLOB!";
        EasyMock.expect(rs.getObject(DatabaseConfigurationTestHelper.COL_VALUE))
                .andReturn(clob);
        EasyMock.expect(clob.length())
                .andReturn(Long.valueOf(content.length()));
        EasyMock.expect(clob.getSubString(1, content.length())).andReturn(
                content);
        EasyMock.replay(rs, clob);
        DatabaseConfiguration config = helper.setUpConfig();
        assertEquals("Wrong extracted value", content,
                config.extractPropertyValue(rs));
        EasyMock.verify(rs, clob);
    }

    /**
     * Tests whether an empty CLOB is correctly handled by
     * extractPropertyValue().
     */
    @Test
    public void testExtractPropertyValueCLOBEmpty()
            throws ConfigurationException, SQLException
    {
        ResultSet rs = EasyMock.createMock(ResultSet.class);
        Clob clob = EasyMock.createMock(Clob.class);
        EasyMock.expect(rs.getObject(DatabaseConfigurationTestHelper.COL_VALUE))
                .andReturn(clob);
        EasyMock.expect(clob.length()).andReturn(0L);
        EasyMock.replay(rs, clob);
        DatabaseConfiguration config = helper.setUpConfig();
        assertEquals("Wrong extracted value", "",
                config.extractPropertyValue(rs));
        EasyMock.verify(rs, clob);
    }

    /**
     * A specialized database configuration implementation that can be
     * configured to throw an exception when obtaining a connection. This way
     * database exceptions can be simulated.
     */
    public static class PotentialErrorDatabaseConfiguration extends DatabaseConfiguration
    {
        /** A flag whether a getConnection() call should fail. */
        boolean failOnConnect;

        @Override
        public DataSource getDatasource()
        {
            if (failOnConnect)
            {
                DataSource ds = EasyMock.createMock(DataSource.class);
                try
                {
                    EasyMock.expect(ds.getConnection()).andThrow(
                            new SQLException("Simulated DB error"));
                }
                catch (SQLException e)
                {
                    // should not happen
                    throw new AssertionError("Unexpected exception!");
                }
                EasyMock.replay(ds);
                return ds;
            }
            return super.getDatasource();
        }
    }
}
