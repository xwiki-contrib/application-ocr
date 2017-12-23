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
package org.xwiki.contrib.ocr.filter;

import org.xwiki.filter.FilterStreamProperties;
import org.xwiki.filter.input.InputSource;
import org.xwiki.properties.annotation.PropertyDescription;
import org.xwiki.properties.annotation.PropertyName;

/**
 * Define the properties used by {@link org.xwiki.contrib.ocr.filter.internal.input.AbstractOCRInputFilterStream}.
 *
 * @version $Id$
 * @since 1.0
 */
public class OCRInputFilterProperties implements FilterStreamProperties
{
    /**
     * @see #isVerbose()
     */
    private boolean verbose = true;

    /**
     * @see #getSource()
     */
    private InputSource source;

    /**
     * @see #getName()
     */
    private String name;

    @Override
    public boolean isVerbose()
    {
        return this.verbose;
    }

    @Override
    public void setVerbose(boolean verbose)
    {
        this.verbose = verbose;
    }

    /**
     * @return the {@link InputSource} that contains used
     */
    @PropertyName("Source")
    @PropertyDescription("Indicates the source that will be used")
    public InputSource getSource()
    {
        return this.source;
    }

    /**
     * @param source the {@link InputSource} that contains the content used
     */
    public void setSource(InputSource source)
    {
        this.source = source;
    }

    /**
     * @return the name of the document that will be created
     */
    @PropertyName("Name")
    @PropertyDescription("The name of the document that will be created")
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name the name of the document that will be created
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
