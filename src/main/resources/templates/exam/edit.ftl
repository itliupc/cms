<form id="exam-edit" class="easyui-form">
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
			<td class="formLayoutTit">年审日期:</td>
			<td><input class="easyui-datebox" type="text" name="endDate" data-options="parser:DateUtil.parseDatebox"></input></td>
			<td>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>