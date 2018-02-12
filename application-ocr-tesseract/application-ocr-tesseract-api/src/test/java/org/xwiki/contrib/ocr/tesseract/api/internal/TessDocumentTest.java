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

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.Before;
import org.junit.Test;
import org.xwiki.contrib.ocr.api.OCRDocumentPage;
import org.xwiki.contrib.ocr.tesseract.api.TessSyntax;
import org.xwiki.rendering.syntax.Syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TessDocument}.
 *
 * @version $Id$
 * @since 1.0
 */
public class TessDocumentTest
{
    private TessDocument document;

    private OCRDocumentPage mockPage1;

    private OCRDocumentPage mockPage2;

    private OCRDocumentPage mockPage3;

    @Before
    public void setUp() throws Exception
    {
        document = new TessDocument();

        mockPage1 = mock(OCRDocumentPage.class);
        when(mockPage1.getSupportedSyntaxes()).thenReturn(Arrays.asList(TessSyntax.PLAIN_1_0));
        when(mockPage1.getContentAs(TessSyntax.PLAIN_1_0)).thenReturn("page1");

        mockPage2 = mock(OCRDocumentPage.class);
        when(mockPage2.getContentAs(any(Syntax.class))).thenReturn("page2");

        mockPage3 = mock(OCRDocumentPage.class);
        when(mockPage3.getContentAs(TessSyntax.PLAIN_1_0)).thenReturn("page3");
    }

    @Test
    public void supportedSyntaxesWithBlankDocument() throws Exception
    {
        assertEquals(TessSyntax.getAllSupportedSyntaxes(), document.getSupportedSyntaxes());
    }

    @Test
    public void supportedSyntaxesWithPages() throws Exception
    {
        document.appendPage(mockPage1, -1);
        List<Syntax> supportedSyntaxes = document.getSupportedSyntaxes();
        assertEquals(1, supportedSyntaxes.size());
        assertTrue(supportedSyntaxes.contains(TessSyntax.PLAIN_1_0));
    }

    @Test
    public void getContentAsWithNoSeparators() throws Exception
    {
        document.appendPage(mockPage2, -1);
        document.appendPage(mockPage1, 0);

        assertEquals("page1page2", document.getContentAs(TessSyntax.PLAIN_1_0));
    }

    @Test
    public void getContentAsWithSeparators() throws Exception
    {
        document.appendPage(mockPage1, -1);
        document.appendPage(mockPage2, -1);
        document.appendPage(mockPage3, -1);

        BiFunction<Integer, Integer, String> separator = (x, y) -> String.format("|%s|%s|", x, y);
        assertEquals("page1|0|3|page2|1|3|page3|2|3|",
                document.getContentAs(TessSyntax.PLAIN_1_0, separator));
    }

    @Test
    public void getComentAsWithHOCRWrapper() throws Exception
    {
        document.appendPage(mockPage2, -1);

        assertEquals("<div class=\"ocr_document\">page2</div>", document.getContentAs(TessSyntax.HOCR_1_2));
    }

    @Test
    public void getPage() throws Exception
    {
        document.appendPage(mockPage1, -1);
        document.appendPage(mockPage2, -1);
        document.appendPage(mockPage3, -1);

        assertEquals(mockPage1, document.getPage(0));
        assertEquals(mockPage3, document.getPage(2));
    }

    @Test
    public void getPageCount() throws Exception
    {
        document.appendPage(mockPage1, -1);
        document.appendPage(mockPage2, -1);

        assertEquals(2, document.getPageCount());
    }
}
