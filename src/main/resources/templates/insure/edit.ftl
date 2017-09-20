<form id="insure-edit" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit"><input type="hidden" name="id" /><input type="hidden" name="carId" />
				车号:
			</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="carNum" data-options="required:true,validType:'length[1,20]',readonly:true" />
			</td>
			<td class="formLayoutTit">建运号:</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="operateNum" data-options="required:true,validType:'length[1,20]',readonly:true" />
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">车主姓名:</td>
			<td>
				<input class="easyui-textbox" type="text" name="ownerName" data-options="validType:'length[1,20]',readonly:true" />
			</td>
			<td class="formLayoutTit">联系方式:</td>
			<td>
				<input class="easyui-textbox" type="text" name="ownerPhone" data-options="validType:'Mobile',readonly:true" />
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">交强止期:</td>
			<td><input class="easyui-datebox" type="text" name="forceInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
			<td class="formLayoutTit">商业止期:</td>
			<td><input class="easyui-datebox" type="text" name="busInsure" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">外购:</td>
			<td>
				<select id="insure-out-buy" class="easyui-combobox" name="outBuy" data-options="editable:false" style="width:135px;">
				    <option value="0">&nbsp;</option>
				    <option value="1">是</option>
				</select>
			</td>
			<td class="formLayoutTit">办理情况:</td>
			<td>
				<select id="insure-do-type" class="easyui-combobox" name="doType" data-options="editable:false" style="width:135px;">
				    <option value="0">&nbsp;</option>
				    <option value="1">未缴费</option>
				    <option value="2">未领取</option>
				</select>
			</td>
		</tr>
	</table>
</form>