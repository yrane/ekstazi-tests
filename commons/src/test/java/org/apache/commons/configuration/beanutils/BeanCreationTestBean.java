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
package org.apache.commons.configuration.beanutils;

/**
 * A simple bean class used for testing bean creation operations.
 *
 * @version $Id: BeanCreationTestBean.java 1408072 2012-11-11 18:27:04Z oheger $
 */
public class BeanCreationTestBean
{
    private String stringValue;

    private int intValue;

    private BeanCreationTestBean buddy;

    public BeanCreationTestBean getBuddy()
    {
        return buddy;
    }

    public void setBuddy(BeanCreationTestBean buddy)
    {
        this.buddy = buddy;
    }

    public int getIntValue()
    {
        return intValue;
    }

    public void setIntValue(int intValue)
    {
        this.intValue = intValue;
    }

    public String getStringValue()
    {
        return stringValue;
    }

    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }
}
