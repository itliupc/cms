<form id="user-pwd" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit">原密码:</td>
			<td><input class="easyui-textbox" type="password" name="oldPassword" data-options="required:true" validType="pwd"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">新密码:</td>
			<td><input class="easyui-textbox" type="password" id="password" name="newPassword" data-options="required:true" validType="pwd"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">确认密码:</td>
			<td><input class="easyui-textbox" type="password" id="repassword" name="confirmPassword" data-options="required:true" validType="equalTo['#password']"></input></td>
		</tr>
	</table>
</form>