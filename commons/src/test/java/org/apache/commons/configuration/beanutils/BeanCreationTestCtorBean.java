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
 * Another test bean class for bean creation operations which defines some constructors.
 *
 * @version $Id: BeanCreationTestCtorBean.java 1408072 2012-11-11 18:27:04Z oheger $
 */
public class BeanCreationTestCtorBean extends BeanCreationTestBean
{
    public BeanCreationTestCtorBean()
    {
    }

    public BeanCreationTestCtorBean(BeanCreationTestBean buddy)
    {
        setBuddy(buddy);
    }

    public BeanCreationTestCtorBean(String s)
    {
        setStringValue(s);
    }

    public BeanCreationTestCtorBean(String s, int i)
    {
        this(s);
        setIntValue(i);
    }
}
