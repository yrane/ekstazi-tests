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
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.NoOpLog;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@code DefaultFileSystem}. Note: This class tests only basic
 * functionality. Other parts are tested by actual access to configuration files
 * in other test classes.
 *
 * @version $Id: TestDefaultFileSystem.java 1527363 2013-09-29 17:41:26Z oheger $
 */
public class TestDefaultFileSystem
{
    /** The file system to be tested. */
    private DefaultFileSystem fileSystem;

    @Before
    public void setUp() throws Exception
    {
        fileSystem = new DefaultFileSystem();
    }

    /**
     * Tests the default logger.
     */
    @Test
    public void testDefaultLogger()
    {
        assertTrue("Wrong default logger",
                fileSystem.getLogger() instanceof NoOpLog);
    }

    /**
     * Tests whether the logger can be changed.
     */
    @Test
    public void testSetLogger()
    {
        Log log = LogFactory.getLog(getClass());
        fileSystem.setLogger(log);
        assertSame("Logger not set", log, fileSystem.getLogger());
    }
}
