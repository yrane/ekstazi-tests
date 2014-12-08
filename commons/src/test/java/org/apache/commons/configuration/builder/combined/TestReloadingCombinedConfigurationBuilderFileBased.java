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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import org.apache.commons.configuration.BaseHierarchicalConfiguration;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.builder.BasicBuilderParameters;
import org.apache.commons.configuration.builder.BasicBuilderProperties;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.CopyObjectDefaultHandler;
import org.apache.commons.configuration.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration.builder.FileBasedBuilderProperties;
import org.apache.commons.configuration.builder.ReloadingDetectorFactory;
import org.apache.commons.configuration.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration.builder.fluent.Parameters;
import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.io.FileHandler;
import org.apache.commons.configuration.reloading.AlwaysReloadingDetector;
import org.apache.commons.configuration.reloading.RandomReloadingDetector;
import org.apache.commons.configuration.reloading.ReloadingDetector;
import org.apache.commons.configuration.sync.ReadWriteSynchronizer;
import org.apache.commons.configuration.sync.Synchronizer;
import org.apache.commons.configuration.tree.MergeCombiner;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test class for {@code ReloadingCombinedConfigurationBuilder} which actually
 * accesses files to be reloaded.
 *
 * @version $Id: TestReloadingCombinedConfigurationBuilderFileBased.java 1571735 2014-02-25 16:37:52Z ggregory $
 */
public class TestReloadingCombinedConfigurationBuilderFileBased
{
    /** Constant for the prefix for XML configuration sources. */
    private static final String PROP_SRC = "override.xml";

    /** Constant for the prefix of the reload property. */
    private static final String PROP_RELOAD = "default.xmlReload";

    /** Constant for content of a XML configuration for reload tests. */
    private static final String RELOAD_CONTENT =
            "<config><default><xmlReload{1}>{0}</xmlReload{1}></default></config>";

    /** A helper object for managing temporary files. */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /** A helper object for creating builder parameters. */
    private Parameters parameters;

    /** The builder to be tested. */
    private ReloadingCombinedConfigurationBuilder builder;

    @Before
    public void setUp() throws Exception
    {
        parameters = new Parameters();
        builder = new ReloadingCombinedConfigurationBuilder();
    }

    /**
     * Adds a source for a configuration which can be reloaded to the definition
     * configuration.
     *
     * @param config the definition configuration
     * @param fileName the name of the file
     */
    private static void addReloadSource(Configuration config, String fileName)
    {
        config.addProperty(PROP_SRC + "(-1)[@fileName]", fileName);
        config.addProperty(PROP_SRC + "[@config-reload]", Boolean.TRUE);
    }

    /**
     * Helper method for writing a file.
     *
     * @param file the file to be written
     * @param content the file's content
     * @throws IOException if an error occurs
     */
    private static void writeFile(File file, String content) throws IOException
    {
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(new FileWriter(file));
            out.print(content);
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
    }

    /**
     * Helper method for writing a test file for reloading. The file will be
     * created in the test directory. It is also scheduled for automatic
     * deletion after the test.
     *
     * @param f the file to be written or <b>null</b> for creating a new one
     * @param content the content of the file
     * @return the <code>File</code> object for the test file
     * @throws IOException if an error occurs
     */
    private File writeReloadFile(File f, String content) throws IOException
    {
        File file = (f != null) ? f : folder.newFile();
        writeFile(file, content);
        return file;
    }

    /**
     * Writes a file for testing reload operations.
     *
     * @param f the file to be written or <b>null</b> for creating a new one
     * @param tagIdx the index of the tag
     * @param value the value of the reload test property
     * @return the file that was written
     * @throws IOException if an error occurs
     */
    private File writeReloadFile(File f, int tagIdx, int value)
            throws IOException
    {
        return writeReloadFile(f,
                MessageFormat.format(RELOAD_CONTENT, new Object[] {
                        value, tagIdx
                }));
    }

    /**
     * Returns the name of a test property.
     *
     * @param idx the index of the property
     * @return the test property with this index
     */
    private static String testProperty(int idx)
    {
        return PROP_RELOAD + idx;
    }

    /**
     * Tests whether a changed file is detected on disk.
     */
    @Test
    public void testReloadFromFile() throws ConfigurationException, IOException
    {
        File xmlConf1 = writeReloadFile(null, 1, 0);
        File xmlConf2 = writeReloadFile(null, 2, 0);
        ReloadingDetectorFactory detectorFactory =
                new ReloadingDetectorFactory()
                {
                    @Override
                    public ReloadingDetector createReloadingDetector(
                            FileHandler handler,
                            FileBasedBuilderParametersImpl params)
                            throws ConfigurationException
                    {
                        return new AlwaysReloadingDetector();
                    }
                };
        HierarchicalConfiguration defConf = new BaseHierarchicalConfiguration();
        addReloadSource(defConf, xmlConf1.getAbsolutePath());
        addReloadSource(defConf, xmlConf2.getAbsolutePath());
        builder.configure(parameters
                .combined()
                .setDefinitionBuilder(new ConstantConfigurationBuilder(defConf))
                .registerChildDefaultsHandler(
                        FileBasedBuilderProperties.class,
                        new CopyObjectDefaultHandler(
                                new FileBasedBuilderParametersImpl()
                                        .setReloadingDetectorFactory(detectorFactory))));
        CombinedConfiguration config = builder.getConfiguration();
        assertEquals("Wrong initial value (1)", 0,
                config.getInt(testProperty(1)));
        assertEquals("Wrong initial value (2)", 0,
                config.getInt(testProperty(2)));

        writeReloadFile(xmlConf1, 1, 1);
        builder.getReloadingController().checkForReloading(null);
        config = builder.getConfiguration();
        assertEquals("Updated value not reloaded (1)", 1,
                config.getInt(testProperty(1)));
        assertEquals("Value modified", 0, config.getInt(testProperty(2)));

        writeReloadFile(xmlConf2, 2, 2);
        builder.getReloadingController().checkForReloading(null);
        config = builder.getConfiguration();
        assertEquals("Wrong value for config 1", 1,
                config.getInt(testProperty(1)));
        assertEquals("Updated value not reloaded (2)", 2,
                config.getInt(testProperty(2)));
    }

    /**
     * Tests concurrent access to a reloading builder for combined
     * configurations.
     */
    @Test
    public void testConcurrentGetAndReload() throws Exception
    {
        final int threadCount = 4;
        final int loopCount = 100;
        ReloadingDetectorFactory detectorFactory =
                new ReloadingDetectorFactory()
                {
                    @Override
                    public ReloadingDetector createReloadingDetector(
                            FileHandler handler,
                            FileBasedBuilderParametersImpl params)
                            throws ConfigurationException
                    {
                        return new RandomReloadingDetector();
                    }
                };
        HierarchicalConfiguration defConf = new BaseHierarchicalConfiguration();
        defConf.addProperty("header.result.nodeCombiner[@config-class]",
                MergeCombiner.class.getName());
        defConf.addProperty("header.result.expressionEngine[@config-class]",
                XPathExpressionEngine.class.getName());
        addReloadSource(defConf, "configA.xml");
        addReloadSource(defConf, "configB.xml");
        Synchronizer sync = new ReadWriteSynchronizer();
        builder.configure(parameters
                .combined()
                .setDefinitionBuilder(new ConstantConfigurationBuilder(defConf))
                .setSynchronizer(sync)
                .registerChildDefaultsHandler(
                        BasicBuilderProperties.class,
                        new CopyObjectDefaultHandler(
                                new BasicBuilderParameters()
                                        .setSynchronizer(sync)))
                .registerChildDefaultsHandler(
                        FileBasedBuilderProperties.class,
                        new CopyObjectDefaultHandler(
                                new FileBasedBuilderParametersImpl()
                                        .setReloadingDetectorFactory(detectorFactory))));

        assertEquals("Wrong initial value", "100", builder.getConfiguration()
                .getString("/property[@name='config']/@value"));

        Thread testThreads[] = new Thread[threadCount];
        int failures[] = new int[threadCount];

        for (int i = 0; i < testThreads.length; ++i)
        {
            testThreads[i] = new ReloadThread(builder, failures, i, loopCount);
            testThreads[i].start();
        }

        int totalFailures = 0;
        for (int i = 0; i < testThreads.length; ++i)
        {
            testThreads[i].join();
            totalFailures += failures[i];
        }
        assertTrue(totalFailures + " failures Occurred", totalFailures == 0);
    }

    /**
     * Helper method for testing whether the builder's definition file can be
     * reloaded. This method expects that the test builder has been fully
     * initialized.
     *
     * @param defFile the path to the definition file
     * @throws IOException if an I/O error occurs
     * @throws ConfigurationException if a configuration-related error occurs
     * @throws InterruptedException if waiting is interrupted
     */
    private void checkReloadDefinitionFile(File defFile) throws IOException,
            ConfigurationException, InterruptedException
    {
        File src1 = writeReloadFile(null, 1, 0);
        File src2 = writeReloadFile(null, 1, 1);
        writeDefinitionFile(defFile, src1);
        CombinedConfiguration config = builder.getConfiguration();
        assertEquals("Wrong initial value", 0, config.getInt(testProperty(1)));

        // No change definition file
        boolean reloaded = false;
        for (int attempts = 0; attempts < 50 && !reloaded; attempts++)
        {
            writeDefinitionFile(defFile, src2);
            reloaded = builder.getReloadingController().checkForReloading(null);
            if (!reloaded)
            {
                Thread.sleep(100);
            }
        }
        assertTrue("Need for reload not detected", reloaded);
        config = builder.getConfiguration();
        assertEquals("Wrong reloaded value", 1, config.getInt(testProperty(1)));
    }

    /**
     * Tests whether a change in the definition file is detected and causes a
     * reload if a specific builder for the definition configuration is
     * provided.
     */
    @Test
    public void testReloadDefinitionFileExplicitBuilder()
            throws ConfigurationException, IOException, InterruptedException
    {
        File defFile = folder.newFile();
        builder.configure(parameters.combined().setDefinitionBuilder(
                new ReloadingFileBasedConfigurationBuilder<XMLConfiguration>(
                        XMLConfiguration.class).configure(parameters.xml()
                        .setReloadingRefreshDelay(0L).setFile(defFile))));
        checkReloadDefinitionFile(defFile);
    }

    /**
     * Tests whether the default definition builder is capable of detecting a
     * change in the definition configuration.
     */
    @Test
    public void testReloadDefinitionFileDefaultBuilder()
            throws ConfigurationException, IOException, InterruptedException
    {
        File defFile = folder.newFile();
        builder.configure(parameters.combined().setDefinitionBuilderParameters(
                parameters.xml().setReloadingRefreshDelay(0L).setFile(defFile)));
        checkReloadDefinitionFile(defFile);
    }

    /**
     * Writes a configuration definition file that refers to the specified file
     * source.
     *
     * @param defFile the target definition file
     * @param src the configuration source file to be referenced
     * @throws ConfigurationException if an error occurs
     */
    private void writeDefinitionFile(File defFile, File src)
            throws ConfigurationException
    {
        XMLConfiguration defConf = new XMLConfiguration();
        addReloadSource(defConf, src.getAbsolutePath());
        new FileHandler(defConf).save(defFile);
    }

    /**
     * A test builder class which always returns the same configuration.
     */
    private static class ConstantConfigurationBuilder extends
            BasicConfigurationBuilder<HierarchicalConfiguration>
    {
        private final HierarchicalConfiguration configuration;

        public ConstantConfigurationBuilder(HierarchicalConfiguration conf)
        {
            super(HierarchicalConfiguration.class);
            configuration = conf;
        }

        @Override
        public HierarchicalConfiguration getConfiguration()
                throws ConfigurationException
        {
            return configuration;
        }
    }

    /**
     * A thread class for testing concurrent reload operations.
     */
    private static class ReloadThread extends Thread
    {
        /** The builder to be queried. */
        private final ReloadingCombinedConfigurationBuilder builder;

        /** An array for reporting failures. */
        private final int[] failures;

        /** The index of this thread in the array with failures. */
        private final int index;

        /** The number of test operations. */
        private final int count;

        ReloadThread(ReloadingCombinedConfigurationBuilder bldr,
                int[] failures, int index, int count)
        {
            builder = bldr;
            this.failures = failures;
            this.index = index;
            this.count = count;
        }

        @Override
        public void run()
        {
            failures[index] = 0;
            for (int i = 0; i < count; i++)
            {
                try
                {
                    builder.getReloadingController().checkForReloading(null);
                    String value =
                            builder.getConfiguration().getString(
                                    "/property[@name='config']/@value");
                    if (value == null || !value.equals("100"))
                    {
                        ++failures[index];
                    }
                }
                catch (Exception ex)
                {
                    ++failures[index];
                }
            }
        }
    }
}