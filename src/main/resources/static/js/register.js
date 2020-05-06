
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

/*function register() {
	$.modal.loading($("#btnSubmit").data("loading"));
	var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    $.ajax({
        type: "post",
        url: ctx + "register",
        data: {
            "loginName": username,
            "password": password,
            "validateCode": validateCode
        },
        success: function(r) {
            if (r.code == 0) {
            	layer.alert("<font color='red'>恭喜你，您的账号 " + username + " 注册成功！</font>", {
        	        icon: 1,
        	        title: "系统提示"
        	    },
        	    function(index) {
        	        //关闭弹窗
        	        layer.close(index);
        	        location.href = ctx + 'login';
        	    });
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}*/
function register(){
    var username = $("#username").val();
    var password11 = $("#password1").val();
    var password22 = $("#password2").val();
    var mail = $("#mail").val();
    var verifyInput = $("#verifyInput").val();
    
    var password1 = $.base64.encode(password11);
    var password2 = $.base64.encode(password22);
    
    $.ajax({
        //encodeURI:对整个url进行编码(若url里面有中文。。)
        //url:要进行请求的资源的地址，用绝对路径.
        url: encodeURI("/register"),
        type: "post",  //请求的方法
        cache: false, //去除缓存
        async: false,  //async:false同步。true异步方式
        data: {  //$(form).ser...
            "username": username,
            "password1": password1,
            "password2": password2,
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

jQuery.validator.addMethod("isMail",
		function(value, element) {   
		// /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
			var tel =/^([a-zA-Z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$/;
		//var tell =/^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?/;
		    return this.optional(element) || (tel.test(value));
		}, "请正确填写邮箱");

jQuery.validator.setDefaults({
		// 仅做校验，不提交表单
		debug: true,
		// 提交表单时做校验
		onfocusin:true,
		onsubmit: true,
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
//			$(element).closest('div').removeClass('has-success').addclass('has-error');
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
					maxlength: 24
//					remote:{
//	                    url:"ajax_check.action",
//	                    type:"post"
//	                }
				},
				password1: {
					required: true,
					minlength: 6,
	                maxlength: 25
				},
				password2: {
					required: true,
					equalTo: "#password1"
				},
				mail: {
					required: true,
					isMail: true,
					/*remote: {
						url: "http://localhost:8080/register",
						type: "post",
						dataType: "json",
						data: {
							email: $("#mail").val()
						}
					}*/
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
					maxlength: jQuery.validator.format("用户名最多填写{0}个字符")
				},
				mail:{
					required:"邮箱不能为空",
					isMail: "请正确填写邮箱"
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