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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.bytedeco.javacpp.tesseract;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xwiki.rendering.syntax.Syntax;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link TessParser}.
 *
 * @version $Id$
 * @since 1.0
 */
public class TessParserTest
{
    private tesseract.TessBaseAPI api;

    private File helloFile;

    @Before
    public void setUp() throws Exception
    {
        api = new tesseract.TessBaseAPI();

        if (api.Init("./target/", "eng") != 0) {
            throw new InstantiationException("Unable to instantiate Tesseract API.");
        }

        helloFile = new File("src/test/resources/samples/hello.png");
    }

    @After
    public void tearDown() throws Exception
    {
        api.End();
    }

    @Test
    public void parseImageWithImage() throws Exception
    {
        TessDocumentPage page = TessParser.parseImage(api, ImageIO.read(helloFile));
        assertEquals("hello", page.getContentAs(Syntax.PLAIN_1_0).toLowerCase().trim());
    }

    @Test
    public void parseImageWithByteArray() throws Exception
    {
        byte[] helloImage = IOUtils.toByteArray(new FileInputStream(helloFile));

        TessDocumentPage page = TessParser.parseImage(api, helloImage);
        assertEquals("hello", page.getContentAs(Syntax.PLAIN_1_0).toLowerCase().trim());
    }
}
