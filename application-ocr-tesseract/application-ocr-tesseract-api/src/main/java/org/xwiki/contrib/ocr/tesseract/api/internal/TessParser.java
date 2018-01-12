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

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.contrib.ocr.tesseract.api.TessException;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixReadMem;

/**
 * Handle the different steps for parsing a file using the Tesseract OCR library.
 *
 * @version $Id$
 * @since 1.0
 */
public final class TessParser
{
    /**
     * Empty constructor.
     */
    private TessParser()
    {

    }

    /**
     * Parse the given image (as a byte array) and return its contents as a {@link TessDocumentPage}.
     *
     * @param api the API to use
     * @param image the image to parse
     * @return the generated document
     * @throws TessException if an error occurs during the importation
     */
    public static TessDocumentPage parseImage(TessBaseAPI api, byte[] image) throws TessException
    {
        lept.PIX leptImage = null;

        try {
            leptImage = pixReadMem(image, image.length);
            api.SetImage(leptImage);
        } finally {
            pixDestroy(leptImage);
        }

        return new TessDocumentPage(api);
    }

    /**
     * Parse the given image file and return its contents.
     *
     * @param api the API to use
     * @param image the image to parse
     * @return the generated document
     * @throws TessException if an error occurs during the importation
     */
    public static TessDocumentPage parseImage(TessBaseAPI api, Image image) throws TessException
    {
        return parseImage(api, toByteArray(image));
    }

    /**
     * Converts a given {@link Image} to a byte array.
     *
     * @param image the image to convert
     * @return the associated byte array
     * @throws TessException if the conversion failed
     */
    private static byte[] toByteArray(Image image) throws TessException
    {
        BufferedImage bufferedImage = toBufferedImage(image);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            throw new TessException("Failed to convert the given image to byte array.", e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Converts a given Image into a BufferedImage.
     * Credits to https://code.google.com/archive/p/game-engine-for-java/.
     *
     * @param image The Image to be converted
     * @return The converted BufferedImage
     */
    private static BufferedImage toBufferedImage(Image image)
    {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }
}
