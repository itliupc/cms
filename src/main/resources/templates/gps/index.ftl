<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:170px;" >
		<form id="gps-search" class="easyui-form">
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
						<label for="deadline" >条件筛选:</label>
					</td>
					<td width='200px' align="left">
						<select id="gps-deadline" class="easyui-combobox" name="hasDeal" data-options="editable:false" style="width:135px;">
						    <option value="0">全部</option>
						    <option value="1">即将到期</option>
						    <option value="2">已经到期</option>
						</select>
					</td>
				</tr>
				<tr style="height:45px;">
					<td  style="text-align:right"  width='100px' colspan="3">
						<a href="#" style="margin-right:50px;" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:GpsManage.query()">查询</a>
					</td>
					<td  style="text-align:left" width='100px' colspan="3">
						<a href="#" style="margin-left:50px;" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:GpsManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="gps-content" region="center" title="列表区">
		<table id="gps-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'gps-manage/list',toolbar:'#gps-toolbar',
				idField:'id',fitColumns:'true',fit:'true',
				rownumbers:'true',pagination:'true',border:'false'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" hidden="true"></th>
					<th field="carNum" width="60" data-options="formatter: function(value, row, index){return row.car.carNum}">车号</th>
					<th field="operateNum" width="70" data-options="formatter: function(value, row, index){return row.car.operateNum}">建运号</th>
					<th field="ownerName" width="60" data-options="formatter: function(value, row, index){return row.car.ownerName}">车主姓名</th>
					<th field="ownerPhone" width="70" data-options="formatter: function(value, row, index){return row.car.ownerPhone}">联系方式</th>
					<th field="endDate" width="80" data-options="styler:GpsManage.dateRowStyle,formatter: GpsManage.formatRowDate">到期时间</th>
					<th field="user.name" width="60" data-options="formatter: GpsManage.formatOperateUser">操作员</th>
					<th data-options="field:'fck', width:60, formatter:GpsManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="gps-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:GpsManage.add()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:GpsManage.remove()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="javascript:GpsManage.importData()">数据导入</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:GpsManage.exportData()">数据导出</a>
		</div>
	</div>
</div>
<div id="add_gps_dialog"></div>
<div id="edit_gps_dialog"></div>
<script type="text/javascript" src="static/js/gps-manage.js"></script>
