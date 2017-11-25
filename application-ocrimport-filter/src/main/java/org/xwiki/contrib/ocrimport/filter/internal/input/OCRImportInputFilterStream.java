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
package org.xwiki.contrib.ocrimport.filter.internal.input;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.ocrimport.filter.OCRImportInputFilterProperties;
import org.xwiki.contrib.ocrimport.filter.internal.OCRImportFilter;
import org.xwiki.filter.FilterException;
import org.xwiki.filter.input.AbstractBeanInputFilterStream;

/**
 * Define the OCR importer input filter stream.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(OCRImportInputFilterProperties.FILTER_STREAM_TYPE_STRING)
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
public class OCRImportInputFilterStream
        extends AbstractBeanInputFilterStream<OCRImportInputFilterProperties, OCRImportFilter>
{
    @Override
    protected void read(Object filter, OCRImportFilter proxyFilter) throws FilterException
    {

    }

    @Override
    public void close() throws IOException
    {
        this.properties.getInputSource().close();
    }

    /**
     * Determine if the given file name has an extension of a supported image type.
     *
     * @param fileName the file name to use
     * @return true if the file has a supported image type extension
     */
    private boolean isImage(String fileName)
    {
        Pattern p = Pattern.compile(".*\\.(jpg|png)");
        return p.matcher(fileName).matches();
    }
}
