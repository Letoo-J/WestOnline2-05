package org.boot.mine.service;

import java.util.Map;

public interface RegisterService {
	
	public Map<String, Object> register(String username, String password1,
			String password2,String mail,String verifyInput);

}
