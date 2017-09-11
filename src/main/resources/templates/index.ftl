<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆信息管理</title>
<link rel="stylesheet" type="text/css" href="static/easyui/1.5.2/themes/insdep/easyui.css" />
<link rel="stylesheet" type="text/css" href="static/css/weui.css" />
<link rel="stylesheet" type="text/css" href="static/css/icon.css" />
<script type="text/javascript" src="static/easyui/1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="static/easyui/1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="static/easyui/1.5.2/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="cui-header" data-options="region:'north',border:false,split:true">
    	<div class="cui-header-left">
        	<h1>车辆信息管理</h1>
        </div>
        <div class="cui-header-right">
        	<p><strong>admin</strong>，欢迎您！</p>
            <p><a href="#">修改密码</a>|<a href="logout">安全退出</a></p>
        </div>
    </div>
    <!-- end of header -->
    <!-- begin of sidebar -->
	<div class="cui-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false,fit:true"> 
        	<ul class="easyui-tree cui-side-tree">
             	 <li iconCls="icon-users"><a href="javascript:void(0)" data-icon="icon-users" data-link="users/index" iframe="0">用户管理</a></li>
                 <li iconCls="icon-chart-organisation"><a href="javascript:void(0)" data-icon="icon-chart-organisation" data-link="pages/layout-3.html" iframe="0">用户管理</a></li>
                 <li iconCls="icon-user-group"><a href="javascript:void(0)" data-icon="icon-user-group" data-link="pages/layout-3.html" iframe="0">角色管理</a></li>
                 <li iconCls="icon-book"><a href="javascript:void(0)" data-icon="icon-book" data-link="pages/layout-3.html" iframe="0">数据字典</a></li>
                 <li iconCls="icon-cog"><a href="javascript:void(0)" data-icon="icon-cog" data-link="pages/layout-3.html" iframe="0">系统参数</a></li>
                 <li iconCls="icon-application-osx-error"><a href="javascript:void(0)" data-icon="icon-application-osx-error" data-link="pages/layout-3.html" iframe="0">操作日志</a></li>
             </ul>
        </div>
    </div>	
    <!-- end of sidebar -->    
    <!-- begin of main -->
    <div class="cui-main" data-options="region:'center'">
        <div id="cui-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="href:'user-manage/view/index',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
        </div>
    </div>
    <!-- end of main --> 
    <!-- begin of footer -->
	<div class="cui-footer" data-options="region:'south',border:false,split:true,maxHeight:30",minHeight:30">
    	&copy; 2017 All Rights Reserved
    </div>
    <!-- end of footer -->  
    <script type="text/javascript">
		$(function(){
			$('.cui-side-tree a').bind("click",function(){
				var title = $(this).text();
				var url = $(this).attr('data-link');
				var iconCls = $(this).attr('data-icon');
				var iframe = $(this).attr('iframe')==1?true:false;
				addTab(title,url,iconCls,iframe);
			});	
		})
		
		/**
		* Name 载入树形菜单 
		*/
		$('#cui-side-tree').tree({
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
		});
		
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
	</script>
</body>
</html>
