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
 * {@link org.xwiki.job.Request} implementation for {@link AbstractTessFileDownloadJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class TessFileDownloadJobRequest extends DefaultRequest
{
    /**
     * The name of the property used to get the {@link TessRemoteDataFile} to download in the job request.
     */
    private static final String REMOTE_DATA_FILE_PROPERTY = "remoteDataFile";

    /**
     * Define the {@link TessRemoteDataFile} that should be downloaded.
     *
     * @param remoteDataFile the {@link TessRemoteDataFile}
     */
    public void setRemoteDataFile(TessRemoteDataFile remoteDataFile)
    {
        setProperty(REMOTE_DATA_FILE_PROPERTY, remoteDataFile);
    }

    /**
     * @return the {@link TessRemoteDataFile} to download
     */
    public TessRemoteDataFile getRemoteDataFile()
    {
        return getProperty(REMOTE_DATA_FILE_PROPERTY);
    }
}
