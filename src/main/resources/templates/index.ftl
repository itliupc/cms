<!DOCTYPE html>
<html>
	<head>
		<title>车辆信息管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="icon" href="static/images/liu.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="static/images/liu.ico" type="image/x-icon" />
		<link rel="bookmark" href="static/images/liu.ico" type="image/x-icon" />
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
	             	 	<li iconCls="icon-01"><a id="user-dashed" href="javascript:void(0)" data-icon="icon-01" data-link="user-manage/view/index" iframe="0">用户管理</a></li>
	             	 </#if>
	                 <li iconCls="icon-02"><a id="car-dashed" href="javascript:void(0)" data-icon="icon-02" data-link="car-manage/view/index" iframe="0">车辆管理</a></li>
	                 <li iconCls="icon-01"><a id="insure-dashed" href="javascript:void(0)" data-icon="icon-01" data-link="insure-manage/view/index" iframe="0">车险管理</a></li>
	                 <li iconCls="icon-03"><a id="violate-dashed" href="javascript:void(0)" data-icon="icon-03" data-link="violate-manage/view/index" iframe="0">违章管理</a></li>
	                 <li iconCls="icon-04"><a id="gps-dashed" href="javascript:void(0)" data-icon="icon-04" data-link="gps-manage/view/index" iframe="0">GPS管理</a></li>
	                 <li iconCls="icon-05"><a id="operate-dashed" href="javascript:void(0)" data-icon="icon-05" data-link="operate-manage/view/index" iframe="0">营运管理</a></li>
	             	 <li iconCls="icon-06"><a id="exam-dashed" href="javascript:void(0)" data-icon="icon-06" data-link="exam-manage/view/index" iframe="0">审车管理</a></li>
	             	 <li iconCls="icon-07"><a id="manage-dashed" href="javascript:void(0)" data-icon="icon-07" data-link="manage-manage/view/index" iframe="0">管理费用</a></li>
	             </ul>
	        </div>
	    </div>	
	    <!-- end of sidebar -->    
	    <!-- begin of main -->
	    <div class="cui-main" data-options="region:'center'">
	        <div id="cui-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
	            <div title="首页" data-options="id:'home-tab', href:'home-manage/view/index',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
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
					var id = $(this).find('a').attr('id');
					var title = $(this).find('a').text();
					var url = $(this).find('a').attr('data-link');
					var iconCls = $(this).find('a').attr('data-icon');
					var iframe = $(this).find('a').attr('iframe')==1?true:false;
					addTab(id,title,url,iconCls,iframe);
				});	
			})
			
			/**
			* Name 选项卡初始化
			*/
			$('#cui-tabs').tabs({
				onBeforeClose:function(title,index){
					var closeTab = $('#cui-tabs').tabs('getTab',index);
					var tabId = closeTab.panel('options').id;
					for(var i=0; i<CommonUtil.openedTabs.length; i++) {
					  if(CommonUtil.openedTabs[i] == tabId) {
					    CommonUtil.openedTabs.splice(i, 1);
					    break;
					  }
					}
					// console.info(title + " is before close.");
				},
				onSelect:function(title,index){
					var selectTab = $('#cui-tabs').tabs('getTab',index);
					var tabId = selectTab.panel('options').id;
					if($.inArray(tabId,CommonUtil.openedTabs) > -1 && CommonUtil.preSelectTab != tabId){
						var dgId = tabId.replace('-tab','-datagrid');
						$("#" + dgId).datagrid('reload');
						// console.info(title + " is select. " + dgId + " is reload.");
					}
					CommonUtil.preSelectTab = tabId;
					if($.inArray(tabId,CommonUtil.openedTabs) == -1){
						CommonUtil.openedTabs.push(tabId);
					}
				},
				onClose:function(title,index){
					// console.info(title + " is close.")
				}
			});
				
			/**
			* Name 添加菜单选项
			* Param id id
			* Param title 名称
			* Param href 链接
			* Param iconCls 图标样式
			* Param iframe 链接跳转方式（true为iframe，false为href）
			*/	
			function addTab(id, title, href, iconCls, iframe){
				var tabId = id.replace('-dashed','-tab');
				var tabPanel = $('#cui-tabs');
				if(!tabPanel.tabs('exists',title)){
					var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
					if(iframe){
						tabPanel.tabs('add',{
							id:tabId,
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
							id:tabId,
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
