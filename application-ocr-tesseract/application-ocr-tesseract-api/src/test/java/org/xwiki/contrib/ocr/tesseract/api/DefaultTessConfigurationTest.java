/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.ocr.tesseract.api;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.contrib.ocr.tesseract.api.internal.DefaultTessConfiguration;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultTessConfiguration}.
 *
 * @version $Id$
 * @since 1.0
 */
public class DefaultTessConfigurationTest
{
    @Rule
    public final MockitoComponentMockingRule<DefaultTessConfiguration> mocker =
            new MockitoComponentMockingRule<>(DefaultTessConfiguration.class);

    private ConfigurationSource configurationSource;

    @Before
    public void setUp() throws Exception
    {
        configurationSource = mocker.registerMockComponent(ConfigurationSource.class);
    }

    @Test
    public void defaultLangage() throws Exception
    {
        when(configurationSource.getProperty("tesseract.defaultLangage", "eng")).thenReturn("ron");
        assertEquals("ron", mocker.getComponentUnderTest().defaultLangage());
    }

    @Test
    public void dataPath() throws Exception
    {
        when(configurationSource.getProperty("tesseract.dataPath", "./data/"))
                .thenReturn("myfolder");
        assertEquals("myfolder", mocker.getComponentUnderTest().dataPath());
    }

    @Test
    public void allowAutoDownload() throws Exception
    {
        when(configurationSource.getProperty("tesseract.allowAutoDownload", true)).thenReturn(false);
        assertFalse(mocker.getComponentUnderTest().allowAutoDownload());
    }
}