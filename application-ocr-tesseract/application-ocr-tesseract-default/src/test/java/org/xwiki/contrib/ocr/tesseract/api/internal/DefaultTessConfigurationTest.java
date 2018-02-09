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

import javax.inject.Provider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
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

    private XWikiContext xWikiContext;

    private XWiki xwiki;

    private XWikiDocument configurationDocument;

    @Before
    public void setUp() throws Exception
    {
        configurationSource = mocker.registerMockComponent(ConfigurationSource.class);
        Provider<XWikiContext> xWikiContextProvider = mocker.getInstance(XWikiContext.TYPE_PROVIDER);

        xWikiContext = mock(XWikiContext.class);
        xwiki = mock(XWiki.class);
        configurationDocument = mock(XWikiDocument.class);

        when(xWikiContextProvider.get()).thenReturn(xWikiContext);
        when(xWikiContext.getWiki()).thenReturn(xwiki);
        when(xwiki.getDocument(any(DocumentReference.class), eq(xWikiContext))).thenReturn(configurationDocument);
    }

    /**
     * Mock the configurationDocument to return a mocked BaseObject when searching for a configuration XObject.
     */
    private BaseObject mockDocumentBaseObject() throws Exception
    {
        BaseObject baseObject = mock(BaseObject.class);
        when(configurationDocument.getXObject(any(EntityReference.class))).thenReturn(baseObject);
        return baseObject;
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
        when(configurationSource.getProperty("tesseract.dataPath", "./data"))
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
