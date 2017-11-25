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

import java.io.InputStream;

import org.xwiki.component.annotation.Role;
import org.xwiki.stability.Unstable;

/**
 * Handle the different steps for importing a new document in the wiki using the Tesseract OCR library.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
@Unstable
public interface OCRImportManager
{
    /**
     * Parse the given image file and return its contents.
     *
     * @param fileStream the file to parse
     * @return the generated document
     * @throws OCRImportException if an error occurs during the importation
     */
    OCRDocument parseImage(InputStream fileStream) throws OCRImportException;
}
