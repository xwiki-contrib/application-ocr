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
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Define a set of methods and attributes that can be used by children content handlers when parsing hOCR content.
 *
 * @version $Id$
 * @since 1.0
 */
public abstract class AbstractContentHandler implements ContentHandler
{
    /**
     * A class attribute.
     */
    protected static final String CLASS = "class";

    @Override
    public void setDocumentLocator(Locator locator)
    {

    }

    @Override
    public void startDocument() throws SAXException
    {

    }

    @Override
    public void endDocument() throws SAXException
    {

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
    public void startElement(String s, String s1, String s2, Attributes attributes) throws SAXException
    {

    }

    @Override
    public void endElement(String s, String s1, String s2) throws SAXException
    {

    }

    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException
    {

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
