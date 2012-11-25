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
		var right = document.getElementById('addObjectForm_rightRole');		 
		for ( var i = 0; i < right.length; i++)	{	 
		  right[i].selected = true;		 
		}		  
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveUser.action";
		addObjectForm.submit();
	  }
	
	  function cancel(){
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listUserPage.action";
		addObjectForm.submit();
	  }
	
	  function copyAddress(){
		if ($('#copy_checkbox').attr('checked')) { 
			$("input[name='user.other_street']").attr("value",$("input[name='user.mail_street']").val());	
			$("input[name='user.other_street']").attr("disabled","disabled"); 
			$("input[name='user.other_city']").attr("value",$("input[name='user.mail_city']").val());
			$("input[name='user.other_city']").attr("disabled","disabled");
			$("input[name='user.other_state']").attr("value",$("input[name='user.mail_state']").val());
			$("input[name='user.other_state']").attr("disabled","disabled");
			$("input[name='user.other_postal_code']").attr("value",$("input[name='user.mail_postal_code']").val());
			$("input[name='user.other_postal_code']").attr("disabled","disabled");
			$("input[name='user.other_country']").attr("value",$("input[name='user.mail_country']").val());
			$("input[name='user.other_country']").attr("disabled","disabled");
		} else {
			$("input[name='user.other_street']").removeAttr("disabled"); 
			$("input[name='user.other_city']").removeAttr("disabled"); 
			$("input[name='user.other_state']").removeAttr("disabled"); 
			$("input[name='user.other_postal_code']").removeAttr("disabled"); 
			$("input[name='user.other_country']").removeAttr("disabled"); 	
		}	
	  }
	
	  $(document).ready(function(){
		$('#reportToID').combogrid('setValue', '${reportToID}');
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
				<s:if test="user!=null">
					<h2>
						<s:text name="title.updateUser" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createUser" />
					</h2>
				</s:else>
			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/system"
					method="post">
					<s:hidden name="user.id" value="%{user.id}" />
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
										name="user.name.label"></s:text>：</label></td>
							<td class="td-value"><input name="user.name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="user.name" />" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.first_name.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="user.first_name"
									cssClass="record-value" /></td>
						</tr>
						<tr>
							<td class="td-label"><label class="record-label"><s:text
										name="user.status.label"></s:text>：</label></td>
							<td class="td-value"><s:select name="statusID" list="statuses"
									listKey="id" listValue="name" cssClass="record-value" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.last_name.label"></s:text>：</label></td>
							<td class="td-value"><s:textfield name="user.last_name"
									cssClass="record-value" /></td>
						</tr>						
					</table>

					<div class="easyui-tabs">					
						<div title="<s:text name='tab.overview'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.password.label"></s:text>：</label></td>
									<td class="td-value"><input type="password" name="user.password" class="record-value" value="<s:property value="user.password" />"/></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.title.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.title" cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.department.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="user.department"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.report_to.label"></s:text>：</label></td>
									<td class="td-value"><select id="reportToID"
										class="easyui-combogrid record-value" name="reportToID"
										style="width: 250px;"
										data-options="  
							            panelWidth:500,  
							            idField:'id',  
							            textField:'name',  
							            url:'listUser.action',
							            pagination : true,
							            fit: true,
							            mode:'remote',
							            pageSize: 10,
							            pageList: [10],  
							            columns:[[  
							                {field:'id',title:'ID',width:60},  
							                {field:'name',title:'Name',width:100},  
							                {field:'title',title:'Title',width:120},  
							                {field:'department',title:'Department',width:100}  
							            ]]  
							        ">
									</select></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.age.label"></s:text>：</label></td>
									<td class="td-value"><input name="user.age"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="user.age" />"></input></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.smtp_username.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.smtp_username" cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.smtp_password.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.smtp_password" cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>																
							</table>		
																	
							<div class="section-header">
								<span><s:text name="span.contact" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.email.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="user.email"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.mobile.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.mobile" cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.phone.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="user.phone"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.fax.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="user.fax"
											cssClass="record-value" /></td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="span.mailing_address" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.mail_street.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.mail_street" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.mail_city.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="user.mail_city"
											cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.mail_state.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.mail_state" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.mail_postal_code.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.mail_postal_code" cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.mail_country.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.mail_country" cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
							</table>

							<div class="section-header">
								<span><s:text name="span.other_address" /></span>
							</div>
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.other_street.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.other_street" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.other_city.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield name="user.other_city"
											cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.other_state.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.other_state" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.other_postal_code.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.other_postal_code" cssClass="record-value" /></td>
								</tr>
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="user.other_country.label"></s:text>：</label></td>
									<td class="td-value"><s:textfield
											name="user.other_country" cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="user.copyAddress.label" />：</label></td>
									<td class="td-value"><input id="copy_checkbox"
										name="copy_checkbox" type="checkbox" onclick="copyAddress();" />
									</td>
								</tr>
							</table>
						</div>

						<div title="<s:text name='tab.descriptions'/>"
							style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label" valign="top"><label
										class="record-label"><s:text
												name="entity.description.label"></s:text>：</label></td>
									<td class="td-value" valign="top"><s:textarea
											name="user.description" rows="20" cssStyle="width:500px;"
											cssClass="record-value" /></td>
									<td class="td-label"></td>
									<td class="td-value"></td>
								</tr>
							</table>
						</div>
						
						<div title="<s:text name='tab.role'/>"
							style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label" valign="top"><label
										class="record-label"><s:text
												name="user.role.label"></s:text>：</label></td>
									<td class="td-value" colspan="3"><s:optiontransferselect
											name="leftRole" list="leftRoles" listKey="id"
											listValue="name" doubleName="rightRole"
											doubleList="rightRoles" doubleListKey="id"
											doubleListValue="name" cssStyle="width:150px"
											doubleCssStyle="width:150px"
											ondblclick="moveSelectedOptions(document.getElementById('addObjectForm_leftRole'), document.getElementById('addObjectForm_rightRole'), false, '');"
											doubleOndblclick="moveSelectedOptions(document.getElementById('addObjectForm_rightRole'), document.getElementById('addObjectForm_leftRole'), false, '');" /></td>

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



