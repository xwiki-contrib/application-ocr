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

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.configuration.ConfigurationSource;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.model.reference.DocumentReference;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;

import opennlp.tools.util.StringUtil;

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

    private static final String DEFAULT_LANGUAGE = "defaultLanguage";

    private static final String DATA_PATH = "dataPath";

    private static final String TRAINING_FILES_URL = "trainingFilesURL";

    private static final String DATA_STORE_UPDATE_INTERVAL = "dataStoreUpdateInterval";

    private static final String ALLOW_AUTO_DOWNLOAD = "allowAutoDownload";

    private static final String XWIKI = "xwiki";

    private static final List<String> TESSERACT_CODE_SPACE = Arrays.asList("OCR", "Tesseract", "Code");

    @Inject
    private ConfigurationSource configurationSource;

    @Inject
    private Provider<XWikiContext> xWikiContextProvider;

    @Override
    public String defaultLangage()
    {
        String defaultValue = configurationSource.getProperty(CONFIGURATION_PREFIX + DEFAULT_LANGUAGE, "eng");

        return getConfigStringOrDefault(DEFAULT_LANGUAGE, defaultValue);
    }

    @Override
    public String dataPath()
    {
        String defaultValue = configurationSource.getProperty(CONFIGURATION_PREFIX + DATA_PATH, "./data");

        return getConfigStringOrDefault(DATA_PATH, defaultValue);
    }

    @Override
    public boolean allowAutoDownload()
    {
        boolean defaultValue = configurationSource.getProperty(CONFIGURATION_PREFIX + ALLOW_AUTO_DOWNLOAD, true);

        try {
            BaseObject baseObject = getConfigurationObject();

            if (baseObject != null) {
                int defaultIntValue = (defaultValue) ? 1 : 0;
                return (baseObject.getIntValue(ALLOW_AUTO_DOWNLOAD, defaultIntValue) == 1);
            }
        } catch (XWikiException e) {
            // Fail silently
        }

        return defaultValue;
    }

    @Override
    public String trainingFilesURL()
    {
        String defaultValue = configurationSource.getProperty(CONFIGURATION_PREFIX + TRAINING_FILES_URL,
                "https://api.github.com/repos/tesseract-ocr/tessdata/contents?ref=3.04.00");

        return getConfigStringOrDefault(TRAINING_FILES_URL, defaultValue);
    }

    @Override
    public int dataStoreUpdateInterval()
    {
        // Default to 10 days
        int defaultValue = configurationSource.getProperty(CONFIGURATION_PREFIX + DATA_STORE_UPDATE_INTERVAL, 864000);

        try {
            BaseObject baseObject = getConfigurationObject();

            if (baseObject != null) {
                return baseObject.getIntValue(DATA_STORE_UPDATE_INTERVAL, defaultValue);
            }
        } catch (XWikiException e) {
            // Fail silently
        }

        return defaultValue;
    }

    /**
     * Tries to extract form the configuration XObject the string value corresponding to the given configurationKey.
     * If the object cannot be retrieved, or the string value is empty, returns the default value.
     *
     * @param configurationKey the configuration key to use against the XObject
     * @param defaultValue the default value to return
     * @return the configuration parameter
     */
    private String getConfigStringOrDefault(String configurationKey, String defaultValue)
    {
        try {
            BaseObject baseObject = getConfigurationObject();

            if (baseObject != null) {
                String propertyValue = baseObject.getStringValue(configurationKey);
                if (!StringUtil.isEmpty(propertyValue)) {
                    return propertyValue;
                }
            }
        } catch (XWikiException e) {
            // Fail silently
        }

        return defaultValue;
    }

    private BaseObject getConfigurationObject() throws XWikiException
    {
        XWikiContext xWikiContext = xWikiContextProvider.get();
        XWiki xwiki = xWikiContext.getWiki();

        XWikiDocument adminDoc = xwiki.getDocument(new DocumentReference(XWIKI,
                TESSERACT_CODE_SPACE, "Configuration"), xWikiContext);

        return adminDoc.getXObject(new DocumentReference(XWIKI,
                TESSERACT_CODE_SPACE, "TesseractConfigurationClass"));
    }
}
