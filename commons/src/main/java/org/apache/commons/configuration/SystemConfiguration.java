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

import java.util.Iterator;

import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.io.FileHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A configuration based on the system properties.
 *
 * @author Emmanuel Bourg
 * @version $Id: SystemConfiguration.java 1554634 2014-01-01 16:41:48Z oheger $
 * @since 1.1
 */
public class SystemConfiguration extends MapConfiguration
{
    /** The logger. */
    private static Log log = LogFactory.getLog(SystemConfiguration.class);

    /**
     * Create a Configuration based on the system properties.
     *
     * @see System#getProperties
     */
    public SystemConfiguration()
    {
        super(System.getProperties());
    }

    /**
     * Sets system properties from a file specified by its file name. This is
     * just a short cut for {@code setSystemProperties(null, fileName)}.
     *
     * @param fileName The name of the property file.
     * @throws Exception if an error occurs.
     * @since 1.6
     */
    public static void setSystemProperties(String fileName)
            throws ConfigurationException
    {
        setSystemProperties(null, fileName);
    }

    /**
     * Sets system properties from a file specified using its base path and
     * file name. The file can either be a properties file or an XML properties
     * file. It is loaded, and all properties it contains are added to system
     * properties.
     *
     * @param basePath The base path to look for the property file.
     * @param fileName The name of the property file.
     * @throws ConfigurationException if an error occurs.
     * @since 1.6
     */
    public static void setSystemProperties(String basePath, String fileName)
            throws ConfigurationException
    {
        FileBasedConfiguration config =
                fileName.endsWith(".xml") ? new XMLPropertiesConfiguration()
                        : new PropertiesConfiguration();

        FileHandler handler = new FileHandler(config);
        handler.setBasePath(basePath);
        handler.setFileName(fileName);
        handler.load();
        setSystemProperties(config);
    }

    /**
     * Set System properties from a configuration object.
     * @param systemConfig The configuration containing the properties to be set.
     * @since 1.6
     */
    public static void setSystemProperties(Configuration systemConfig)
    {
        Iterator<String> iter = systemConfig.getKeys();
        while (iter.hasNext())
        {
            String key = iter.next();
            String value = (String) systemConfig.getProperty(key);
            if (log.isDebugEnabled())
            {
                log.debug("Setting system property " + key + " to " + value);
            }
            System.setProperty(key, value);
        }
    }
}
