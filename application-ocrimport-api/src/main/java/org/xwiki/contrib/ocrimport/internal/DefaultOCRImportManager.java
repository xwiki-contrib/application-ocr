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
package org.xwiki.contrib.ocrimport.internal;

import java.io.InputStream;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocrimport.OCRImporterException;
import org.xwiki.contrib.ocrimport.OCRImporterManager;
import org.xwiki.contrib.ocrimport.TessBaseAPIProvider;
import org.xwiki.contrib.ocrimport.XDOMDocument;
import org.xwiki.contrib.ocrimport.XDOMDocumentBuilder;
import org.xwiki.model.reference.DocumentReference;

/**
 * This is the default implementation for {@link OCRImporterManager}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultOCRImportManager implements OCRImporterManager
{
    @Inject
    @Named("image")
    private XDOMDocumentBuilder imageBuilder;

    @Inject
    private TessBaseAPIProvider apiProvider;

    @Override
    public XDOMDocument importDocument(InputStream fileStream, String fileName)
            throws OCRImporterException
    {
        XDOMDocument resultDocument;

        TessBaseAPI api = apiProvider.get();

        try {
            if (isImage(fileName)) {
                resultDocument = imageBuilder.build(fileStream, api);
            /*} else if (isPDF(fileName)) {
                // TODO: Implement PDF import
                resultDocument = null;*/
            } else {
                throw new OCRImporterException("Incompatible document file type");
            }
        } finally {
            api.End();
        }

        return resultDocument;
    }

    @Override
    public void saveDocument(XDOMDocument document, DocumentReference targetReference, String syntaxId, String title,
            boolean append) throws OCRImporterException
    {

    }

    /**
     * Determine if the given file name has a .pdf extension.
     *
     * @param fileName the file name to use
     * @return true if the file name has the .pdf extension
     */
    private boolean isPDF(String fileName)
    {
        Pattern p = Pattern.compile(".*\\.pdf");
        return p.matcher(fileName).matches();
    }

    /**
     * Determine if the given file name has an extension of a supported image type.
     *
     * @param fileName the file name to use
     * @return true if the file has a supported image type extension
     */
    private boolean isImage(String fileName)
    {
        Pattern p = Pattern.compile(".*\\.(jpg|png)");
        return p.matcher(fileName).matches();
    }
}
