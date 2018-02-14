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

import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessDataFileRemovalJob;

/**
 * Job used to remove locally stored Tesseract training data files.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(AbstractTessDataFileRemovalJob.JOB_TYPE)
public class DefaultTessDataFileRemovalJob extends AbstractTessDataFileRemovalJob
{
    @Override
    protected void runInternal() throws Exception
    {
        logger.info("Retrieving file information ...");
        File localDataFile = new File(request.getLocalDataFile().getFilePath());
        boolean deletionSucceeded = localDataFile.delete();

        if (deletionSucceeded) {
            logger.info("Removal complete!");
        } else {
            logger.error("Failed to remove the data file.");
        }
    }
}
