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
package org.xwiki.contrib.ocr.tesseract.api.internal;

import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultTessBaseAPIProvider}.
 *
 * @version $Id$
 * @since 1.0
 */
public class DefaultTessBaseAPIProviderTest
{
    @Rule
    public final MockitoComponentMockingRule<DefaultTessBaseAPIProvider> mocker =
            new MockitoComponentMockingRule<>(DefaultTessBaseAPIProvider.class);

    private TessConfiguration tessConfiguration;

    @Before
    public void setUp() throws Exception
    {
        tessConfiguration = mocker.registerMockComponent(TessConfiguration.class);
        when(tessConfiguration.dataPath()).thenReturn("./target/");
    }

    @Test
    public void get() throws Exception
    {
        when(tessConfiguration.defaultLangage()).thenReturn("eng");
        TessBaseAPI api = mocker.getComponentUnderTest().get();

        assertEquals("./target/tessdata/", api.GetDatapath().getString());
        assertEquals("eng", api.GetInitLanguagesAsString().getString());
    }

    @Test
    public void getWithLangage() throws Exception
    {
        TessBaseAPI api = mocker.getComponentUnderTest().get("eng");

        assertEquals("./target/tessdata/", api.GetDatapath().getString());
        assertEquals("eng", api.GetInitLanguagesAsString().getString());
    }
}
