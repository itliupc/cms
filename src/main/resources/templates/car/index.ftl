<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" style="height:120px;" >
		<form id="car-search" class="easyui-form">
			<table width='100%' style="margin:15px auto;">
				<tr style="height:45px;">
					<td width='100px' align="right">
						<label for="carNum" >车号:</label>
					</td>
					<td width='100px' align="left" class="uppercase">
						<input class="easyui-textbox" type="text" name="carNum"></input>
					</td>
					<td width='100px' align="right">
						<label for="operateNum" >建运号:</label>
					</td>
					<td width='100px' align="left" class="uppercase">
						<input class="easyui-textbox" type="text" name="operateNum"></input>
					</td>
					<td  style="text-align:center"  width='100px'>
						<a href="#" style="margin-right:50px;" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:InsureManage.query()">查询</a>
					</td>
					<td  style="text-align:center" width='100px'>
						<a href="#" style="margin-left:50px;" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:InsureManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="car-content" region="center" title="列表区">
		<table id="car-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'car-manage/list',singleSelect:true,
				idField:'id',fitColumns:'true',fit:'true',
				rownumbers:'true',pagination:'true',border:'false'">
			<thead>
				<tr>
					<th field="id" hidden="true"></th>
					<th field="carNum" width="60">车号</th>
					<th field="operateNum" width="70">建运号</th>
					<th field="ownerName" width="60">车主姓名</th>
					<th field="ownerPhone" width="70">联系方式</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript" src="static/js/car-manage.js"></script>
