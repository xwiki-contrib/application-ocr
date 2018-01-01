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
package org.xwiki.contrib.ocr.tesseract.data.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codehaus.plexus.util.FileUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.data.TessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.internal.file.DefaultTessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileDownloadJob;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileListingJob;

/**
 * This is the default implementation of {@link TessDataManager}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultTessDataManager implements TessDataManager
{
    @Inject
    private TessConfiguration tessConfiguration;

    @Override
    public List<TessLocalDataFile> getLocalFiles() throws OCRException
    {
        File localFolder = getLocalFolder();
        List<TessLocalDataFile> localDataFiles = new ArrayList<>();

        File[] fileList = localFolder.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                String fileLang = file.getName().split(".")[0];

                localDataFiles.add(new DefaultTessLocalDataFile(fileLang, file.getPath()));
            }
        }

        return localDataFiles;
    }

    @Override
    public List<TessRemoteDataFile> getAvailableFiles() throws OCRException
    {
        return null;
    }

    @Override
    public AbstractTessFileListingJob getAvailableFilesAsync() throws OCRException
    {
        return null;
    }

    @Override
    public TessLocalDataFile getFile(String lang) throws OCRException
    {
        return null;
    }

    @Override
    public AbstractTessFileDownloadJob downloadFileAsync(TessRemoteDataFile remoteDataFile) throws OCRException
    {
        return null;
    }

    /**
     * Get the local folder containing Tesseract data files using configuration variables. If the folder does not
     * exists, it will be created.
     *
     * @return the local folder
     * @throws OCRException if the folder could not be created
     */
    private File getLocalFolder() throws OCRException
    {
        File localFolder = new File(tessConfiguration.dataPath());

        try {
            FileUtils.forceMkdir(localFolder);
        } catch (IOException e) {
            throw new OCRException("Failed to create the local data folder.", e);
        }

        return localFolder;
    }
}
