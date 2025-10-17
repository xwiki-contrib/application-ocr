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
package org.xwiki.contrib.ocr.tesseract.script;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.ocr.tesseract.api.TessConfiguration;
import org.xwiki.contrib.ocr.tesseract.filter.internal.input.XWikiSyntaxFilterStreamFactory;
import org.xwiki.contrib.ocr.script.OCRScriptService;
import org.xwiki.contrib.ocr.tesseract.api.TessException;
import org.xwiki.filter.input.DefaultFileInputSource;
import org.xwiki.filter.internal.job.FilterStreamConverterJob;
import org.xwiki.filter.job.FilterStreamConverterJobRequest;
import org.xwiki.filter.type.FilterStreamType;
import org.xwiki.job.JobExecutor;
import org.xwiki.job.event.status.JobStatus;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceResolver;
import org.xwiki.script.service.ScriptService;
import org.xwiki.script.service.ScriptServiceManager;
import org.xwiki.stability.Unstable;

/**
 * Define script services that are specific to the Tesseract library implementation of the OCR application.
 *
 * @version $Id$
 * @since 1.0
 */
@Component
@Singleton
@Named(OCRScriptService.ROLE_HINT + "." + TessScriptService.ROLE_HINT)
@Unstable
public class TessScriptService implements ScriptService
{
    /**
     * The role suffix of the script service.
     */
    static final String ROLE_HINT = "tesseract";

    private static final String COMPONENT_NAME = OCRScriptService.ROLE_HINT + "." + TessScriptService.ROLE_HINT;

    private JobStatus lastImportStatus;

    @Inject
    private TessConfiguration tessConfiguration;

    @Inject
    private DocumentAccessBridge documentAccessBridge;

    @Inject
    private EntityReferenceResolver<String> entityReferenceResolver;

    @Inject
    private JobExecutor jobExecutor;

    @Inject
    private ScriptServiceManager scriptServiceManager;

    /**
     * Get a sub script service related to the Tesseract script service.
     *
     * @param serviceName id of the script service
     * @return the service asked or null if none could be found
     */
    public ScriptService get(String serviceName)
    {
        return scriptServiceManager.get(COMPONENT_NAME + '.' + serviceName);
    }

    /**
     * Import the given file using Tesseract.
     *
     * @param filePath the path to the file to import
     * @param language the language of the file. This language should be contained in the Tesseract data store
     * @param parentReference the reference to the parent document of the imported document
     * @param name the document name
     * @param title the document title
     * @return the status of the conversion job
     * @throws TessException if an error happened
     */
    public JobStatus importFile(String filePath, String language, String parentReference, String name, String title)
            throws TessException
    {
        String documentLang = (language == null) ? tessConfiguration.defaultLangage() : language;
        EntityReference spaceReference = entityReferenceResolver.resolve(parentReference, EntityType.SPACE);

        Map<String, Object> outputProperties = new HashMap<>();
        outputProperties.put("previousDeleted", true);
        outputProperties.put("defaultReference", spaceReference);
        outputProperties.put("isAuthorSet", true);
        outputProperties.put("author", documentAccessBridge.getCurrentUserReference());

        try {
            Map<String, Object> inputProperties = new HashMap<>();
            inputProperties.put("name", name);
            inputProperties.put("title", title);
            inputProperties.put("language", documentLang);
            inputProperties.put("source", new DefaultFileInputSource(new File(filePath)));

            FilterStreamConverterJobRequest request =
                    new FilterStreamConverterJobRequest(
                            XWikiSyntaxFilterStreamFactory.FILTER_STREAM_TYPE, inputProperties,
                            FilterStreamType.XWIKI_INSTANCE, outputProperties);

            this.lastImportStatus = this.jobExecutor.execute(FilterStreamConverterJob.JOBTYPE, request).getStatus();
            return lastImportStatus;
        } catch (Exception e) {
            throw new TessException("Failed to start the file import.", e);
        }
    }

    /**
     * Import the given file using Tesseract. Same as {@link #importFile(String, String, String, String, String)} but
     * using the default language as the file language.
     *
     * @param filePath the path to the file to import
     * @param parentReference the reference to the parent document of the imported document
     * @param name the document name
     * @param title the document title
     * @return the status of the conversion job
     * @throws TessException if an error happened
     */
    public JobStatus importFile(String filePath, String parentReference, String name, String title)
            throws TessException
    {
        return importFile(filePath, null, parentReference, name, title);
    }

    /**
     * @return the last registered file import job status. If no job has been triggered yet, returns null.
     */
    public JobStatus getCurrentImportStatus()
    {
        return this.lastImportStatus;
    }
}
