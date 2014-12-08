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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.apache.commons.configuration.BaseHierarchicalConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationAssert;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.builder.BasicBuilderParameters;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.BuilderParameters;
import org.apache.commons.configuration.builder.ConfigurationBuilder;
import org.apache.commons.configuration.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration.convert.DisabledListDelimiterHandler;
import org.apache.commons.configuration.ex.ConfigurationException;
import org.junit.Test;

/**
 * Test class for {@code BaseConfigurationBuilderProvider}.
 *
 * @version $Id: TestBaseConfigurationBuilderProvider.java 1554634 2014-01-01 16:41:48Z oheger $
 */
public class TestBaseConfigurationBuilderProvider
{
    /**
     * Creates a configuration object describing a configuration source.
     *
     * @param reload a flag whether reload operations are supported
     * @return the configuration object
     */
    private HierarchicalConfiguration setUpConfig(boolean reload)
    {
        HierarchicalConfiguration config = new BaseHierarchicalConfiguration();
        config.addProperty(CombinedConfigurationBuilder.ATTR_RELOAD,
                Boolean.valueOf(reload));
        config.addProperty("[@throwExceptionOnMissing]", Boolean.TRUE);
        config.addProperty("[@path]",
                ConfigurationAssert.getTestFile("test.properties")
                        .getAbsolutePath());
        config.addProperty("listDelimiterHandler[@config-class]",
                DefaultListDelimiterHandler.class.getName());
        config.addProperty(
                "listDelimiterHandler.config-constrarg[@config-value]", ";");
        return config;
    }

    /**
     * Creates a configuration declaration based on the given configuration.
     *
     * @param declConfig the configuration for the declaration
     * @return the declaration
     */
    private ConfigurationDeclaration createDeclaration(
            HierarchicalConfiguration declConfig)
    {
        CombinedConfigurationBuilder parentBuilder =
                new CombinedConfigurationBuilder()
                {
                    @Override
                    protected void initChildBuilderParameters(
                            BuilderParameters params)
                    {
                        // set a property value; this should be overridden by
                        // child builders
                        if (params instanceof BasicBuilderParameters)
                        {
                            ((BasicBuilderParameters) params)
                                    .setListDelimiterHandler(DisabledListDelimiterHandler.INSTANCE);
                        }
                    }
                };
        ConfigurationDeclaration decl =
                new ConfigurationDeclaration(parentBuilder, declConfig)
                {
                    @Override
                    protected Object interpolate(Object value)
                    {
                        return value;
                    }
                };
        return decl;
    }

    /**
     * Creates a default test instance.
     *
     * @return the test instance
     */
    private BaseConfigurationBuilderProvider createProvider()
    {
        return new BaseConfigurationBuilderProvider(
                FileBasedConfigurationBuilder.class.getName(),
                ReloadingFileBasedConfigurationBuilder.class.getName(),
                PropertiesConfiguration.class.getName(),
                Arrays.asList(FileBasedBuilderParametersImpl.class.getName()));
    }

    /**
     * Helper method for setting up a builder and checking properties of the
     * created configuration object.
     *
     * @param reload a flag whether reloading is supported
     * @return the builder created by the provider
     * @throws ConfigurationException if an error occurs
     */
    private ConfigurationBuilder<? extends Configuration> checkBuilder(
            boolean reload) throws ConfigurationException
    {
        HierarchicalConfiguration declConfig = setUpConfig(reload);
        ConfigurationDeclaration decl = createDeclaration(declConfig);
        ConfigurationBuilder<? extends Configuration> builder =
                createProvider().getConfigurationBuilder(decl);
        Configuration config = builder.getConfiguration();
        assertEquals("Wrong configuration class",
                PropertiesConfiguration.class, config.getClass());
        PropertiesConfiguration pconfig = (PropertiesConfiguration) config;
        assertTrue("Wrong exception flag",
                pconfig.isThrowExceptionOnMissing());
        DefaultListDelimiterHandler listHandler =
                (DefaultListDelimiterHandler) pconfig.getListDelimiterHandler();
        assertEquals("Wrong list delimiter", ';', listHandler.getDelimiter());
        assertTrue("Configuration not loaded",
                pconfig.getBoolean("configuration.loaded"));
        return builder;
    }

    /**
     * Tries to create an instance without a builder class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInitNoBuilderClass()
    {
        new BaseConfigurationBuilderProvider(null, null,
                PropertiesConfiguration.class.getName(), null);
    }

    /**
     * Tries to create an instance without a configuration class.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInitNoConfigurationClass()
    {
        new BaseConfigurationBuilderProvider(BasicConfigurationBuilder.class.getName(),
                null, null, null);
    }

    /**
     * Tests whether a builder without reloading support can be created.
     */
    @Test
    public void testGetBuilderNotReloading() throws ConfigurationException
    {
        ConfigurationBuilder<? extends Configuration> builder =
                checkBuilder(false);
        assertEquals("Wrong builder class",
                FileBasedConfigurationBuilder.class, builder.getClass());
    }

    /**
     * Tests whether a builder with reloading support can be created.
     */
    @Test
    public void testGetBuilderReloading() throws ConfigurationException
    {
        ConfigurationBuilder<? extends Configuration> builder =
                checkBuilder(true);
        assertEquals("Wrong builder class",
                ReloadingFileBasedConfigurationBuilder.class,
                builder.getClass());
    }

    /**
     * Tries to create a reloading builder if this is not supported by the
     * provider.
     */
    @Test(expected = ConfigurationException.class)
    public void testGetReloadingBuilderNotSupported()
            throws ConfigurationException
    {
        BaseConfigurationBuilderProvider provider =
                new BaseConfigurationBuilderProvider(
                        FileBasedConfigurationBuilder.class.getName(), null,
                        PropertiesConfiguration.class.getName(),
                        Arrays.asList(FileBasedBuilderParametersImpl.class
                                .getName()));
        HierarchicalConfiguration declConfig = setUpConfig(true);
        ConfigurationDeclaration decl = createDeclaration(declConfig);
        provider.getConfigurationBuilder(decl);
    }

    /**
     * Helper method for testing whether the builder's allowFailOnInit flag is
     * set correctly.
     *
     * @param expFlag the expected flag value
     * @param props the properties to set in the configuration for the
     *        declaration
     * @throws ConfigurationException if an error occurs
     */
    private void checkAllowFailOnInit(boolean expFlag, String... props)
            throws ConfigurationException
    {
        HierarchicalConfiguration declConfig = setUpConfig(false);
        for (String key : props)
        {
            declConfig.addProperty(key, Boolean.TRUE);
        }
        ConfigurationDeclaration decl = createDeclaration(declConfig);
        BasicConfigurationBuilder<? extends Configuration> builder =
                (BasicConfigurationBuilder<? extends Configuration>) createProvider()
                        .getConfigurationBuilder(decl);
        assertEquals("Wrong flag value", expFlag, builder.isAllowFailOnInit());
    }

    /**
     * Tests that the allowFailOnInit flag is not set per default on the
     * builder.
     */
    @Test
    public void testGetBuilderNoFailOnInit() throws ConfigurationException
    {
        checkAllowFailOnInit(false);
    }

    /**
     * Tests that the allowFailOnInit flag is not set for builders which are not
     * optional.
     */
    public void testGetBuilderAllowFailOnInitNotOptional()
            throws ConfigurationException
    {
        checkAllowFailOnInit(false,
                CombinedConfigurationBuilder.ATTR_FORCECREATE);
    }

    /**
     * Tests whether the allowFailOnInit flag can be enabled on the builder.
     */
    @Test
    public void testGetBuilderAllowFailOnInit() throws ConfigurationException
    {
        checkAllowFailOnInit(true,
                CombinedConfigurationBuilder.ATTR_OPTIONAL_RES,
                CombinedConfigurationBuilder.ATTR_FORCECREATE);
    }

    /**
     * Tests whether a null collection of parameter classes is handled
     * correctly.
     */
    @Test
    public void testInitNoParameterClasses()
    {
        BaseConfigurationBuilderProvider provider =
                new BaseConfigurationBuilderProvider(
                        BasicConfigurationBuilder.class.getName(), null,
                        PropertiesConfiguration.class.getName(), null);
        assertTrue("Got parameter classes", provider.getParameterClasses()
                .isEmpty());
    }

    /**
     * Tests that the collection with parameter classes cannot be modified.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testGetParameterClassesModify()
    {
        BaseConfigurationBuilderProvider provider =
                new BaseConfigurationBuilderProvider(
                        BasicConfigurationBuilder.class.getName(), null,
                        PropertiesConfiguration.class.getName(),
                        Arrays.asList(BasicBuilderParameters.class.getName()));
        provider.getParameterClasses().clear();
    }
}
