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
	function submit(){
		var convertLeadForm = document.getElementById('convertLeadForm');
		convertLeadForm.action = "convertLead.action";
		convertLeadForm.submit();
	}
	
	function cancel(){
		window.close();
	}

  </script>
</head>
<body>
	<div id="feature">
			<div id="shortcuts" class="headerList">
			    <b style="white-space:nowrap;color:#444;"><s:text name="title.action" />:&nbsp;&nbsp;</b>
				<span> <span style="white-space: nowrap;"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-ok" onclick="submit()"
								plain="true"><s:text name="button.convert" /></a>
				</span> <span style="white-space: nowrap;"> <a href="#"
								class="easyui-linkbutton" iconCls="icon-cancel"
								onclick="cancel()" plain="true"><s:text name="button.cancel" /></a>
				</span>
				</span>
			</div>	
	
	
		<div id="feature-title">
			<h2>
				<s:text name="title.convert.lead" />
			</h2>
			<s:actionerror />
			<s:fielderror />
		</div>

		<div id="feature-content">
			<s:form id="convertLeadForm" action="convertLead" method="POST"
				namespace="/jsp/crm">
				    
				    <s:hidden name="id" value="%{#parameters.id}"/>
					<table style="padding: 10px;" cellspacing="10" cellpadding="0" width="100%">
						<tr>
							<td align="left" colspan="2"> <label class="record-label"><s:text name="convert.lead.message" />:</label></td>
						</tr>					
						<tr>
						    <td class="td-label"><label class="record-label"><s:text name="convert.target.account" /></label></td>
							<td class="td-value"><s:checkbox id="accountCheck" name="accountCheck"/></td>
						</tr>
						<tr>
						    <td class="td-label"><label class="record-label"><s:text name="convert.target.contact" /></label></td>
							<td class="td-value"><s:checkbox id="contactCheck" name="contactCheck"/></td>
						</tr>
						<tr>
						    <td class="td-label"><label class="record-label"><s:text name="convert.target.opportunity" /></label></td>
							<td class="td-value"><s:checkbox id="opportunityCheck" name="opportunityCheck"/></td>
						</tr>													
					</table>
			</s:form>
		</div>
	</div>

</body>
</html>

