<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<link href="/favicon.ico" rel="icon" type="image/x-icon" />
<title>项目管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

<LINK rel="stylesheet" type="text/css" href="${baseurl}common/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<SCRIPT type="text/javascript">
	var menu_file = 'menu.json';
	var role = '${activeUser.role}';  //角色
	if(role==0){//普通组员
		menu_file = 'menu_member.json';
	}else if(role==1){//测试
		menu_file = 'menu_tester.json';
	}else if(role==2){//系统管理员
		menu_file = 'menu_leader.json';
	}else if(role==3){
		menu_file = 'menu_manager.json';
	}
    var tabOnSelect = function(title) {
		//根据标题获取tab对象
		var currTab = $('#tabs').tabs('getTab', title);
		var iframe = $(currTab.panel('options').content);//获取标签的内容
		
		var src = iframe.attr('src');//获取iframe的src
		//当重新选中tab时将ifram的内容重新加载一遍，目的是获取当前页面的最新内容
		if (src)
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(src)//ifram内容
				}
			});

	};
	var _menus;
	$(function() {//预加载方法
		//通过ajax请求菜单
		$.ajax({
			url : '${baseurl}'+menu_file,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				_menus = data;
				initMenu(_menus);//解析json数据，将菜单生成
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});

		//tabClose();
		//tabCloseEven();
		//加载欢迎页面
		$('#tabs').tabs('add', {
			title : '欢迎使用',
			content : createFrame('${baseurl}welcome.action')
		}).tabs({
			//当重新选中tab时将ifram的内容重新加载一遍
			onSelect : tabOnSelect
		});
		
		//修改密码
		$('#modifypwd').click(menuclick);

	});

	//退出系统方法
	function logout() {
		_confirm('您确定要退出本系统吗?',null,
				function(){
					location.href = '${baseurl}logout.action';
				}
		)
	}
	

	//帮助
	function showhelp(){
	    window.open('${baseurl}help/help.html','帮助文档'); 
	}
	
	
</SCRIPT>



<META name="GENERATOR" content="MSHTML 9.00.8112.16540">
</HEAD>

<BODY style="overflow-y: hidden;" class="easyui-layout" scroll="no" >
	<DIV
		style='background: url("common/images/layout-browser-hd-bg.png") repeat-x center 100% rgb(127, 153, 190);height:40px;color: rgb(255, 255, 255); line-height: 30px; overflow: hidden; font-family: Verdana, 微软雅黑, 黑体;'
		border="false" split="true" region="north">
		<SPAN style="padding-right: 20px; font-size:14px;float: right;" class="head">
			欢迎当前用户：【${activeUser.name}】&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${activeUser.role!=3}">
				当前项目：【${activeUser.projectname}】&nbsp;&nbsp;&nbsp;&nbsp;
			</c:if>
			<c:if test="${activeUser.role!=3}">
				<A style="font-size:14px;" title='修改密码' ref='modifypwd' href="#" rel='${baseurl}user/toupdatepwd.action' icon='icon-null' id="modifypwd" >修改密码</A>
			</c:if>
				&nbsp;&nbsp;
			<A style="font-size:14px;" id="loginOut" href=javascript:logout()>退出系统</A>
		</SPAN> 
		<SPAN style="padding-left: 10px; font-size: 16px;"><IMG
			align="absmiddle" src="common/images/blocks.gif" width="20" height="20">
			项目管理系统</SPAN> <SPAN style="padding-left: 15px;" id="News"></SPAN>
	</DIV>

	<DIV style="background: rgb(210, 224, 242); height: 30px;" split="false"
		region="south">

		<DIV class="footer">
			系统版本号：1.0&nbsp;&nbsp;&nbsp;发布日期：2017-07-01
		</DIV>
	</DIV>

	<DIV style="width: 180px;" id="west" title="导航菜单" split="true"
		region="west" hide="true">

		<DIV id="nav" class="easyui-accordion" border="false" fit="true">
			<!--  导航内容 -->
		</DIV>
	</DIV>

	<DIV style="background: rgb(238, 238, 238); overflow-y: hidden;"
		id="mainPanle" region="center">
		<DIV id="tabs" class="easyui-tabs" border="false" fit="true"></DIV>
	</DIV>


</BODY>
</HTML>
