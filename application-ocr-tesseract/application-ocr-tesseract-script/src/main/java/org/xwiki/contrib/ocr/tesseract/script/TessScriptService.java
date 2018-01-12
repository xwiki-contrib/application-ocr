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
package org.xwiki.contrib.ocr.tesseract.script;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.script.OCRScriptService;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.contrib.ocr.tesseract.data.TessDataManager;
import org.xwiki.contrib.ocr.tesseract.data.file.TessLocalDataFile;
import org.xwiki.script.service.ScriptService;
import org.xwiki.stability.Unstable;

/**
 * Define script services that are specific to the Tesseract library implementation of the OCR application.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named(OCRScriptService.ROLE_HINT + "." + TessScriptService.ROLE_HINT)
@Unstable
public class TessScriptService implements ScriptService
{
    /**
     * The role suffix of the script service.
     */
    static final String ROLE_HINT = "tesseract";

    @Inject
    private TessDataManager tessDataManager;

    /**
     * @return a list of locally installed files.
     * @throws TessException if an error occurred
     */
    public List<TessLocalDataFile> getLocalFiles() throws TessException
    {
        return tessDataManager.getLocalFiles();
    }
}
