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

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationAssert;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code AbsoluteNameLocationStrategy}.
 *
 * @version $Id: TestAbsoluteNameLocationStrategy.java 1525405 2013-09-22 17:59:12Z oheger $
 */
public class TestAbsoluteNameLocationStrategy
{
    /** A mock for the file system. */
    private FileSystem fileSystem;

    /** The strategy to be tested. */
    private AbsoluteNameLocationStrategy strategy;

    @Before
    public void setUp() throws Exception
    {
        fileSystem = EasyMock.createMock(FileSystem.class);
        EasyMock.replay(fileSystem);
        strategy = new AbsoluteNameLocationStrategy();
    }

    /**
     * Tests a locate() operation if no file name is provided.
     */
    @Test
    public void testNoFileName()
    {
        FileLocator locator = FileLocatorUtils.fileLocator().create();
        assertNull("Got a URL", strategy.locate(fileSystem, locator));
    }

    /**
     * Tests a locate() operation if no absolute file name is provided.
     */
    @Test
    public void testNoAbsoluteFileName()
    {
        FileLocator locator =
                FileLocatorUtils.fileLocator().fileName("test.xml").create();
        assertNull("Got a URL", strategy.locate(fileSystem, locator));
    }

    /**
     * Tests a locate() operation if an absolute file name is provided, but this
     * file does not exist.
     */
    @Test
    public void testNonExistingAbsoluteFile()
    {
        File file = ConfigurationAssert.getOutFile("NotExistingFile.tst");
        FileLocator locator =
                FileLocatorUtils.fileLocator().fileName(file.getAbsolutePath())
                        .create();
        assertNull("Got a URL", strategy.locate(fileSystem, locator));
    }

    /**
     * Tests a successful locate() operation.
     */
    @Test
    public void testExistingAbsoluteFile()
    {
        File file = ConfigurationAssert.getTestFile("test.xml");
        FileLocator locator =
                FileLocatorUtils.fileLocator().fileName(file.getAbsolutePath())
                        .create();
        URL url = strategy.locate(fileSystem, locator);
        assertEquals("Wrong URL", file.getAbsoluteFile(), FileLocatorUtils
                .fileFromURL(url).getAbsoluteFile());
    }
}
