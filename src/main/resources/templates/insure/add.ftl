<form id="insure-add" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit">车号:</td>
			<td>
				<input type="hidden" name="id"></input>
				<input class="easyui-textbox" type="text" name="carNum" data-options="required:true,validType:'length[1,20]'"></input>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">建运号:</td>
			<td><input class="easyui-textbox" type="text" name="operateNum" data-options="required:true,validType:'length[1,20]'"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">交强止期:</td>
			<td><input class="easyui-datebox" type="text" name="forceInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">商业止期:</td>
			<td><input class="easyui-datebox" type="text" name="busInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
	</table>
</form>