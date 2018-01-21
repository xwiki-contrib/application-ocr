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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.TessDataFileStore;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessStoreUpdateJob;
import org.xwiki.contrib.ocr.tesseract.data.job.TessStoreUpdateJobRequest;
import org.xwiki.job.Job;
import org.xwiki.job.JobException;
import org.xwiki.job.JobExecutor;
import org.xwiki.job.event.status.JobStatus;

/**
 * Default implementation of {@link TessDataFileStore}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultTessDataFileStore implements TessDataFileStore
{
    private List<TessLocalDataFile> localDataFiles = new ArrayList<>();

    private List<TessRemoteDataFile> remoteDataFiles = new ArrayList<>();

    private LocalDateTime lastUpdate;

    @Inject
    private TessConfiguration tessConfiguration;

    @Inject
    private JobExecutor jobExecutor;

    @Override
    public List<TessLocalDataFile> getLocalDataFiles()
    {
        return this.localDataFiles;
    }

    @Override
    public List<TessRemoteDataFile> getRemoteDataFiles()
    {
        return this.remoteDataFiles;
    }

    @Override
    public boolean needsUpdate()
    {
        return (lastUpdate == null || lastUpdate.plus(tessConfiguration.dataStoreUpdateInterval(), ChronoUnit.SECONDS)
                .isBefore(LocalDateTime.now()));
    }

    @Override
    public JobStatus updateStore() throws TessException
    {
        return startStoreUpdateJob().getStatus();
    }

    /**
     * Define a new set of locally available data files.
     *
     * @param localDataFiles the new local data files
     */
    public void setLocalDataFiles(List<TessLocalDataFile> localDataFiles)
    {
        this.localDataFiles = localDataFiles;
    }

    /**
     * Define a new set of remotely available data files.
     *
     * @param remoteDataFiles the new remote data files
     */
    public void setRemoteDataFiles(List<TessRemoteDataFile> remoteDataFiles)
    {
        this.remoteDataFiles = remoteDataFiles;
    }

    /**
     * Set the store update date to now.
     */
    public void setLastUpdateDate()
    {
        lastUpdate = LocalDateTime.now();
    }

    /**
     * Instantiate and execute an {@link AbstractTessStoreUpdateJob} and returns the associated job.
     *
     * @return the started job
     * @throws TessException if an error happens
     */
    private Job startStoreUpdateJob() throws TessException
    {
        TessStoreUpdateJobRequest jobRequest = new TessStoreUpdateJobRequest();
        jobRequest.setId(Arrays.asList(AbstractTessStoreUpdateJob.JOB_TYPE, UUID.randomUUID().toString()));
        jobRequest.setFilesURL(tessConfiguration.trainingFilesURL());

        try {
            return jobExecutor.execute(AbstractTessStoreUpdateJob.JOB_TYPE, jobRequest);
        } catch (JobException e) {
            throw new TessException("Failed to execute the store update job.", e);
        }
    }
}
