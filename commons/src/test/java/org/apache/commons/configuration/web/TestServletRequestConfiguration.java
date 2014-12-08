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

package org.apache.commons.configuration.web;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationMap;
import org.apache.commons.configuration.TestAbstractConfiguration;
import org.apache.commons.configuration.convert.DefaultListDelimiterHandler;
import org.junit.Test;

import com.mockobjects.servlet.MockHttpServletRequest;

/**
 * Test case for the {@link ServletRequestConfiguration} class.
 *
 * @author Emmanuel Bourg
 * @version $Id: TestServletRequestConfiguration.java 1512796 2013-08-10 16:44:04Z oheger $
 */
public class TestServletRequestConfiguration extends TestAbstractConfiguration
{
    @Override
    protected AbstractConfiguration getConfiguration()
    {
        final Configuration configuration = new BaseConfiguration();
        configuration.setProperty("key1", "value1");
        configuration.setProperty("key2", "value2");
        configuration.addProperty("list", "value1");
        configuration.addProperty("list", "value2");
        configuration.addProperty("listesc", "value1\\,value2");

        return createConfiguration(configuration);
    }

    @Override
    protected AbstractConfiguration getEmptyConfiguration()
    {
        ServletRequest request = new MockHttpServletRequest()
        {
            @Override
            public String getParameter(String key)
            {
                return null;
            }

            @Override
            public Map<?, ?> getParameterMap()
            {
                return new HashMap<Object, Object>();
            }
        };

        return new ServletRequestConfiguration(request);
    }

    /**
     * Returns a new servlet request configuration that is backed by the passed
     * in configuration.
     *
     * @param base the configuration with the underlying values
     * @return the servlet request configuration
     */
    private ServletRequestConfiguration createConfiguration(final Configuration base)
    {
        ServletRequest request = new MockHttpServletRequest()
        {
            @Override
            public String[] getParameterValues(String key)
            {
                return base.getStringArray(key);
            }

            @Override
            public Map<?, ?> getParameterMap()
            {
                return new ConfigurationMap(base);
            }
        };

        ServletRequestConfiguration config = new ServletRequestConfiguration(request);
        config.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        return config;
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    public void testAddPropertyDirect()
    {
        super.testAddPropertyDirect();
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    public void testClearProperty()
    {
        super.testClearProperty();
    }

    /**
     * Tests a list with elements that contain an escaped list delimiter.
     */
    @Test
    public void testListWithEscapedElements()
    {
        String[] values = { "test1", "test2\\,test3", "test4\\,test5" };
        String listKey = "test.list";

        BaseConfiguration config = new BaseConfiguration();
        config.addProperty(listKey, values);

        assertEquals("Wrong number of list elements", values.length, config.getList(listKey).size());

        Configuration c = createConfiguration(config);
        List<?> v = c.getList(listKey);

        assertEquals("Wrong number of elements in list", values.length, v.size());

        for (int i = 0; i < values.length; i++)
        {
            assertEquals("Wrong value at index " + i, values[i].replaceAll("\\\\", ""), v.get(i));
        }
    }
}
