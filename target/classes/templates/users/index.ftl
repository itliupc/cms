<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:120px;" >
		<form id="user-search" class="easyui-form">
			<table width='800px' style="margin:15px auto">
				<tr>
					<td width='100px' align="right">
						<label for="name" >姓名:</label>
					</td>
					<td width='200px' align="left">
						<input class="easyui-textbox" type="text" name="name"></input>
					</td>
					<td width='100px' align="right">
						<label for="userName" >账号:</label>
					</td>
					<td width='200px' align="left">
						<input class="easyui-textbox" type="text" name="userName"></input>
					</td>
					<td  style="text-align:center"  width='100px'>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:UserManage.query()">查询</a>
					</td>
					<td  style="text-align:center" width='100px'>
						<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:UserManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="user-content" region="center" title="列表区">
		<table id="user-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'user-manage/list',toolbar:'#user-toolbar',
				idField:'userId',fitColumns:'true',fit:'true',
				rownumbers:'true',pagination:'true',border:'false'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="userId" hidden="true"></th>
					<th field="userName" width="100">账号</th>
					<th field="name" width="100">姓名</th>
					<th field="email" width="100">邮箱地址</th>
					<th field="phone" width="100">手机号码</th>
					<th field="latestLoginTime" width="100" data-options="formatter:UserManage.formatRowDateTime">上次登陆</th>
					<th field="status" width="60" data-options="formatter:UserManage.formatStatus">状态</th>
					<th data-options="field:'fck', width:100, formatter:UserManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="user-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:UserManage.add()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:UserManage.remove()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="javascript:UserManage.resetPassword()">重置密码</a>
		</div>
	</div>
</div>
<div id="add_user_dialog"></div>
<div id="edit_user_dialog"></div>
<script type="text/javascript" src="static/js/user-manage.js"></script>
