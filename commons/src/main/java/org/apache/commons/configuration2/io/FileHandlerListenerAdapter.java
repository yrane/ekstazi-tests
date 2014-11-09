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
package org.apache.commons.configuration2.io;

/**
 * <p>
 * An adapter class simplifying the implementation of custom
 * {@code FileHandlerListener} classes.
 * </p>
 * <p>
 * This class provides empty dummy implementations for all methods defined by
 * the {@code FileHandlerListener} interface. Custom listener implementations
 * can extend this adapter class and override only the methods they actually
 * need.
 * </p>
 *
 * @version $Id: FileHandlerListenerAdapter.java 1624601 2014-09-12 18:04:36Z oheger $
 * @since 2.0
 */
public class FileHandlerListenerAdapter implements FileHandlerListener
{
    @Override
    public void loading(FileHandler handler)
    {
    }

    @Override
    public void loaded(FileHandler handler)
    {
    }

    @Override
    public void saving(FileHandler handler)
    {
    }

    @Override
    public void saved(FileHandler handler)
    {
    }

    @Override
    public void locationChanged(FileHandler handler)
    {
    }
}
