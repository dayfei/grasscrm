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
package com.gcrm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Account;
import com.gcrm.domain.Case;
import com.gcrm.domain.CaseOrigin;
import com.gcrm.domain.CasePriority;
import com.gcrm.domain.CaseReason;
import com.gcrm.domain.CaseStatus;
import com.gcrm.domain.CaseType;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Document;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Case
 * 
 */
public class ListCaseAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Case> baseService;
	private IBaseService<CaseStatus> caseStatusService;
	private IBaseService<CasePriority> casePriorityService;
	private IBaseService<CaseType> caseTypeService;
	private IBaseService<CaseOrigin> caseOriginService;
	private IBaseService<CaseReason> caseReasonService;		
	private IBaseService<Account> accountService;
	private IBaseService<User> userService;
	private IBaseService<Document> documentService;
	private Case caseInstance;

	private static final String CLAZZ = Case.class.getSimpleName();

	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Case> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<Case> cases = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();

		getListJson(cases, totalRecords);
		return null;
	}

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public static void getListJson(Iterator<Case> cases, long totalRecords)
			throws Exception {

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";

		String userName = null;
		String accountName = null;
		String priorityName = null;
		String statusName = null;
		while (cases.hasNext()) {
			Case instance = cases.next();
			int id = instance.getId();
			String subject = instance.getSubject();
			Account account = instance.getAccount();
			if (account != null) {
				accountName = account.getName();
			} else {
				accountName = "";
			}
			CasePriority casePriority = instance.getPriority();
			if (casePriority != null) {
				priorityName = casePriority.getName();
			} else {
				priorityName = "";
			}
			CaseStatus caseStatus = instance.getStatus();
			if (caseStatus != null) {
				statusName = caseStatus.getName();
			} else {
				statusName = "";
			}
			User user = instance.getAssigned_to();
			if (user != null) {
				userName = user.getName();
			} else {
				userName = "";
			}
			User createdBy = instance.getCreated_by();
			String createdByName = "";
			if (createdBy != null) {
				createdByName = CommonUtil.fromNullToEmpty(createdBy.getName());
			}
			User updatedBy = instance.getUpdated_by();
			String updatedByName = "";
			if (updatedBy != null) {
				updatedByName = CommonUtil.fromNullToEmpty(updatedBy.getName());
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constant.DATE_TIME_FORMAT);
			Date createdOn = instance.getCreated_on();
			String createdOnName = "";
			if (createdOn != null) {
				createdOnName = dateFormat.format(createdOn);
			}
			Date updatedOn = instance.getUpdated_on();
			String updatedOnName = "";
			if (updatedOn != null) {
				updatedOnName = dateFormat.format(updatedOn);
			}

			json += "{\"id\":\"" + id 
					+ "\",\"subject\":\"" + subject + "\",\"accountName\":\""
					+ accountName + "\",\"priorityName\":\"" + priorityName
					+ "\",\"statusName\":\"" + statusName
					+ "\",\"user_name\":\"" + userName + "\",\"created_by\":\""
					+ createdByName + "\",\"updated_by\":\"" + updatedByName
					+ "\",\"created_on\":\"" + createdOnName
					+ "\",\"updated_on\":\"" + updatedOnName + "\"}";
			if (cases.hasNext()) {
				json += ",";
			}
		}
		json += "]}";

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(json);
	}

	/**
	 * Selects the entities
	 * 
	 * @return the SUCCESS result
	 */
	public String select() throws ServiceException {
		Document document = null;
		Set<Case> cases = null;
		if ("Document".equals(this.getRelationKey())) {
			document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			cases = document.getCases();
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String selectId = ids[i];
				caseInstance = baseService.getEntityById(Case.class,
						Integer.valueOf(selectId));
				cases.add(caseInstance);
			}
		}
		if ("Document".equals(this.getRelationKey())) {
			documentService.makePersistent(document);
		}
		return SUCCESS;
	}

	/**
	 * Unselects the entities
	 * 
	 * @return the SUCCESS result
	 */
	public String unselect() throws ServiceException {
		Document document = null;
		Set<Case> cases = null;
		if ("Document".equals(this.getRelationKey())) {
			document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			cases = document.getCases();
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			Collection<Case> selectedCases = new ArrayList<Case>();
			for (int i = 0; i < ids.length; i++) {
				Integer removeId = Integer.valueOf(ids[i]);
				A: for (Case caseInstance : cases) {
					if (caseInstance.getId().intValue() == removeId.intValue()) {
						selectedCases.add(caseInstance);
						break A;
					}
				}
			}
			cases.removeAll(selectedCases);
		}
		if ("Document".equals(this.getRelationKey())) {
			documentService.makePersistent(document);
		}
		return SUCCESS;
	}

	/**
	 * Gets the related contacts.
	 * 
	 * @return null
	 */
	public String filterCaseContact() throws Exception {
		caseInstance = baseService.getEntityById(Case.class, id);
		Set<Contact> contacts = caseInstance.getContacts();
		Iterator<Contact> contactIterator = contacts.iterator();
		long totalRecords = contacts.size();
		ListContactAction.getListJson(contactIterator, totalRecords);
		return null;
	}

	/**
	 * Gets the related documents.
	 * 
	 * @return null
	 */
	public String filteCaseDocument() throws Exception {
		caseInstance = baseService.getEntityById(Case.class, id);
		Set<Document> documents = caseInstance.getDocuments();
		Iterator<Document> documentIterator = documents.iterator();
		long totalRecords = documents.size();
		ListDocumentAction.getListJson(documentIterator, totalRecords);
		return null;
	}

	/**
	 * Deletes the selected entities.
	 * 
	 * @return the SUCCESS result
	 */
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Case.class, this.getSeleteIDs());
		return SUCCESS;
	}

	/**
	 * Removes the related entities
	 * 
	 * @return the SUCCESS result
	 */
	public String remove() throws ServiceException {
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String removeId = ids[i];
				caseInstance = baseService.getEntityById(Case.class,
						Integer.valueOf(removeId));
				if ("Account".equals(super.getRemoveKey())) {
					caseInstance.setAccount(null);
				} else if ("Contact".equals(super.getRemoveKey())) {

				}
				this.baseService.makePersistent(caseInstance);
			}
		}

		return SUCCESS;
	}

	/**
	 * Copies the selected entities
	 * 
	 * @return the SUCCESS result
	 */
	public String copy() throws ServiceException {
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String copyid = ids[i];
				Case oriRecord = baseService.getEntityById(Case.class,
						Integer.valueOf(copyid));
				Case targetRecord = oriRecord.clone();
				targetRecord.setId(null);
				this.getbaseService().makePersistent(targetRecord);
			}
		}
		return SUCCESS;
	}

	/**
	 * Exports the entities
	 * 
	 * @return the exported entities inputStream
	 */
	public InputStream getInputStream() throws Exception {
		File file = new File(CLAZZ + ".csv");
		ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
				CsvPreference.EXCEL_PREFERENCE);
		try {
			final String[] header = new String[] { "ID", "Priority",
					"Status", "Case Type", "Case Origin","Case Reason","Subject", "Description",
					"Resolution", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Case caseInstance = baseService.getEntityById(Case.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], caseInstance.getId());
			    if (caseInstance.getPriority() != null) {
					data1.put(header[1], caseInstance.getPriority().getId());
				} else {
					data1.put(header[1], "");
				}
				if (caseInstance.getStatus() != null) {
					data1.put(header[2], caseInstance.getStatus().getId());
				} else {
					data1.put(header[2], "");
				}
				if (caseInstance.getType() != null) {
					data1.put(header[3], caseInstance.getType().getId());
				} else {
					data1.put(header[3], "");
				}
				if (caseInstance.getOrigin() != null) {
					data1.put(header[4], caseInstance.getOrigin().getId());
				} else {
					data1.put(header[4], "");
				}
				if (caseInstance.getReason() != null) {
					data1.put(header[5], caseInstance.getReason().getId());
				} else {
					data1.put(header[5], "");
				}					
				data1.put(header[6],
						CommonUtil.fromNullToEmpty(caseInstance.getSubject()));
				data1.put(header[7], CommonUtil.fromNullToEmpty(caseInstance
						.getDescription()));
				data1.put(header[8], CommonUtil.fromNullToEmpty(caseInstance
						.getResolution()));
				if (caseInstance.getAssigned_to() != null) {
					data1.put(header[9], caseInstance.getAssigned_to().getId());
				} else {
					data1.put(header[9], "");
				}
				writer.write(data1, header);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			writer.close();
		}

		InputStream in = new FileInputStream(file);
		this.setFileName(CLAZZ + ".csv");
		return in;
	}

	/**
	 * Imports the entities
	 * 
	 * @return the SUCCESS result
	 */
	public String importCSV() throws Exception {
		File file = this.getUpload();
		CsvListReader reader = new CsvListReader(new FileReader(file),
				CsvPreference.EXCEL_PREFERENCE);
		int failedNum = 0;
		int successfulNum = 0;
		try {
			final String[] header = reader.getCSVHeader(true);

			List<String> line = new ArrayList<String>();
			Map<String, String> failedMsg = new HashMap<String, String>();
			while ((line = reader.read()) != null) {

				Map<String, String> row = new HashMap<String, String>();
				for (int i = 0; i < line.size(); i++) {
					row.put(header[i], line.get(i));
				}
				Case caseInstance = new Case();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						caseInstance.setId(Integer.parseInt(id));
					}
					String priorityID = row.get("Priority");
					if (CommonUtil.isNullOrEmpty(priorityID)) {
						caseInstance.setPriority(null);
					} else {
						CasePriority priority = casePriorityService
								.getEntityById(CasePriority.class,
										Integer.parseInt(priorityID));
						caseInstance.setPriority(priority);
					}
					String statusID = row.get("Status");
					if (CommonUtil.isNullOrEmpty(statusID)) {
						caseInstance.setStatus(null);
					} else {
						CaseStatus status = caseStatusService.getEntityById(
								CaseStatus.class, Integer.parseInt(statusID));
						caseInstance.setStatus(status);
					}
					String typeID = row.get("Case Type");
					if (CommonUtil.isNullOrEmpty(typeID)) {
						caseInstance.setType(null);
					} else {
						CaseType type = caseTypeService.getEntityById(
								CaseType.class, Integer.parseInt(typeID));
						caseInstance.setType(type);
					}
					String originID = row.get("Case Origin");
					if (CommonUtil.isNullOrEmpty(originID)) {
						caseInstance.setOrigin(null);
					} else {
						CaseOrigin origin = caseOriginService.getEntityById(
								CaseOrigin.class, Integer.parseInt(originID));
						caseInstance.setOrigin(origin);
					}
					String reasonID = row.get("Case Reason");
					if (CommonUtil.isNullOrEmpty(reasonID)) {
						caseInstance.setReason(null);
					} else {
						CaseReason reason = caseReasonService.getEntityById(
								CaseReason.class, Integer.parseInt(reasonID));
						caseInstance.setReason(reason);
					}						
					caseInstance.setSubject(CommonUtil.fromNullToEmpty(row
							.get("Subject")));
					caseInstance.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					caseInstance.setResolution(CommonUtil.fromNullToEmpty(row
							.get("Resolution")));
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						caseInstance.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						caseInstance.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(caseInstance);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					failedMsg.put(String.valueOf(caseInstance.getSubject()),
							e.getMessage());
				}

			}

			this.setFailedMsg(failedMsg);
			this.setFailedNum(failedNum);
			this.setSuccessfulNum(successfulNum);
			this.setTotalNum(successfulNum + failedNum);
		} finally {
			reader.close();
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

	public IBaseService<Case> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Case> baseService) {
		this.baseService = baseService;
	}

	public Case getCase() {
		return caseInstance;
	}

	public void setCase(Case caseInstance) {
		this.caseInstance = caseInstance;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the caseStatusService
	 */
	public IBaseService<CaseStatus> getCaseStatusService() {
		return caseStatusService;
	}

	/**
	 * @param caseStatusService
	 *            the caseStatusService to set
	 */
	public void setCaseStatusService(IBaseService<CaseStatus> caseStatusService) {
		this.caseStatusService = caseStatusService;
	}

	/**
	 * @return the casePriorityService
	 */
	public IBaseService<CasePriority> getCasePriorityService() {
		return casePriorityService;
	}

	/**
	 * @param casePriorityService
	 *            the casePriorityService to set
	 */
	public void setCasePriorityService(
			IBaseService<CasePriority> casePriorityService) {
		this.casePriorityService = casePriorityService;
	}

	/**
	 * @return the caseTypeService
	 */
	public IBaseService<CaseType> getCaseTypeService() {
		return caseTypeService;
	}

	/**
	 * @param caseTypeService
	 *            the caseTypeService to set
	 */
	public void setCaseTypeService(IBaseService<CaseType> caseTypeService) {
		this.caseTypeService = caseTypeService;
	}

	/**
	 * @return the accountService
	 */
	public IBaseService<Account> getAccountService() {
		return accountService;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	public void setAccountService(IBaseService<Account> accountService) {
		this.accountService = accountService;
	}

	/**
	 * @return the userService
	 */
	public IBaseService<User> getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IBaseService<User> userService) {
		this.userService = userService;
	}

	/**
	 * @return the caseInstance
	 */
	public Case getCaseInstance() {
		return caseInstance;
	}

	/**
	 * @param caseInstance
	 *            the caseInstance to set
	 */
	public void setCaseInstance(Case caseInstance) {
		this.caseInstance = caseInstance;
	}

	/**
	 * @return the documentService
	 */
	public IBaseService<Document> getDocumentService() {
		return documentService;
	}

	/**
	 * @param documentService
	 *            the documentService to set
	 */
	public void setDocumentService(IBaseService<Document> documentService) {
		this.documentService = documentService;
	}

	/**
	 * @return the caseOriginService
	 */
	public IBaseService<CaseOrigin> getCaseOriginService() {
		return caseOriginService;
	}

	/**
	 * @param caseOriginService the caseOriginService to set
	 */
	public void setCaseOriginService(IBaseService<CaseOrigin> caseOriginService) {
		this.caseOriginService = caseOriginService;
	}

	/**
	 * @return the caseReasonService
	 */
	public IBaseService<CaseReason> getCaseReasonService() {
		return caseReasonService;
	}

	/**
	 * @param caseReasonService the caseReasonService to set
	 */
	public void setCaseReasonService(IBaseService<CaseReason> caseReasonService) {
		this.caseReasonService = caseReasonService;
	}

}
