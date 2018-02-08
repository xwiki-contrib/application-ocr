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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.mime.MediaType;
import org.xwiki.contrib.ocr.api.OCRException;

/**
 * Handles media type informations and actions on a given file.
 *
 * @version $Id$
 * @since 1.0
 */
public class OCRMediaTypeChecker
{
    /**
     * Contains image media types that are supported by the Leptonica library.
     * As Tesseract relies on this library to parse its images, those are also the only image media types
     * supported by Tesseract.
     *
     * Media types contained in this list have been sourced from the Mozilla MDN documentation available here:
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types
     */
    private static final List<MediaType> IMAGE_MEDIA_TYPES = Arrays.asList(
            MediaType.parse("image/png"),
            MediaType.parse("image/jpeg"),
            MediaType.parse("image/tiff"),
            MediaType.parse("image/webp")
    );

    /**
     * The media type of a PDF file.
     */
    private static final MediaType PDF_MEDIA_TYPE = MediaType.parse("application/pdf");

    private MediaType fileMediaType;

    /**
     * Constructs a new {@link OCRMediaTypeChecker}.
     *
     * @param file the file to check
     * @param fileName a file name, used as a hint to guess the content type
     * @throws OCRException if the given file has a format that is not supported by the application
     */
    public OCRMediaTypeChecker(InputStream file, String fileName) throws OCRException
    {
        Detector detector = new DefaultDetector();
        Tika tika = new Tika(detector);

        try {
            this.fileMediaType = MediaType.parse(tika.detect(file, fileName));
        } catch (IOException e) {
            throw new OCRException("Unable to parse file media type.", e);
        }

        if (!(isImage() || isPDF())) {
            throw new OCRException(String.format("The file media type [%s] is not supported.", this.fileMediaType));
        }
    }

    /**
     * Constructs a new {@link OCRMediaTypeChecker}.
     *
     * @param file the file to check
     * @throws OCRException if the given file has a format that is not supported by the application
     */
    public OCRMediaTypeChecker(InputStream file) throws OCRException
    {
        this(file, null);
    }

    /**
     * @return the computed file media type
     */
    public MediaType getMediaType()
    {
        return this.fileMediaType;
    }

    /**
     * @return true if the given file is an image
     */
    public boolean isImage()
    {
        return IMAGE_MEDIA_TYPES.contains(fileMediaType);
    }

    /**
     * @return true if the given file is a PDF
     */
    public boolean isPDF()
    {
        return PDF_MEDIA_TYPE.equals(fileMediaType);
    }
}
