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
		addObjectForm.action = "saveDocument.action";
		addObjectForm.submit();
	}

	function cancel() {
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listDocumentPage.action";
		addObjectForm.submit();
	}

	$(document).ready(function() {
		('#relatedDocumentID').combogrid('setValue', '${relatedDocumentID}');
		$('#assignedToID').combogrid('setValue', '${assignedToID}');
		$('#publishDateS').datebox('setValue', '${publishDateS}');
		$('#expirationDateS').datebox('setValue', '${expirationDateS}');
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
				<s:if test="document!=null">
					<h2>
						<s:text name="title.updateDocument" />
					</h2>
				</s:if>
				<s:else>
					<h2>
						<s:text name="title.createDocument" />
					</h2>
				</s:else>
			</div>

			<div id="feature-content">
				<s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
					method="post" enctype="multipart/form-data">
					<s:hidden name="document.id" value="%{document.id}" />
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
							<td class="td-value"><input name="document.name"
								class="easyui-validatebox record-value"
								data-options="required:true"
								value="<s:property value="document.name" />" /></td>
							<td class="td-label"><label class="record-label"><s:text
										name="document.file.label"></s:text>：</label></td>
							<td class="td-value">
								<s:url id="url" action="downloadDocument.action?id=%{document.id}"/>
								<s:a href="%{url}"><s:property value="document.fileName" /></s:a>
								<s:file name="upload" label="File" />
							</td>
						</tr>
					</table>

					<div class="easyui-tabs">
						<div title="<s:text name='tab.overview'/>" style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="document.status.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="statusID"
											list="statuses" listKey="id" listValue="name"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="document.revision.label"></s:text>：</label></td>
									<td class="td-value"><input name="document.revision"
										type="text" class="easyui-numberbox record-value"
										value="<s:property value="document.revision" />"
										data-options="min:0,precision:0"></input></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="document.publish_date.label"></s:text>：</label></td>
									<td class="td-value"><input id="publishDateS"
										name="publishDateS" type="text"
										class="easyui-datebox record-value" /></input></td>
									<td class="td-label"><label class="record-label"><s:text
												name="document.expiration_date.label"></s:text>：</label></td>
									<td class="td-value"><input id="expirationDateS"
										name="expirationDateS" type="text"
										class="easyui-datebox record-value" /></input></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="document.category.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="categoryID"
											list="categories" listKey="id" listValue="name"
											cssClass="record-value" /></td>
									<td class="td-label"><label class="record-label"><s:text
												name="document.sub_category.label"></s:text>：</label></td>
									<td class="td-value"><s:select name="subCategoryID"
											list="subCategories" listKey="id" listValue="name"
											cssClass="record-value" /></td>
								</tr>

								<tr>
									<td class="td-label"><label class="record-label"><s:text
												name="document.related_document.label"></s:text>：</label></td>
									<td class="td-value"><select id="relatedDocumentID"
										class="easyui-combogrid record-value" name="relatedDocumentID"
										style="width: 250px;"
										data-options="  
						            panelWidth:500,  
						            idField:'id',  
						            textField:'name',  
						            url:'listDocument.action',
						            pagination : true,
						            fit: true,
						            mode:'remote',
						            pageSize: 10,
						            pageList: [10],  
						            columns:[[  
						                {field:'id',title:'ID',width:60},  
						                {field:'name',title:'Name',width:100},  
						                {field:'revision',title:'Revision',width:120}
						            ]]  
						        ">
									</select></td>
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
						            pagination : true,
						            fit: true,
						            mode:'remote',
						            pageSize: 10,
						            pageList: [10],  
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

						<div title="<s:text name='tab.descriptions'/>"
							style="padding: 10px;">
							<table style="" cellspacing="10" cellpadding="0" width="100%">
								<tr>
									<td class="td-label" valign="top"><label
										class="record-label"><s:text
												name="entity.description.label"></s:text>：</label></td>
									<td class="td-value" valign="top"><s:textarea
											name="document.description" rows="20" cssStyle="width:500px;"
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
													href="filterDocumentAccountPage.action?id=<s:property value="document.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.accounts.title" /></label></a><br /> <a
													href="filterDocumentContactPage.action?id=<s:property value="document.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.contacts.title" /></label></a><br /> <a
													href="filterDocumentOpportunityPage.action?id=<s:property value="document.id" />"
													target="contentFrame"><label
													class="record-value menuLink"><s:text
															name="menu.opportunities.title" /></label></a>
											</div>
											<div title="<s:text name="menu.support.title"/>"
												iconCls="icon-ok" style="overflow: auto; padding: 10px;">
												<a
													href="filterDocumentCasePage.action?id=<s:property value="document.id" />"
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



