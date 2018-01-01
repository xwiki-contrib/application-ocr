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
package org.xwiki.contrib.ocr.tesseract.data.internal.file;

import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;

/**
 * This is the default implementation of {@link TessRemoteDataFile}.
 *
 * @version $Id$
 * @since 1.0
 */
public class DefaultTessRemoteDataFile implements TessRemoteDataFile
{
    private String downloadURL;

    private String lang;

    /**
     * Builds a new {@link DefaultTessRemoteDataFile}.
     *
     * @param lang the lang code of the file
     * @param downloadURL the file raw download url
     */
    public DefaultTessRemoteDataFile(String lang, String downloadURL)
    {
        this.lang = lang;
        this.downloadURL = downloadURL;
    }

    @Override
    public String getDownloadURL()
    {
        return downloadURL;
    }

    @Override
    public String getLanguage()
    {
        return lang;
    }
}
