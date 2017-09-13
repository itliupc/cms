<form id="insure-edit" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit">carNum:</td>
			<td>
				<input type="hidden" name="id"></input>
				<input class="easyui-textbox" type="text" name="carNum" data-options="required:true,validType:'length[1,20]'"></input>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">operateNum:</td>
			<td><input class="easyui-textbox" type="text" name="operateNum" data-options="required:true,validType:'length[1,20]'"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">forceInsure:</td>
			<td><input class="easyui-textbox" type="text" name="forceInsure"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">busInsure:</td>
			<td><input class="easyui-textbox" type="text" name="busInsure"></input></td>
		</tr>
	</table>
</form>