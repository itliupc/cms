<form id="home-detail" class="easyui-form">
	<fieldset>
		<legend>基本信息</legend>
		<table cellpadding="5" class="formLayout">
			<tr>
				<td class="formLayoutTit">车号:</td>
				<td class="uppercase">
					<input class="easyui-textbox" type="text" name="carNum" data-options="readonly:true" />
				</td>
				<td class="formLayoutTit">建运号:</td>
				<td class="uppercase">
					<input class="easyui-textbox" type="text" name="operateNum" data-options="readonly:true"></input>
				</td>
			</tr>
			<tr>
				<td class="formLayoutTit">车主姓名:</td>
				<td>
					<input class="easyui-textbox" type="text" name="ownerName" data-options="readonly:true" />
				</td>
				<td class="formLayoutTit">联系方式:</td>
				<td>
					<input class="easyui-textbox" type="text" name="ownerPhone" data-options="readonly:true" />
				</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>车险信息</legend>
		<table cellpadding="5" class="formLayout">
			<tr>
				<td class="formLayoutTit">交强止期:</td>
				<td>
					<input class="easyui-datebox" type="text" name="forceInsure" data-options="readonly:true" />
				</td>
				<td class="formLayoutTit">商业止期:</td>
				<td>
					<input class="easyui-datebox" type="text" name="busInsure" data-options="readonly:true"></input>
				</td>
			</tr>
			<tr>
				<td class="formLayoutTit">外购:</td>
				<td>
					<select class="easyui-combobox" name="outBuy" data-options="editable:false,readonly:true" style="width:135px;">
					    <option value="0">&nbsp;</option>
					    <option value="1">是</option>
					</select>
				</td>
				<td class="formLayoutTit">办理情况:</td>
				<td>
					<select class="easyui-combobox" name="doType" data-options="editable:false,readonly:true" style="width:135px;">
					    <option value="0">&nbsp;</option>
					    <option value="1">未缴费</option>
					    <option value="2">未领取</option>
					</select>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>GPS信息</legend>
		<table cellpadding="5" class="formLayout">
			<tr>
				<td class="formLayoutTit">GPS止期:</td>
				<td>
					<input class="easyui-datebox" type="text" name="gpsDate" data-options="readonly:true" />
				</td>
				<td></td>
				<td>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>审车信息</legend>
		<table cellpadding="5" class="formLayout">
			<tr>
				<td class="formLayoutTit">年审日期:</td>
				<td>
					<input class="easyui-datebox" type="text" name="examDate" data-options="readonly:true" />
				</td>
				<td></td>
				<td>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>营运证信息</legend>
		<table cellpadding="5" class="formLayout">
			<tr>
				<td class="formLayoutTit">营运证止期:</td>
				<td>
					<input class="easyui-datebox" type="text" name="operateDate" data-options="readonly:true" />
				</td>
				<td></td>
				<td>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>管理费信息</legend>
		<table cellpadding="5" class="formLayout">
			<tr>
				<td class="formLayoutTit">管理费止期:</td>
				<td>
					<input class="easyui-datebox" type="text" name="manageDate" data-options="readonly:true" />
				</td>
				<td></td>
				<td>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>违章信息</legend>
		<table id="detail-violate-datagrid" class="easyui-datagrid" style="min-height:200px;"
			data-options="url:'violate-manage/listByCarId',queryParams:{'carId':-1},
				idField:'id',fitColumns:'true',fit:'true',
				rownumbers:'true',border:'false'" >
			<thead>
				<tr>
					<th field="recordDate" width="60" data-options="formatter: function(value, row, index){return DateUtil.formatDatebox(value);}">违章日期</th>
					<th field="hasDeal" width="60" data-options="formatter: function(value, row, index){if(1==value){return '已繳費';}else{return '';}}">缴费情况</th>
				</tr>
			</thead>
		</table>
	</fieldset>
</form>