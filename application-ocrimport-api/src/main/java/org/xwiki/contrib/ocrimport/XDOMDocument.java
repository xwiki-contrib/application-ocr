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

import org.xwiki.rendering.block.XDOM;
import org.xwiki.stability.Unstable;

/**
 * Represents an OCR document being imported.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class XDOMDocument
{
    /**
     * A {@link XDOM} tree representing the document content.
     */
    private XDOM xdom;

    /**
     * Constructs a new {@link XDOMDocument}.
     *
     * @param xdom the content of the document
     */
    public XDOMDocument(XDOM xdom)
    {
        this.xdom = xdom;
    }

    /**
     * @return the {@link XDOM} content of the document
     */
    public XDOM getDocumentContent()
    {
        return this.xdom;
    }
}
