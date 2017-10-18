<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="查询区" style="height:180px;" >
		<form id="exam-search" class="easyui-form">
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
						<select id="exam-deadline" class="easyui-combobox" name="deadline" data-options="editable:false" style="width:135px;">
						    <option value="0">全部</option>
						    <option value="1">即将到期</option>
						    <option value="2">已经到期</option>
						</select>
					</td>
				</tr>
				<tr style="height:45px;">
					<td  style="text-align:right"  width='450px' colspan="3">
						<a href="#" style="margin-right:50px;" class="easyui-linkbutton" iconCls="icon-search" plain="false" onclick="javascript:ExamManage.query()">查询</a>
					</td>
					<td  style="text-align:left" width='450px' colspan="3">
						<a href="#" style="margin-left:50px;" class="easyui-linkbutton" iconCls="icon-reload" plain="false" onclick="javascript:ExamManage.reset()">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="exam-content" region="center" title="列表区">
		<table id="exam-datagrid" class="easyui-datagrid" style="height:100%"
			data-options="url:'exam-manage/list',toolbar:'#exam-toolbar',
				idField:'id',fitColumns:'true',fit:'true',
				rownumbers:'true',pagination:'true',border:'false'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="id" hidden="true"></th>
					<th field="carNum" width="60" data-options="formatter: function(value, row, index){return row.car.carNum}">车号</th>
					<th field="car.operateNum" width="70" data-options="sortable:true,formatter: function(value, row, index){return row.car.operateNum}">建运号</th>
					<th field="ownerName" width="60" data-options="formatter: function(value, row, index){return row.car.ownerName}">车主姓名</th>
					<th field="ownerPhone" width="70" data-options="formatter: function(value, row, index){return row.car.ownerPhone}">联系方式</th>
					<th field="endDate" width="80" data-options="sortable:true,styler:ExamManage.dateRowStyle,formatter: ExamManage.formatRowDate">年审日期</th>
					<th field="user.name" width="60" data-options="formatter: ExamManage.formatOperateUser">操作员</th>
					<th data-options="field:'fck', width:60, formatter:ExamManage.editBtn" align="left">操作</th>
				</tr>
			</thead>
		</table>
		<div id="exam-toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:ExamManage.add()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:ExamManage.remove()">删除</a>
			<#if userRole == 0>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="javascript:ExamManage.importData()">数据导入</a>
			</#if>
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:ExamManage.exportData()">数据导出</a>
		</div>
	</div>
</div>
<div id="add_exam_dialog"></div>
<div id="edit_exam_dialog"></div>
<script type="text/javascript" src="static/js/exam-manage.js"></script>
