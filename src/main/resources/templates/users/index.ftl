<div class="easyui-layout" style="height:100%;">
	<div region="north" split="true" title="Navigator" style="height:150px;" >
		<p style="padding:5px;margin:0;">Select language:</p>
		<ul>
			<li><a href="javascript:void(0)" onclick="showcontent('java')">Java</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('cshape')">C#</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('vb')">VB</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('erlang')">Erlang</a></li>
		</ul>
	</div>
	<div id="content" region="center" title="aaaa">
		<table id="tt" class="easyui-datagrid" style="height:100%"
				url="datagrid2_getdata.php"
				rownumbers="true" pagination="true" border="false">
			<thead>
				<tr>
					<th field="itemid">Item ID</th>
					<th field="productid">Product ID</th>
					<th field="listprice" align="right">List Price</th>
					<th field="unitcost" align="right">Unit Cost</th>
					<th field="attr1">Attribute</th>
					<th field="status" align="center">Stauts</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
