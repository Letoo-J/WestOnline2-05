package org.boot.mine.service.impl;

import java.util.HashMap;
import java.util.List;

import org.boot.mine.common.annotation.DataScope;
import org.boot.mine.common.constant.UserConstants;
import org.boot.mine.common.core.text.Convert;
import org.boot.mine.common.exception.BusinessException;
import org.boot.mine.common.utils.StringUtils;
import org.boot.mine.dao.UserMapper;
import org.boot.mine.models.User;
import org.boot.mine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户 业务层处理
 * 
 * @author Letoo
 */
@Service
public class UserServiceImpl implements UserService
{
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private ISysConfigService configService;

    /**
     * 根据条件分页查询用户列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }
    
    /**
     * 查询所有用户(除了自己)
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUser(Integer UID){  //selectAll  selectAllUser
    	return  userMapper.selectAllUser(UID);
    }
    
    /**
     * 游客:查询所有用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUserYou(){
    	return userMapper.selectAllUserYou();
    }
    
    /**
     * 管理员：查询所有非管理员用户
     * 
     * @param 
     * @return 用户集合
     */
    public List<User> selectAllUserNo(){
    	return userMapper.selectAllUserNo();
    }

    
    /**
     * 管理员：通过用户名/邮箱（模糊）查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public List<User> selectUserLikeUserName(String name)
    {
       return userMapper.selectUserLikeUserName(name);
    }
    
    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByUserName(String userName)
    {
       return userMapper.selectUserByUserName(userName);
    }
 

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }
    
    /**
     * 激活对应用户邮箱
     * @param mailCode
     * @return
     */
    @Override
    public int VerifiUserByMaliCode(String mailCode) {
    	return userMapper.VerifiUserByMaliCode(mailCode);
    }
    
    /**
     * 查找激活码对应用户
     * @param mailCode
     * @return
     */
    @Override
    public User selectUserByMaliCode(String mailCode) {
    	return userMapper.selectUserByMaliCode(mailCode);
    }


    
    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Integer userId)
    {
        return userMapper.selectUserById(userId);
    }
    
    /**
     * 通过用户名+邮箱查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserByNM(String userName, String email)
    {
        return userMapper.selectUserByNM(userName,email);
    }
  

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Integer userId)
    {
        //return userMapper.deleteUserById(userId);
        return userMapper.deleteByPrimaryKey(userId);
    }
    
    

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws BusinessException
    {
        Integer[] userIds = Convert.toIntArray(ids);
        for (Integer userId : userIds)
        {
            checkUserAllowed(new User(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(User user)
    {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        return rows;
    }

    /**
     * 注册用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(User user)
    {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改/更新用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(User user)
    {
        Integer userId = user.getUID();
//        // 删除用户与角色关联
//        userRoleMapper.deleteUserRoleByUserId(userId);
//        // 新增用户与角色管理
//        insertUserRole(user.getUserId(), user.getRoleIds());
//        // 删除用户与岗位关联
//        userPostMapper.deleteUserPostByUserId(userId);
//        // 新增用户与岗位管理
//        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    
    /**
     * 修改用户个人详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user)
    {
        return userMapper.updateUser(user);
    }


    /**
     * 修改用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user)
    {
        return updateUserInfo(user);
    }


    /**
     * 校验登录名称是否唯一
     * 
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkUsernameNameUnique(String loginName)
    {
        int count = userMapper.checkUsernameUnique(loginName);
        if (count > 0)
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

  
    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(User user)
    {
        Integer userId = (int) (StringUtils.isNull(user.getUID()) ? -1 : user.getUID());
        User info = userMapper.checkEmailUnique(user.getMailBox());
        if (StringUtils.isNotNull(info) && info.getUID() != userId )
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 校验用户是否允许操作/是否为管理员
     * 
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(User user)
    {
        if (StringUtils.isNotNull(user.getUID()) && user.isAdmin())
        {
            throw new BusinessException("不允许操作超级管理员用户");
        }
    }

    

    
    /**
     * 导入用户数据
     * 
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
//    @Override
//    public String importUser(List<User> userList, Boolean isUpdateSupport, String operName)
//    {
//        if (StringUtils.isNull(userList) || userList.size() == 0)
//        {
//            throw new BusinessException("导入用户数据不能为空！");
//        }
//        int successNum = 0;
//        int failureNum = 0;
//        StringBuilder successMsg = new StringBuilder();
//        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");
//        for (User user : userList)
//        {
//            try
//            {
//                // 验证是否存在这个用户
//                User u = userMapper.selectUserByLoginName(user.getUsername());
//                if (StringUtils.isNull(u))
//                {
//                    user.setPassword(Md5Utils.hash(user.getUsername() + password));
//                    user.setCreateBy(operName);
//                    this.insertUser(user);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUsername() + " 导入成功");
//                }
//                else if (isUpdateSupport)
//                {
//                    user.setUpdateBy(operName);
//                    this.updateUser(user);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUsername() + " 更新成功");
//                }
//                else
//                {
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUsername() + " 已存在");
//                }
//            }
//            catch (Exception e)
//            {
//                failureNum++;
//                String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
//                failureMsg.append(msg + e.getMessage());
//                log.error(msg, e);
//            }
//        }
//        if (failureNum > 0)
//        {
//            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
//            throw new BusinessException(failureMsg.toString());
//        }
//        else
//        {
//            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
//        }
//        return successMsg.toString();
//    }

    /**
     * 用户状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(User user)
    {
        return userMapper.updateUser(user);
    }
    
    /**
     * 用户提问箱状态修改
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int changeOpenStatus(User user) {
    	return userMapper.updateUser(user);
    }
}
