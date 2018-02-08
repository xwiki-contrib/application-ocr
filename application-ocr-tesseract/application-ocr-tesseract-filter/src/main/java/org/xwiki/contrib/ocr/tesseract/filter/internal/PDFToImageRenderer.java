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
package org.xwiki.contrib.ocr.tesseract.filter.internal;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.xwiki.contrib.ocr.api.OCRException;

/**
 * Uses Apache PDFBox in order to render documents in a specific format into a {@link Image}.
 *
 * @version $Id$
 * @since 1.0
 */
public final class PDFToImageRenderer
{
    private PDFToImageRenderer() { }

    /**
     * Renders the given {@link InputStream} into a list of {@link Image} (one image per document page) using the
     * given resolution.
     * If the resolution is null, 300 DPI will be used.
     *
     * @param file the document to render
     * @param resolution the resolution of the document (in DPI)
     * @return the rendered images
     * @throws OCRException if an error happens
     */
    public static List<Image> renderPDF(InputStream file, Integer resolution) throws OCRException
    {
        List<Image> images = new ArrayList<>();
        int dpi = (resolution == null) ? 300 : resolution;

        try {
            PDDocument document = PDDocument.load(file);

            PDFRenderer pdfRenderer = new PDFRenderer(document);
            int pageCounter = 0;
            for (PDPage page : document.getPages()) {
                images.add(pdfRenderer.renderImageWithDPI(pageCounter, dpi, ImageType.RGB));
                pageCounter++;
            }

            document.close();

            return images;
        } catch (IOException e) {
            throw new OCRException("Unable to render the file as images.", e);
        }
    }

    /**
     * As {@link #renderPDF(InputStream, Integer)}, render the given document into multiple images corresponding to the
     * document pages.
     *
     * @param file the document to render
     * @return the rendered images
     * @throws OCRException if an error happens
     */
    public static List<Image> renderPDF(InputStream file) throws OCRException
    {
        return PDFToImageRenderer.renderPDF(file, null);
    }
}
