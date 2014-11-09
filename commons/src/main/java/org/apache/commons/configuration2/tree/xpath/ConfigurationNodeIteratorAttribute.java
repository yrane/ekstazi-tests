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
package org.apache.commons.configuration2.tree.xpath;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.jxpath.ri.QName;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.apache.commons.lang3.StringUtils;

/**
 * A specialized node iterator implementation that deals with attribute nodes.
 *
 * @version $Id: ConfigurationNodeIteratorAttribute.java 1624601 2014-09-12 18:04:36Z oheger $
 * @param <T> the type of the nodes this iterator deals with
 */
class ConfigurationNodeIteratorAttribute<T> extends
        ConfigurationNodeIteratorBase<T>
{
    /** Constant for the wildcard node name.*/
    private static final String WILDCARD = "*";

    /** Stores the parent node pointer. */
    private ConfigurationNodePointer<T> parentPointer;

    /** A list with the names of the managed attributes. */
    private List<String> attributeNames;

    /**
     * Creates a new instance of {@code ConfigurationNodeIteratorAttribute}.
     * @param parent the parent node pointer
     * @param name the name of the selected attribute
     */
    public ConfigurationNodeIteratorAttribute(
            ConfigurationNodePointer<T> parent, QName name)
    {
        super(parent, false);
        parentPointer = parent;
        attributeNames = createAttributeDataList(parent, name);
    }

    /**
     * Creates a pointer for the node at the specified position.
     *
     * @param position the desired position
     * @return a pointer for the attribute at this position
     */
    @Override
    protected NodePointer createNodePointer(int position)
    {
        return new ConfigurationAttributePointer<T>(parentPointer,
                attributeNames.get(position));
    }

    /**
     * Returns the size of the managed iteration.
     *
     * @return the iteration size
     */
    @Override
    protected int size()
    {
        return attributeNames.size();
    }

    /**
     * Determines which attributes are selected based on the passed in node
     * name.
     *
     * @param parent the parent node pointer
     * @param name the name of the selected attribute
     * @return a list with the selected attributes
     */
    private List<String> createAttributeDataList(
            ConfigurationNodePointer<T> parent, QName name)
    {
        List<String> result = new ArrayList<String>();
        if (!WILDCARD.equals(name.getName()))
        {
            addAttributeData(parent, result, qualifiedName(name));
        }
        else
        {
            Set<String> names =
                    new LinkedHashSet<String>(parent.getNodeHandler()
                            .getAttributes(parent.getConfigurationNode()));
            String prefix =
                    (name.getPrefix() != null) ? prefixName(name.getPrefix(),
                            null) : null;
            for (String n : names)
            {
                if (prefix == null || StringUtils.startsWith(n, prefix))
                {
                    addAttributeData(parent, result, n);
                }
            }
        }

        return result;
    }

    /**
     * Helper method for checking whether an attribute is defined and adding it
     * to the list of attributes to iterate over.
     *
     * @param parent the parent node pointer
     * @param result the result list
     * @param name the name of the current attribute
     */
    private void addAttributeData(ConfigurationNodePointer<T> parent,
            List<String> result, String name)
    {
        if (parent.getNodeHandler().getAttributeValue(
                parent.getConfigurationNode(), name) != null)
        {
            result.add(name);
        }
    }
}
