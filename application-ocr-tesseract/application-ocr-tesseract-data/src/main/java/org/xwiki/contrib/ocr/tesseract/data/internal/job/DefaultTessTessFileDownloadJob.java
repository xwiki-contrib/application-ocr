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
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileDownloadJob;
import org.xwiki.job.DefaultRequest;

/**
 * Tesseract oriented implementation of {@link AbstractTessFileDownloadJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(DefaultTessTessFileDownloadJob.JOBTYPE)
public class DefaultTessTessFileDownloadJob extends AbstractTessFileDownloadJob
{
    /**
     * The type of the job. Also used as a job identifier.
     */
    public static final String JOBTYPE = "tesseractFileDownload";

    @Inject
    private TessConfiguration tessConfiguration;

    @Override
    protected void runInternal() throws Exception
    {
        DefaultRequest request = getRequest();
        TessRemoteDataFile remoteDataFile = request.getProperty("remoteFile");

        URL downloadURL = new URL(remoteDataFile.getDownloadURL());
        ReadableByteChannel rbc = Channels.newChannel(downloadURL.openStream());
        FileOutputStream fos = new FileOutputStream(String.format("{}/{}.traineddata",
                tessConfiguration.dataPath(), remoteDataFile.getLanguage().replaceAll("/", "\\/")));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }

    @Override
    public String getType()
    {
        return JOBTYPE;
    }
}
