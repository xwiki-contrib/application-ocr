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

import java.io.IOException;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.ocrimport.api.OCRDocument;
import org.xwiki.contrib.ocrimport.api.OCRImportException;
import org.xwiki.contrib.ocrimport.api.OCRImportManager;
import org.xwiki.contrib.ocrimport.filter.OCRInputFilterProperties;
import org.xwiki.filter.FilterEventParameters;
import org.xwiki.filter.FilterException;
import org.xwiki.filter.event.model.WikiDocumentFilter;
import org.xwiki.filter.input.AbstractBeanInputFilterStream;
import org.xwiki.model.reference.EntityReferenceSerializer;

/**
 * Define the OCR importer input filter stream.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(OCRInputFilterProperties.FILTER_STREAM_TYPE_STRING)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class OCRInputFilterStream
        extends AbstractBeanInputFilterStream<OCRInputFilterProperties, WikiDocumentFilter>
{
    @Inject
    private OCRImportManager manager;

    @Inject
    @Named("local")
    private EntityReferenceSerializer entityReferenceSerializer;

    @Override
    protected void read(Object filter, WikiDocumentFilter proxyFilter) throws FilterException
    {
        if (isImage(this.properties.getFileType())) {
            try {
                OCRDocument document = manager.parseImage(this.properties.getInputSource().getInputStream());

                // TODO: Allow the user to change the document title
                // TODO: Support localized documents
                String documentName = "ImportedDocument";
                proxyFilter.beginWikiDocument(documentName, generateEventParameters(document));
                proxyFilter.endWikiDocument(documentName, FilterEventParameters.EMPTY);

                document.dispose();
            } catch (IOException e) {
                throw new FilterException(
                        String.format("Unable to read source : [{}]", e));
            } catch (OCRImportException e) {
                throw new FilterException(
                        String.format("Unable to import the file : [{}]", e));
            }
        } else {
            throw new FilterException("Unsupported file type.");
        }
    }

    @Override
    public void close() throws IOException
    {
        this.properties.getInputSource().close();
    }

    /**
     * Uses the given {@link OCRDocument} to generate {@link FilterEventParameters} that are compatible with
     * {@link WikiDocumentFilter#beginWikiDocument(String, FilterEventParameters)}.
     *
     * @param document the document to use
     * @return the {@link FilterEventParameters}
     */
    private FilterEventParameters generateEventParameters(OCRDocument document)
    {
        FilterEventParameters parameters = new FilterEventParameters();
        parameters.put(WikiDocumentFilter.PARAMETER_CONTENT, document.getPlainContent());
        return parameters;
    }

    /**
     * Determine if the given file type is a supported image type.
     *
     * @param fileType the file type to use
     * @return true if the file type corresponds to a supported image type
     */
    private boolean isImage(String fileType)
    {
        Pattern p = Pattern.compile("(jpg|png)");
        return p.matcher(fileType).matches();
    }
}
