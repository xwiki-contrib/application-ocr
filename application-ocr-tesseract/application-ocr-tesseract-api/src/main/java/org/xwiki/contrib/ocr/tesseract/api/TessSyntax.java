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
package org.xwiki.contrib.ocr.tesseract.api;

import java.util.Arrays;
import java.util.List;

import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxType;
import org.xwiki.stability.Unstable;

/**
 * Declare a list of output syntaxes supported by Tesseract.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public final class TessSyntax
{
    /**
     * Plain text.
     */
    public static final Syntax PLAIN_1_0 = Syntax.PLAIN_1_0;

    /**
     * hOCR markup.
     */
    public static final Syntax HOCR_1_2 = new Syntax(new SyntaxType("hocr", "hOCR"), "1.2");

    /**
     * Builds a new {@link TessSyntax}.
     */
    private TessSyntax()
    {

    }

    /**
     * @return a list of every supported syntax by Tesseract documents.
     */
    public static List<Syntax> getAllSupportedSyntaxes()
    {
        return Arrays.asList(PLAIN_1_0, HOCR_1_2);
    }
}
