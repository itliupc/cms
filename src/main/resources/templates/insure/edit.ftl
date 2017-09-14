<form id="insure-edit" class="easyui-form">
	<table cellpadding="5" style="margin-top:30px;" class="formLayout">
		<tr>
			<td class="formLayoutTit"><input type="hidden" name="id"></input>
				车号:
			</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="carNum" data-options="required:true,validType:'length[1,20]'"></input>
			</td>
			<td class="formLayoutTit">建运号:</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="operateNum" data-options="required:true,validType:'length[1,20]'"></input>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">交强止期:</td>
			<td><input class="easyui-datebox" type="text" name="forceInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
			<td class="formLayoutTit">商业止期:</td>
			<td><input class="easyui-datebox" type="text" name="busInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">缴费情况:</td>
			<td>
				<select id="insure-has-pay" class="easyui-combobox" name="hasPay" data-options="editable:false" style="width:135px;">
				    <option value="1">已缴费</option>
				    <option value="0">未缴费</option>
				</select>
			</td>
			<td class="formLayoutTit">领取情况:</td>
			<td>
				<select id="insure-has-receive" class="easyui-combobox" name="hasReceive" data-options="editable:false" style="width:135px;">
				    <option value="1">已领取</option>
				    <option value="0">未领取</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">是否外购:</td>
			<td>
				<select id="insure-out-buy" class="easyui-combobox" name="outBuy" data-options="editable:false" style="width:135px;">
				    <option value="0">否</option>
				    <option value="1">是</option>
				</select>
			</td>
			<td>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>