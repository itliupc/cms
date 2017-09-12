<form id="user-edit" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit">姓名:</td>
			<td>
				<input type="hidden" name="userId"></input>
				<input class="easyui-textbox" type="text" name="name" data-options="required:true"></input>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">账号:</td>
			<td><input class="easyui-textbox" type="text" name="userName" data-options="required:true"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">状态:</td>
			<td>
				<select id="user-status" class="easyui-combobox" name="status" data-options="editable:false" style="width:135px;">
				    <option value="0">启用</option>
				    <option value="1">停用</option>
				</select>
			</td>
		</tr>
	</table>
</form>