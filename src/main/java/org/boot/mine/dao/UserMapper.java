package org.boot.mine.dao;

import java.util.List;
import org.boot.mine.models.User;

/**
 * 用户表 数据层
 * 
 * @author Letoo
 */
public interface UserMapper {
	/**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
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
    //查询所有用户
    List<User> selectAll();
    
    /**
     * 游客:查询所有用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUserYou();

    
    /**
     * 管理员：通过用户名/邮箱(模糊)查询用户
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
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public User selectUserById(Integer userId);
    
    /**
     * 通过用户名+邮箱查询用户
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
    //int deleteByPrimaryKey(Integer UID);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(Integer[] ids);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param userName 登录名称
     * @return 结果
     */
    public int checkUsernameUnique(String userName);


    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public User checkEmailUnique(String email);
    
    
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
     * 管理员：查询所有非管理员用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUserNo();
    	
    
    //按照主键删除用户
    int deleteByPrimaryKey(Integer UID);

    //插入一个新用户
    int insert(User record);

    //按照主键查询
    User selectByPrimaryKey(Integer UID);

   

    //按照主键更新
    int updateByPrimaryKey(User record);

	

	
}