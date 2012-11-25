/**
 * Copyright (C) 2012, Grass CRM Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gcrm.service;

import java.io.File;

import com.gcrm.dao.IDocumentDao;
import com.gcrm.domain.Document;

/**
 * Document service
 */
public class DocumentService extends BaseService<Document> implements IDocumentService {

	private IDocumentDao documentDao;
	
	/* (non-Javadoc)
	 * @see com.gcrm.service.IDocumentService#save(com.gcrm.domain.Document, java.io.File)
	 */
	public void save(Document document, File f) throws Exception{
		documentDao.save(document, f);
	}

	/**
	 * @return the documentDao
	 */
	public IDocumentDao getDocumentDao() {
		return documentDao;
	}

	/**
	 * @param documentDao the documentDao to set
	 */
	public void setDocumentDao(IDocumentDao documentDao) {
		this.documentDao = documentDao;
	}

}
