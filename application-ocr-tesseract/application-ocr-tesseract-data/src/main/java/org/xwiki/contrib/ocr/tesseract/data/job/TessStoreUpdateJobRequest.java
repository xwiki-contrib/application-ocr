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
package org.xwiki.contrib.ocr.tesseract.data.job;

import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.job.DefaultRequest;
import org.xwiki.stability.Unstable;

/**
 * {@link org.xwiki.job.Request} for {@link AbstractTessStoreUpdateJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class TessStoreUpdateJobRequest extends DefaultRequest
{
    /**
     * The name of the property used to get a list of available {@link TessRemoteDataFile}.
     */
    private static final String TRAINING_FILES_URL_PROPERTY = "trainingFilesURL";

    /**
     * Set the URL that should be used in order to fetch the available Tesseract data files list.
     *
     * @param filesURL the URL
     */
    public void setFilesURL(String filesURL)
    {
        setProperty(TRAINING_FILES_URL_PROPERTY, filesURL);
    }

    /**
     * @return the URL to get the file list from
     */
    public String getFilesURL()
    {
        return getProperty(TRAINING_FILES_URL_PROPERTY);
    }
}
