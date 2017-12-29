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
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xwiki.rendering.listener.Listener;

/**
 * This handler is responsible for transmitting hOCR content into wiki documents.
 * Note that during the conversion, a significant part of the hOCR information will be lost.
 *
 * @version $Id$
 * @since 1.0
 */
public class HOCRContentHandler extends AbstractContentHandler
{
    private Listener listener;
    private XMLReader reader;

    private HOCRParContentHandler parContentHandler;


    /**
     * Constructs a new {@link HOCRContentHandler}.
     *
     * @param reader the {@link XMLReader} that will be used for the parsing
     * @param listener the listener used for passing document events during the parsing
     */
    public HOCRContentHandler(XMLReader reader, Listener listener)
    {
        this.reader = reader;
        this.listener = listener;
        this.parContentHandler = new HOCRParContentHandler(reader, this, listener);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if ("ocr_par".equals(attributes.getValue(CLASS))) {
            listener.beginParagraph(Collections.EMPTY_MAP);
            reader.setContentHandler(parContentHandler);
        }
    }
}
