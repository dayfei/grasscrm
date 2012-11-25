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
	  function add(){
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveLead.action";
		addObjectForm.submit();
	  }
	
	  function cancel(){
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listLeadPage.action";
		addObjectForm.submit();
	  }
	
	  function convert(){
		   var leadID = document.getElementById('id').value;
		   openwindow('/crm/convertLead.jsp?id=' + leadID,'Convert Lead" />');
	  }
	  
	  function copyAddress(){
		if ($('#copy_checkbox').attr('checked')) { 
			$("input[name='lead.other_address']").attr("value",$("input[name='lead.primary_address']").val());	
			$("input[name='lead.other_address']").attr("disabled","disabled"); 
			$("input[name='lead.other_city']").attr("value",$("input[name='lead.primary_city']").val());
			$("input[name='lead.other_city']").attr("disabled","disabled");
			$("input[name='lead.other_state']").attr("value",$("input[name='lead.primary_state']").val());
			$("input[name='lead.other_state']").attr("disabled","disabled");
			$("input[name='lead.other_postal_code']").attr("value",$("input[name='lead.primary_postal_code']").val());
			$("input[name='lead.other_postal_code']").attr("disabled","disabled");
			$("input[name='lead.other_country']").attr("value",$("input[name='lead.primary_country']").val());
			$("input[name='lead.other_country']").attr("disabled","disabled");
		} else {
			$("input[name='lead.other_address']").removeAttr("disabled"); 
			$("input[name='lead.other_city']").removeAttr("disabled"); 
			$("input[name='lead.other_state']").removeAttr("disabled"); 
			$("input[name='lead.other_postal_code']").removeAttr("disabled"); 
			$("input[name='lead.other_country']").removeAttr("disabled"); 	
		}	
	  }
	  
	  $(document).ready(function(){
		$('#accountID').datebox('setValue', '${accountID}');
		$('#leadID').datebox('setValue', '${leadID}');	
		$('#campaignID').datebox('setValue', '${campaignID}');
		$('#assignedToID').combogrid('setValue', '${assignedToID}');
	  })
	</script>
</head>

<body>
	<div id="page-wrap">

		<s:include value="../header.jsp" />

		<s:include value="../menu.jsp" />

		<div id="feature">
			<div id="shortcuts" class="headerList">
			    <b style="white-space:nowrap;color:#444;"><s:text name="title.action" />:&nbsp;&nbsp;</b>
				<span> <span style="white-space: nowrap;"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-ok" onclick="add()"
						plain="true"><s:text name="button.save" /></a>
				</span> <span style="white-space: nowrap;"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()"
						plain="true"><s:text name="button.cancel" /></a>
				</span>
				</span> <span style="white-space: nowrap;"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-edit" onclick="convert()"
						plain="true"><s:text name="button.convert" /></a>
				</span>				
				</span>
			</div>

			<div id="feature-title">
				<s:if test="lead!=null">
					<h2>
						<s:text name="title.updateLead" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createLead" />
					</h2>
				</s:else>
			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
					method="post">
					<s:hidden id="id" name="lead.id" value="%{lead.id}" />
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
												<font color="red">
												<s:property escape="false" /></font>
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
										name="entity.first_name.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="lead.first_name"
									cssClass="record-value" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.last_name.label"></s:text>：</label></td>
							<td class="td-value"><input name="lead.last_name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="lead.last_name" />" /></td>
						</tr>

						<tr>
							<td class="td-label"><label class="record-label"><s:text
										name="lead.title.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="lead.title"
									cssClass="record-value" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="lead.department.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="lead.department"
									cssClass="record-value" /></td>
						</tr>

						<tr>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.account.label"></s:text>：</label></td>
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
							<td class="td-label"></td>
							<td class="td-value"></td>
						</tr>
					</table>

					<div class="easyui-tabs">
						<div title="<s:text name='tab.overview'/>" style="padding: 10px;">
							<div class="section-header">
								<span><s:text name="span.contact" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.email.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.email"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.office_phone.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.office_phone"
											cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.fax.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.fax"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.mobile.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.mobile"
											cssClass="record-value" /></td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="span.primary_address" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.primary_address.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.primary_address" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.primary_city.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.primary_city"
											cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.primary_state.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.primary_state" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.primary_postal_code.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.primary_postal_code" cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.primary_country.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.primary_country" cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
							</table>

							<div class="section-header">
								<span> <s:text name="span.shipping_address" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.other_address.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.other_address" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.other_city.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.other_city"
											cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.other_state.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.other_state"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.other_postal_code.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.other_postal_code" cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.other_country.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.other_country" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.copyAddress.label" />：</label></td>
									<td class="td-value"><input id="copy_checkbox"
										name="copy_checkbox" type="checkbox" class="record-value"
										onclick="copyAddress();" /></td>
								</tr>
							 </table>

							 <div class="section-header">
								<span><s:text name="span.other_info" /></span>
							 </div>
							 <table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.status.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="leadStatusID"
											list="leadStatuses" listKey="id" listValue="name" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.status_description.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.status_description" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.lead_source.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="leadSourceID"
											list="leadSources" listKey="id" listValue="name" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.lead_source_description.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.lead_source_description" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.opportunity_amount.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="lead.opportunity_amount" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="lead.referred_by.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="lead.referred_by" />
									</td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="entity.campaign.label"></s:text>：</label></td>
									<td class="td-value"><select id="campaignID"
										class="easyui-combogrid" name="campaignID"
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
									<td class="td-label"><label class="record-label"><s:text
												name="lead.not_call.label"></s:text>：</label></td>
									<td class="td-value"><s:checkbox id="lead.not_call"
											name="lead.not_call" /></td>
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
											name="lead.description" rows="20" cssStyle="width:500px;"
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
													href="filterLeadDocumentPage.action?id=<s:property value="lead.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.documents.title" /></label></a>
											</div>										
											<div title="<s:text name="menu.activities.title"/>"
												iconCls="icon-ok" style="overflow: auto; padding: 10px;">
												<a
													href="filterTaskPage.action?filter_key=related_record&id=<s:property value="lead.id" />&moreFilterKey=relationKey&moreFilterValue=Lead&createKey=relationValue&removeKey=Lead"
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



