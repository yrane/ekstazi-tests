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
package org.apache.commons.configuration.builder.fluent;

import org.apache.commons.configuration.builder.BasicBuilderProperties;
import org.apache.commons.configuration.builder.BuilderParameters;
import org.apache.commons.configuration.builder.FileBasedBuilderProperties;
import org.apache.commons.configuration.builder.HierarchicalBuilderProperties;
import org.apache.commons.configuration.builder.XMLBuilderProperties;

/**
 * <p>
 * Definition of a parameters interface providing a fluent API for setting all
 * properties for a XML configuration.
 * </p>
 *
 * @version $Id: XMLBuilderParameters.java 1426559 2012-12-28 16:06:11Z oheger $
 * @since 2.0
 */
public interface XMLBuilderParameters extends
        BasicBuilderProperties<XMLBuilderParameters>,
        FileBasedBuilderProperties<XMLBuilderParameters>,
        HierarchicalBuilderProperties<XMLBuilderParameters>,
        XMLBuilderProperties<XMLBuilderParameters>, BuilderParameters
{
}
