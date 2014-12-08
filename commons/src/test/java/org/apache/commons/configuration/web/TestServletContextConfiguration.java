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

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.TestAbstractConfiguration;
import org.apache.commons.configuration.convert.DefaultListDelimiterHandler;
import org.junit.Test;

import com.mockobjects.servlet.MockServletConfig;
import com.mockobjects.servlet.MockServletContext;

/**
 * Test case for the {@link ServletContextConfiguration} class.
 *
 * @author Emmanuel Bourg
 * @version $Id: TestServletContextConfiguration.java 1512796 2013-08-10 16:44:04Z oheger $
 */
public class TestServletContextConfiguration extends TestAbstractConfiguration
{
    @Override
    protected AbstractConfiguration getConfiguration()
    {
        final Properties parameters = new Properties();
        parameters.setProperty("key1", "value1");
        parameters.setProperty("key2", "value2");
        parameters.setProperty("list", "value1, value2");
        parameters.setProperty("listesc", "value1\\,value2");

        // create a servlet context
        ServletContext context = new MockServletContext()
        {
            @Override
            public String getInitParameter(String key)
            {
                return parameters.getProperty(key);
            }

            @Override
            public Enumeration<?> getInitParameterNames()
            {
                return parameters.keys();
            }
        };

        // create a servlet config
        final MockServletConfig config = new MockServletConfig();
        config.setServletContext(context);

        // create a servlet
        Servlet servlet = new HttpServlet()
        {
            /**
             * Serial version UID.
             */
            private static final long serialVersionUID = 1L;

            @Override
            public ServletConfig getServletConfig()
            {
                return config;
            }
        };

        ServletContextConfiguration resultConfig = new ServletContextConfiguration(servlet);
        resultConfig.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        return resultConfig;
    }

    @Override
    protected AbstractConfiguration getEmptyConfiguration()
    {
        // create a servlet context
        ServletContext context = new MockServletContext()
        {
            @Override
            public Enumeration<?> getInitParameterNames()
            {
                return new Properties().keys();
            }
        };

        return new ServletContextConfiguration(context);
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
}
