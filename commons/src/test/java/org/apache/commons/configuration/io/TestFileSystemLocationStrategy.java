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

import static org.junit.Assert.assertSame;

import java.net.URL;

import org.apache.commons.configuration.ConfigurationAssert;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code FileSystemLocationStrategy}.
 *
 * @version $Id: TestFileSystemLocationStrategy.java 1525405 2013-09-22 17:59:12Z oheger $
 */
public class TestFileSystemLocationStrategy
{
    /** The strategy to be tested. */
    private FileSystemLocationStrategy strategy;

    @Before
    public void setUp() throws Exception
    {
        strategy = new FileSystemLocationStrategy();
    }

    /**
     * Tests a locate() operation.
     */
    @Test
    public void testLocate()
    {
        FileSystem fs = EasyMock.createMock(FileSystem.class);
        URL url = ConfigurationAssert.getTestURL("test.xml");
        final String basePath = "testBasePath";
        final String fileName = "testFileName.txt";
        EasyMock.expect(fs.locateFromURL(basePath, fileName)).andReturn(url);
        EasyMock.replay(fs);
        FileLocator locator =
                FileLocatorUtils
                        .fileLocator()
                        .basePath(basePath)
                        .fileName(fileName)
                        .fileSystem(FileLocatorUtils.DEFAULT_FILE_SYSTEM)
                        .sourceURL(
                                ConfigurationAssert
                                        .getTestURL("test.properties"))
                        .create();

        assertSame("Wrong result", url, strategy.locate(fs, locator));
        EasyMock.verify(fs);
    }
}
