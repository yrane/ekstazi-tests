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
package org.apache.commons.configuration2.builder;

import org.apache.commons.configuration2.PropertiesConfiguration.IOFactory;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;

/**
 * <p>
 * A specialized parameter class for configuring {@code PropertiesConfiguration}
 * instances.
 * </p>
 * <p>
 * This class allows setting of some properties specific to properties
 * configuration, e.g. the layout object. By inheriting from
 * {@link FileBasedBuilderParametersImpl}, basic properties and properties
 * related to file-based configurations are available, too.
 * </p>
 * <p>
 * This class is not thread-safe. It is intended that an instance is constructed
 * and initialized by a single thread during configuration of a
 * {@code ConfigurationBuilder}.
 * </p>
 *
 * @version $Id: PropertiesBuilderParametersImpl.java 1624601 2014-09-12 18:04:36Z oheger $
 * @since 2.0
 */
public class PropertiesBuilderParametersImpl extends
        FileBasedBuilderParametersImpl implements
        PropertiesBuilderProperties<PropertiesBuilderParametersImpl>
{
    /** The key for the includes allowed property. */
    private static final String PROP_INCLUDES_ALLOWED = "includesAllowed";

    /** The key for the layout property. */
    private static final String PROP_LAYOUT = "layout";

    /** The key for the IO factory property. */
    private static final String PROP_IO_FACTORY = "iOFactory";

    @Override
    public PropertiesBuilderParametersImpl setIncludesAllowed(boolean f)
    {
        storeProperty(PROP_INCLUDES_ALLOWED, Boolean.valueOf(f));
        return this;
    }

    @Override
    public PropertiesBuilderParametersImpl setLayout(
            PropertiesConfigurationLayout layout)
    {
        storeProperty(PROP_LAYOUT, layout);
        return this;
    }

    @Override
    public PropertiesBuilderParametersImpl setIOFactory(IOFactory factory)
    {
        storeProperty(PROP_IO_FACTORY, factory);
        return this;
    }
}
