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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.data.TessDataFileStore;
import org.xwiki.contrib.ocr.tesseract.data.job.AbstractTessStoreUpdateJob;
import org.xwiki.contrib.ocr.tesseract.data.job.TessStoreUpdateJobStatus;
import org.xwiki.job.Job;
import org.xwiki.job.event.JobFinishingEvent;
import org.xwiki.observation.AbstractEventListener;
import org.xwiki.observation.event.Event;

/**
 * Listens for {@link org.xwiki.job.event.JobFinishedEvent} related to the {@link AbstractTessStoreUpdateJob}
 * and uses the result of this job in order to update the {@link DefaultTessDataFileStore}.
 *
 * If the {@link DefaultTessDataFileStore} has been overrided, the listener won't do anything.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named(TessStoreUpdateEventListener.LISTENER_NAME)
public class TessStoreUpdateEventListener extends AbstractEventListener
{
    /**
     * The listener name.
     */
    static final String LISTENER_NAME = "tesseractStoreUpdate";

    @Inject
    private TessDataFileStore tessDataFileStore;

    /**
     * Builds a new {@link TessStoreUpdateEventListener}.
     */
    public TessStoreUpdateEventListener()
    {
        super(LISTENER_NAME, new JobFinishingEvent());
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        if (event instanceof JobFinishingEvent) {
            JobFinishingEvent jobEvent = (JobFinishingEvent) event;

            if (tessDataFileStore instanceof DefaultTessDataFileStore
                    && AbstractTessStoreUpdateJob.JOB_TYPE.equals(jobEvent.getJobType())) {
                DefaultTessDataFileStore defaultTessDataFileStore = (DefaultTessDataFileStore) tessDataFileStore;
                TessStoreUpdateJobStatus jobStatus = ((TessStoreUpdateJobStatus) ((Job) source).getStatus());

                defaultTessDataFileStore.setLocalDataFiles(jobStatus.getLocalDataFiles());
                defaultTessDataFileStore.setRemoteDataFiles(jobStatus.getRemoteDataFiles());
                defaultTessDataFileStore.setLastUpdateDate();
            }
        }
    }
}
