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
package org.xwiki.contrib.ocr.filter.internal.input;

import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.ocr.api.OCRDocument;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.tesseract.api.TessSyntax;
import org.xwiki.filter.FilterEventParameters;
import org.xwiki.filter.event.model.WikiDocumentFilter;

/**
 * Input filter stream that transforms graphing documents containing graphical content into wiki documents
 * containing the raw content given by Tesseract OCR library.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(PlainFilterStreamFactory.FILTER_STREAM_TYPE_STRING)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class PlainFilterStream extends AbstractOCRInputFilterStream
{
    @Override
    protected FilterEventParameters generateEventParameters(OCRDocument document) throws OCRException
    {
        FilterEventParameters parameters = new FilterEventParameters();
        parameters.put(WikiDocumentFilter.PARAMETER_CONTENT, document.getContentAs(TessSyntax.PLAIN_1_0));
        return parameters;
    }
}
