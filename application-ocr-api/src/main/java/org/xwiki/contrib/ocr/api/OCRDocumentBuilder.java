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

import java.awt.Image;

import org.xwiki.component.annotation.Role;

/**
 * Provide a set of methods in order to parse a set of {@link java.awt.Image} into a {@link OCRDocument}.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
public interface OCRDocumentBuilder
{
    /**
     * Add a new page to the end of the  {@link OCRDocument} being built.
     * The {@link Image} should first be converted into an {@link OCRDocumentPage} and then inserted into
     * the {@link OCRDocument}.
     *
     * @param page the page to add
     * @throws OCRException if an error happened
     */
    void appendPage(Image page) throws OCRException;

    /**
     * Add a new page to the {@link OCRDocument} being built at the given position. As for handling arrays, document
     * pages should be counted starting with the page number 0.
     * If the position is less than zero or greater or equal than the number of pages, the page is added at the end
     * of the document.
     *
     * @param page the page to add
     * @param position the position of the page in the document
     * @throws OCRException if an error happened
     */
    void appendPage(Image page, int position) throws OCRException;

    /**
     * Just as {@link #appendPage(Image)}, add a new page at the end of the file in which the content comes from
     * the given byte[].
     *
     * @param page the page to add
     * @throws OCRException if an error happened
     */
    void appendPage(byte[] page) throws OCRException;

    /**
     * Just as {@link #appendPage(Image, int)}, add a new page at the given position. The position is 0-based and
     * an incorrect position will cause the page to be inserted at the end of the document.
     *
     * @param page the page to add
     * @param position the position of the page in the document
     * @throws OCRException if an error happened
     */
    void appendPage(byte[] page, int position) throws OCRException;

    /**
     * Get the {@link OCRDocument} that is built by this builder.
     *
     * @return the document being built
     */
    OCRDocument getDocument();

    /**
     * Free the resources used by the OCR library (if any).
     *
     * @throws OCRException if an error happened
     */
    void dispose() throws OCRException;
}
