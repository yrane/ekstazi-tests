package org.apache.commons.configuration;

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

import static org.junit.Assert.assertEquals;

import org.apache.commons.configuration.io.FileHandler;
import org.junit.Test;

/**
 * test if properties that contain a "=" will be loaded correctly.
 *
 * @version $Id: TestEqualsProperty.java 1571738 2014-02-25 16:42:18Z ggregory $
 */
public class TestEqualsProperty
{
    /** The File that we test with */
    private final String testProperties = ConfigurationAssert.getTestFile("test.properties").getAbsolutePath();

    @Test
    public void testEquals() throws Exception
    {
        PropertiesConfiguration conf = new PropertiesConfiguration();
        FileHandler handler = new FileHandler(conf);
        handler.setFileName(testProperties);
        handler.load();

        String equals = conf.getString("test.equals");
        assertEquals("value=one", equals);
    }
}
