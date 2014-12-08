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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.net.URL;

import org.apache.commons.configuration.ConfigurationAssert;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code ProvidedURLLocationStrategy}.
 *
 * @version $Id: TestProvidedURLLocationStrategy.java 1525405 2013-09-22 17:59:12Z oheger $
 */
public class TestProvidedURLLocationStrategy
{
    /** The strategy to be tested. */
    private ProvidedURLLocationStrategy strategy;

    @Before
    public void setUp() throws Exception
    {
        strategy = new ProvidedURLLocationStrategy();
    }

    /**
     * Tests a successful locate() operation.
     */
    @Test
    public void testLocateSuccess()
    {
        FileSystem fs = EasyMock.createMock(FileSystem.class);
        EasyMock.replay(fs);
        URL url = ConfigurationAssert.getTestURL("test.xml");
        FileLocator locator =
                FileLocatorUtils.fileLocator().sourceURL(url).create();
        assertSame("Wrong URL", url, strategy.locate(fs, locator));
    }

    /**
     * Tests a failed locate() operation.
     */
    @Test
    public void testLocateFail()
    {
        FileSystem fs = EasyMock.createMock(FileSystem.class);
        EasyMock.replay(fs);
        FileLocator locator =
                FileLocatorUtils.fileLocator().basePath("somePath")
                        .fileName("someFile.xml").create();
        assertNull("Got a URL", strategy.locate(fs, locator));
    }
}
