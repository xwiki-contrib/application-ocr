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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiFunction;

import org.xwiki.contrib.ocr.api.OCRDocument;
import org.xwiki.contrib.ocr.api.OCRDocumentPage;
import org.xwiki.contrib.ocr.tesseract.api.TessSyntax;
import org.xwiki.rendering.syntax.Syntax;

/**
 * Tesseract-specific implementation of {@link OCRDocument}.
 *
 * @version $Id$
 * @since 1.0
 */
public class TessDocument implements OCRDocument
{
    private List<OCRDocumentPage> pages;

    /**
     * Constructs a new {@link TessDocument}.
     */
    public TessDocument()
    {
        pages = new ArrayList<>();
    }

    /**
     * Adds a new {@link OCRDocumentPage} to the document. If the position parameter is not in the range of pages
     * contained in the document, the page will be added at the end of the document.
     *
     * @param page the page to add
     * @param position its position in the document
     */
    public void appendPage(OCRDocumentPage page, int position)
    {
        if (position < 0 || position >= pages.size()) {
            pages.add(page);
        } else {
            pages.add(position, page);
        }
    }

    @Override
    public List<Syntax> getSupportedSyntaxes()
    {
        List<Syntax> supportedSyntaxes = new ArrayList<>(TessSyntax.getAllSupportedSyntaxes());

        for (OCRDocumentPage page : pages) {
            supportedSyntaxes.retainAll(page.getSupportedSyntaxes());
        }

        return supportedSyntaxes;
    }

    @Override
    public String getContentAs(Syntax syntax) throws UnsupportedOperationException
    {
        return getContentAs(syntax, null);
    }

    @Override
    public String getContentAs(Syntax syntax, BiFunction<Integer, Integer, String> separator)
            throws UnsupportedOperationException
    {
        StringBuilder builder = new StringBuilder();
        ListIterator<OCRDocumentPage> it = pages.listIterator();

        // The exported pages will not form a single root element ; we therefore have to build it "manually"
        // in order to generate a valid XML.
        if (syntax.equals(TessSyntax.HOCR_1_2)) {
            builder.append("<div class=\"ocr_document\">");
        }

        while (it.hasNext()) {
            builder.append(it.next().getContentAs(syntax));

            if (separator != null) {
                builder.append(separator.apply(it.previousIndex(), pages.size()));
            }
        }

        if (syntax.equals(TessSyntax.HOCR_1_2)) {
            builder.append("</div>");
        }

        return builder.toString();
    }

    @Override
    public OCRDocumentPage getPage(int index) throws IndexOutOfBoundsException
    {
        return pages.get(index);
    }

    @Override
    public int getPageCount()
    {
        return pages.size();
    }
}
