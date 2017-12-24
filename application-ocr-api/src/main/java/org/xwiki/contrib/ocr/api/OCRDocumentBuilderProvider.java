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
package org.xwiki.contrib.ocr.api;

import org.xwiki.component.annotation.Role;

/**
 * Provides {@link OCRDocumentBuilder} for a specific OCR library.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
public interface OCRDocumentBuilderProvider
{
    /**
     * Instantiate a new {@link OCRDocumentBuilder} using the given language.
     *
     * @param language the language to use during the recognition
     * @return a new builder
     * @throws OCRException if an error happens during the instantiation
     */
    OCRDocumentBuilder getBuilder(String language) throws OCRException;

    /**
     * Instantiate a new {@link OCRDocumentBuilder} using the default language set in the configuration.
     *
     * @return the new builder
     * @throws OCRException if an error happens during the instantiation
     */
    OCRDocumentBuilder getBuilder() throws OCRException;
}
