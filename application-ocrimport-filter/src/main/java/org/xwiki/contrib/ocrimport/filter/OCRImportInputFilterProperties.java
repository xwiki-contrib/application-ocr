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
package org.xwiki.contrib.ocrimport.filter;

import org.xwiki.filter.FilterStreamProperties;
import org.xwiki.filter.input.InputStreamInputSource;
import org.xwiki.filter.type.FilterStreamType;
import org.xwiki.filter.type.SystemType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.properties.annotation.PropertyDescription;
import org.xwiki.properties.annotation.PropertyName;

/**
 * Define the properties used by {@link org.xwiki.contrib.ocrimport.filter.internal.input.OCRImportInputFilterStream}.
 *
 * @version $Id$
 * @since 1.0
 */
public class OCRImportInputFilterProperties implements FilterStreamProperties
{
    /**
     * The OCR importer format.
     */
    public static final FilterStreamType FILTER_STREAM_TYPE =
            new FilterStreamType(SystemType.FILTER, "binary");

    /**
     * The OCR importer format, as a string.
     */
    public static final String FILTER_STREAM_TYPE_STRING = "ocrimporter+binary";

    /**
     * @see #isVerbose()
     */
    private boolean verbose = true;

    /**
     * @see #getInputSource()
     */
    private InputStreamInputSource inputSource;

    /**
     * @see #getParentDocument()
     */
    private EntityReference parentDocument;

    /**
     * @see #getFileType()
     */
    private String fileType;

    /**
     * @see #getDocumentAuthor()
     */
    private EntityReference documentAuthor;

    @Override
    public boolean isVerbose()
    {
        return this.verbose;
    }

    @Override
    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    /**
     * @return the {@link InputStreamInputSource} that contains the content imported using OCR
     */
    @PropertyName("Input source")
    @PropertyDescription("Indicates the source that will be imported using OCR")
    public InputStreamInputSource getInputSource()
    {
        return this.inputSource;
    }

    /**
     * @param inputSource the {@link InputStreamInputSource} that contains the content to import using OCR
     */
    public void setInputSource(InputStreamInputSource inputSource)
    {
        this.inputSource = inputSource;
    }

    /**
     * @return an {@link EntityReference} to the parent document of the document that will be created
     */
    @PropertyName("Parent document")
    @PropertyDescription("A reference to the parent document")
    public EntityReference getParentDocument()
    {
        return this.parentDocument;
    }

    /**
     * @param parentDocument the parent document of the document that is imported
     */
    public void setParentDocument(EntityReference parentDocument)
    {
        this.parentDocument = parentDocument;
    }

    /**
     * @return the type (.pdf, .png, ...) of the file that will be imported
     */
    @PropertyName("File type")
    @PropertyDescription("The type of the file that will be imported")
    public String getFileType()
    {
        return this.fileType;
    }

    /**
     * @param fileType the type of file that will be imported
     */
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    /**
     * @return the author that should be used for creating the document
     */
    @PropertyName("Document author")
    @PropertyDescription("The author that should be used for creating the document")
    public EntityReference getDocumentAuthor()
    {
        return this.documentAuthor;
    }

    /**
     * @param documentAuthor the author used for creating the document
     */
    public void setDocumentAuthor(EntityReference documentAuthor)
    {
        this.documentAuthor = documentAuthor;
    }
}
