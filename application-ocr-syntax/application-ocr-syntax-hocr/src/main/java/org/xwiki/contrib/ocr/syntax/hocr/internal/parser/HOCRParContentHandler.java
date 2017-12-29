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
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xwiki.rendering.listener.Listener;

/**
 * Handler for hOCR paragraphs.
 *
 * @version $Id$
 * @since 1.0
 */
public class HOCRParContentHandler extends AbstractChildContentHandler
{
    private HOCRLineContentHandler lineContentHandler;

    /**
     * @see AbstractChildContentHandler#AbstractChildContentHandler(XMLReader, ContentHandler, Listener)
     *
     * @param reader the XML reader that will be used during the parsing
     * @param contentHandler the parent content handler
     * @param listener the listener used for passing document events during the parsing.
     */
    public HOCRParContentHandler(XMLReader reader, ContentHandler contentHandler, Listener listener)
    {
        super(reader, contentHandler, listener);
        lineContentHandler = new HOCRLineContentHandler(reader, this, listener);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);

        if ("ocr_line".equals(attributes.getValue(CLASS))) {
            reader.setContentHandler(lineContentHandler);
        }
    }

    @Override
    protected void onParentHandlerSwitch()
    {
        listener.onNewLine();
        listener.endParagraph(Collections.EMPTY_MAP);
    }
}
