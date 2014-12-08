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
package org.apache.commons.configuration.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.apache.commons.configuration.ConfigurationAssert;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code ClasspathLocationStrategy}.
 *
 * @version $Id: TestClasspathLocationStrategy.java 1525405 2013-09-22 17:59:12Z oheger $
 */
public class TestClasspathLocationStrategy
{
    /** Constant for a test file name. */
    private static final String FILE_NAME = "test.xml";

    /** A mock for the file system. */
    private FileSystem fileSystem;

    /** The strategy to be tested. */
    private ClasspathLocationStrategy strategy;

    @Before
    public void setUp() throws Exception
    {
        fileSystem = EasyMock.createMock(FileSystem.class);
        EasyMock.replay(fileSystem);
        strategy = new ClasspathLocationStrategy();
    }

    /**
     * Tests a successful location of a provided resource name.
     */
    @Test
    public void testLocateSuccess()
    {
        FileLocator locator =
                FileLocatorUtils.fileLocator().fileName(FILE_NAME)
                        .basePath("somePath").create();
        URL url = strategy.locate(fileSystem, locator);
        assertEquals("Wrong URL", ConfigurationAssert.getTestURL(FILE_NAME)
                .toExternalForm(), url.toExternalForm());
    }

    /**
     * Tests a failed locate() operation.
     */
    @Test
    public void testLocateFailed()
    {
        FileLocator locator =
                FileLocatorUtils.fileLocator()
                        .fileName("non existing resource name!").create();
        assertNull("Got a URL", strategy.locate(fileSystem, locator));
    }

    /**
     * Tests a locate() operation if no file name is provided.
     */
    @Test
    public void testLocateNoFileName()
    {
        FileLocator locator =
                FileLocatorUtils.fileLocator().fileName("").create();
        assertNull("Got a URL", strategy.locate(fileSystem, locator));
    }
}
