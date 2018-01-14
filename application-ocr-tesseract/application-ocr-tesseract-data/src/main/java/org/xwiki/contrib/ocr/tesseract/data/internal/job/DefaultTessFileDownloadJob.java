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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileDownloadJob;

/**
 * Job used to download Tesseract training data files.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(AbstractTessFileDownloadJob.JOB_TYPE)
public class DefaultTessFileDownloadJob extends AbstractTessFileDownloadJob
{
    @Inject
    private TessConfiguration tessConfiguration;

    @Override
    protected void runInternal() throws Exception
    {
        logger.info("Retrieving file information ...");
        TessRemoteDataFile remoteDataFile = request.getRemoteDataFile();
        URL downloadURL = new URL(remoteDataFile.getDownloadURL());
        String filePath = String.format("%s/%s.traineddata",
                tessConfiguration.dataPath(), remoteDataFile.getLanguage().replaceAll("/", "\\/"));

        logger.info("Starting file download ...");
        ReadableByteChannel rbc = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();

        String sha1Digest = DigestUtils.sha1Hex(new FileInputStream(filePath));
        if (!sha1Digest.equals(remoteDataFile.sha1Digest())) {
            throw new TessException(String.format("Failed to download file [%s]: invalid control sum.", filePath));
        } else {
            logger.info("Download complete!");
        }
    }
}
