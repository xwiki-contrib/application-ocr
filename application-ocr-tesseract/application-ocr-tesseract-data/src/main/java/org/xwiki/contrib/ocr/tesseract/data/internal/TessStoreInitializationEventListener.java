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

import org.slf4j.Logger;
import org.xwiki.bridge.event.ApplicationReadyEvent;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.TessDataFileStore;
import org.xwiki.observation.AbstractEventListener;
import org.xwiki.observation.event.Event;

/**
 * Listens to {@link ApplicationReadyEvent} and triggers a refresh of the {@link TessDataFileStore}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named(TessStoreInitializationEventListener.LISTENER_NAME)
public class TessStoreInitializationEventListener extends AbstractEventListener
{
    /**
     * The listener name.
     */
    public static final String LISTENER_NAME = "tesseractStoreInitialization";

    @Inject
    private TessDataFileStore tessDataFileStore;

    @Inject
    private Logger logger;

    /**
     * Constructs a new {@link TessStoreInitializationEventListener}.
     */
    public TessStoreInitializationEventListener()
    {
        super(LISTENER_NAME, new ApplicationReadyEvent());
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        try {
            logger.info("Automatically updating the Tesseract data store...");
            tessDataFileStore.updateStore();
        } catch (TessException e) {
            logger.error("Failed to to initialize the Tesseract data store.", e);
        }
    }
}
