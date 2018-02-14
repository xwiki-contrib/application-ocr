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

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.data.TessDataFileStore;
import org.xwiki.contrib.ocr.tesseract.data.TessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.internal.DefaultTessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessDataFileDownloadJob;
import org.xwiki.job.Job;
import org.xwiki.job.JobExecutor;

/**
 * Job used to download Tesseract training data files.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(AbstractTessDataFileDownloadJob.JOB_TYPE)
public class DefaultTessDataFileDownloadJob extends AbstractTessDataFileDownloadJob
{
    @Inject
    private TessConfiguration tessConfiguration;

    @Inject
    private TessDataManager tessDataManager;

    @Inject
    private TessDataFileStore tessDataFileStore;

    @Inject
    private JobExecutor jobExecutor;

    @Override
    protected void runInternal() throws Exception
    {
        logger.info("Retrieving file information ...");
        TessRemoteDataFile remoteDataFile = request.getRemoteDataFile();
        URL downloadURL = new URL(remoteDataFile.getDownloadURL());

        // Create the tessdata folder if not already present
        tessDataManager.getLocalDataFolder();

        String filePath = String.format("%s/tessdata/%s%s",
                tessConfiguration.dataPath(),
                remoteDataFile.getLanguage().replaceAll("/", "\\/"),
                DefaultTessDataManager.TRAINING_FILE_EXTENSION);

        logger.info("Starting file download ...");
        ReadableByteChannel rbc = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        logger.info("Download complete!");

        logger.info("Triggering store update ...");
        Job updateJob = jobExecutor.getJob(tessDataFileStore.updateStore().getRequest().getId());
        updateJob.join();
    }
}
