<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<style type="text/css">
	select{
		width:93%;
		height:25px;
	}
	.form_input{
		width:99%
	}
</style>
<script type="text/javascript">
  function proposebug(){
	  jquerySubByFId('bugform',bugsave_callback,null,"json");
	  
  }
  //ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
  function bugsave_callback(data){
	  if(data.success){
		  $.messager.alert('提示信息',data.msg,'success');
		  parent.closemodalwindow();
		  parent.querytask();
	  }else{
		  $.messager.alert('提示信息',data.msg,'error');
	  }
	  //message_alert(data);
  }
</script>
<title>bug查看</title>
</head>
<body>


<form id="bugform" action="${baseurl}task/proposebug.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" height="100%" bgColor=#c4d8ed>

   <TBODY>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							<TR height="220">
								<TD class=category width="35%" colspan="3">
									<div style="width:100%">
										<textarea rows="14" cols="20" name="bug" class="form_input">${bug}</textarea>
										<input type="hidden" name="id" value="${id}"/>
									</div>
								</TD>
							</TR>
							<tr>
							  <td colspan=2 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="proposebug()">提交</a>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a id="closebtn"  class="easyui-linkbutton" iconCls="icon-cancel" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
   </TBODY>
</TABLE>
</form>
</body>
</html>