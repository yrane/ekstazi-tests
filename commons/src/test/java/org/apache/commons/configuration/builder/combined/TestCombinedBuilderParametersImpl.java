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
package org.apache.commons.configuration.builder.combined;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationAssert;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.BuilderParameters;
import org.apache.commons.configuration.builder.ConfigurationBuilder;
import org.apache.commons.configuration.builder.DefaultParametersHandler;
import org.apache.commons.configuration.builder.DefaultParametersManager;
import org.apache.commons.configuration.builder.XMLBuilderParametersImpl;
import org.apache.commons.configuration.builder.fluent.FileBasedBuilderParameters;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Test class for {@code CombinedBuilderParametersImpl}.
 *
 * @version $Id: TestCombinedBuilderParametersImpl.java 1553024 2013-12-22 20:36:06Z oheger $
 */
public class TestCombinedBuilderParametersImpl
{
    /**
     * Tests fromParameters() if the map does not contain an instance.
     */
    @Test
    public void testFromParametersNotFound()
    {
        assertNull("Got an instance",
                CombinedBuilderParametersImpl
                        .fromParameters(new HashMap<String, Object>()));
    }

    /**
     * Tests whether a new instance can be created if none is found in the
     * parameters map.
     */
    @Test
    public void testFromParametersCreate()
    {
        CombinedBuilderParametersImpl params =
                CombinedBuilderParametersImpl.fromParameters(
                        new HashMap<String, Object>(), true);
        assertNotNull("No instance", params);
        assertNull("Got data", params.getDefinitionBuilder());
    }

    /**
     * Tests whether an instance can be obtained from a parameters map.
     */
    @Test
    public void testFromParametersExisting()
    {
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        Map<String, Object> map = params.getParameters();
        assertSame("Wrong result", params,
                CombinedBuilderParametersImpl.fromParameters(map));
    }

    /**
     * Tests that inherited properties are also stored in the parameters map.
     */
    @Test
    public void testGetParametersInherited()
    {
        CombinedBuilderParametersImpl params =
                new CombinedBuilderParametersImpl();
        params.setThrowExceptionOnMissing(true);
        Map<String, Object> map = params.getParameters();
        assertEquals("Exception flag not found", Boolean.TRUE,
                map.get("throwExceptionOnMissing"));
    }

    /**
     * Tests whether the definition builder can be set.
     */
    @Test
    public void testSetDefinitionBuilder()
    {
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        assertNull("Got a definition builder", params.getDefinitionBuilder());
        ConfigurationBuilder<XMLConfiguration> builder =
                new BasicConfigurationBuilder<XMLConfiguration>(
                        XMLConfiguration.class);
        assertSame("Wrong result", params, params.setDefinitionBuilder(builder));
        assertSame("Builder was not set", builder,
                params.getDefinitionBuilder());
    }

    /**
     * Tests whether the map with providers is initially empty.
     */
    @Test
    public void testGetProvidersInitial()
    {
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        assertTrue("Got providers", params.getProviders().isEmpty());
    }

    /**
     * Tests whether a new builder provider can be registered.
     */
    @Test
    public void testRegisterProvider()
    {
        ConfigurationBuilderProvider provider =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        EasyMock.replay(provider);
        String tagName = "testTag";
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        assertSame("Wrong result", params,
                params.registerProvider(tagName, provider));
        Map<String, ConfigurationBuilderProvider> providers =
                params.getProviders();
        assertEquals("Wrong number of providers", 1, providers.size());
        assertSame("Wrong provider (1)", provider, providers.get(tagName));
        assertSame("Wrong provider (2)", provider,
                params.providerForTag(tagName));
    }

    /**
     * Tries to register a provider without a tag name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterProviderNoTag()
    {
        new CombinedBuilderParametersImpl().registerProvider(null,
                EasyMock.createMock(ConfigurationBuilderProvider.class));
    }

    /**
     * Tries to register a null provider.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterProviderNoProvider()
    {
        new CombinedBuilderParametersImpl().registerProvider("aTag", null);
    }

    /**
     * Tests that the map with providers cannot be modified.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetProvidersModify()
    {
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        params.getProviders().put("tag",
                EasyMock.createMock(ConfigurationBuilderProvider.class));
    }

    /**
     * Tests whether missing providers can be registered.
     */
    @Test
    public void testRegisterMissingProviders()
    {
        ConfigurationBuilderProvider provider1 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        ConfigurationBuilderProvider provider2 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        ConfigurationBuilderProvider provider3 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        String tagPrefix = "testTag";
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        params.registerProvider(tagPrefix, provider1);
        Map<String, ConfigurationBuilderProvider> map =
                new HashMap<String, ConfigurationBuilderProvider>();
        map.put(tagPrefix, provider2);
        map.put(tagPrefix + 1, provider3);
        assertSame("Wrong result", params, params.registerMissingProviders(map));
        assertEquals("Wrong number of providers", 2, params.getProviders()
                .size());
        assertSame("Wrong provider (1)", provider1,
                params.providerForTag(tagPrefix));
        assertSame("Wrong provider (2)", provider3,
                params.providerForTag(tagPrefix + 1));
    }

    /**
     * Tries to register a null map with missing providers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMissingProvidersNullMap()
    {
        Map<String, ConfigurationBuilderProvider> map = null;
        new CombinedBuilderParametersImpl().registerMissingProviders(map);
    }

    /**
     * Tries to register a map with missing providers containing a null entry.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMissingProvidersNullEntry()
    {
        Map<String, ConfigurationBuilderProvider> map =
                new HashMap<String, ConfigurationBuilderProvider>();
        map.put("tag", null);
        new CombinedBuilderParametersImpl().registerMissingProviders(map);
    }

    /**
     * Tests whether missing providers can be copied from a parameters object.
     */
    @Test
    public void testRegisterMissingProvidersParams()
    {
        ConfigurationBuilderProvider provider1 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        ConfigurationBuilderProvider provider2 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        ConfigurationBuilderProvider provider3 =
                EasyMock.createMock(ConfigurationBuilderProvider.class);
        String tagPrefix = "testTag";
        CombinedBuilderParametersImpl params =
                new CombinedBuilderParametersImpl();
        CombinedBuilderParametersImpl params2 =
                new CombinedBuilderParametersImpl();
        params.registerProvider(tagPrefix, provider1);
        params2.registerProvider(tagPrefix, provider2);
        params2.registerProvider(tagPrefix + 1, provider3);
        assertSame("Wrong result", params,
                params.registerMissingProviders(params2));
        assertEquals("Wrong number of providers", 2, params.getProviders()
                .size());
        assertSame("Wrong provider (1)", provider1,
                params.providerForTag(tagPrefix));
        assertSame("Wrong provider (2)", provider3,
                params.providerForTag(tagPrefix + 1));
    }

    /**
     * Tries to copy providers from a null parameters object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMissingProvidersParamsNull()
    {
        new CombinedBuilderParametersImpl()
                .registerMissingProviders((CombinedBuilderParametersImpl) null);
    }

    /**
     * Tests the result for an unknown provider.
     */
    @Test
    public void testProviderForUnknown()
    {
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        assertNull("Got a provider", params.providerForTag("someTag"));
    }

    /**
     * Tests whether the base path can be set.
     */
    @Test
    public void testSetBasePath()
    {
        CombinedBuilderParametersImpl params =
                new CombinedBuilderParametersImpl();
        String basePath = ConfigurationAssert.OUT_DIR.getAbsolutePath();
        assertSame("Wrong result", params, params.setBasePath(basePath));
        assertEquals("Wrong base path", basePath, params.getBasePath());
    }

    /**
     * Tests whether a parameters object for the definition builder can be set.
     */
    @Test
    public void testSetDefinitionBuilderParameters()
    {
        BuilderParameters defparams =
                EasyMock.createMock(BuilderParameters.class);
        EasyMock.replay(defparams);
        CombinedBuilderParametersImpl params =
                new CombinedBuilderParametersImpl();
        assertSame("Wrong result", params,
                params.setDefinitionBuilderParameters(defparams));
        assertSame("Wrong parameters object", defparams,
                params.getDefinitionBuilderParameters());
    }

    /**
     * Tests whether properties can be set using BeanUtils.
     */
    @Test
    public void testSetBeanProperties() throws Exception
    {
        BuilderParameters defparams =
                EasyMock.createMock(BuilderParameters.class);
        EasyMock.replay(defparams);
        CombinedBuilderParametersImpl params =
                new CombinedBuilderParametersImpl();
        BeanHelper.setProperty(params, "basePath", "testPath");
        BeanHelper.setProperty(params, "definitionBuilderParameters",
                defparams);
        assertEquals("Wrong path", "testPath", params.getBasePath());
        assertSame("Wrong def parameters", defparams,
                params.getDefinitionBuilderParameters());
    }

    /**
     * Tests whether cloning works as expected.
     */
    @Test
    public void testClone()
    {
        CombinedBuilderParametersImpl params =
                new CombinedBuilderParametersImpl();
        params.setBasePath("some base path");
        XMLBuilderParametersImpl defParams = new XMLBuilderParametersImpl();
        defParams.setSystemID("someSysID");
        params.setDefinitionBuilderParameters(defParams);
        CombinedBuilderParametersImpl clone = params.clone();
        assertEquals("Wrong field value", params.getBasePath(),
                clone.getBasePath());
        assertNotSame("Parameters object not cloned",
                params.getDefinitionBuilderParameters(),
                clone.getDefinitionBuilderParameters());
        assertEquals(
                "Wrong field value in parameters object",
                params.getDefinitionBuilderParameters().getParameters()
                        .get("systemID"),
                clone.getDefinitionBuilderParameters().getParameters()
                        .get("systemID"));
    }

    /**
     * Tests whether a default parameters manager is dynamically created if it has not
     * been set.
     */
    @Test
    public void testGetChildDefaultParametersManagerUndefined() {
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        assertNotNull("No default manager", params.getChildDefaultParametersManager());
    }

    /**
     * Tests whether a default parameters manager can be set and queried.
     */
    @Test
    public void testGetChildDefaultParametersManagerSpecific() {
        DefaultParametersManager manager = EasyMock
                .createMock(DefaultParametersManager.class);
        EasyMock.replay(manager);
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        assertSame("Wrong result", params,
                params.setChildDefaultParametersManager(manager));
        assertSame("Wrong manager", manager, params.getChildDefaultParametersManager());
    }

    /**
     * Creates a mock for a defaults handler.
     * @return the handler mock
     */
    private static DefaultParametersHandler<BuilderParameters> createDefaultsHandlerMock()
    {
        @SuppressWarnings("unchecked")
        DefaultParametersHandler<BuilderParameters> mock = EasyMock.createMock(DefaultParametersHandler.class);
        return mock;
    }

    /**
     * Tests whether a defaults handler for a child source can be registered.
     */
    @Test
    public void testRegisterChildDefaultsHandler()
    {
        DefaultParametersManager manager = EasyMock.createMock(DefaultParametersManager.class);
        DefaultParametersHandler<BuilderParameters> handler = createDefaultsHandlerMock();
        manager.registerDefaultsHandler(BuilderParameters.class, handler);
        EasyMock.replay(manager, handler);
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        params.setChildDefaultParametersManager(manager);
        assertSame("Wrong result", params, params.registerChildDefaultsHandler(BuilderParameters.class, handler));
        EasyMock.verify(manager);
    }

    /**
     * Tests whether a defaults handler for a child source with a class restriction can be registered.
     */
    @Test
    public void testRegisterChildDefaultsHandlerWithStartClass()
    {
        DefaultParametersManager manager = EasyMock.createMock(DefaultParametersManager.class);
        DefaultParametersHandler<BuilderParameters> handler = createDefaultsHandlerMock();
        manager.registerDefaultsHandler(BuilderParameters.class, handler, FileBasedBuilderParameters.class);
        EasyMock.replay(manager, handler);
        CombinedBuilderParametersImpl params = new CombinedBuilderParametersImpl();
        params.setChildDefaultParametersManager(manager);
        assertSame("Wrong result", params, params.registerChildDefaultsHandler(BuilderParameters.class, handler, FileBasedBuilderParameters.class));
        EasyMock.verify(manager);
    }
}
