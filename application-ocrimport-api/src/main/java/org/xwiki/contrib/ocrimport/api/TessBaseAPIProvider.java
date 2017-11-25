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
import org.xwiki.component.annotation.Role;
import org.xwiki.stability.Unstable;

/**
 * Allow a quick access to a {@link TessBaseAPI}.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
@Unstable
public interface TessBaseAPIProvider
{
    /**
     * Get an instantiated {@link TessBaseAPI} using the default language and the default data path.
     *
     * @return a new instance of {@link TessBaseAPI}
     * @throws OCRImporterException if the instantiation failed
     */
    TessBaseAPI get() throws OCRImporterException;

    /**
     * Same as {@link #get()}, but with specifying the language that will be used by the {@link TessBaseAPI}.
     *
     * @param language the language that should be used by the {@link TessBaseAPI}
     * @return a new instance of {@link TessBaseAPI}
     * @throws OCRImporterException if the instantiation failed
     */
    TessBaseAPI get(String language) throws OCRImporterException;

    /**
     * Same as {@link #get()}, but with specifying the language and the data path that will be used by the
     * {@link TessBaseAPI}.
     *
     * @param language the language that should be used by the {@link TessBaseAPI}
     * @param dataPath the path containing the {@link TessBaseAPI} data files
     * @return a new instance of {@link TessBaseAPI}
     * @throws OCRImporterException if the instantiation failed
     */
    TessBaseAPI get(String language, String dataPath) throws OCRImporterException;
}
