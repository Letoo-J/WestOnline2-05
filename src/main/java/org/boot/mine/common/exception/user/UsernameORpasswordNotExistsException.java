package org.boot.mine.common.exception.user;

/**
 * 用户不存在异常类
 * 
 * @author ruoyi
 */
public class UsernameORpasswordNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UsernameORpasswordNotExistsException()
    {
        super("用户名或密码为空", null);
    }
}
