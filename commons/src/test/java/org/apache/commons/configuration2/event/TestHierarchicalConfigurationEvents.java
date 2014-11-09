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
package org.apache.commons.configuration2.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.configuration2.AbstractConfiguration;
import org.apache.commons.configuration2.BaseHierarchicalConfiguration;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeHandler;
import org.apache.commons.configuration2.tree.NodeStructureHelper;
import org.apache.commons.configuration2.tree.QueryResult;
import org.junit.Test;

/**
 * Test class for the events generated by hierarchical configurations.
 *
 * @version $Id: TestHierarchicalConfigurationEvents.java 1624601 2014-09-12 18:04:36Z oheger $
 */
public class TestHierarchicalConfigurationEvents extends
        AbstractTestConfigurationEvents
{
    @Override
    protected AbstractConfiguration createConfiguration()
    {
        return new BaseHierarchicalConfiguration();
    }

    /**
     * Tests events generated by the clearTree() method.
     */
    @Test
    public void testClearTreeEvent()
    {
        BaseHierarchicalConfiguration hc = (BaseHierarchicalConfiguration) config;
        String key = EXIST_PROPERTY.substring(0, EXIST_PROPERTY.indexOf('.'));
        NodeHandler<ImmutableNode> nodeHandler = hc.getNodeModel().getNodeHandler();
        Collection<QueryResult<ImmutableNode>> nodes = hc.getExpressionEngine()
                .query(nodeHandler.getRootNode(), key, nodeHandler);
        hc.clearTree(key);
        listener.checkEvent(ConfigurationEvent.CLEAR_TREE, key, null,
                true);
        listener.checkEvent(ConfigurationEvent.CLEAR_TREE, key, nodes,
                false);
        listener.done();
    }

    /**
     * Tests events generated by the addNodes() method.
     */
    @Test
    public void testAddNodesEvent()
    {
        BaseHierarchicalConfiguration hc = (BaseHierarchicalConfiguration) config;
        Collection<ImmutableNode> nodes = new ArrayList<ImmutableNode>(1);
        nodes.add(NodeStructureHelper.createNode("a_key", TEST_PROPVALUE));
        hc.addNodes(TEST_PROPNAME, nodes);
        listener.checkEvent(ConfigurationEvent.ADD_NODES, TEST_PROPNAME,
                nodes, true);
        listener.checkEvent(ConfigurationEvent.ADD_NODES, TEST_PROPNAME,
                nodes, false);
        listener.done();
    }

    /**
     * Tests events generated by addNodes() when the list of nodes is empty. In
     * this case no events should be generated.
     */
    @Test
    public void testAddNodesEmptyEvent()
    {
        ((BaseHierarchicalConfiguration) config).addNodes(TEST_PROPNAME,
                new ArrayList<ImmutableNode>());
        listener.done();
    }

    /**
     * Tests whether manipulations of a connected sub configuration trigger correct
     * events.
     */
    @Test
    public void testSubConfigurationChangedEventConnected()
    {
        HierarchicalConfiguration<ImmutableNode> sub =
                ((BaseHierarchicalConfiguration) config)
                        .configurationAt(EXIST_PROPERTY, true);
        sub.addProperty("newProp", "newValue");
        checkSubnodeEvent(
                listener.nextEvent(ConfigurationEvent.SUBNODE_CHANGED),
                true);
        checkSubnodeEvent(
                listener.nextEvent(ConfigurationEvent.SUBNODE_CHANGED),
                false);
        listener.done();
    }

    /**
     * Tests that no events are generated for a disconnected sub configuration.
     */
    @Test
    public void testSubConfigurationChangedEventNotConnected()
    {
        HierarchicalConfiguration<ImmutableNode> sub =
                ((BaseHierarchicalConfiguration) config)
                        .configurationAt(EXIST_PROPERTY);
        sub.addProperty("newProp", "newValue");
        listener.done();
    }

    /**
     * Tests whether a received event contains a correct subnode event.
     *
     * @param event the event object
     * @param before the expected before flag
     */
    private void checkSubnodeEvent(ConfigurationEvent event, boolean before)
    {
        assertEquals("Wrong before flag of nesting event", before, event
                .isBeforeUpdate());
        assertTrue("No subnode event found in value",
                event.getPropertyValue() instanceof ConfigurationEvent);
        ConfigurationEvent evSub = (ConfigurationEvent) event
                .getPropertyValue();
        assertEquals("Wrong event type",
                ConfigurationEvent.ADD_PROPERTY, evSub.getEventType());
        assertEquals("Wrong property name", "newProp", evSub.getPropertyName());
        assertEquals("Wrong property value", "newValue", evSub
                .getPropertyValue());
        assertEquals("Wrong before flag", before, evSub.isBeforeUpdate());
    }
}
