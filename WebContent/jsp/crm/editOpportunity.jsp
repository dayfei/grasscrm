<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>

<script type="text/javascript">
	function add() {
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveOpportunity.action";
		addObjectForm.submit();
	}

	function cancel() {
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listOpportunityPage.action";
		addObjectForm.submit();
	}

	$(document).ready(function() {
		$('#accountID').combogrid('setValue', '${accountID}');
		$('#assignedToID').combogrid('setValue', '${assignedToID}');
		$('#campaignID').combogrid('setValue', '${campaignID}');
		$('#expectCloseDate').datebox('setValue', '${expectCloseDate}');
	})
</script>

</head>
<body>
	<div id="page-wrap">

		<s:include value="../header.jsp" />

		<s:include value="../menu.jsp" />

		<div id="feature">
			<div id="shortcuts" class="headerList">
				<span> <span style="white-space: nowrap;"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-ok" onclick="add()"
						plain="true"><s:text name="button.save" /></a>
				</span> <span style="white-space: nowrap;"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()"
						plain="true"><s:text name="button.cancel" /></a>
				</span>
				</span>
			</div>

			<div id="feature-title">
				<s:if test="account!=null">
					<h2>
						<s:text name="title.updateOpportunity" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createOpportunity" />
					</h2>
				</s:else>
			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
					method="post">
					<s:hidden name="opportunity.id" value="%{opportunity.id}" />
			        <s:hidden name="relationKey" id="relationKey" value="%{relationKey}" />	
			        <s:hidden name="relationValue" id="relationValue" value="%{relationValue}" />						
					<table style="" cellspacing="10" cellpadding="0" width="100%">
						<s:actionerror />
						<s:if test="hasFieldErrors()">
							<tr>
								<td align="left" colspan="4"><s:actionerror /> <s:iterator
										value="fieldErrors" status="st">
										<s:if test="#st.index  == 0">
											<s:iterator value="value">
												<font color="red"> <s:property escape="false" /></font>
											</s:iterator>
										</s:if>
									</s:iterator></td>
							</tr>
						</s:if>
					</table>

					<table style="padding: 10px;" cellspacing="10" cellpadding="0"
						width="100%">
						<tr>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.name.label"></s:text>：</label></td>
							<td class="td-value"><input name="opportunity.name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="opportunity.name" />" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="opportunity.account.label"></s:text>：</label></td>
							<td class="td-value"><select id="accountID"
								class="easyui-combogrid record-value" name="accountID"
								style="width: 250px;"
								data-options="  
						            panelWidth:500,  
						            idField:'id',  
						            textField:'name',  
						            url:'listAccount.action',
						            fit: true,
						            mode:'remote',
						            columns:[[  
						                {field:'id',title:'ID',width:60},  
						                {field:'name',title:'Name',width:100},  
						                {field:'office_phone',title:'Phone',width:120},  
						                {field:'email',title:'Email',width:100}  
						            ]]  
						        ">
							</select></td>
						</tr>
					</table>

					<div class="easyui-tabs">
						<div title="<s:text name='tab.overview'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.currency.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="currencyID"
											list="currencies" listKey="id" listValue="name"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.expect_close_date.label"></s:text>：</label></td>
									<td class="td-value"><input id="expectCloseDate" name="expectCloseDate"
										type="text" class="easyui-datebox record-value" /></td>
								</tr>							
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.opportunity_amount.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="opportunity.opportunity_amount" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.type.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="typeID" list="types"
											listKey="id" listValue="name" cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.salesStage.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="salesStageID"
											list="salesStages" listKey="id" listValue="name"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.leadSource.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="sourceID"
											list="sources" listKey="id" listValue="name"
											cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.probability.label"></s:text>：</label></td>
									<td class="td-value"><input name="opportunity.probability"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="opportunity.probability" />"
										data-options="min:0,precision:2"></input></td>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.campaign.label"></s:text>：</label></td>
									<td class="td-value"><select id="campaignID"
										class="easyui-combogrid record-value" name="campaignID"
										style="width: 250px;"
										data-options="  
						            panelWidth:500,  
						            idField:'id',  
						            textField:'name',  
						            url:'listCampaign.action',
						            fit: true,
						            mode:'remote',
						            columns:[[  
						                {field:'id',title:'ID',width:60},  
						                {field:'name',title:'Name',width:100},  
						                {field:'status',title:'Status',width:120},  
						                {field:'type',title:'Type',width:100}  
						            ]]  
						        ">
									</select></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="opportunity.next_step.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="opportunity.next_step" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.assigned_to.label"></s:text>：</label></td>
									<td  class="td-value" colspan="3"><select
										id="assignedToID" class="easyui-combogrid record-value"
										name="assignedToID" style="width: 250px;"
										data-options="  
							            panelWidth:500,  
							            idField:'id',  
							            textField:'name',  
							            url:'/grass/jsp/system/listUser.action',
							            fit: true,
							            mode:'remote',
							            columns:[[  
							                {field:'id',title:'ID',width:60},  
							                {field:'name',title:'Name',width:100},  
							                {field:'phone',title:'Phone',width:120},  
							                {field:'age',title:'Age',width:100}  
							            ]]  
						        ">
									</select></td>
								</tr>
							</table>
						</div>

						<div title="<s:text name='tab.details'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label" valign="top"><label
										class="record-label"><s:text
												name="entity.description.label"></s:text>：</label></td>
									<td class="td-value" valign="top"><s:textarea
											name="opportunity.description" rows="20" cssStyle="width:500px;"
											cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.createdBy.label"></s:text>：</label></td>
									<td class="td-value"><label class="record-value"><s:property
												value="createdBy" /></label></td>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.createdOn.label"></s:text>：</label></td>
									<td class="td-value"><label class="record-value"><s:property
												value="createdOn" /></label></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.updatedBy.label"></s:text>：</label></td>
									<td class="td-value"><label class="record-value"><s:property
												value="updatedBy" /></label></td>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.updatedOn.label"></s:text>：</label></td>
									<td class="td-value"><label class="record-value"><s:property
												value="updatedOn" /></label></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.id.label"></s:text>：</label></td>
									<td class="td-value"><label class="record-value"><s:property
												value="id" /></label></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
							</table>
						</div>

						<div title="<s:text name='tab.relations'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td width="20%" valign="top">
										<div class="easyui-accordion" style="width: 200px;">
											<div title="<s:text name="menu.sales.title"/>"
												iconCls="icon-ok" style="overflow: auto; padding: 10px;"
												selected="true">
												<a
													href="filterOpportunityContactPage.action?id=<s:property value="opportunity.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.contacts.title" /></label></a><br /> <a
													href="filterOpportunityLeadPage.action?id=<s:property value="opportunity.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.leads.title" /></label></a><br /> <a
													href="filterOpportunityDocumentPage.action?id=<s:property value="opportunity.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.documents.title" /></label></a>
											</div>
											<div title="<s:text name="menu.activities.title"/>"
												iconCls="icon-ok" style="overflow: auto; padding: 10px;"
												selected="true">
												<a
													href="filterTaskPage.action?filter_key=related_record&id=<s:property value="opportunity.id" />&moreFilterKey=relationKey&moreFilterValue=Opportunity&createKey=relationValue&removeKey=Opportunity"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.tasks.title" /></label></a>
											</div>
										</div>
									</td>
									<td width="80%" valign="top"><Iframe name="contentFrame"
											id="contentFrame" scrolling="no" frameborder="0" width="100%"
											height="360"></iframe></td>
								</tr>
							</table>
						</div>

					</div>

				</s:form>
			</div>
		</div>

		<s:include value="../footer.jsp" />

	</div>
</body>
</html>



