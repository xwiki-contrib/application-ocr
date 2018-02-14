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

import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.job.DefaultRequest;
import org.xwiki.stability.Unstable;

/**
 * {@link org.xwiki.job.Request} implementation for {@link AbstractTessDataFileRemovalJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class TessDataFileRemovalJobRequest extends DefaultRequest
{
    /**
     * The name of the property used to get the {@link TessLocalDataFile} to remove in the job request.
     */
    private static final String LOCAL_DATA_FILE_PROPERTY = "localDataFile";

    /**
     * Define the {@link TessLocalDataFile} that should be removed.
     *
     * @param localDataFile the {@link TessLocalDataFile}
     */
    public void setLocalDataFile(TessLocalDataFile localDataFile)
    {
        setProperty(LOCAL_DATA_FILE_PROPERTY, localDataFile);
    }

    /**
     * @return the {@link TessLocalDataFile} to remove
     */
    public TessLocalDataFile getLocalDataFile()
    {
        return getProperty(LOCAL_DATA_FILE_PROPERTY);
    }
}

