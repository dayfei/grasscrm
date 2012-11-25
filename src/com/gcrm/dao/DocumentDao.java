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
package com.gcrm.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.gcrm.domain.Document;


/**
 * Document DAO
 */
public class DocumentDao extends BaseDao<Document> implements IDocumentDao {

	/* (non-Javadoc)
	 * @see com.gcrm.dao.IDocumentDao#save(com.gcrm.domain.Document, java.io.File)
	 */
	@SuppressWarnings("resource")
	public void save(Document document, File f) throws Exception {
		SessionFactory sessionFactory = this.getHibernateTemplate()
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		InputStream stream = new BufferedInputStream(new FileInputStream(f));
		byte[] input = new byte[stream.available()];
		stream.read(input);
		document.setFileContent(input);
		session.save(document);
		session.getTransaction().commit();
		session.close();
	}

}
