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

import java.util.List;
import java.util.function.BiFunction;

import org.xwiki.rendering.syntax.Syntax;

/**
 * Define a document parsed using OCR.
 *
 * @version $Id$
 * @since 1.0
 */
public interface OCRDocument
{
    /**
     * @return a list of {@link Syntax} supported by the document for exporting content.
     *
     * Note that implementations of {@link OCRDocument} should check if their pages are compatible with the
     * export syntaxes returned by this method.
     */
    List<Syntax> getSupportedSyntaxes();

    /**
     * Get the full document content in the given {@link Syntax} format.
     *
     * @param syntax the syntax that should be used to export the document content
     * @return the full content of the document
     * @throws UnsupportedOperationException if the given {@link Syntax} is not supported
     */
    String getContentAs(Syntax syntax) throws UnsupportedOperationException;

    /**
     * Uses the given {@link BiFunction} to return the full content of the document, where pages are separated
     * by the separator. The separator should take as input the number of the page before him and the total number of
     * pages in the document.
     * If no separator is given (null), it will be ignored.
     *
     * Example: <pre>(pageNbr, total) -> String.format("End of page {} out of {}.\n", pageNbr, total)</pre>
     *
     * @param syntax the syntax that should be used to export the document content
     * @param separator the separator to use
     * @return the document content
     * @throws UnsupportedOperationException if the given {@link Syntax} is not supported
     */
    String getContentAs(Syntax syntax, BiFunction<Integer, Integer, String> separator)
            throws UnsupportedOperationException;

    /**
     * Get the nth page of the document. Note that the index is zero based ; therefore, getting the first page
     * of the document should be done by calling <pre>document.getPage(0);</pre>.
     *
     * @param index the index of the page
     * @return the page corresponding to the index
     * @throws IndexOutOfBoundsException if the index does not corresponds to an existing page
     */
    OCRDocumentPage getPage(int index) throws IndexOutOfBoundsException;

    /**
     * @return the total number of pages contained in the document
     */
    int getPageCount();
}
