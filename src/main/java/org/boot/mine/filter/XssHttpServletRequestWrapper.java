package org.boot.mine.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.boot.mine.util.JsoupUtil;


/**
 * 	实现XSS过滤的关键
 * 	重写了getParameter，getParameterValues，getHeader等方法
 * 	对http请求内的参数进行了过滤
 * 	@author Lenovo
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {  
    HttpServletRequest orgRequest = null;  
    private boolean isIncludeRichText = false;
  
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {  
        super(request);  
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }  
  
    /** 
    * 	覆盖getParameter方法，将参数名和参数值都做xss过滤。 
    * 	如果需要获得原始的值，则通过super.getParameterValues(name)来获取
    * 	getParameterNames,getParameterValues和getParameterMap也可能需要覆盖 
    */  
    @Override  
    public String getParameter(String name) {  
	    	if(("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText){
	    		return super.getParameter(name);
	    	}
	    	name = JsoupUtil.clean(name);
        String value = super.getParameter(name);  
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value); 
            
        }
        return value;  
    } 
    
    /**
     * 	覆盖getParameterValues方法，将参数名和参数值都做xss过滤。
     * 	如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     */
    @Override
    public String[] getParameterValues(String name) {
    	String[] arr = super.getParameterValues(name);
    	if(arr != null){
    		for (int i=0;i<arr.length;i++) {
    			arr[i] = JsoupUtil.clean(arr[i]);
    		}
    	}
    	return arr;
    }
    
  
    /** 
    * 	覆盖getHeader方法，将参数名和参数值都做xss过滤。
    * 	如果需要获得原始的值，则通过super.getHeaders(name)来获取
    * 	getHeaderNames 也可能需要覆盖 
    */  
    @Override  
    public String getHeader(String name) {  
    		name = JsoupUtil.clean(name);
        String value = super.getHeader(name);  
        if (StringUtils.isNotBlank(value)) {  
            value = JsoupUtil.clean(value); 
        }  
        return value;  
    }  
  
    /** 
    * 	获取最原始的request 
    * 
    * 	@return 
    */  
    public HttpServletRequest getOrgRequest() {  
        return orgRequest;  
    }  
  
    /** 
    * 	获取最原始的request的静态方法 
    * 
    * 	@return 
    */  
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {  
        if (req instanceof XssHttpServletRequestWrapper) {  
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();  
        }  
  
        return req;  
    }  
   
}  
