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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codehaus.plexus.util.FileUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.TessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.internal.file.DefaultTessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileDownloadJob;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileListJob;
import org.xwiki.contrib.ocr.tesseract.data.job.TessFileDownloadJobRequest;
import org.xwiki.contrib.ocr.tesseract.data.job.TessFileListJobRequest;
import org.xwiki.contrib.ocr.tesseract.data.job.TessFileListJobStatus;
import org.xwiki.job.Job;
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
    private static final String TRAINING_FILE_EXTENSION = ".traineddata";

    @Inject
    private TessConfiguration tessConfiguration;

    @Inject
    private JobExecutor jobExecutor;

    @Override
    public List<TessLocalDataFile> getLocalFiles() throws TessException
    {
        File localFolder = getLocalFolder();
        List<TessLocalDataFile> localDataFiles = new ArrayList<>();

        File[] fileList = localFolder.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.getName().endsWith(TRAINING_FILE_EXTENSION)) {
                    String fileLang = file.getName().split("\\.")[0];

                    localDataFiles.add(new DefaultTessLocalDataFile(fileLang, file.getPath()));
                }
            }
        }

        return localDataFiles;
    }

    @Override
    public List<TessRemoteDataFile> getAvailableFiles() throws TessException
    {
        Job job = startFileListingJob();

        try {
            job.join();
            JobStatus jobStatus = job.getStatus();

            if (jobStatus instanceof TessFileListJobStatus) {
                return ((TessFileListJobStatus) jobStatus).getRemoteDataFiles();
            } else {
                throw new TessException("Invalid job status given by the file listing job.");
            }
        } catch (InterruptedException e) {
            throw new TessException("Failed to wait for the end of the file listing job.", e);
        }

    }

    @Override
    public TessFileListJobStatus getAvailableFilesAsync() throws TessException
    {
        JobStatus jobStatus = startFileListingJob().getStatus();

        if (jobStatus instanceof TessFileListJobStatus) {
            return (TessFileListJobStatus) jobStatus;
        } else {
            throw new TessException("Invalid job status returned when starting the file listing job.");
        }
    }

    @Override
    public TessLocalDataFile getFile(String lang) throws TessException
    {
        String filePath = String.format("%s.%s", lang, TRAINING_FILE_EXTENSION);

        if (FileUtils.fileExists(filePath)) {
            return new DefaultTessLocalDataFile(lang, filePath);
        } else {
            // TODO: Download the file if possible
            throw new TessException("Unable to find a training file corresponding to the given language.");
        }
    }

    @Override
    public JobStatus downloadFileAsync(TessRemoteDataFile remoteDataFile) throws TessException
    {
        TessFileDownloadJobRequest jobRequest = new TessFileDownloadJobRequest();
        jobRequest.setId(Arrays.asList(AbstractTessFileDownloadJob.JOB_TYPE, UUID.randomUUID().toString()));
        jobRequest.setRemoteDataFile(remoteDataFile);

        try {
            return jobExecutor.execute(AbstractTessFileDownloadJob.JOB_TYPE, jobRequest).getStatus();
        } catch (JobException e) {
            throw new TessException("Failed to execute the download job.", e);
        }
    }

    /**
     * Instantiate and execute an {@link AbstractTessFileListJob} and returns the associated job.
     *
     * @return the started job
     * @throws TessException if an error happens
     */
    private Job startFileListingJob() throws TessException
    {
        TessFileListJobRequest jobRequest = new TessFileListJobRequest();
        jobRequest.setId(Arrays.asList(AbstractTessFileListJob.JOB_TYPE, UUID.randomUUID().toString()));
        jobRequest.setFilesURL(tessConfiguration.trainingFilesURL());

        try {
            return jobExecutor.execute(AbstractTessFileListJob.JOB_TYPE, jobRequest);
        } catch (JobException e) {
            throw new TessException("Failed to execute the file listing job.", e);
        }
    }

    /**
     * Get the local folder containing Tesseract data files using configuration variables. If the folder does not
     * exists, it will be created.
     *
     * @return the local folder
     * @throws OCRException if the folder could not be created
     */
    private File getLocalFolder() throws TessException
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
