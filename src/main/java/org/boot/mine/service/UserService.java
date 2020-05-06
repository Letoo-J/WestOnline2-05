package org.boot.mine.service;

import java.util.List;
import org.boot.mine.models.User;

/**
 * 用户 业务层接口
 * 
 * @author Letoo
 */
public interface UserService
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUserList(User user);
    
    /**
     * 查询所有用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUser(Integer UID);
    
    /**
     * 游客:查询所有用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUserYou();
    
    
    /**
     * 管理员：查询所有非管理员用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUserNo();


    /**
     * 管理员：通过用户名/邮箱（模糊）查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public List<User> selectUserLikeUserName(String name);
    
    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public User selectUserByUserName(String userName);


    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    public User selectUserByEmail(String email);
    
    /**
     * 激活对应用户邮箱
     * @param mailCode
     * @return
     */
    public int VerifiUserByMaliCode(String mailCode);
    
    /**
     * 查找激活码对应用户
     * @param mailCode
     * @return
     */
    public User selectUserByMaliCode(String mailCode);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public User selectUserById(Integer userId);
    
    /**
     * 通过用户名+邮箱查询用户  好像没啥用...........
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public User selectUserByNM(String userName, String email);


    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Integer userId);

    
    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public int deleteUserByIds(String ids) throws Exception;

    
    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    
    /**
     * 注册用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public boolean registerUser(User user);

    
    /**
     * 修改/更新用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    
    /**
     * 修改用户详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(User user);


    /**
     * 修改用户密码信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(User user);

    
    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkUsernameNameUnique(String loginName);


    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(User user);

    
    /**
     * 校验用户是否允许操作/是否为管理员
     * 
     * @param user 用户信息
     */
    public void checkUserAllowed(User user);


    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    //public String importUser(List<User> userList, Boolean isUpdateSupport, String operName);

    /**
     * 用户状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int changeStatus(User user);
    
    
    /**
     * 用户提问箱状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int changeOpenStatus(User user);
}
