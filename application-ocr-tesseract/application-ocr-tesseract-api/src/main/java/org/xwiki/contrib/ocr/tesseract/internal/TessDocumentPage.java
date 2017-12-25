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
package org.xwiki.contrib.ocr.tesseract.internal;

import java.util.List;

import org.bytedeco.javacpp.tesseract;
import org.xwiki.contrib.ocr.api.OCRDocumentPage;
import org.xwiki.contrib.ocr.tesseract.TessSyntax;
import org.xwiki.rendering.syntax.Syntax;

/**
 * Tesseract specific implementation of {@link OCRDocumentPage}.
 *
 * @version $Id$
 * @since 1.0
 */
public class TessDocumentPage implements OCRDocumentPage
{
    private String plainContent;

    private String hOCRContent;

    /**
     * Builds a new {@link TessDocumentPage} using the given {@link org.bytedeco.javacpp.tesseract.TessBaseAPI}
     * results.
     *
     * @param api the Tesseract API containing the results of the OCR recognition
     */
    public TessDocumentPage(tesseract.TessBaseAPI api)
    {
        this.hOCRContent = api.GetHOCRText(0).getString();
        this.plainContent = api.GetUTF8Text().getString();
    }

    @Override
    public List<Syntax> getSupportedSyntaxes()
    {
        return TessSyntax.getAllSupportedSyntaxes();
    }

    @Override
    public String getContentAs(Syntax syntax) throws UnsupportedOperationException
    {
        if (TessSyntax.PLAIN_1_0.equals(syntax)) {
            return plainContent;
        } else if (TessSyntax.HOCR_1_2.equals(syntax)) {
            return hOCRContent;
        } else {
            throw new UnsupportedOperationException(String.format("Unsupported syntax [{}].", syntax.toIdString()));
        }
    }
}
