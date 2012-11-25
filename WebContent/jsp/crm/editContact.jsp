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
		addObjectForm.action = "saveContact.action";
		addObjectForm.submit();
	  }
	
	  function cancel(){
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listContactPage.action";
		addObjectForm.submit();
	  }
	  
	  function copyAddress(){
		if ($('#copy_checkbox').attr('checked')) { 
			$("input[name='contact.other_address']").attr("value",$("input[name='contact.mailing_address']").val());	
			$("input[name='contact.other_address']").attr("disabled","disabled"); 
			$("input[name='contact.other_city']").attr("value",$("input[name='contact.mailing_city']").val());
			$("input[name='contact.other_city']").attr("disabled","disabled");
			$("input[name='contact.other_state']").attr("value",$("input[name='contact.mailing_state']").val());
			$("input[name='contact.other_state']").attr("disabled","disabled");
			$("input[name='contact.other_postal_code']").attr("value",$("input[name='contact.mailing_postal_code']").val());
			$("input[name='contact.other_postal_code']").attr("disabled","disabled");
			$("input[name='contact.other_country']").attr("value",$("input[name='contact.mailing_country']").val());
			$("input[name='contact.other_country']").attr("disabled","disabled");
		} else {
			$("input[name='contact.other_address']").removeAttr("disabled"); 
			$("input[name='contact.other_city']").removeAttr("disabled"); 
			$("input[name='contact.other_state']").removeAttr("disabled"); 
			$("input[name='contact.other_postal_code']").removeAttr("disabled"); 
			$("input[name='contact.other_country']").removeAttr("disabled"); 	
		}	
      }
	  
	  $(document).ready(function(){
		$('#reportToID').combogrid('setValue', '${reportToID}');	
		$('#campaignID').combogrid('setValue', '${campaignID}');
		$('#assignedToID').combogrid('setValue', '${assignedToID}');
		$('#accountID').combogrid({    
		    onChange : function(n,o){
		      var officePhone = $('#office_phone').val();
		      var mailingAddress = $('#mailing_address').val();
		      var mailingCity = $('#mailing_city').val();
		      var mailingState = $('#mailing_state').val();
		      var mailingPostalCode = $('#mailing_postal_code').val();
		      var mailingCountry = $('#mailing_country').val();		      		      
		      var g = $('#accountID').combogrid('grid');	// get datagrid object
		      var r = g.datagrid('getSelected');	// get the selected row
              if (r==null){
            	  return;
              }
		      var billStreet = r.bill_street;
		      var billCity = r.bill_city;
		      var billCountry = r.bill_country;
		      var billState = r.bill_state;
		      var billPostalCode = r.bill_postal_code;
		      var accountOfficePhone = r.office_phone;
		      if (officePhone != '' || mailingAddress != '' || mailingCity != '' || mailingState != '' || mailingPostalCode != '' || mailingCountry != ''){
		    	$.messager.confirm('Confirm', 'This record currently contains values in the Office' +
		    			'Phone And Address And Address fields. To overwrite these values with the' +
		    			' following Office and Address of the Account that you selected, click \"OK\".'+
		    			'. To keep the current vaues, click \"Cancel\"\n\n' +
		    			'\nAddress:' + billStreet +
		    			'\nCity:' + billCity +
		    			'\nState:' + billState +
		    			'\nPostal Code:' + billPostalCode +
		    			'\nCountry:' + billCountry +
		    			'\nOffice Phone:' + accountOfficePhone +
		    			"", function(r){
		    		          if (r){
		    			        $('#mailing_address').val(billStreet);
		    			        $('#mailing_city').val(billCity);
		    			        $('#mailing_state').val(billState);
		    			        $('#mailing_postal_code').val(billPostalCode);
		    			        $('#mailing_country').val(billCountry);
		    			        $('#office_phone').val(accountOfficePhone);		    			        
		    		        }
		    	});	
		     } else {
			        $('#mailing_address').val(billStreet);
			        $('#mailing_city').val(billCity);
			        $('#mailing_state').val(billState);
			        $('#mailing_postal_code').val(billPostalCode);
			        $('#mailing_country').val(billCountry);
			        $('#office_phone').val(accountOfficePhone);	
		     }	
		   }  
		});		
		$('#accountID').combogrid('setValue', '${accountID}');		
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
				<s:if test="contact!=null">
					<h2>
						<s:text name="title.updateContact" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createContact" />
					</h2>
				</s:else>
			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
					method="post">
					<s:hidden name="contact.id" value="%{contact.id}" />
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
										name="entity.first_name.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="contact.first_name"
									cssClass="record-value" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.last_name.label"></s:text>：</label></td>
							<td class="td-value"><input name="contact.last_name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="contact.last_name" />" /></td>
						</tr>

						<tr>
							<td class="td-label"><label class="record-label"><s:text
										name="contact.title.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="contact.title"
									cssClass="record-value" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="contact.department.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="contact.department"
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
												name="contact.email.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="contact.email"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.office_phone.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield id="office_phone"
											name="contact.office_phone" cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.website.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="contact.website"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.fax.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="contact.fax"
											cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.mobile.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="contact.mobile"
											cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="span.mailing_address" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.mailing_address.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield id="mailing_address"
											name="contact.mailing_address" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.mailing_city.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield id="mailing_city"
											name="contact.mailing_city" cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.mailing_state.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield id="mailing_state"
											name="contact.mailing_state" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.mailing_postal_code.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield id="mailing_postal_code"
											name="contact.mailing_postal_code" cssClass="record-value" />
									</td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.mailing_country.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield id="mailing_country"
											name="contact.mailing_country" cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
							</table>

							<div class="section-header">
								<span> <s:text name="span.other_address" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.other_address.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="contact.other_address" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.other_city.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="contact.other_city" cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.other_state.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="contact.other_state" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.other_postal_code.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="contact.other_postal_code" cssClass="record-value" />
									</td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.other_country.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="contact.other_country" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.copyAddress.label" />：</label></td>
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
												name="contact.report_to.label"></s:text>：</label></td>
									<td class="td-value"><select id="reportToID"
										class="easyui-combogrid record-value" name="reportToID"
										style="width: 250px;"
										data-options="  
						            panelWidth:500,  
						            idField:'id',  
						            textField:'name',  
						            url:'listContact.action',
						            fit: true,
						            mode:'remote',
						            columns:[[  
						                {field:'id',title:'ID',width:60},  
						                {field:'first_name',title:'First Name',width:100},  
						                {field:'last_name',title:'Last Name',width:120},  
						                {field:'title',title:'Title',width:100}  
						            ]]  
						        ">
									</select></td>
									<td class="td-label"><label class="record-label"><s:text
												name="contact.leadSource.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="leadSourceID"
											list="leadSources" listKey="id" listValue="name"
											cssClass="record-value" /></td>
								</tr>

								<tr>
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
									<td class="td-label"><label class="record-label"><s:text
												name="contact.not_call.label"></s:text>：</label></td>
									<td class="td-value"><s:checkbox id="contact.not_call"
											name="contact.not_call" cssClass="record-value" /></td>
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
											name="contact.description" rows="20" cssStyle="width:500px;"
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
													href="filterContactOpportunityPage.action?id=<s:property value="contact.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.opportunities.title" /></label></a><br /> <a
													href="filterContactLeadPage.action?id=<s:property value="contact.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.leads.title" /></label></a><br /> <a
													href="filterContactDocumentPage.action?id=<s:property value="contact.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.documents.title" /></label></a>
											</div>
											<div title="<s:text name="menu.activities.title"/>"
												iconCls="icon-ok" style="overflow: auto; padding: 10px;"
												selected="true">
												<a
													href="filterTaskPage.action?filter_key=contact.id&id=<s:property value="contact.id" />&createKey=contactID&removeKey=Contact"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.tasks.title" /></label></a>
											</div>
											<div title="<s:text name="menu.support.title"/>"
												iconCls="icon-ok" style="overflow: auto; padding: 10px;">
												<a
													href="filterCasePage.action?filter_key=contact.id&id=<s:property value="contact.id" />&createKey=contactID&removeKey=Contact"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.cases.title" /></label></a>
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



