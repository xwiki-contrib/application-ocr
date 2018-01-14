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

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.data.file.TessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.internal.file.DefaultTessRemoteDataFile;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessFileListJob;

/**
 * Default implementation of {@link AbstractTessFileListJob}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(AbstractTessFileListJob.JOB_TYPE)
public class DefaultTessFileListJob extends AbstractTessFileListJob
{
    @Override
    protected void runInternal() throws Exception
    {
        logger.info("Fetching file list ...");
        URL listURL = new URL(request.getFilesURL());
        List<TessRemoteDataFile> remoteDataFiles = new ArrayList<>();
        JSONArray fileList = new JSONArray(IOUtils.toString(listURL, Charset.forName("UTF-8")));

        logger.info("Parsing remote files information ...");
        for (int i = 0; i < fileList.length(); i++) {
            JSONObject fileDescriptor = fileList.getJSONObject(i);

            TessRemoteDataFile remoteDataFile = new DefaultTessRemoteDataFile(
                    fileDescriptor.getString("name").split("\\.")[0],
                    fileDescriptor.getString("download_url"),
                    fileDescriptor.getString("sha"));

            remoteDataFiles.add(remoteDataFile);
        }

        status.setRemoteDataFiles(remoteDataFiles);
        logger.info("Done!");
    }
}
