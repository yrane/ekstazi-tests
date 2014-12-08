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

import java.io.File;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.io.FileHandler;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;

/**
 * Test class for {@code XMLPropertiesConfiguration}.
 *
 * @author Emmanuel Bourg
 * @version $Id: TestXMLPropertiesConfiguration.java 1554634 2014-01-01 16:41:48Z oheger $
 */
public class TestXMLPropertiesConfiguration
{
    /** Constant for the name of the test file. */
    private static final String TEST_PROPERTIES_FILE = "test.properties.xml";

    /** A helper object for creating temporary files. */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Helper method for loading a configuration file.
     *
     * @param fileName the name of the file to be loaded
     * @return the configuration instance
     * @throws ConfigurationException if an error occurs
     */
    private static XMLPropertiesConfiguration load(String fileName)
            throws ConfigurationException
    {
        XMLPropertiesConfiguration conf = new XMLPropertiesConfiguration();
        FileHandler handler = new FileHandler(conf);
        handler.load(fileName);
        return conf;
    }

    @Test
    public void testLoad() throws Exception
    {
        XMLPropertiesConfiguration conf = load(TEST_PROPERTIES_FILE);
        assertEquals("header", "Description of the property list", conf.getHeader());

        assertFalse("The configuration is empty", conf.isEmpty());
        assertEquals("'key1' property", "value1", conf.getProperty("key1"));
        assertEquals("'key2' property", "value2", conf.getProperty("key2"));
        assertEquals("'key3' property", "value3", conf.getProperty("key3"));
    }

    @Test
    public void testDOMLoad() throws Exception
    {
        URL location = ConfigurationAssert.getTestURL(TEST_PROPERTIES_FILE);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        File file = new File(location.toURI());
        Document doc = dBuilder.parse(file);
        XMLPropertiesConfiguration conf = new XMLPropertiesConfiguration(doc.getDocumentElement());

        assertEquals("header", "Description of the property list", conf.getHeader());

        assertFalse("The configuration is empty", conf.isEmpty());
        assertEquals("'key1' property", "value1", conf.getProperty("key1"));
        assertEquals("'key2' property", "value2", conf.getProperty("key2"));
        assertEquals("'key3' property", "value3", conf.getProperty("key3"));
    }

    @Test
    public void testSave() throws Exception
    {
        // load the configuration
        XMLPropertiesConfiguration conf = load(TEST_PROPERTIES_FILE);

        // update the configuration
        conf.addProperty("key4", "value4");
        conf.clearProperty("key2");
        conf.setHeader("Description of the new property list");

        // save the configuration
        File saveFile = folder.newFile("test2.properties.xml");
        FileHandler saveHandler = new FileHandler(conf);
        saveHandler.save(saveFile);

        // reload the configuration
        XMLPropertiesConfiguration conf2 = load(saveFile.getAbsolutePath());

        // test the configuration
        assertEquals("header", "Description of the new property list", conf2.getHeader());

        assertFalse("The configuration is empty", conf2.isEmpty());
        assertEquals("'key1' property", "value1", conf2.getProperty("key1"));
        assertEquals("'key3' property", "value3", conf2.getProperty("key3"));
        assertEquals("'key4' property", "value4", conf2.getProperty("key4"));
    }

    @Test
    public void testDOMSave() throws Exception
    {
        // load the configuration
        XMLPropertiesConfiguration conf = load(TEST_PROPERTIES_FILE);

        // update the configuration
        conf.addProperty("key4", "value4");
        conf.clearProperty("key2");
        conf.setHeader("Description of the new property list");

        // save the configuration
        File saveFile = folder.newFile("test2.properties.xml");

        // save as DOM into saveFile
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.newDocument();
        conf.save(document, document);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        Result result = new StreamResult(saveFile);
        transformer.transform(source, result);

        // reload the configuration
        XMLPropertiesConfiguration conf2 = load(saveFile.getAbsolutePath());

        // test the configuration
        assertEquals("header", "Description of the new property list", conf2.getHeader());

        assertFalse("The configuration is empty", conf2.isEmpty());
        assertEquals("'key1' property", "value1", conf2.getProperty("key1"));
        assertEquals("'key3' property", "value3", conf2.getProperty("key3"));
        assertEquals("'key4' property", "value4", conf2.getProperty("key4"));
    }
}
