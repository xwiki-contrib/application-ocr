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

import java.awt.Image;

import org.bytedeco.javacpp.tesseract;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.api.OCRDocument;
import org.xwiki.contrib.ocr.api.OCRDocumentBuilder;

/**
 * Implementation of {@link OCRDocumentBuilder} for the Tesseract library.
 *
 * @version $Id$
 * @since 1.0
 */
public class TessDocumentBuilder implements OCRDocumentBuilder
{
    private tesseract.TessBaseAPI api;

    private TessDocument document;

    /**
     * Constructs a new {@link TessDocumentBuilder}.
     *
     * @param api the {@link org.bytedeco.javacpp.tesseract.TessBaseAPI} that should be used for parsing documents
     */
    public TessDocumentBuilder(tesseract.TessBaseAPI api)
    {
        this.api = api;
        this.document = new TessDocument();
    }

    @Override
    public void appendPage(Image page) throws OCRException
    {
        appendPage(page, -1);
    }

    @Override
    public void appendPage(Image page, int position) throws OCRException
    {
        document.appendPage(TessParser.parseImage(api, page), position);
    }

    @Override
    public void appendPage(byte[] page) throws OCRException
    {
        appendPage(page, -1);
    }

    @Override
    public void appendPage(byte[] page, int position) throws OCRException
    {
        document.appendPage(TessParser.parseImage(api, page), position);
    }

    @Override
    public OCRDocument getDocument()
    {
        return document;
    }

    @Override
    public void dispose() throws OCRException
    {
        api.End();
    }
}
