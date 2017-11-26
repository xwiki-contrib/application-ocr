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
package org.xwiki.contrib.ocrimport.filter.internal.input;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocrimport.filter.OCRInputFilterProperties;
import org.xwiki.filter.event.model.WikiDocumentFilter;
import org.xwiki.filter.input.AbstractBeanInputFilterStreamFactory;

/**
 * Create OCR import input filters.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(OCRInputFilterProperties.FILTER_STREAM_TYPE_STRING)
@Singleton
public class OCRInputFilterStreamFactory
        extends AbstractBeanInputFilterStreamFactory<OCRInputFilterProperties, WikiDocumentFilter>
{
    /**
     * Creates a new {@link OCRInputFilterStreamFactory}.
     */
    public OCRInputFilterStreamFactory()
    {
        super(OCRInputFilterProperties.FILTER_STREAM_TYPE);

        setName("OCR binary input stream");
        setDescription("Generate wiki events from a given graphical file using optical character recognition");
    }
}