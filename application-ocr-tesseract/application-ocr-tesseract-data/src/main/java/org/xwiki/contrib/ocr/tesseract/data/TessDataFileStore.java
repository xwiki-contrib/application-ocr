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
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.job.event.status.JobStatus;

/**
 * Acts as an in-memory store for locally and remotely available training data files.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
public interface TessDataFileStore
{
    /**
     * @return a list of local training data files currently found.
     */
    List<TessLocalDataFile> getLocalDataFiles();

    /**
     * @return a list of remotely available training data files.
     */
    List<TessRemoteDataFile> getRemoteDataFiles();

    /**
     * @return true if the file store needs to be updated in order to discover local files and remotely available files.
     */
    boolean needsUpdate();

    /**
     * Triggers a store update.
     *
     * @return the {@link JobStatus} used to update the store
     * @throws TessException if an error happens
     */
    JobStatus updateStore() throws TessException;

    /**
     * @param lang the language of the file to retrieve
     * @return the {@link TessRemoteDataFile} corresponding to the given language
     * @throws TessException if the file could not be found in the store
     */
    TessRemoteDataFile getRemoteFile(String lang) throws TessException;

    /**
     * @param lang the language of the file to retrieve
     * @return the {@link TessLocalDataFile} corresponding to the given language
     * @throws TessException if the file could not be found in the store
     */
    TessLocalDataFile getLocalFile(String lang) throws TessException;
}
