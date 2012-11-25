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
<script type="text/javascript" src="../../js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript" src="../../js/datagrid.js"></script>

<script type="text/javascript">
	$(function() {
		$('#tt').edatagrid({
			url : 'listDocumentCategory.action',
			saveUrl : 'saveDocumentCategory.action',
			updateUrl : 'saveDocumentCategory.action',
			destroyUrl : 'deleteDocumentCategory.action'
		});
	    $("#delete").click(function() {	
			  many_deleterow("deleteDocumentCategory.action?seleteIDs=");
		    });		
	});
</script>
</head>
<body>
	<div id="page-wrap">

		<s:include value="../header.jsp" />

		<s:include value="../menu.jsp" />

		<div id="feature">
			<div id="feature-title">
				<h2>
					<s:text name="title.grid.documentCategory" />
				</h2>
			</div>
			<div id="feature-content">
				<table id="tt" title="<s:text name='title.grid.documentCategory'/>"
					style="width: 700px; height: 380px" toolbar="#toolbar"
					pagination="true" rownumbers="true" fitColumns="true"
					singleSelect="true">
					<thead>
						<tr>
						    <th data-options="field:'ck',checkbox:true"></th>
							<th field="id" width="1" hidden="true"><s:text
									name='entity.id.label' /></th>							
							<th field="documentCategory.id" width="50" hidden="true"><s:text
									name='entity.id.label' /></th>
							<th field="documentCategory.name" width="50"
								editor="{type:'validatebox',options:{required:true}}"><s:text
									name='entity.name.label' /></th>
							<th field="documentCategory.sequence" width="50"
								editor="{type:'numberbox',options:{precision:0}}"><s:text
									name='entity.sequence.label' /></th>
						</tr>
					</thead>
				</table>
				<div id="toolbar">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add"
						plain="true" onclick="javascript:$('#tt').edatagrid('addRow')"><s:text
							name='button.create' /></a> <a id="delete" href="#" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true"><s:text
							name='button.delete' /></a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-save" plain="true"
						onclick="javascript:$('#tt').edatagrid('saveRow')"><s:text
							name='button.save' /></a> <a href="#" class="easyui-linkbutton"
						iconCls="icon-undo" plain="true"
						onclick="javascript:$('#tt').edatagrid('cancelRow')"><s:text
							name='button.cancel' /></a>
				</div>
			</div>

			<s:include value="../footer.jsp" />
		</div>
	</div>
</body>
</html>



