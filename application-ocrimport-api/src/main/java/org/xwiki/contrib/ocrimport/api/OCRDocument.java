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
package org.xwiki.contrib.ocrimport.api;

import org.bytedeco.javacpp.tesseract.TessBaseAPI;

/**
 * Describe a file parsed using OCR.
 * Please note that once an {@link OCRDocument} isn't meant to be used anymore, {@link #dispose()} should be called.
 *
 * @version $Id$
 * @since 1.0
 */
public class OCRDocument
{
    private TessBaseAPI api;

    /**
     * Builds a new {@link OCRDocument} using the given {@link TessBaseAPI}. This API should have already parsed
     * the file used by this document.
     *
     * @param api the {@link TessBaseAPI} to use
     */
    public OCRDocument(TessBaseAPI api)
    {
        this.api = api;
    }

    /**
     * @return the raw content extracted from the document source
     */
    public String getPlainContent()
    {
        return api.GetUTF8Text().getString();
    }

    /**
     * Dispose of the API.
     */
    public void dispose()
    {
        api.End();
    }
}
