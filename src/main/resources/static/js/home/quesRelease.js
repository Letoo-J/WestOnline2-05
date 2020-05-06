
$(function() {
    validateRule();
    /*$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});*/
});

function quesRelease(){
    var title = $("#title").val();
    $.ajax({
        //encodeURI:对整个url进行编码(若url里面有中文。。)
        //url:要进行请求的资源的地址，用绝对路径.
        url: encodeURI("/home/question/release"+ window.location.search),
        type: "post",  //请求的方法
        cache: false, //去除缓存
        async: false,  //async:false同步。true异步方式
        data: {  //$(form).ser...
            "title": title,
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
				layer.msg("提交失败！");
				$("#title").val("");   //清空输入
			}
			if(data.code==200){
				alert("提交成功！");
				window.location.href ="/home";
			}
        },
        error: function () {
            alert(".......页面出错了.......");
        }
    });
}

jQuery.validator.setDefaults({
		// 仅做校验，不提交表单
		debug: false,
		// 提交表单时做校验
		onfocusin:true,
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
			alert("/home/question/release"+ window.location.search);
			quesRelease();
			
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
	    //验证
	    $("#quesRelease").validate({
			rules: {
				title: {
					required: true,
					minlength: 5,
				}
			},
			//自定义错误提示信息
			messages: {
				titles: {
					required: "问题不能为空！",
					minlength: jQuery.validator.format("问题至少需填写{0}个字符"),
				}
			},
		});
	}