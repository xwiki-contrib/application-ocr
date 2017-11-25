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
package org.xwiki.contrib.ocrimport.api.internal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocrimport.api.OCRImporterConfiguration;
import org.xwiki.contrib.ocrimport.api.OCRImporterException;
import org.xwiki.contrib.ocrimport.api.TessBaseAPIProvider;

/**
 * This is the default implementation of {@link TessBaseAPIProvider}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultTessBaseAPIProvider implements TessBaseAPIProvider
{
    @Inject
    private OCRImporterConfiguration ocrImporterConfiguration;

    @Override
    public TessBaseAPI get() throws OCRImporterException
    {
        return get(ocrImporterConfiguration.defaultLangage(), ocrImporterConfiguration.dataPath());
    }

    @Override
    public TessBaseAPI get(String language) throws OCRImporterException
    {
        return get(language, ocrImporterConfiguration.dataPath());
    }

    @Override
    public TessBaseAPI get(String language, String dataPath) throws OCRImporterException
    {
        TessBaseAPI api = new TessBaseAPI();

        if (api.Init(dataPath, language) != 0) {
            throw new OCRImporterException("Unable to instantiate Tesseract API.");
        }

        return api;
    }
}
