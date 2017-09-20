<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆信息管理系统</title>
<link rel="stylesheet" type="text/css" href="static/easyui/1.5.2/themes/insdep/easyui.css" />
<link rel="stylesheet" type="text/css" href="static/css/weui.css" />
<link rel="stylesheet" type="text/css" href="static/css/icon.css" />
<script type="text/javascript" src="static/easyui/1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="static/easyui/1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="static/easyui/1.5.2/jquery.form.js"></script>
<script type="text/javascript" src="static/easyui/1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="static/js/common.js"></script>
</head>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="cui-header" data-options="region:'north',border:false,split:true">
    	<div class="cui-header-left">
        	<h1>车辆信息管理系统</h1>
        </div>
        <div class="cui-header-right">
        	<p><strong>${userName}</strong>，欢迎您！</p>
            <p><a href="javascript:void(0)" onclick="changePassword()">修改密码</a>|<a href="logout">安全退出</a></p>
        </div>
    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
	<div class="cui-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false,fit:true"> 
        	<ul class="easyui-tree cui-side-tree">
        		 <#if userRole == 0>
             	 	<li iconCls="icon-users"><a id="user-dashed" href="javascript:void(0)" data-icon="icon-users" data-link="user-manage/view/index" iframe="0">用户管理</a></li>
             	 </#if>
                 <li iconCls="icon-cog"><a id="car-dashed" href="javascript:void(0)" data-icon="icon-cog" data-link="car-manage/view/index" iframe="0">车辆管理</a></li>
                 <li iconCls="icon-book"><a id="insure-dashed" href="javascript:void(0)" data-icon="icon-book" data-link="insure-manage/view/index" iframe="0">车险管理</a></li>
                 <li iconCls="icon-application-osx-error"><a id="violate-dashed" href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="violate-manage/view/index" iframe="0">违章管理</a></li>
                 <!-- <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="pages/layout-3.html" iframe="0">系统参数</a></li>
                 <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="pages/layout-3.html" iframe="0">操作日志</a></li>-->
             </ul>
        </div>
    </div>	
    <!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="cui-main" data-options="region:'center'">
        <div id="cui-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="href:'home',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
	<div class="cui-footer" data-options="region:'south',border:false,split:true,maxHeight:30",minHeight:30">
    	&copy; 2017 All Rights Reserved
    </div>
    <div id="cui-cpwd"></div>
    <!-- end of footer -->  
    <script type="text/javascript">
		$(function(){
			$('.cui-side-tree li').bind("click",function(){
				var title = $(this).find('a').text();
				var url = $(this).find('a').attr('data-link');
				var iconCls = $(this).find('a').attr('data-icon');
				var iframe = $(this).find('a').attr('iframe')==1?true:false;
				addTab(title,url,iconCls,iframe);
			});	
		})
		
		/**
		* Name 载入树形菜单 
		*/
		/*$('#cui-side-tree').tree({
			url:'pages/menu.php',
			cache:false,
			onClick:function(node){
				var url = node.attributes['url'];
				if(url==null || url == ""){
					return false;
				}
				else{
					addTab(node.text, url, '', node.attributes['iframe']);
				}
			}
		});*/
		
		/**
		* Name 选项卡初始化
		*/
		$('#cui-tabs').tabs();
			
		/**
		* Name 添加菜单选项
		* Param title 名称
		* Param href 链接
		* Param iconCls 图标样式
		* Param iframe 链接跳转方式（true为iframe，false为href）
		*/	
		function addTab(title, href, iconCls, iframe){
			var tabPanel = $('#cui-tabs');
			if(!tabPanel.tabs('exists',title)){
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
				if(iframe){
					tabPanel.tabs('add',{
						title:title,
						content:content,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
				else{
					tabPanel.tabs('add',{
						title:title,
						href:href,
						iconCls:iconCls,
						fit:true,
						cls:'pd3',
						closable:true
					});
				}
			}
			else
			{
				tabPanel.tabs('select',title);
			}
		}
		/**
		* Name 移除菜单选项
		*/
		function removeTab(){
			var tabPanel = $('#cui-tabs');
			var tab = tabPanel.tabs('getSelected');
			if (tab){
				var index = tabPanel.tabs('getTabIndex', tab);
				tabPanel.tabs('close', index);
			}
		}
		
		/**
		* 打开Tab
		*/
		function openTab(tabId){
			$('#' + tabId).trigger('click');
		}
		
		function changePassword(){
			$("#cui-cpwd").dialog({
				title : '修改密码',
				width : 400,
				height : 240,
				closed : false,
				cache : false,
				resizable : false,
				href : "user-manage/view/password",
				modal : true,
				onLoad : function() {
					
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '保存',
					handler : function(){
						if($("#user-pwd").form("validate")){
							var oldPassword=$.trim($("#user-pwd").find("input[name='oldPassword']").val());
							var newPassword=$.trim($("#user-pwd").find("input[name='newPassword']").val());
							var confirmPassword=$.trim($("#user-pwd").find("input[name='confirmPassword']").val());
							if(newPassword != confirmPassword){
								$.messager.alert("提示","确认密码与新密码输入不一致!","info");
							} else {
								$.ajax({
									method : 'post',
									url : 'changepwd',
									data : {
										'newPassword' : newPassword,
										'oldPassword' : oldPassword
									},
									async : false,
									success : function(data) {
										if(data.result){
											$("#cui-cpwd").dialog('close');
											$.messager.alert('提示','密码修改成功,请重新登陆!',"info",function(){window.location.href="logout";});
										}else{
											$.messager.alert('提示',data.message,"info");
										}
									}
								});
							}
						}
					}
				}, {
					iconCls: "icon-cancel",
					text : '关闭',
					handler : function() {
						$("#cui-cpwd").dialog('close');
					}
				} ]
			});
		}
		
	</script>
</body>
</html>
