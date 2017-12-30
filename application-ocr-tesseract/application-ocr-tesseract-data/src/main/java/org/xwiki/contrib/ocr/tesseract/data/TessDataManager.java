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

import java.util.List;

import org.xwiki.component.annotation.Role;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractFileDownloadJob;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractFileListingJob;
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
     * @return a list of training data files stored locally
     * @throws OCRException if an error happened
     */
    List<TessLocalDataFile> getLocalFiles() throws OCRException;

    /**
     * Synchronous method that fetches a list of remotely available files.
     * To get the list of files asynchronously, see {@link #getAvailableFilesAsync()}.
     *
     * @return a list of training data files available remotely
     * @throws OCRException if an error happened
     */
    List<TessRemoteDataFile> getAvailableFiles() throws OCRException;

    /**
     * Asynchronously retrieves a list of remotely available data files.
     *
     * @return the {@link AbstractFileListingJob} representing the job
     * @throws OCRException if an error happened
     */
    AbstractFileListingJob getAvailableFilesAsync() throws OCRException;

    /**
     * Get the {@link TessLocalDataFile} associated with the given language. If the configuration option
     * `tesseract.allowAutoDownload` is set to true, the file should be automatically downloaded if not already
     * present on the filesystem.
     *
     * @param lang the lang of the file to get
     * @return the corresponding {@link TessLocalDataFile}
     * @throws OCRException if an error happened
     */
    TessLocalDataFile getFile(String lang) throws OCRException;

    /**
     * Download and store the Tesseract data file corresponding to the given language code.
     *
     * @param lang the language to download
     * @return a descriptor for the file download job
     * @throws OCRException if an error happened
     */
    AbstractFileDownloadJob downloadFileAsync(String lang) throws OCRException;

    /**
     * Download and store the Tesseract data file corresponding to the given {@link TessRemoteDataFile}.
     *
     * @param remoteDataFile the {@link TessRemoteDataFile} to download
     * @return a descriptor for the file download job
     * @throws OCRException if an error happened
     */
    AbstractFileDownloadJob downloadFileAsync(TessRemoteDataFile remoteDataFile) throws OCRException;
}
