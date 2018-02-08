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
package org.xwiki.contrib.ocr.tesseract.api.internal;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;

/**
 * This is the default implementation of {@link TessConfiguration}.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
public class DefaultTessConfiguration implements TessConfiguration
{
    private static final String CONFIGURATION_PREFIX = "tesseract.";

    @Inject
    private ConfigurationSource configurationSource;

    @Override
    public String defaultLangage()
    {
        return configurationSource.getProperty(CONFIGURATION_PREFIX + "defaultLangage", "eng");
    }

    @Override
    public String dataPath()
    {
        return configurationSource.getProperty(CONFIGURATION_PREFIX + "dataPath", "./data");
    }

    @Override
    public boolean allowAutoDownload()
    {
        return configurationSource.getProperty(CONFIGURATION_PREFIX + "allowAutoDownload", true);
    }

    @Override
    public String trainingFilesURL()
    {
        return configurationSource.getProperty(CONFIGURATION_PREFIX + "trainingFilesURL",
                "https://api.github.com/repos/tesseract-ocr/tessdata/contents?ref=3.04.00");
    }

    @Override
    public int dataStoreUpdateInterval()
    {
        // Default to 10 days
        return configurationSource.getProperty(CONFIGURATION_PREFIX + "dataStoreUpdateInterval", 864000);
    }
}
