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
package org.xwiki.contrib.ocr.tesseract.api.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.tesseract.api.TessBaseAPIProvider;
import org.xwiki.contrib.ocr.api.OCRDocumentBuilder;
import org.xwiki.contrib.ocr.api.OCRDocumentBuilderProvider;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;

/**
 * This is the implementation of {@link OCRDocumentBuilderProvider} for the Tesseract OCR library.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named("tesseract")
public class TessDocumentBuilderProvider implements OCRDocumentBuilderProvider
{
    @Inject
    private TessBaseAPIProvider provider;

    @Inject
    private TessConfiguration configuration;

    @Override
    public OCRDocumentBuilder getBuilder(String language) throws OCRException
    {
        return new TessDocumentBuilder(provider.get(language));
    }

    @Override
    public OCRDocumentBuilder getBuilder() throws OCRException
    {
        return getBuilder(configuration.defaultLangage());
    }
}
