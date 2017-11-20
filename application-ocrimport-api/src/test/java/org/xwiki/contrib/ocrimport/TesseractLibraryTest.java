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
package org.xwiki.contrib.ocrimport;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.junit.Test;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;
import static org.junit.Assert.assertTrue;

/**
 * Test if the Tesseract OCR library is properly working.
 *
 * @since 1.0
 * @version $Id$
 */
public class TesseractLibraryTest
{
    private static final String DATA_PATH = "./target/";

    private static final String LANG = "eng";

    @Test
    public void testSampleDocumentPNG() throws Exception
    {
        TessBaseAPI api = new TessBaseAPI();

        if (api.Init(DATA_PATH, LANG) != 0) {
            throw new InstantiationException("Unable to instantiate Tesseract API.");
        }

        PIX image = pixRead("src/test/resources/samples/sampleText.png");

        api.SetImage(image);
        // Get OCR result
        BytePointer outText = api.GetUTF8Text();
        String string = outText.getString();
        assertTrue(!string.isEmpty());

        api.End();
        outText.deallocate();
        pixDestroy(image);
    }
}
