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
		addObjectForm.action = "saveCampaign.action";
		addObjectForm.submit();
	}

	function cancel() {
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listCampaignPage.action";
		addObjectForm.submit();
	}

	$(document).ready(function() {
		$('#assignedToID').combogrid('setValue', '${assignedToID}');
		$('#startDate').datebox('setValue', '${startDate}');
		$('#endDate').datebox('setValue', '${endDate}');
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
				<s:if test="campaign!=null">
					<h2>
						<s:text name="title.updateCampaign" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createCampaign" />
					</h2>
				</s:else>
			</div>


			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
					method="post">
					<s:hidden name="campaign.id" value="%{campaign.id}" />
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
							<td class="td-value"><input name="campaign.name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="campaign.name" />" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="campaign.status.label"></s:text>：</label></td>
							<td class="td-value"><s:select name="statusID"
									list="statuses" listKey="id" listValue="name"
									cssClass="record-value" /></td>
						</tr>
					</table>

					<div class="easyui-tabs">
						<div title="<s:text name='tab.overview'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.startDate.label"></s:text>：</label></td>
									<td class="td-value"><input id="startDate"
										name="startDate" type="text"
										class="easyui-datebox record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.type.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="typeID" list="types"
											listKey="id" listValue="name" cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.endDate.label"></s:text>：</label></td>
									<td class="td-value"><input id="endDate" name="endDate"
										type="text" class="easyui-datebox record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.currency.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="currencyID"
											list="currencies" listKey="id" listValue="fullName"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.impressions.label"></s:text>：</label></td>
									<td class="td-value"><input name="campaign.impressions"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="campaign.impressions" />"></input></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.budget.label"></s:text>：</label></td>
									<td class="td-value"><input name="campaign.budget"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="campaign.budget" />"
										data-options="min:0,precision:2"></input></td>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.expectedCost.label"></s:text>：</label></td>
									<td class="td-value"><input name="campaign.expected_cost"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="campaign.expected_cost" />"
										data-options="min:0,precision:2"></input></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.actualCost.label"></s:text>：</label></td>
									<td class="td-value"><input name="campaign.actual_cost"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="campaign.actual_cost" />"
										data-options="min:0,precision:2"></input></td>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.expectedRevenue.label"></s:text>：</label></td>
									<td class="td-value"><input
										name="campaign.expected_revenue" type="text"
										class="easyui-numberbox record-value"
										value="<s:property value="campaign.expected_revenue" />"
										data-options="min:0,precision:2"></input></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.assigned_to.label"></s:text>：</label></td>
									<td class="td-value"><select id="assignedToID"
										class="easyui-combogrid record-value" name="assignedToID"
										style="width: 250px;"
										data-options="  
						            panelWidth:500,  
						            idField:'id',  
						            textField:'name',  
						            url:'listUser.action',
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
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="campaign.objective.label"></s:text>：</label></td>
									<td class="td-value" colspan="3"><s:textarea
											name="campaign.objective" rows="20" cssStyle="width:500px;"
											cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
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
											name="campaign.description" rows="20" cssStyle="width:500px;"
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

					</div>
				</s:form>
			</div>
		</div>

		<s:include value="../footer.jsp" />

	</div>
</body>
</html>



