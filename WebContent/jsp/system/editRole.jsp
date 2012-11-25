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
		var right = document.getElementById('addObjectForm_rightPermission');		 
		for ( var i = 0; i < right.length; i++)	{	 
		  right[i].selected = true;		 
		}
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveRole.action";
		addObjectForm.submit();
	}

	function cancel() {		
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listRolePage.action";
		addObjectForm.submit();
	}
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
				<s:if test="role!=null">
					<h2>
						<s:text name="title.updateRole" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createRole" />
					</h2>
				</s:else>
			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/system"
					method="post">
					<s:hidden name="role.id" value="%{role.id}" />
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
							<td class="td-value"><input name="role.name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="role.name" />" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="entity.sequence.label"></s:text>：</label></td>
							<td class="td-value"><input name="role.sequence" type="text"
								class="easyui-numberbox record-value"
								value="<s:property value="role.sequence" />"
								data-options="min:0,precision:0"></input></td>
						</tr>
					</table>

					<div class="easyui-tabs">
						<div title="<s:text name='tab.overview'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="role.permission.label"></s:text>：</label></td>
									<td class="td-value" colspan="3"><s:optiontransferselect
											name="leftPermission" list="leftPermissions" listKey="id"
											listValue="name" doubleName="rightPermission"
											doubleList="rightPermissions" doubleListKey="id"
											doubleListValue="name" cssStyle="width:150px"
											doubleCssStyle="width:150px"
											ondblclick="moveSelectedOptions(document.getElementById('addObjectForm_leftPermission'), document.getElementById('addObjectForm_rightPermission'), false, '');"
											doubleOndblclick="moveSelectedOptions(document.getElementById('addObjectForm_rightPermission'), document.getElementById('addObjectForm_leftPermission'), false, '');" /></td>
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



