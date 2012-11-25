<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/datagrid.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>

<script type="text/javascript">
    $(document).ready(function(){	 
  	  $("#select").click(function() {	
		  many_selectrow('selectLead.action?relationKey=<s:property value="relationKey"/>&relationValue=<s:property value="relationValue"/>&seleteIDs=');
	  });	
  	  
	  $('#tt').datagrid({
		title:"<s:text name='title.grid.leads'/>",
		iconCls:'icon-save',
		width:700,
		height:350,
		pagination:true,
		idField:'id', 
		url:'listLead.action',
		columns:[[
				{field:'ck',checkbox:true},
				{field:'id',title:'ID',width:80,align:'center',sortable:'true'},
				{field:'name',title:'Name',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
					   new_format_value = "<a href='editLead.action?id=" + row.id + "'>" + value + "</a>";
					   return new_format_value 
	             }  
	            },
				{field:'title',title:'Title',width:80,align:'center',sortable:'true'},
				{field:'accountName',title:'Account Name',width:80,align:'right',sortable:'true'},
				{field:'office_phone',title:'Phone',width:80,align:'center',sortable:'true'},
				{field:'email',title:'Email',width:80,align:'center',sortable:'true'},
				{field:'user_name',title:'User Name',width:80,align:'center',sortable:'true'}
		]],
	  });
		
    }); 
  </script>
</head>
<body>
	<div id="page-wrap">
	  <div id="feature">
		<div id="shortcuts" class="headerList">
		  <b style="white-space:nowrap;color:#444;"><s:text name="title.action" />:&nbsp;&nbsp;</b>
		  <span>
		     <span style="white-space:nowrap;">
		       <a id="select" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true"><s:text name="action.select" /></a> 
		     </span>		     		     
		   </span>
         </div> 
		 <div id="feature-title">
		   <h2>
			 <s:text name="title.listLead" />
		   </h2>
		 </div>
		 <div id="feature-content">
		   <div id="tb" style="padding: 5px; height: auto">
			 <div>
			   <input id="filter_key" class="easyui-combobox" name="filter_key" style="width:60px;" data-options="
				        required:true,valueField:'label',textField:'value',
						data: [{
							label: '<s:text name="entity.id.label" />',
							value: 'id',
							selected: true 
						},{label: '<s:text name="entity.name.label" />',
							value: 'name'
						}]" />

					<input id="filter_op" class="easyui-combobox" name="filter_op"
						style="width: 40px;"
						data-options="valueField:'value',textField:'label',
						data: [{
							label: '<s:text name="filter.oper.equal" />',
							value: '=',
							selected: true 
						},{label: '<s:text name="filter.oper.notequal" />',
							value: '<>'
						},{label: '<s:text name="filter.oper.less" />',
							value: '<'
						},{label: '<s:text name="filter.oper.lessequal" />',
							value: '<='
						},{label: '<s:text name="filter.oper.greater" />',
							value: '>'		
						},{label: '<s:text name="filter.oper.greaterequal" />',
							value: '>='									
						},{label: '<s:text name="filter.oper.like" />',
							value: 'like'															
						}]" />				
                    <input id="filter_value" style="line-height:20px; border:1px solid #ccc"/>                   
			        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()" plain="true"><s:text name="button.search" /></a>  
			        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()" plain="true"><s:text name="button.reset" /></a>  
			    </div>  
			</div>  
			<s:form id="addObjectForm" namespace="/jsp/crm"
				method="post">
			  <s:hidden name="relationKey" id="relationKey" value="%{relationKey}" />	
			  <s:hidden name="relationValue" id="relationValue" value="%{relationValue}" />		   
			  <table id="tt"></table>	        
			</s:form>	      
		  </div>
		</div>
	</div>	  
</body>
</html>



