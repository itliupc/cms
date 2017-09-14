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

