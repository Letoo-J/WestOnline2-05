package org.boot.mine.controller;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.boot.mine.util.RSAUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RSAController {
	
	//声明加密秘钥
	static String privateKey;
	static String publicKey;
	//声明加密模块
	static String modulus; 
	
	
	//2
	//调用RSA工具类的getModulus方法获取配套的公钥，秘钥，和加密模块信息，并将公钥和加密模块传送到前台，秘钥和加密模块
	//保存到后台
		@ResponseBody
		@RequestMapping(value = "/getMoudle", method = RequestMethod.POST)
		public Object getMoudle() throws NoSuchAlgorithmException {
			Map jmInfo = null;
			try {
				jmInfo = RSAUtils.getModulus();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("模块："+ jmInfo);
			
	    	privateKey = (String) jmInfo.get("m");
	    	publicKey = (String) jmInfo.get("g");
	    	modulus = (String) jmInfo.get("modu");
	    	
	    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
	    	resultMap.put("publicKey",publicKey);
	    	resultMap.put("modulus",modulus);
	    	
			return resultMap;
		}

}
