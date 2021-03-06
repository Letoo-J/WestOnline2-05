
$(function() {
	//validateKickout();
    validateRule();
	/*$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});*/
});

function changeCode() {
    const src = "/getVerifyCode?" + new Date().getTime(); //加时间戳，防止浏览器利用缓存
    $('.verifyCode').attr("src", src);
}

/*function loag() {
    var btn = $("#btn_login");
    btn.button('loading');
    setTimeout(function () { btn.button('reset'); },2000);
}*/

//异步检测用户名是否存在


//声明加密对象
var RSADo = {
    "modulus":"",
    "publicKey":""}
//获取加密信息
function getModule(){
	$.ajax({
    	url: encodeURI("/getMoudle"),
        type: "post",  //请求的方法
        cache: false, //去除缓存
        async: false,  //async:false同步。true异步方式
        dataType: "json",  //指定响应得到的数据类型：【“list”必须严格为一个json数据！】
        data: {  
        },
        success: function(data) {
        	RSADo.modulus = data.modulus;
            RSADo.publicKey = data.publicKey;
            //console.log("publicKey:"+data.publicKey); 
        },
        error: function () {
            alert(".......出错了.......");
        }
    });
}
//公钥加密方法
function encryption(str){
	// 实例化js的RSA对象生成
    var rsa = new RSAKey()
    rsa.setPublic(RSADo.modulus, RSADo.publicKey)
    var encryptStr = rsa.encrypt(str);
    return encryptStr;
}

function login() {
	var username = $("#username").val();
    var password = $("#password").val();
    var verifyInput = $("#verifyInput").val();
//    var rememberMe = {}; //定义一个空数组 
//    $("input[name='rememberMe']:checked").each(function(i){//把所有被选中的复选框的值存入数组
//    	rememberMe[i] =$(this).val();
//    });
    var rememberMe = $("input[name='rememberMe']:checked").val();
    
    //alert("加密前"+ password1);
    //加密传给后端
    //var password = $.base64.encode(password1);
    //alert("加密后："+password);
    //$("#passWord").val(password);
    
    //-----密码进行DES加密------
//    var key ='[[${session.uuid}]]';  //获取uuid值
//    var passwordDES = encryptByDES(password, key);
    
    //获取模块：
    getModule();
    var encryptPassword = encryption(password);
    $('#password').val(encryptPassword); //填充表单
    //console.log("加密后："+encryptPassword);  //'加密后（encrypt）：',
  
    $.ajax({
    	url: encodeURI("/login"),
        type: "post",  //请求的方法
        cache: false, //去除缓存
        async: false,  //async:false同步。true异步方式
        dataType: "json",  //指定响应得到的数据类型：【“list”必须严格为一个json数据！】
        data: {  //data:$(form).serialize(),
            "username": username,
            "encryptPassword": encryptPassword,
            "verifyInput": verifyInput,   //验证码
            "rememberMe": rememberMe ,
        },
        success: function(result) {
        	if(result.user_status != null ){
            	alert(result.message);
            }
        	else if (result.code == 200) { //登入成功
            	alert(result.msg+"  即将跳转到首页！");
            	window.location.href = "/home";  //成功则跳转到用户首页
            } 
            else {
            	//$.modal.closeLoading(); //关闭加载层
            	layer.msg(result.msg); 
            	changeCode();  //刷新验证码
            	$("#verifyInput").val("");   //清空验证码输入
            	$("#password").val("");   //清空密码输入
            	//$.modal.msg(r.msg);   //弹出消息return error(msg)
            }
        },
        error: function () {
            alert(".......页面出错了.......");
        }
    });
   
}


jQuery.validator.addMethod("isMail",
		function(value, element) {   
		// /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
			var tel =/^([a-zA-Z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$/;
			return this.optional(element) || (tel.test(value));
		}, "请正确填写邮箱");

jQuery.validator.setDefaults({
		// false:仅做校验，不提交表单
		debug: true,
		//失去焦点时验证
		onfocusin:true,
		// 提交表单时做校验(true)
		onsubmit: true,
		// 焦点自动定位到第一个无效元素
		focusInvalid: true,
		// 元素获取焦点时清除错误信息
		focusCleanup: false,
		//忽略 class="ignore" 的项不做校验
		ignore: ".ignore",
		// 忽略title属性的错误提示信息
		ignoreTitle: true,
		// 为错误信息提醒元素的 class 属性增加 invalid 
		errorClass: "invalid",
		// 为通过校验的元素的 class 属性增加 valid 
		validClass: "valid",
		// 使用 <em> 元素进行错误提醒
		errorElement: "em",
		// 使用 <li> 元素包装错误提醒元素
		wrapper: "li",
		// 将错误提醒元素统一添加到指定元素
		//errorLabelContainer: "#error_messages ul",
		// 自定义错误容器
		errorContainer: "#error_messages, #error_container",
		// 自定义错误提示如何展示
		showErrors: function(errorMap, errorList) {
			$("#error_tips").html("Your form contains " + this.numberOfInvalids() + " errors, see details below.");
			this.defaultShowErrors();
		},
		// 自定义错误提示位置
		errorPlacement: function(error, element) {
			error.insertAfter(element);
//			element.popover({
//			content:error[0].innerHTML
//			});
//			element.click();
//			element.closest('div').removeClass('has-success').addClass('has-error');
		},
		// 单个元素校验通过后处理
		success: function(label, element) {
			console.log(label);
			console.log(element);
//			$(element).parent('div').removeClass('has-error').addclass('has-success');
//			$(element).popover('destroy');
			label.addClass("valid").text("Ok!");
		},
		highlight: function(element, errorClass, validClass) {
			$(element).addClass(errorClass).removeClass(validClass);
			$(element.form).find("label[for=" + element.id + "]").addClass(errorClass);
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).removeClass(errorClass).addClass(validClass);
			$(element.form).find("label[for=" + element.id + "]").removeClass(errorClass);
		},
		//校验通过后的回调，可用来提交表单~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		submitHandler: function(form, event) {
			//console.log($(form).attr("id"));
			//$(form).ajaxSubmit();
			//form.submit();
			login();
			
		},
		//校验未通过后的回调
		invalidHandler: function(event, validator) {
			// 'this' refers to the form
			var errors = validator.numberOfInvalids();
			if (errors) {
				var message = errors == 1 ? 'You missed 1 field. It has been highlighted': 'You missed ' + errors + ' fields. They have been highlighted';
				console.log(message);
			}
		}
	});


function validateRule() {
    //登录验证
    $("#loginForm").validate({
		rules: {
			username: {
				required: true,
				minlength: 2,
				maxlength: 24,	
			},
			password: {
				required: true,
				minlength: 6,
                maxlength: 25
			},
			verifyInput: {
				required: true,
				rangelength: [4,4]    
			}
		},
		//自定义错误提示信息
		messages: {
			username: {
				required: "请输入用户名 / 邮箱",
				minlength: jQuery.validator.format("至少需填写{0}个字符"),
			},
			password: {
            	required: "请输入您的密码",
            },
            verifyInput: {
				required: "请输入验证码",
				rangelength: "验证码长度不正确"
			}
		},
	});
}





function validateKickout() {
	if (getParam("kickout") == 1) {
	    layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
	        icon: 0,
	        title: "系统提示"
	    },
	    function(index) {
	        //关闭弹窗
	        layer.close(index);
	        if (top != self) {
	            top.location = self.location;
	        } else {
	            var url  =  location.search;
	            if (url) {
	                var oldUrl  = window.location.href;
	                var newUrl  = oldUrl.substring(0,  oldUrl.indexOf('?'));
	                self.location  = newUrl;
	            }
	        }
	    });
	}
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}


