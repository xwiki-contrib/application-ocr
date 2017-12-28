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
package org.xwiki.contrib.ocr.syntax.hocr.internal.parser;

import java.io.IOException;
import java.io.Reader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.listener.Listener;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.parser.StreamParser;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.syntax.SyntaxType;
import org.xwiki.xml.XMLReaderFactory;

/**
 * Parser for hOCR syntax.
 * Specifications: https://kba.github.io/hocr-spec/1.2/
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named("hocr/1.2")
public class HOCRStreamParser implements StreamParser
{
    @Inject
    private XMLReaderFactory xmlReaderFactory;

    @Override
    public Syntax getSyntax()
    {
        return new Syntax(new SyntaxType("hocr", "hOCR"), "1.2");
    }

    @Override
    public void parse(Reader source, Listener listener) throws ParseException
    {
        try {
            XMLReader reader = xmlReaderFactory.createXMLReader();

            reader.setContentHandler(new HOCRContentHandler(reader, listener));
            reader.parse(new InputSource(source));
        } catch (SAXException e) {
            throw new ParseException("Failed to parse the XML.", e);
        } catch (IOException e) {
            throw new ParseException("Failed to read the content source.", e);
        } catch (ParserConfigurationException e) {
            throw new ParseException("Failed to configure the SAX parser.", e);
        }
    }
}
