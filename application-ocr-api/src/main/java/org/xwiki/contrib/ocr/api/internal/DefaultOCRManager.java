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
package org.xwiki.contrib.ocr.api.internal;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.compress.utils.IOUtils;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.api.OCRManager;
import org.xwiki.contrib.ocr.api.TessBaseAPIProvider;
import org.xwiki.contrib.ocr.api.OCRDocument;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixReadMem;

/**
 * This is the default implementation for {@link OCRManager}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultOCRManager implements OCRManager
{
    @Inject
    private TessBaseAPIProvider apiProvider;

    @Override
    public OCRDocument parseImage(InputStream fileStream) throws OCRException
    {
        byte[] fileBytes;
        lept.PIX image = null;
        TessBaseAPI api = apiProvider.get();

        try {
            fileBytes = IOUtils.toByteArray(fileStream);
            image = pixReadMem(fileBytes, fileBytes.length);
        } catch (IOException e) {
            throw new OCRException(String.format("Unable to process input stream : [%s]", e));
        } finally {
            pixDestroy(image);
        }

        return new OCRDocument(api);
    }
}
