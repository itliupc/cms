jQuery(function($){  
    // 备份jquery的ajax方法    
    var _ajax = $.ajax;  
    // 重写ajax方法，先判断登录在执行success函数   
    $.ajax = function(opt){  
        var _success = opt && opt.success || function(a, b){};  
        var _opt = $.extend(opt, {  
            success:function(data, textStatus){
                // 如果后台将请求重定向到了登录页  
                if(data && data.toString().indexOf('check_session_for_login') != -1) {  
                    window.location.href= 'login';  
                    return;  
                }  
                _success(data, textStatus);    
            }    
        });  
        _ajax(_opt);  
    };  
});
$.extend($.fn.validatebox.defaults.rules, {
    /*必须和某个字段相等*/
	equalTo: { 
		validator: function(value, param){
			return $(param[0]).val() == value;
		}, 
		message: '确认密码与新密码输入不一致' 
	},
	//验证密码  
    pwd: {  
        validator: function (value) {  
            return /^(\w){6,20}$/.test(value);  
        },  
        message: '请输入6到20位的字母、数字或下划线.'  
    },
    //验证汉字  
    char: {  
        validator: function (value) {  
            return /^\w+$/.test(value);  
        },  
        message: '请输入字母、数字或下划线.'  
    },
	//验证汉字  
    CHS: {  
        validator: function (value) {  
            return /^[\u0391-\uFFE5]+$/.test(value);  
        },  
        message: 'The input Chinese characters only.'  
    },  
    //移动手机号码验证  
    Mobile: {//value值为文本框中的值  
        validator: function (value) {  
            var reg = /^1[3|4|5|6|7|8|9]\d{9}$/;  
            return reg.test(value);  
        },  
        message: '请输入正确的手机号码.'  
    }, 
    //邮箱验证  
    Email: {//value值为文本框中的值  
        validator: function (value) {  
            var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;  
            return reg.test(value);  
        },  
        message: '请输入正确的邮箱地址.'  
    }, 
    //国内邮编验证  
    ZipCode: {  
        validator: function (value) {  
            var reg = /^[0-9]\d{5}$/;  
            return reg.test(value);  
        },  
        message: 'The zip code must be 6 digits and 0 began.'  
    },  
  //数字  
    Number: {  
        validator: function (value) {  
            var reg =/^[0-9]*$/;  
            return reg.test(value);  
        },  
        message: 'Please input number.'  
    }
});


 

var DateUtil = (function () {
	return {
		formatDateTime : function(value) {  
		    if (value == null || value == '') {  
		        return '';  
		    }  
		    var dt;  
		    if (value instanceof Date) {  
		        dt = value;  
		    } else {  
		        dt = new Date(value);  
		    }  
		    var y = dt.getFullYear();
			var m = dt.getMonth()+1;
			var d = dt.getDate();
			var h = dt.getHours();
			var min = dt.getMinutes();
			var s = dt.getSeconds();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+" "+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(s<10?('0'+s):s);
		},
		formatDatebox : function(value) {  
		    if (value == null || value == '') {  
		        return '';  
		    }  
		    var dt;  
		    if (value instanceof Date) {  
		        dt = value;  
		    } else {  
		        dt = new Date(value);  
		    }  
		    var y = dt.getFullYear();
			var m = dt.getMonth()+1;
			var d = dt.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		},
		parseDatebox : function(s){
			if (!s) return new Date();
			var str = DateUtil.formatDatebox(s);
			if(!str) return new Date();
			var ss = (str.split('-'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
	};
})();

var HomeManage = (function () {
	return {
		operateBtn : function(value, row, index) {
			var button = "<a href=\"#\" data-roles=\"mui-linkbutton\" title=\"详细信息\" data-options=\"iconCls:'icon-help',plain:true\" class=\"l-btn l-btn-plain\" onclick=\"HomeManage.showDetail('"
					+ index + "');\">";
			button = button
					+ "<span class=\"l-btn-left\"><span class=\"l-btn-text icon-help l-btn-icon-left\">详细信息</span></span></a>";
			return button;
		},
		showDetail : function(index){
			var record = $("#home-datagrid").datagrid('getRows')[index];
			alert(record.carNum);
		}
	};
})();

var CommonUtil = (function () {
	return {
		preSelectTab : null,
		openedTabs : [],
		carSelect : function(id) { 
			var selectDialog = $("<div></div>").dialog({
				title : '车辆选择',
				width : 700,
				height : 640,
				closed : false,
				cache : false,
				resizable : false,
				href : "car-manage/view/select",
				modal : true,
				onClose : function(){
					selectDialog.dialog('destroy');
				},
				onLoad : function() {
					var scid = $("#"+id).find("input[name='carId']").val();
					$("#car-select-search").parent().find("input[name='carId']").val(scid);
				},
				buttons : [ {
					iconCls: "icon-save",
					text : '确定',
					handler : function(){
						var row = $("#car-select-datagrid").datagrid('getSelected');
						if(row){
							var result = {
								'carId' : row.id,
								'carNum' : row.carNum,
								'operateNum' : row.operateNum,
								'ownerName' : row.ownerName,
								'ownerPhone' : row.ownerPhone	
							};
							$("#"+id).form('load',result);
						}
						selectDialog.dialog('close');
					}
				}, {
					iconCls: "icon-cancel",
					text : '关闭',
					handler : function() {
						selectDialog.dialog('close');
					}
				} ]
			});
		},
		checkSelectRow : function(data){
			var scid = $("#car-select-search").parent().find("input[name='carId']").val();
			if(scid && data.total > 0){
				for(var j = 0; j < data.total; j++){
					if(data.rows[j].id == scid){
						$("#car-select-datagrid").datagrid('selectRow',j);
						break;
					}
				}
			}
		}
	};
})();

