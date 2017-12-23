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

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.plexus.util.IOUtil;
import org.slf4j.Logger;
import org.xwiki.contrib.ocr.filter.OCRInputFilterProperties;
import org.xwiki.contrib.ocr.filter.internal.ImageRenderer;
import org.xwiki.contrib.ocr.filter.internal.OCRDocument;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.filter.internal.OCRMediaTypeChecker;
import org.xwiki.contrib.ocr.filter.internal.OCRParser;
import org.xwiki.filter.FilterEventParameters;
import org.xwiki.filter.FilterException;
import org.xwiki.filter.event.model.WikiDocumentFilter;
import org.xwiki.filter.input.AbstractBeanInputFilterStream;
import org.xwiki.filter.input.FileInputSource;
import org.xwiki.filter.input.InputSource;
import org.xwiki.filter.input.InputStreamInputSource;

/**
 * Define generic methods for an OCR input filter stream. Input filter streams derived from this abstract are
 * creating WikiDocument events containing a content specific to the filter.
 *
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractOCRInputFilterStream
        extends AbstractBeanInputFilterStream<OCRInputFilterProperties, WikiDocumentFilter>
{
    @Inject
    private OCRParser manager;

    @Inject
    private Logger logger;

    @Override
    protected void read(Object filter, WikiDocumentFilter proxyFilter) throws FilterException
    {
        OCRDocument document;

        try {
            // TODO: Support multiple streams
            logVerbose("Extracting source ...");
            InputStream source = extractSource(this.properties.getSource());

            logVerbose("Checking source media type ...");
            OCRMediaTypeChecker mediaTypeChecker = new OCRMediaTypeChecker(source);

            if (mediaTypeChecker.isImage()) {
                source = extractSource(this.properties.getSource());

                logVerbose("Found supported input type : [{}]", mediaTypeChecker.getMediaType());
                document = manager.parseImage(IOUtil.toByteArray(source));

                // TODO: Support localized documents
                String documentName = this.properties.getName();
                proxyFilter.beginWikiDocument(documentName, generateEventParameters(document));
                proxyFilter.endWikiDocument(documentName, FilterEventParameters.EMPTY);

                document.dispose();
            } else if (mediaTypeChecker.isPDF()) {
                source.close();
                source = extractSource(this.properties.getSource());

                List<Image> images = ImageRenderer.renderPDF(source);

                for (Image image : images) {
                    document = manager.parseImage(image);
                }
            } else {
                throw new FilterException("Unsupported file type.");
            }
        } catch (IOException e) {
            throw new FilterException("Unable to read source.", e);
        } catch (OCRException e) {
            throw new FilterException("Unable to import the file.", e);
        }
    }

    @Override
    public void close() throws IOException
    {
        this.properties.getSource().close();
    }

    /**
     * Check if the given {@link InputSource} is supported and, if so, extract it as a byte array to be used by the
     * parsing library.
     *
     * @param source the {@link InputSource} to use
     * @return the source content
     * @throws FilterException if the {@link InputSource} is not supported
     * @throws IOException if the retrieval of the source content failed
     */
    private InputStream extractSource(InputSource source) throws FilterException, IOException
    {
        if (source instanceof FileInputSource) {
            return new FileInputStream(((FileInputSource) source).getFile());
        }
        if (source instanceof InputStreamInputSource) {
            return ((InputStreamInputSource) source).getInputStream();
        } else {
            throw new FilterException("Unsupported input source.");
        }
    }

    /**
     * Uses the given {@link OCRDocument} to generate {@link FilterEventParameters} that are compatible with
     * {@link WikiDocumentFilter#beginWikiDocument(String, FilterEventParameters)}.
     *
     * @param document the document to use
     * @return the {@link FilterEventParameters}
     * @throws OCRException if an error happens
     */
    protected abstract FilterEventParameters generateEventParameters(OCRDocument document) throws OCRException;

    /**
     * Uses {@link #logger} to log information about the state of the filtering process
     * if {@link OCRInputFilterProperties#isVerbose()} is true.
     *
     * @param message the message to log
     * @param parameters the parameters of the message
     */
    protected void logVerbose(String message, Object... parameters)
    {
        if (this.properties.isVerbose()) {
            this.logger.info(message, parameters);
        }
    }
}
