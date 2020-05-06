package org.boot.mine.shiro;


import org.apache.shiro.authc.AuthenticationToken;

public class MyToken implements AuthenticationToken {


    private String verifyInput;//新增的校验因子
    
    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    //verifyInput
    public String getVerifyInput() {
		return verifyInput;
	}
	public void setVerifyInput(String verifyInput) {
		this.verifyInput = verifyInput;
	}
	
	//username
	public void setUsername(String username) {
        this.username = username;
    }
	public String getUsername() {
        return username;
    }
	
	//password
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public Object getPrincipal() {
        return getUsername();
    }
    public Object getCredentials() {
        return getPassword();
    }
    

    public MyToken() {}
    public MyToken(final String username, final String password,
                                 final String verifyInput) {

        this.username = username;
        this.password = password;
        this.verifyInput = verifyInput;
    }

}

