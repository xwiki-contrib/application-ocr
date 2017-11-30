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
package org.xwiki.contrib.ocr.filter.internal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.api.TessBaseAPIProvider;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixReadMem;

/**
 * Handle the different steps for parsing a file using the Tesseract OCR library.
 *
 * @version $Id$
 * @since 1.0
 */
@Component(roles = OCRParser.class)
@Singleton
public class OCRParser
{
    @Inject
    private TessBaseAPIProvider apiProvider;

    /**
     * Parse the given image file and return its contents.
     *
     * @param fileBytes the file to parse
     * @return the generated document
     * @throws OCRException if an error occurs during the importation
     */
    public OCRDocument parseImage(byte[] fileBytes) throws OCRException
    {
        lept.PIX image = null;
        TessBaseAPI api = apiProvider.get();

        try {
            image = pixReadMem(fileBytes, fileBytes.length);
            api.SetImage(image);
        } finally {
            pixDestroy(image);
        }

        return new OCRDocument(api);
    }
}
