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

import java.io.StringReader;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.contrib.ocr.filter.internal.OCRDocument;
import org.xwiki.filter.FilterEventParameters;
import org.xwiki.filter.event.model.WikiDocumentFilter;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.parser.StreamParser;
import org.xwiki.rendering.renderer.PrintRenderer;
import org.xwiki.rendering.renderer.printer.DefaultWikiPrinter;
import org.xwiki.rendering.syntax.Syntax;

/**
 * Input filter stream that transforms graphing documents containing graphical content into wiki documents
 * with XWiki syntax.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(XWikiSyntaxFilterStreamFactory.FILTER_STREAM_TYPE_STRING)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class XWikiSyntaxFilterStream extends AbstractOCRInputFilterStream
{
    @Inject
    @Named("hocr/1.2")
    private StreamParser parser;

    @Inject
    @Named("xwiki/2.1")
    private PrintRenderer renderer;

    @Override
    protected FilterEventParameters generateEventParameters(OCRDocument document) throws OCRException
    {
        FilterEventParameters parameters = new FilterEventParameters();
        parameters.put(WikiDocumentFilter.PARAMETER_CONTENT, convertToWikiSyntax(document));
        return parameters;
    }

    private String convertToWikiSyntax(OCRDocument document) throws OCRException
    {
        try {
            if (renderer instanceof Initializable) {
                ((Initializable) renderer).initialize();
            }
            renderer.setPrinter(new DefaultWikiPrinter());

            logVerbose("Converting content from [{}] to [{}] ...",
                    parser.getSyntax().toIdString(), Syntax.XWIKI_2_1.toIdString());
            parser.parse(new StringReader(document.getHOCRContent()), renderer);

            return renderer.getPrinter().toString();
        } catch (ParseException | InitializationException e) {
            throw new OCRException("Failed to get the document content.", e);
        }
    }
}
