<form id="violate-add" class="easyui-form">
	<table cellpadding="5" class="formLayout">
		<tr>
			<td class="formLayoutTit"><input type="hidden" name="id" /><input type="hidden" name="carId" />
				车号:
			</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="carNum" data-options="required:true,validType:'length[1,20]',readonly:true" />
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">建运号:</td>
			<td class="uppercase">
				<input class="easyui-textbox" type="text" name="operateNum" data-options="required:true,validType:'length[1,20]',readonly:true" />
			</td>
		</tr>
		<tr style="display:none;">
			<td class="formLayoutTit">车主姓名:</td>
			<td>
				<input class="easyui-textbox" type="text" name="ownerName" data-options="validType:'length[1,20]',readonly:true" />
			</td>
		</tr>
		<tr style="display:none;">
			<td class="formLayoutTit">联系方式:</td>
			<td>
				<input class="easyui-textbox" type="text" name="ownerPhone" data-options="validType:'Mobile',readonly:true" />
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">违章日期:</td>
			<td><input class="easyui-datebox" type="text" name="recordDate" data-options="parser:DateUtil.parseDatebox"></input></td>
		</tr>
		<tr>
			<td class="formLayoutTit">缴费情况:</td>
			<td>
				<select id="violate-has-deal" class="easyui-combobox" name="hasDeal" data-options="editable:false" style="width:135px;">
				    <option value="0">&nbsp;</option>
				    <option value="1">已缴费</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="formLayoutTit">备注:</td>
			<td>
				<textarea id="violate-remark" name="remark" rows="4" cols="50"></textarea>
			</td>
		</tr>
	</table>
</form>