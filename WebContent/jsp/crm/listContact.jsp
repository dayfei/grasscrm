<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf8" />
  <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css"/>  
  <link rel="stylesheet" type="text/css" href="../../themes/icon.css"/>  
  <link rel="stylesheet" type="text/css" href="../../css/global.css" /> 
  
  <script type="text/javascript" src="../../js/jquery-1.7.2.min.js"></script>  
  <script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>   
  <script type="text/javascript" src="../../js/datagrid.js"></script> 
  <script type="text/javascript" src="../../js/global.js"></script>   
  
  <script type="text/javascript">
    $(document).ready(function(){
	  $("#delete").click(function() {	
		  many_deleterow("deleteContact.action?seleteIDs=");
	  });	

	  $("#export").click(function() {	
		  many_exportrow("/crm/exportContact.action?seleteIDs=");
	  });

	  $("#copy").click(function() {	
		  many_copyrow("/crm/copyContact.action?seleteIDs=");
	  });
	  
	  $('#tt').datagrid({
		title:"<s:text name='title.grid.contacts'/>",
		iconCls:'icon-save',
		width:700,
		height:350,
		pagination:true,
		idField:'id', 
		url:'listContact.action',
		columns:[[
			{field:'ck',title:'<s:text name="entity.checkbox.label" />',checkbox:true},
			{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
			{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
				   new_format_value = "<a href='editContact.action?id=" + row.id + "'>" + value + "</a>";
				   return new_format_value 
             }  
            },
			{field:'title',title:'<s:text name="contact.title.label" />',width:80,align:'center',sortable:'true'},
			{field:'accountName',title:'<s:text name="entity.account.label" />',width:80,align:'right',sortable:'true'},
			{field:'email',title:'<s:text name="contact.email.label" />',width:80,align:'center',sortable:'true'},
			{field:'officePhone',title:'<s:text name="contact.office_phone.label" />',width:80,align:'center',sortable:'true'},
			{field:'user_name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'},
			{field:'created_by',title:'<s:text name="entity.createdBy.label" />',width:80,align:'center',sortable:'true',hidden:'true'},
			{field:'updated_by',title:'<s:text name="entity.updatedBy.label" />',width:80,align:'center',sortable:'true',hidden:'true'},
			{field:'created_on',title:'<s:text name="entity.createdOn.label" />',width:80,align:'center',sortable:'true',hidden:'true'},
			{field:'updated_on',title:'<s:text name="entity.updatedOn.label" />',width:80,align:'center',sortable:'true',hidden:'true'}
		]],
		onHeaderContextMenu: function(e, field){
			e.preventDefault();
			if (!$('#tmenu').length){
				createColumnMenu();
			}
			$('#tmenu').menu('show', {
				left:e.pageX,
				top:e.pageY
			});
		}		
	  });
		
    }); 
  </script>
</head>
<body>
	<div id="page-wrap">

      <s:include value="../header.jsp" />
		
      <s:include value="../menu.jsp" />
		
	  <div id="feature">
		<div id="shortcuts" class="headerList">
		  <b style="white-space:nowrap;color:#444;"><s:text name="title.action" />:&nbsp;&nbsp;</b>
		  <span>
		     <span style="white-space:nowrap;">
		       <a href="editContact.action" class="easyui-linkbutton" iconCls="icon-add" plain="true"><s:text name="action.createContact" /></a>  
		     </span>
		     <span style="white-space:nowrap;">
		       <a id="delete" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"><s:text name="action.deleteContact" /></a>  
		     </span>
		   </span>
	       <span style="white-space:nowrap;">
	         <a href="javascript:void(0)" id="mtmt" class="easyui-menubutton" data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text name='menu.toolbar.more.title'/></a>
	       	 <div id="mtm1" style="width:150px;">
				<div onClick="openwindow('/crm/uploadContact.jsp','<s:text name="title.import.contact" />')"><s:text name='menu.item.import.title'/></div>
				<div id="export"><s:text name='menu.item.export.title'/></div>
				<div id="copy"><s:text name='menu.item.copy.title'/></div>
			 </div>
	       </span>			   
         </div> 
		  <div id="feature-title">
			<h2> <s:text name="title.listContact" /> </h2>	   
		  </div>		
		  <div id="feature-content">
		    <div id="tb" style="padding:5px;height:auto">  
			    <div>  
				   <input id="filter_key" class="easyui-combobox" name="filter_key" style="width:60px;" data-options="
					        required:true,valueField:'label',textField:'value',
							data: [{
								label: '<s:text name="entity.id.label" />',
								value: 'id',
								selected: true 
							},{label: '<s:text name="entity.name.label" />',
								value: 'name'
							},{label: '<s:text name="contact.email.label" />',
								value: 'email'
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
		    <table id="tt"></table>	
        
		  </div>
		</div>
		
        <s:include value="../footer.jsp" />
		
	</div>	  
</body>
</html>


 
