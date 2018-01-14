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

import java.util.List;

import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.job.DefaultJobStatus;
import org.xwiki.job.DefaultRequest;
import org.xwiki.job.event.status.JobStatus;
import org.xwiki.logging.LoggerManager;
import org.xwiki.observation.ObservationManager;
import org.xwiki.stability.Unstable;

/**
 * {@link JobStatus} implementation for {@link AbstractTessFileListJob}. This custom job allows to store
 * the fetched {@link TessRemoteDataFile} during the job and access them afterwards using {@link #getRemoteDataFiles()}.
 *
 * @version $Id$
 * @since 1.0
 */
@Unstable
public class TessFileListJobStatus extends DefaultJobStatus<DefaultRequest>
{
    private List<TessRemoteDataFile> remoteDataFiles;

    /**
     * Builds a new {@link TessFileListJobStatus}.
     *
     * @param request the job request
     * @param parentJobStatus the parent job status
     * @param observationManager the observation manager
     * @param loggerManager the logger manager
     */
    public TessFileListJobStatus(DefaultRequest request, JobStatus parentJobStatus,
            ObservationManager observationManager, LoggerManager loggerManager)
    {
        super(request, parentJobStatus, observationManager, loggerManager);
    }

    /**
     * Register a list of fetched {@link TessRemoteDataFile}.
     *
     * @param remoteDataFiles the list of files
     */
    public void setRemoteDataFiles(List<TessRemoteDataFile> remoteDataFiles)
    {
        this.remoteDataFiles = remoteDataFiles;
    }

    /**
     * @return the registered list of {@link TessRemoteDataFile}.
     */
    public List<TessRemoteDataFile> getRemoteDataFiles()
    {
        return remoteDataFiles;
    }
}
