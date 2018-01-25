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
package org.xwiki.contrib.ocr.tesseract.data.internal.job;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.TessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.internal.DefaultTessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.internal.file.DefaultTessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.internal.file.DefaultTessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessStoreUpdateJob;

/**
 * Default implementation of {@link AbstractTessStoreUpdateJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(AbstractTessStoreUpdateJob.JOB_TYPE)
public class DefaultTessStoreUpdateJob extends AbstractTessStoreUpdateJob
{
    private static final String JSON_NAME_KEY = "name";

    private static final String SPLIT_BY_POINT_REGEX = "\\.";

    @Inject
    private TessDataManager tessDataManager;

    @Override
    protected void runInternal() throws Exception
    {
        try {
            progressManager.pushLevelProgress(2, this);
            progressManager.startStep(this);
            status.setLocalDataFiles(listLocalFiles());
            progressManager.startStep(this);
            status.setRemoteDataFiles(listRemoteFiles());
            logger.info("Done!");
        } finally {
            progressManager.popLevelProgress(this);
        }
    }

    private List<TessLocalDataFile> listLocalFiles() throws TessException
    {
        logger.info("Listing local files ...");
        File localFolder = tessDataManager.getLocalDataFolder();
        List<TessLocalDataFile> localDataFiles = new ArrayList<>();

        File[] fileList = localFolder.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.getName().endsWith(DefaultTessDataManager.TRAINING_FILE_EXTENSION)) {
                    String fileLang = file.getName().split(SPLIT_BY_POINT_REGEX)[0];

                    localDataFiles.add(new DefaultTessLocalDataFile(fileLang, file.getPath()));
                }
            }
        }

        return localDataFiles;
    }

    private List<TessRemoteDataFile> listRemoteFiles() throws Exception
    {
        logger.info("Fetching remote file list ...");
        URL listURL = new URL(request.getFilesURL());
        List<TessRemoteDataFile> remoteDataFiles = new ArrayList<>();
        JSONArray fileList = new JSONArray(IOUtils.toString(listURL, Charset.forName("UTF-8")));

        logger.info("Parsing remote files information ...");
        for (int i = 0; i < fileList.length(); i++) {
            JSONObject fileDescriptor = fileList.getJSONObject(i);

            if (fileDescriptor.getString(JSON_NAME_KEY).endsWith(DefaultTessDataManager.TRAINING_FILE_EXTENSION)) {
                TessRemoteDataFile remoteDataFile = new DefaultTessRemoteDataFile(
                        fileDescriptor.getString(JSON_NAME_KEY).split(SPLIT_BY_POINT_REGEX)[0],
                        fileDescriptor.getString("download_url"));
                remoteDataFiles.add(remoteDataFile);
            }
        }

        return remoteDataFiles;
    }
}
