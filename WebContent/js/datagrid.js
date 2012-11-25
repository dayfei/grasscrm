 $(document).ready(function(){
   $("#filter_value").keyup(function(){
        if(event.keyCode == 13){
        	doSearch();
        }
    });
 })

	function createColumnMenu(){
		var tmenu = $('<div id="tmenu" style="width:150px;"></div>').appendTo('body');
		var fields = $('#tt').datagrid('getColumnFields');
		for(var i=0; i<fields.length; i++){
			var option = $('#tt').datagrid('getColumnOption',fields[i]);
			if (option.hidden){
				 $('<div id="' + fields[i] + '" iconCls="icon-empty"/>').html(option.title).appendTo(tmenu);
			} else {
			     $('<div id="' + fields[i] + '" iconCls="icon-ok"/>').html(option.title).appendTo(tmenu);
			}
		}
		tmenu.menu({
			onClick: function(item){
				if (item.iconCls=='icon-ok'){
					$('#tt').datagrid('hideColumn', item.id);
					tmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} else {
					$('#tt').datagrid('showColumn', item.id);
					tmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-ok'
					});
				}
			}
		});
	}          
 
   function doSearch(){  
	    $('#tt').datagrid('load',{  
	    	_search:true,
	    	filter_key:$('#filter_key').combobox('getValue'),
	    	filter_op:$('#filter_op').combobox('getValue'),
	    	filter_value: $('#filter_value').val()
	    });  
	} 


   function reset(){  
	    $('#tt').datagrid('load',{  
	    	_search:false
	    });  
	    $('#filter_value').val("");
	} 	
  
   function many_deleterow(url){
	   
        rows = $('#tt').datagrid('getSelections');
        var num =  rows.length;
        var url = url
        
        
		if (num==0){
			$.messager.alert("Warning","No record is selected.")
		}
		else {
			$.messager.confirm('Confirm','Are you sure you want to delete?',function(r){
				if (r){
					var ids = null ;
					data = rows.concat(); 
					for (var i = 0 ;i < num; i++){
						if (null == ids || i == 0 ) {                 
						    ids =  data[i].id;             
						} else  {                 
							ids = ids + "," +  data[i].id;             
						} 
					}	
					for (var i = num - 1 ;i >= 0; i--){
						index = $('#tt').datagrid('getRowIndex',data[i]);
						$('#tt').datagrid('deleteRow',index);
					}						
				    url = url + ids; 
				    $.post( 
				       url     
				    ); 	
				}
			});
		}
	}
 
   function many_selectrow(url){
	   
       rows = $('#tt').datagrid('getSelections');
       var num =  rows.length;
       var url = url
       
       
		if (num==0){
			$.messager.alert("Warning","No record is selected.")
		}
		else {
			var ids = null ;
			data = rows.concat(); 
			for (var i = 0 ;i < num; i++){
				if (null == ids || i == 0 ) {                 
				    ids =  data[i].id;             
				} else  {                 
					ids = ids + "," +  data[i].id;             
				} 
			}	
		    url = url + ids; 
		    $.post(url, function(data) {
		    	window.opener.location.reload();$.messager.progress('close');window.close();
		     });
		    $.messager.progress({  
              title:'Please waiting',  
              msg:'Operation in progress...'  
            });              
		}
	}

   function many_removerow(url){
	   
       rows = $('#tt').datagrid('getSelections');
       var num =  rows.length;
       var url = url
       
       
		if (num==0){
			$.messager.alert("Warning","No record is selected.")
		}
		else {
			$.messager.confirm('Confirm','Are you sure you want to remove this relationship? Only the relationship will be removed. The record will not be deleted.',function(r){
				if (r){
					var ids = null ;
					data = rows.concat(); 
					for (var i = 0 ;i < num; i++){
						if (null == ids || i == 0 ) {                 
						    ids =  data[i].id;             
						} else  {                 
							ids = ids + "," +  data[i].id;             
						} 
					}	
					for (var i = num - 1 ;i >= 0; i--){
						index = $('#tt').datagrid('getRowIndex',data[i]);
						$('#tt').datagrid('deleteRow',index);
					}						
				    url = url + ids; 
				    $.post( 
				       url     
				    ); 	
				}
			});
		}
	}   
   
   function many_exportrow(url){
       rows = $('#tt').datagrid('getSelections');
       var num =  rows.length;

		if (num==0){
			$.messager.alert("Warning","No record is selected.")
		}
		else {
			var ids = null ;
			data = rows.concat() 
			for (var i=0;i<num;i++){
				if (null == ids || i == 0 ) {                 
				    ids =  data[i].id;             
				} else  {                 
					ids = ids + "," +  data[i].id;             
				} 
			}
		    var url = url + ids; 
		    url = "/grass/jsp" + url;
			window.open(url,"_self"); 						
				
		}
	}   
   
   function many_copyrow(url){
       rows = $('#tt').datagrid('getSelections');
       var num =  rows.length;

		if (num==0){
			$.messager.alert("Warning","No record is selected.")
		}
		else {
			var ids = null ;
			data = rows.concat() 
			for (var i=0;i<num;i++){
				if (null == ids || i == 0 ) {                 
				    ids =  data[i].id;             
				} else  {                 
					ids = ids + "," +  data[i].id;             
				} 
			}
		    var url = url + ids; 
		    url = "/grass/jsp" + url;
			window.open(url,"_self"); 						
				
		}
	}   
   
	function keypress(){
		var ev=window.event.keyCode;
		if(ev == 13){
			doSearch();
		}
	}   