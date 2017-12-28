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

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xwiki.rendering.listener.Listener;

/**
 * Define methods useful for child content handlers.
 *
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractChildContentHandler extends AbstractContentHandler
{
    /**
     * The depth of the parser in the current handler ; useful to determine when this content handler should
     * give the control back to his parent.
     */
    protected int depth = 1;

    /**
     * The {@link Listener} to transmit document events.
     */
    protected Listener listener;

    /**
     * The current {@link XMLReader}.
     */
    protected XMLReader reader;

    /**
     * The parent {@link ContentHandler}.
     */
    protected ContentHandler parentContentHandler;

    /**
     * Constructs a new {@link AbstractChildContentHandler}.
     *
     * @param reader the XML reader that will be used during the parsing
     * @param contentHandler the parent content handler
     * @param listener the listener used for passing document events during the parsing.
     */
    public AbstractChildContentHandler(XMLReader reader, ContentHandler contentHandler, Listener listener)
    {
        this.reader = reader;
        this.listener = listener;
        this.parentContentHandler = contentHandler;
    }

    /**
     * Reset the depth of the content handler to its default value.
     */
    public void resetDepth()
    {
        depth = 1;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException
    {
        depth++;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        depth--;
        if (depth == 0) {
            resetDepth();
            onParentHandlerSwitch();
            reader.setContentHandler(parentContentHandler);
        }
    }

    /**
     * Method called when the reader content handler will be switched from the current handler to the parent handler.
     */
    protected void onParentHandlerSwitch()
    {

    }
}
