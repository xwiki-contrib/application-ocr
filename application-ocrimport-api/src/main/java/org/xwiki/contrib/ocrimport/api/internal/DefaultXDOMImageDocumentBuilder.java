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
package org.xwiki.contrib.ocrimport.api.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.compress.utils.IOUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocrimport.api.OCRImporterException;
import org.xwiki.contrib.ocrimport.api.XDOMDocument;
import org.xwiki.contrib.ocrimport.api.XDOMDocumentBuilder;
import org.xwiki.rendering.block.RawBlock;
import org.xwiki.rendering.internal.parser.XDOMBuilder;
import org.xwiki.rendering.syntax.Syntax;

import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixReadMem;

/**
 * Implementation of {@link XDOMDocumentBuilder} used to build {@link XDOMDocument} from image files.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named("image")
public class DefaultXDOMImageDocumentBuilder implements XDOMDocumentBuilder
{
    @Override
    public XDOMDocument build(InputStream fileStream, TessBaseAPI api)
            throws OCRImporterException
    {
        byte[] fileBytes;
        PIX image = null;
        XDOMBuilder builder = new XDOMBuilder();

        try {
            fileBytes = IOUtils.toByteArray(fileStream);
            image = pixReadMem(fileBytes, fileBytes.length);

            BytePointer documentContent = api.GetUTF8Text();

            builder.startBlockList();
            builder.addBlock(new RawBlock(documentContent.getString("UTF-8"), Syntax.PLAIN_1_0));
            builder.endBlockList();
        } catch (UnsupportedEncodingException e) {
            throw new OCRImporterException(String.format("Failed to create document XDOM : [%s]", e));
        } catch (IOException e) {
            throw new OCRImporterException(String.format("Unable to process input stream : [%s]", e));
        } finally {
            pixDestroy(image);
        }

        return new XDOMDocument(builder.getXDOM());
    }
}
