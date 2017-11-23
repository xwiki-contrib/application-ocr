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

import java.io.InputStream;

import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.component.annotation.Role;
import org.xwiki.stability.Unstable;

/**
 * Component responsible for building {@link XDOMDocument} from image or PDF files using the Tesseract library.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
@Unstable
public interface XDOMDocumentBuilder
{
    /**
     * Builds a {@link XDOMDocument} corresponding to the given office document.
     *
     * @param fileStream {@link InputStream} corresponding to the file to import
     * @param api the {@link TessBaseAPI} to use during the importation
     * @return an {@link XDOMDocument} corresponding to the given file
     * @throws OCRImporterException if an error occurs while performing the import operation
     */
    XDOMDocument build(InputStream fileStream, TessBaseAPI api) throws OCRImporterException;
}
