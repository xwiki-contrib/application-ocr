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

import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.stability.Unstable;

/**
 * We consider a page to be an image that went through OCR. This page can be bound to a document or not.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public interface OCRDocumentPage
{
    /**
     * @return a list of {@link Syntax} supported by the page for exporting content.
     */
    List<Syntax> getSupportedSyntaxes();

    /**
     * Get the full content of the page using a specific {@link Syntax}.
     *
     * @param syntax the syntax that should be used in order to export the content.
     * @return the page content as the given syntax
     * @throws UnsupportedOperationException if the given syntax is not supported by the page.
     */
    String getContentAs(Syntax syntax) throws UnsupportedOperationException;
}
