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

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.ocr.api.OCRDocument;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.filter.OCRParser;
import org.xwiki.contrib.ocr.filter.OCRInputFilterProperties;
import org.xwiki.filter.FilterEventParameters;
import org.xwiki.filter.FilterException;
import org.xwiki.filter.event.model.WikiDocumentFilter;
import org.xwiki.filter.input.AbstractBeanInputFilterStream;
import org.xwiki.filter.input.InputSource;
import org.xwiki.filter.input.InputStreamInputSource;

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
    private OCRParser manager;

    @Inject
    private Logger logger;

    @Override
    protected void read(Object filter, WikiDocumentFilter proxyFilter) throws FilterException
    {
        try {
            // TODO: Support multiple streams
            logVerbose("Extracting source ...");
            byte[] source = extractSource(this.properties.getSource());
            logVerbose("Checking source content type ...");
            MediaType streamType = extractContentType(source);
            // TODO: Support multiple file types
            MediaType pngType = MediaType.parse("image/png");

            if (streamType.equals(pngType)) {
                logVerbose("Found supported input type : [{}]", streamType);
                OCRDocument document = manager.parseImage(source);

                // TODO: Allow the user to change the document title
                // TODO: Support localized documents
                String documentName = this.properties.getName();
                proxyFilter.beginWikiDocument(documentName, generateEventParameters(document));
                proxyFilter.endWikiDocument(documentName, FilterEventParameters.EMPTY);

                document.dispose();
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
    private byte[] extractSource(InputSource source) throws FilterException, IOException
    {
        if (source instanceof InputStreamInputSource) {
            return IOUtils.toByteArray(((InputStreamInputSource) source).getInputStream());
        } else {
            throw new FilterException("Unsupported input source.");
        }
    }

    /**
     * Extract the content type of the given stream. If a file name is given, it will be used in order to ease the
     * guessing process of the stream type.
     *
     * @param source the file for which the type should be guessed
     * @param fileName the file name
     * @return the {@link MediaType} of the stream. If no type could be guessed, returns {@link MediaType#OCTET_STREAM}
     * @throws IOException if an error occurs
     */
    public MediaType extractContentType(byte[] source, String fileName) throws IOException
    {
        Detector detector = new DefaultDetector();
        Tika tika = new Tika(detector);

        MediaType mediaType = MediaType.parse(tika.detect(source, fileName));

        return mediaType;
    }

    /**
     * Just as {@link #extractContentType(byte[], String)}, extract the content type of the given stream.
     *
     * @param source the file for which the type should be guessed
     * @return the {@link MediaType} of the stream. If no type could be guessed, returns {@link MediaType#OCTET_STREAM}
     * @throws IOException if an error occurs
     */
    public MediaType extractContentType(byte[] source) throws IOException
    {
        return extractContentType(source, null);
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
     * Uses {@link #logger} to log information about the state of the filtering process
     * if {@link OCRInputFilterProperties#isVerbose()} is true.
     *
     * @param message the message to log
     * @param parameters the parameters of the message
     */
    private void logVerbose(String message, Object... parameters)
    {
        if (this.properties.isVerbose()) {
            this.logger.info(message, parameters);
        }
    }
}
