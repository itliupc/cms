<form id="insure-edit" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit"><input type="hidden" name="id"></input>
				车号:
			</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="carNum" data-options="required:true,validType:'length[1,20]'"></input>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">建运号:</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="operateNum" data-options="required:true,validType:'length[1,20]'"></input>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">交强止期:</td>
			<td><input class="easyui-datebox" type="text" name="forceInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">商业止期:</td>
			<td><input class="easyui-datebox" type="text" name="busInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">是否外购:</td>
			<td>
				<select id="insure-out-buy" class="easyui-combobox" name="outBuy" data-options="editable:false" style="width:135px;">
				    <option value="0">否</option>
				    <option value="1">是</option>
				</select>
			</td>
		</tr>
	</table>
</form>