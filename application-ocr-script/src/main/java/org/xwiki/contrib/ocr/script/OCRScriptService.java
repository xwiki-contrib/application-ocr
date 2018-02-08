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
package org.xwiki.contrib.ocr.script;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.api.OCRException;
import org.xwiki.resource.temporary.TemporaryResourceReference;
import org.xwiki.resource.temporary.TemporaryResourceStore;
import org.xwiki.script.service.ScriptService;
import org.xwiki.script.service.ScriptServiceManager;
import org.xwiki.stability.Unstable;

/**
 * Generic Script Service for the OCR application.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Named(OCRScriptService.ROLE_HINT)
@Singleton
@Unstable
public class OCRScriptService implements ScriptService
{
    /**
     * The name of the script service.
     */
    public static final String ROLE_HINT = "ocr";

    /**
     * The module ID to use when storing temporary files that have been uploaded but not yet processed.
     */
    private static final String MODULE_ID = ROLE_HINT;

    @Inject
    private ScriptServiceManager scriptServiceManager;

    /**
     * Used to create and access the temporary files.
     */
    @Inject
    private TemporaryResourceStore temporaryResourceStore;

    /**
     * Get a sub script service related to ocr. (Note that we're voluntarily using an API name of "get" to make it
     * extra easy to access Script Services from Velocity (since in Velocity writing <code>$services.ocr.name</code> is
     * equivalent to writing <code>$services.ocr.get("name")</code>). It also makes it a short and easy API name for
     * other scripting languages.
     *
     * @param serviceName id of the script service
     * @return the service asked or null if none could be found
     */
    public ScriptService get(String serviceName)
    {
        return scriptServiceManager.get(ROLE_HINT + '.' + serviceName);
    }

    /**
     * Save the given stream as a temporary file and returns its path.
     *
     * @param inputStream the stream to save
     * @return the saved file temporary path
     * @throws OCRException if an error happened
     */
    public String registerUploadedFile(InputStream inputStream) throws OCRException
    {
        String resourceName = UUID.randomUUID().toString();
        TemporaryResourceReference resourceReference = new TemporaryResourceReference(MODULE_ID, resourceName);

        try {
            return temporaryResourceStore.createTemporaryFile(resourceReference, inputStream).getPath();
        } catch (IOException e) {
            throw new OCRException("Failed to register the uploaded file.", e);
        }
    }

    /**
     * Save the given file content as a temporary file and returns its path.
     * Just as {@link #registerUploadedFile(byte[])} but with byte arrays.
     *
     * @param file the file to save
     * @return the saved file temporary path
     * @throws OCRException if an error happened
     */
    public String registerUploadedFile(byte[] file) throws OCRException
    {
        return registerUploadedFile(new ByteArrayInputStream(file));
    }
}
