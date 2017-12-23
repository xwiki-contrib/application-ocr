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

import java.util.Collections;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xwiki.rendering.listener.Listener;
import org.xwiki.rendering.listener.MetaData;
import org.xwiki.rendering.syntax.Syntax;

/**
 * This handler is responsible for transmitting hOCR content into wiki documents.
 * Note that during the conversion, a significant part of the hOCR information will be lost.
 *
 * @version $Id$
 * @since 1.0
 */
public class HOCRContentHandler implements ContentHandler
{
    /**
     * A paragraph element.
     */
    private static final String PARAGRAPH = "p";

    private Listener listener;

    /**
     * Constructs a new {@link HOCRContentHandler}.
     *
     * @param listener the listener used for passing document events during the parsing.
     */
    public HOCRContentHandler(Listener listener)
    {
        this.listener = listener;
    }

    @Override
    public void setDocumentLocator(Locator locator)
    {

    }

    @Override
    public void startDocument() throws SAXException
    {
        listener.beginDocument(MetaData.EMPTY);
    }

    @Override
    public void endDocument() throws SAXException
    {
        listener.endDocument(MetaData.EMPTY);
    }

    @Override
    public void startPrefixMapping(String s, String s1) throws SAXException
    {

    }

    @Override
    public void endPrefixMapping(String s) throws SAXException
    {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equals(PARAGRAPH)) {
            listener.beginParagraph(Collections.EMPTY_MAP);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equals(PARAGRAPH)) {
            listener.endParagraph(Collections.EMPTY_MAP);
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException
    {
        listener.onRawText(new String(chars, start, length), Syntax.PLAIN_1_0);
    }

    @Override
    public void ignorableWhitespace(char[] chars, int i, int i1) throws SAXException
    {

    }

    @Override
    public void processingInstruction(String s, String s1) throws SAXException
    {

    }

    @Override
    public void skippedEntity(String s) throws SAXException
    {

    }
}
