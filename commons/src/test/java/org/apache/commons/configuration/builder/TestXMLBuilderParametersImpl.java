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
package org.apache.commons.configuration.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.configuration.beanutils.BeanHelper;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.EntityResolver;

/**
 * Test class for {@code XMLBuilderParametersImpl}.
 *
 * @version $Id: TestXMLBuilderParametersImpl.java 1553024 2013-12-22 20:36:06Z oheger $
 */
public class TestXMLBuilderParametersImpl
{
    /** The parameters object to be tested. */
    private XMLBuilderParametersImpl params;

    @Before
    public void setUp() throws Exception
    {
        params = new XMLBuilderParametersImpl();
    }

    /**
     * Tests whether an entity resolver can be set.
     */
    @Test
    public void testSetEntityResolver()
    {
        EntityResolver resolver = EasyMock.createMock(EntityResolver.class);
        EasyMock.replay(resolver);
        assertSame("Wrong result", params, params.setEntityResolver(resolver));
        assertSame("Resolver not set", resolver, params.getEntityResolver());
        assertSame("Resolver not in parameters", resolver, params
                .getParameters().get("entityResolver"));
    }

    /**
     * Tests whether a document builder can be set.
     */
    @Test
    public void testSetDocumentBuilder()
    {
        DocumentBuilder builder = EasyMock.createMock(DocumentBuilder.class);
        EasyMock.replay(builder);
        assertSame("Wrong result", params, params.setDocumentBuilder(builder));
        assertSame("Builder not in parameters", builder, params.getParameters()
                .get("documentBuilder"));
    }

    /**
     * Tests whether a public ID can be set.
     */
    @Test
    public void testSetPublicID()
    {
        String pubID = "testPublicID";
        assertSame("Wrong result", params, params.setPublicID(pubID));
        assertEquals("ID not in parameters", pubID,
                params.getParameters().get("publicID"));
    }

    /**
     * Tests whether a system ID can be set.
     */
    @Test
    public void testSetSystemID()
    {
        String sysID = "testSystemID";
        assertSame("Wrong result", params, params.setSystemID(sysID));
        assertEquals("ID not in parameters", sysID,
                params.getParameters().get("systemID"));
    }

    /**
     * Tests whether validating property can be set.
     */
    @Test
    public void testSetValidating()
    {
        assertSame("Wrong result", params, params.setValidating(true));
        assertEquals("Flag not in parameters", Boolean.TRUE, params
                .getParameters().get("validating"));
    }

    /**
     * Tests whether the schema validation flag can be set.
     */
    @Test
    public void testSetSchemaValidation()
    {
        assertSame("Wrong result", params, params.setSchemaValidation(false));
        assertEquals("Flag not in parameters", Boolean.FALSE, params
                .getParameters().get("schemaValidation"));
    }

    /**
     * Tests whether properties can be set through BeanUtils.
     */
    @Test
    public void testBeanPropertiesAccess() throws Exception
    {
        EntityResolver resolver = EasyMock.createMock(EntityResolver.class);
        DocumentBuilder builder = EasyMock.createMock(DocumentBuilder.class);
        EasyMock.replay(resolver, builder);
        BeanHelper.setProperty(params, "throwExceptionOnMissing",
                Boolean.TRUE);
        BeanHelper.setProperty(params, "fileName", "test.xml");
        BeanHelper.setProperty(params, "entityResolver", resolver);
        BeanHelper.setProperty(params, "documentBuilder", builder);
        assertEquals("Wrong file name", "test.xml", params.getFileHandler()
                .getFileName());
        Map<String, Object> paramsMap = params.getParameters();
        assertEquals("Wrong exception flag", Boolean.TRUE,
                paramsMap.get("throwExceptionOnMissing"));
        assertSame("Wrong resolver", resolver, paramsMap.get("entityResolver"));
        assertSame("Wrong builder", builder, paramsMap.get("documentBuilder"));
    }
}
