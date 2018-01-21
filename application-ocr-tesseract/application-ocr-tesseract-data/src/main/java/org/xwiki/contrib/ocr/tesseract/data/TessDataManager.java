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
package org.xwiki.contrib.ocr.tesseract.data;

import java.io.File;

import org.xwiki.component.annotation.Role;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.job.event.status.JobStatus;
import org.xwiki.stability.Unstable;

/**
 * Manage Tesseract training data files.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
@Unstable
public interface TessDataManager
{
    /**
     * Get the {@link TessLocalDataFile} associated with the given language. If the configuration option
     * `tesseract.allowAutoDownload` is set to true, the file should be automatically downloaded if not already
     * present on the filesystem.
     *
     * @param lang the lang of the file to get
     * @return the corresponding {@link TessLocalDataFile}
     * @throws TessException if an error happened
     */
    TessLocalDataFile getFile(String lang) throws TessException;

    /**
     * Download and store the Tesseract data file corresponding to the given {@link TessRemoteDataFile}.
     *
     * @param remoteDataFile the {@link TessRemoteDataFile} to download
     * @return the file download job status
     * @throws TessException if an error happened
     */
    JobStatus downloadFile(TessRemoteDataFile remoteDataFile) throws TessException;

    /**
     * Get the local folder containing Tesseract data files using configuration variables. If the folder does not
     * exists, it will be created.
     *
     * @return the local folder
     * @throws TessException if the folder could not be created
     */
    File getLocalDataFolder() throws TessException;
}
