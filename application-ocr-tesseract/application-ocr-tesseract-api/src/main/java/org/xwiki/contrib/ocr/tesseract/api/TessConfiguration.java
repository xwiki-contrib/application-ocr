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
package org.xwiki.contrib.ocr.tesseract.api;

import org.xwiki.component.annotation.Role;
import org.xwiki.stability.Unstable;

/**
 * Get the configuration options concerning the OCR Importer Application.
 *
 * @version $Id$
 * @since 1.0
 */
@Role
@Unstable
public interface TessConfiguration
{
    /**
     * @return the default langage that should be used by the {@link org.bytedeco.javacpp.tesseract.TessBaseAPI}
     */
    String defaultLangage();

    /**
     * @return the path to the {@link org.bytedeco.javacpp.tesseract.TessBaseAPI} data files. Note that Tesseract
     * files will be effectively stored in <DATA_PATH>/tessdata.
     */
    String dataPath();

    /**
     * @return true if Tesseract data files should be automatically downloaded when performing an import.
     *
     * Note that if this option is disabled, the download of data files should still be possible in the
     * administration section of the wiki.
     */
    boolean allowAutoDownload();

    /**
     * @return the URL to a list of downloadable training data files for the Tesseract library.
     */
    String trainingFilesURL();

    /**
     * @return the number of seconds that should pass before needing to update the training files data store.
     */
    int dataStoreUpdateInterval();
}
