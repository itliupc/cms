<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:180px;" >
		<form id="insure-search" class="easyui-form">
			<table style="margin:15px auto;min-width:915px;">
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
						<select id="search-deadline" class="easyui-combobox" name="deadline" data-options="editable:false" style="width:135px;">
						    <option value="">全部</option>
						    <option value="0">外购</option>
						    <option value="1">未领取</option>
						    <option value="2">未缴费</option>
						    <option value="3">即将过期</option>
						    <option value="4">已经过期</option>
						</select>
					</td>
				</tr>
				<tr style="height:45px;">
					<td  style="text-align:right"  width='100px' colspan="3">
						<a href="#" style="margin-right:50px;" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:InsureManage.query()">查询</a>
					</td>
					<td  style="text-align:left" width='100px' colspan="3">
						<a href="#" style="margin-left:50px;" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:InsureManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="insure-content" region="center" title="列表区">
		<table id="insure-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'insure-manage/list',toolbar:'#insure-toolbar',
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
					<th field="forceInsure" width="80" data-options="styler:InsureManage.dateRowStyle,formatter: InsureManage.formatRowDate">交强止期</th>
					<th field="busInsure" width="80" data-options="styler:InsureManage.dateRowStyle,formatter: InsureManage.formatRowDate">商业止期</th>
					<th field="hasPay" width="30" data-options="styler:InsureManage.hasPayStyle,formatter: InsureManage.formatHasPay">未缴费</th>
					<th field="hasReceive" width="30" data-options="styler:InsureManage.hasReceiveStyle,formatter: InsureManage.formatHasReceive">未领取</th>
					<th field="outBuy" width="20" data-options="styler:InsureManage.outBuyStyle,formatter: InsureManage.formatOutBuy">外购</th>
					<th field="user.name" width="60" data-options="formatter: InsureManage.formatOperateUser">操作员</th>
					<th data-options="field:'fck', width:60, formatter:InsureManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="insure-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:InsureManage.add()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:InsureManage.remove()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="javascript:InsureManage.importData()">数据导入</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:InsureManage.exportData()">数据导出</a>
		</div>
	</div>
</div>
<div id="add_insure_dialog"></div>
<div id="edit_insure_dialog"></div>
<script type="text/javascript" src="static/js/insure-manage.js"></script>
