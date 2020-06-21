
$(function() {
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


function register(){
    var username = $("#username").val();
    var password11 = $("#password1").val();
    var password22 = $("#password2").val();
    var mail = $("#mail").val();
    var verifyInput = $("#verifyInput").val();
    
    //获取模块：
    getModule();
    var encryptPassword1 = encryption(password11);
    var encryptPassword2 = encryption(password22);
    $('#password1').val(encryptPassword1); //填充表单
    $('#password2').val(encryptPassword2); //填充表单
    
    $.ajax({
        //encodeURI:对整个url进行编码(若url里面有中文。。)
        //url:要进行请求的资源的地址，用绝对路径.
        url: encodeURI("/register"),
        type: "post",  //请求的方法
        cache: false, //去除缓存
        async: true,  //async:false同步。true异步方式
        data: {  //$(form).ser...
            "username": username,
            "encryptPassword1": encryptPassword1,
            "encryptPassword2": encryptPassword2,
            "mail": mail,
            "verifyInput": verifyInput,   //验证码
        },
        dataType: "json",  //指定响应得到的数据类型：【“list”必须严格为一个json数据！】
        success: function (data) {  //发起这个ajax请求后成功得到响应（响应内容为msg!）之后,去调用此函数进行处理
            //$("#msg").append(msg); //把服务端（AjaxServlet）响应的数据（“xxx”）追加到页面【<div id="msg"></div>】处。
            // $.each(list, function (index, as) {
            //     var idenCode = as.idenCode;
            //     var name = as.name;
            //     var str = "<option value=" + idenCode + ">" + idenCode +": "+ name + "</option>";
            //     $("#idenCode").append(str);
            //location.href = '/index';  //成功则跳转到用户首页
			if(data.code!=200){
				layer.msg(data.msg);
				changeCode();
				$("#verifyInput").val("");   //清空验证码输入
            	$("#password1").val("");   //清空密码输入
            	$("#password2").val("");   //清空密码输入
			}
			if(data.code==200){
				alert(data.msg);
				window.location.href ="/login";
			}
        },
        error: function () {
            alert(".......页面出错了.......");
        }
    });
}

//邮箱格式验证
jQuery.validator.addMethod("isMail",
		function(value, element) {   
		// /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
		var tel =/^([a-zA-Z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$/;
		return this.optional(element) || (tel.test(value));
	}, "请正确填写邮箱");

//密码验证正则表达式
jQuery.validator.addMethod("VerifyPwd", function(value, element, param){
    //方法中又有三个参数:value:被验证的值， element:当前验证的dom对象，param:参数(多个即是数组)
    //alert(value + "," + $(element).val() + "," + param[0] + "," + param[1]);
	var tel2 = /^(?=.*[0-9])(?=.*[a-zA-Z]).{6,25}$/;
    return new RegExp(tel2).test(value);
}, "密码中必须包含字母、数字");


jQuery.validator.setDefaults({
		// 仅做校验，不提交表单
		debug: true,
		//点击时验证  
		onclick:true,
		//失去焦点时验证
		//onfocusin:true,
		// 提交表单时做校验(true)
		onsubmit: true,
		//不需要每次键入就验证
		//onkeyup: false
		// 焦点自动定位到第一个无效元素
		focusInvalid: true,
		// 元素获取焦点时清除错误信息
		focusCleanup: false,   //true
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
			error.insertAfter(element);  // error.appendTo();
//			element.popover({
//			content:error[0].innerHTML
//			});
//			element.click();
//			element.closest('div').removeClass('has-success').addClass('has-error');
			if(element.is($("input[id='password1']").eq(0))){  //若为password1验证失败，则隐藏密码强弱提示
				$('#passstrength').hide();
			}
		},
		// 单个元素校验通过后处理
		success: function(label, element) {
			console.log(label);
			console.log(element);
//			$(element).closest('div').removeClass('has-success').addclass('has-error');
//			$(element).popover('destroy');
			if($('#password1').attr("name") == element.name){  //若为password1验证成功，则去除提示信息的元素
				$('#passstrength').show();
				$('#password1-error').parent().remove();
			}
			label.addClass("valid").text("Ok!");
			//alert(element.name);
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
			register();
			
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
	    //注册验证
	    $("#signupForm").validate({
			rules: {
				username: {
					required: true,
					minlength: 2,
					maxlength: 24,	
					remote:{   
						url: "/register/validateUsername", //后台处理程序    
                        type: "post",  //数据发送方式   
                        dataType: "json",       //接受数据格式       
                        data:  {                     //要传递的数据   
                        	username: function() {   
                        		return $("#username").val();   
                        	}   
                        }            
                    },
				},
				password1: {
					required: true,
					minlength: 6,
	                maxlength: 25,
	                VerifyPwd: true,
				},
				password2: {
					required: true,
					equalTo: "#password1"
				},
				mail: {
					required: true,
					email: true,
					isMail: true,
					remote:{   
						url: "/register/validateEMail", //后台处理程序    
                        type: "post",  //数据发送方式   
                        dataType: "json",       //接受数据格式       
                        data:  {                     //要传递的数据   
                        	mail: function() {   
                        		return $("#mail").val();   
                        	}   
                        }            
                    },
				},
				verifyInput: {
					required: true,
					rangelength: [4,4]    
				}
			},
			//自定义错误提示信息
			messages: {
				username: {
					required: "请输入用户名",
					minlength: jQuery.validator.format("用户名至少需填写{0}个字符"),
					maxlength: jQuery.validator.format("用户名最多填写{0}个字符"),
					remote: "该用户名已经存在！",
				},
				mail:{
					required:"邮箱不能为空",
					email:"请正确填写邮箱",
					isMail: "请填写正确格式邮箱",
					remote: "该邮箱已经存在！",
				},
				password1: {
	            	required: "请输入您的密码",
	            },
	            password2: {
	                required: "请再次输入您的密码",
	                equalTo: "两次密码输入不一致"
	            },
	            verifyInput: {
					required: "请输入验证码",
					rangelength: "验证码长度不正确"
				}
			},
		});
	}
	
	
	$('#password1').keyup(function(e) {  //keyup
		 //密码中必须包含大小写 字母、数字、特称字符，至少8个字符，最多30个字符
	     var strongRegex = new RegExp('(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,25}');
	     //密码中必须包含字母（不区分大小写）、数字、特称字符，至少8个字符，最多30个字符
	     var mediumRegex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,25}');

	     var enoughRegex = new RegExp(/^(?=.*[0-9])(?=.*[a-zA-Z]).{6,25}$/);
	     if (false == enoughRegex.test($(this).val())) {
	             //$('#passstrength').html('More Characters');
	     } else if (strongRegex.test($(this).val())) {
	             $('#passstrength').className = 'ok';
	             $('#passstrength').html('强');
	     } else if (mediumRegex.test($(this).val())) {
	             $('#passstrength').className = 'alert';
	             $('#passstrength').html('中');
	             //$('#password1-error').append('中');
	     } else {
	             $('#passstrength').className = 'error';
	             $('#passstrength').html('弱');
	             //$('#password1-error').append('弱');
	     }
	     return true;
	});
	