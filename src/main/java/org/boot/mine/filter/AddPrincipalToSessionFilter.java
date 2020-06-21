package org.boot.mine.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.boot.mine.models.User;
import org.boot.mine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 解决session丢失
 */
public class AddPrincipalToSessionFilter extends OncePerRequestFilter {
    @Autowired
    UserService _userService;

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        //查询当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        //判断用户是不是通过自动登录进来的
        if (subject.isRemembered()) {
            User user2=(User)subject.getPrincipal();
            String userName = user2.getUsername();
            if(userName==null){
                return;
            }
            
            //根据用户名查询该用户的信息
            User user=_userService.selectUserByUserName(userName);
            
            if (user == null) {
            	return;
            }
            //由于是继承的OncePerRequestFilter，没办法设置session
            //这里发现可以将servletReques强转为子类，所以使用request.getsiion())
            HttpServletRequest request=(HttpServletRequest) servletRequest;
            HttpSession session = request.getSession();

            //当session为空的时候
            if (session.getAttribute("user")==null){
                //把查询到的用户信息设置为session，时效为3600秒
                session.setAttribute("user",user);
                session.setMaxInactiveInterval(3600);
                //System.out.println("remember-user: " + user);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
