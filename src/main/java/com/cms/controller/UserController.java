package com.cms.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cms.domain.User;
import com.cms.service.UserService;
import com.cms.vo.ResponseResult;
import com.cms.vo.UserVo;

@Controller
@RequestMapping("/users")
@Transactional
public class UserController {

  @Autowired
  UserService userService;
  
  Logger logger = LoggerFactory.getLogger(UserController.class);

  
  @RequestMapping(value = "/index")
  public String loginView() {
    return "users/index";
  }
  
  

  /**
   * 查询user信息
   * @return 封装的user list信息
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  @Transactional(readOnly = true)
  public ResponseResult userList(){
    return ResponseResult.success(false);
  }
  
  /**
   * 新建user
   * @param user基本信息
   * @return 封装的user信息
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseResult userCreate(@RequestBody UserVo userInfo) {
    User userForCompareName = userService.findByUserName(userInfo.getUserName());
    User userForCompareEmail = userService.getUserbyEmail(userInfo.getEmail());
    if (null != userForCompareName) {
      return ResponseResult.failure("");
    } else if (null != userForCompareEmail) {
      return ResponseResult.failure("");
    } else {
      User user = new User();
      user.setUserName(userInfo.getUserName());
      user.setCreateTime(new Date());
      user.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
      // 暂定设置所有的角色为user
      user.setUserAuthority(1);
      userService.userSave(user);


      return ResponseResult.success(false);
    }
  }
  
  /**
   * 根据userId删除user信息，实际是将user进行伪删除，更新status字段状态即可
   * @param userId
   * @return 封装的user信息
   */
  @RequestMapping(value = "", method = RequestMethod.DELETE)
  public ResponseResult userDelete(@PathVariable long userId){
    
    userService.updateUserStatusByUserId(userId);
    
    return ResponseResult.success(false);
  }
  
  /**
   * 更新user
   * @param user基本信息
   * @return 封装的user信息
   */
  @RequestMapping(value = "", method = RequestMethod.PUT)
  public ResponseResult userModify(@RequestBody UserVo user) {
    User userForCompareName =
        userService.getOtherUserbyUserName(user.getUserName(), user.getUserId());
    User userForCompareEmail = userService.getOtherUserbyEmail(user.getEmail(), user.getUserId());
    if (null != userForCompareName) {
      return ResponseResult.failure("");
    } else if (null != userForCompareEmail) {
      return ResponseResult.failure("");
    } else {
      User userInfo = userService.getUserbyUserId(user.getUserId());

      if (null != user.getUserName()) {
        userInfo.setUserName(user.getUserName());
      }
      userInfo.setUpdateTime(new Date());
      userService.userSave(userInfo);


      return ResponseResult.success(false);
    }
  }
}

