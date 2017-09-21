<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:170px;" >
		<form id="violate-search" class="easyui-form">
			<table width='80%' style="margin:15px auto;">
				<tr style="height:45px;">
					<td width='100px' align="right">
						<label for="carNum" >车号:</label>
					</td>
					<td width='200px' align="left" class="uppercase">
						<input class="easyui-textbox" type="text" name="carNum"></input>
					</td>
					<td width='100px' align="right">
						<label for="operateNum" >建运号:</label>
					</td>
					<td width='200px' align="left" class="uppercase">
						<input class="easyui-textbox" type="text" name="operateNum"></input>
					</td>
					<td width='100px' align="right">
						<label for="hasDeal" >条件筛选:</label>
					</td>
					<td width='200px' align="left">
						<select id="search-hasDeal" class="easyui-combobox" name="hasDeal" data-options="editable:false" style="width:135px;">
						    <option value="">全部</option>
						    <option value="1">已缴费</option>
						</select>
					</td>
				</tr>
				<tr style="height:45px;">
					<td  style="text-align:right"  width='100px' colspan="3">
						<a href="#" style="margin-right:50px;" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:ViolateManage.query()">查询</a>
					</td>
					<td  style="text-align:left" width='100px' colspan="3">
						<a href="#" style="margin-left:50px;" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:ViolateManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="violate-content" region="center" title="列表区">
		<table id="violate-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'violate-manage/list',toolbar:'#violate-toolbar',
				idField:'id',fitColumns:'true',fit:'true',
				rownumbers:'true',pagination:'true',border:'false',
				onLoadSuccess: ViolateManage.mergeCell">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" hidden="true"></th>
					<th field="carNum" width="60" data-options="formatter: function(value, row, index){return row.car.carNum}">车号</th>
					<th field="operateNum" width="70" data-options="formatter: function(value, row, index){return row.car.operateNum}">建运号</th>
					<th field="ownerName" width="60" data-options="formatter: function(value, row, index){return row.car.ownerName}">车主姓名</th>
					<th field="ownerPhone" width="70" data-options="formatter: function(value, row, index){return row.car.ownerPhone}">联系方式</th>
					<th field="recordDate" width="80" data-options="formatter: ViolateManage.formatRowDate">违章日期</th>
					<th field="hasDeal" width="30" data-options="formatter: ViolateManage.formatHasDeal">缴费情况</th>
					<th field="user.name" width="60" data-options="formatter: ViolateManage.formatOperateUser">操作员</th>
					<th data-options="field:'fck', width:60, formatter:ViolateManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="violate-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:ViolateManage.add()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:ViolateManage.remove()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="javascript:ViolateManage.importData()">数据导入</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:ViolateManage.exportData()">数据导出</a>
		</div>
	</div>
</div>
<div id="add_violate_dialog"></div>
<div id="edit_violate_dialog"></div>
<script type="text/javascript" src="static/js/violate-manage.js"></script>
