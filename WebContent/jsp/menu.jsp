
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="java.util.Map" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%Map<String,String> permissions = (Map<String,String>)session.getAttribute("permissions"); %>
	<div style="background:#fafafa;padding:5px;width:100%;border:1px solid #ccc">
		<a href="javascript:void(0)" id="mb1" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-sale'"><s:text name='menu.sales.title'/></a>
		<a href="javascript:void(0)" id="mb2" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-market'"><s:text name='menu.marketing.title'/></a>
		<a href="javascript:void(0)" id="mb3" class="easyui-menubutton" data-options="menu:'#mm3',iconCls:'icon-support'"><s:text name='menu.support.title'/></a>	
		<a href="javascript:void(0)" id="mb4" class="easyui-menubutton" data-options="menu:'#mm4',iconCls:'icon-activity'"><s:text name='menu.activities.title'/></a>
		<a href="javascript:void(0)" id="mb5" class="easyui-menubutton" data-options="menu:'#mm5',iconCls:'icon-collaboration'"><s:text name='menu.collaboration.title'/></a>
		<a href="javascript:void(0)" id="mb6" class="easyui-menubutton" data-options="menu:'#mm6',iconCls:'icon-system'"><s:text name='menu.system.title'/></a>									
		<a href="javascript:void(0)" id="mb7" class="easyui-menubutton" data-options="menu:'#mm7',iconCls:'icon-help'"><s:text name='menu.help.title'/></a>
	</div>
	<div id="mm1" style="width:150px;">
		<div><s:text name='menu.home.title'/></div>
		<div class="menu-sep"></div>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listAccount.jsp\")%>"><div onClick="openPage('/crm/listAccountPage.action')"><s:text name='menu.accounts.title'/></div></security:authorize>	
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listContact.jsp\")%>"><div onClick="openPage('/crm/listContactPage.action')"><s:text name='menu.contacts.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listOpportunity.jsp\")%>"><div onClick="openPage('/crm/listOpportunityPage.action')"><s:text name='menu.opportunities.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listLead.jsp\")%>"><div onClick="openPage('/crm/listLeadPage.action')"><s:text name='menu.leads.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTargetList.jsp\")%>"><div onClick="openPage('/crm/listTargetListPage.action')"><s:text name='menu.targetLists.title'/></div></security:authorize>
	</div>
	<div id="mm2" style="width:100px;">
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listAccount.jsp\")%>"><div onClick="openPage('/crm/listAccountPage.action')"><s:text name='menu.accounts.title'/></div></security:authorize>	
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listContact.jsp\")%>"><div onClick="openPage('/crm/listContactPage.action')"><s:text name='menu.contacts.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listCampaign.jsp\")%>"><div onClick="openPage('/crm/listCampaignPage.action')"><s:text name='menu.campaigns.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTarget.jsp\")%>"><div onClick="openPage('/crm/listTargetPage.action')"><s:text name='menu.targets.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTargetList.jsp\")%>"><div onClick="openPage('/crm/listTargetListPage.action')"><s:text name='menu.targetLists.title'/></div></security:authorize>
	</div>
	<div id="mm3" style="width:100px;">
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listContact.jsp\")%>"><div onClick="openPage('/crm/listContactPage.action')"><s:text name='menu.contacts.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listCase.jsp\")%>"><div onClick="openPage('/crm/listCasePage.action')"><s:text name='menu.cases.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTargetList.jsp\")%>"><div onClick="openPage('/crm/listTargetListPage.action')"><s:text name='menu.targetLists.title'/></div></security:authorize>
	</div>
	<div id="mm4" style="width:100px;">
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listCall.jsp\")%>"><div onClick="openPage('/crm/listCallPage.action')"><s:text name='menu.calls.title'/></div></security:authorize>	
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listMeeting.jsp\")%>"><div onClick="openPage('/crm/listMeetingPage.action')"><s:text name='menu.meetings.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTask.jsp\")%>"><div onClick="openPage('/crm/listTaskPage.action')"><s:text name='menu.tasks.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTargetList.jsp\")%>"><div onClick="openPage('/crm/listTargetListPage.action')"><s:text name='menu.targetLists.title'/></div></security:authorize>
	</div>
	<div id="mm5" style="width:100px;">
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listDocument.jsp\")%>"><div onClick="openPage('/crm/listDocumentPage.action')"><s:text name='menu.documents.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/crm/listTargetList.jsp\")%>"><div onClick="openPage('/crm/listTargetListPage.action')"><s:text name='menu.targetLists.title'/></div></security:authorize>
	</div>
	<div id="mm6" style="width:150px;">
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listAccountType.jsp\")%>"><div onClick="openPage('/system/listAccountTypePage.action')"><s:text name='menu.accountType.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCallStatus.jsp\")%>"><div onClick="openPage('/system/listCallStatusPage.action')"><s:text name='menu.callStatus.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCallDirection.jsp\")%>"><div onClick="openPage('/system/listCallDirectionPage.action')"><s:text name='menu.callDirection.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listReminderOption.jsp\")%>"><div onClick="openPage('/system/listReminderOptionPage.action')"><s:text name='menu.reminderOption.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCampaignType.jsp\")%>"><div onClick="openPage('/system/listCampaignTypePage.action')"><s:text name='menu.campaignType.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCampaignStatus.jsp\")%>"><div onClick="openPage('/system/listCampaignStatusPage.action')"><s:text name='menu.campaignStatus.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCaseOrigin.jsp\")%>"><div onClick="openPage('/system/listCaseOriginPage.action')"><s:text name='menu.caseOrigin.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCasePriority.jsp\")%>"><div onClick="openPage('/system/listCasePriorityPage.action')"><s:text name='menu.casePriority.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCaseReason.jsp\")%>"><div onClick="openPage('/system/listCaseReasonPage.action')"><s:text name='menu.caseReason.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCaseStatus.jsp\")%>"><div onClick="openPage('/system/listCaseStatusPage.action')"><s:text name='menu.caseStatus.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCaseType.jsp\")%>"><div onClick="openPage('/system/listCaseTypePage.action')"><s:text name='menu.caseType.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listDocumentCategory.jsp\")%>"><div onClick="openPage('/system/listDocumentCategoryPage.action')"><s:text name='menu.documentCategory.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listDocumentStatus.jsp\")%>"><div onClick="openPage('/system/listDocumentStatusPage.action')"><s:text name='menu.documentStatus.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listDocumentSubCategory.jsp\")%>"><div onClick="openPage('/system/listDocumentSubCategoryPage.action')"><s:text name='menu.documentSubCategory.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listDocumentType.jsp\")%>"><div onClick="openPage('/system/listDocumentTypePage.action')"><s:text name='menu.documentType.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listIndustry.jsp\")%>"><div onClick="openPage('/system/listIndustryPage.action')"><s:text name='menu.industry.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listLeadSource.jsp\")%>"><div onClick="openPage('/system/listLeadSourcePage.action')"><s:text name='menu.leadSource.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listLeadStatus.jsp\")%>"><div onClick="openPage('/system/listLeadStatusPage.action')"><s:text name='menu.leadStatus.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listUserStatus.jsp\")%>"><div onClick="openPage('/system/listUserStatusPage.action')"><s:text name='menu.userStatus.title'/></div></security:authorize>		
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listTaskStatus.jsp\")%>"><div onClick="openPage('/system/listTaskStatusPage.action')"><s:text name='menu.taskStatus.title'/></div></security:authorize>	
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listTaskPriority.jsp\")%>"><div onClick="openPage('/system/listTaskPriorityPage.action')"><s:text name='menu.taskPriority.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listTargetListType.jsp\")%>"><div onClick="openPage('/system/listTargetListTypePage.action')"><s:text name='menu.targetListType.title'/></div></security:authorize>					
		<div class="menu-sep"></div>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listCurrency.jsp\")%>"><div onClick="openPage('/system/listCurrencyPage.action')"><s:text name='menu.currency.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listUser.jsp\")%>"><div onClick="openPage('/system/listUserPage.action')"><s:text name='menu.user.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listRole.jsp\")%>"><div onClick="openPage('/system/listRolePage.action')"><s:text name='menu.role.title'/></div></security:authorize>
		<security:authorize ifAnyGranted="<%= permissions.get(\"/jsp/system/listPermission.jsp\")%>"><div onClick="openPage('/system/listPermissionPage.action')"><s:text name='menu.permission.title'/></div></security:authorize>			
	</div>
	<div id="mm7" style="width:100px;">
		<div onClick="openPage('/help.pdf')"><s:text name='menu.help.title'/></div>
		<div onClick="openPage('/system/aboutPage.action')"><s:text name='menu.about.title'/></div>
	</div>					