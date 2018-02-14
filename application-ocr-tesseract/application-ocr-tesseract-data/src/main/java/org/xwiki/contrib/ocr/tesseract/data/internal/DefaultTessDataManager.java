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
import java.util.Arrays;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codehaus.plexus.util.FileUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.TessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessDataFileDownloadJob;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessDataFileRemovalJob;
import org.xwiki.contrib.ocr.tesseract.data.job.TessDataFileDownloadJobRequest;
import org.xwiki.contrib.ocr.tesseract.data.job.TessDataFileRemovalJobRequest;
import org.xwiki.job.JobException;
import org.xwiki.job.JobExecutor;
import org.xwiki.job.event.status.JobStatus;

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
    /**
     * The extension used for Tesseract training data files.
     */
    public static final String TRAINING_FILE_EXTENSION = ".traineddata";

    @Inject
    private TessConfiguration tessConfiguration;

    @Inject
    private JobExecutor jobExecutor;

    @Override
    public JobStatus downloadDataFile(TessRemoteDataFile remoteDataFile) throws TessException
    {
        TessDataFileDownloadJobRequest jobRequest = new TessDataFileDownloadJobRequest();
        jobRequest.setId(Arrays.asList(AbstractTessDataFileDownloadJob.JOB_TYPE, UUID.randomUUID().toString()));
        jobRequest.setRemoteDataFile(remoteDataFile);

        try {
            return jobExecutor.execute(AbstractTessDataFileDownloadJob.JOB_TYPE, jobRequest).getStatus();
        } catch (JobException e) {
            throw new TessException("Failed to execute the download job.", e);
        }
    }

    @Override
    public JobStatus removeDataFile(TessLocalDataFile localDataFile) throws TessException
    {
        TessDataFileRemovalJobRequest jobRequest = new TessDataFileRemovalJobRequest();
        jobRequest.setId(Arrays.asList(AbstractTessDataFileRemovalJob.JOB_TYPE, UUID.randomUUID().toString()));
        jobRequest.setLocalDataFile(localDataFile);

        try {
            return jobExecutor.execute(AbstractTessDataFileRemovalJob.JOB_TYPE, jobRequest).getStatus();
        } catch (JobException e) {
            throw new TessException("Failed to execute the removal job.", e);
        }
    }

    @Override
    public File getLocalDataFolder() throws TessException
    {
        File localFolder = new File(String.format("%s/tessdata", tessConfiguration.dataPath()));

        try {
            FileUtils.forceMkdir(localFolder);
        } catch (IOException e) {
            throw new TessException("Failed to create the local data folder.", e);
        }

        return localFolder;
    }
}
