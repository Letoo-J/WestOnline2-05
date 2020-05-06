package org.boot.mine.vo;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Result extends HashMap<String, Object> {
	
	//构造私有化
	private Result() { } //禁止被new调用 , 只可使用Result.of(xxx)....
	
	public static Result of(int code) {
		Result er = new Result();
		er.put("code", code);
		return er;
	}
	public static Result of(int code,String msg) {
		Result er = new Result();
		er.put("code", code);
		er.put("msg", msg);
		return er;
	}
	public static Result of(int code,String msg,Object data) {
		Result er = new Result();
		er.put("code", code);
		er.put("msg", msg);
		er.put("data", data);
		return er;
	}
	
	//返回自己(Result):可以连续put!
	//Rusult(code,msg).put(xx,xx).put(xx,xx)...;
	public Result put(String key,Object value) {
		super.put(key, value);
		return this;
	}
	
}
