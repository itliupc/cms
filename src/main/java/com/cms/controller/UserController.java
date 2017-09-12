package com.cms.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cms.config.Constant;
import com.cms.domain.User;
import com.cms.service.UserService;
import com.cms.utils.GridView;
import com.cms.vo.ResponseResult;
import com.cms.vo.UserVo;

@Controller
@RequestMapping("/user-manage")
@Transactional
public class UserController {

	@Autowired
	UserService userService;

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/view/{page}")
	public String loginView(@PathVariable String page) {
		return "users/" + page;
	}

	/**
	 * 查询user信息
	 * 
	 * @return 封装的user list信息
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@Transactional(readOnly = true)
	@ResponseBody
	public GridView<User> userList(@RequestParam Map<String, String> param) {
		Page<User> page = userService.getUserList(param);
		return new GridView<User>(page.getContent(), page.getTotalElements());
	}

	/**
	 * 新建user
	 * 
	 * @param user基本信息
	 * @return 封装的user信息
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult userCreate(UserVo userInfo) {
		User userForCompareName = userService.findByUserName(userInfo
				.getUserName());
		if (null != userForCompareName) {
			return ResponseResult.failure("保存失败,账号已存在!");
		} else {
			User user = new User();
			user.setName(userInfo.getName());
			user.setUserName(userInfo.getUserName());
			user.setStatus(0);
			user.setCreateTime(new Date());
			user.setPassword(new BCryptPasswordEncoder()
					.encode(Constant.DEFAULT_PASSWORD));
			// 暂定设置所有的角色为user
			user.setUserAuthority(1);
			userService.userSave(user);
			return ResponseResult.success(user);
		}
	}

	/**
	 * 更新user
	 * 
	 * @param user基本信息
	 * @return 封装的user信息
	 */
	@RequestMapping(value = "editUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult userModify(UserVo user) {
		User userForCompareName = userService.getOtherUserbyUserName(
				user.getUserName(), user.getUserId());
		if (null != userForCompareName) {
			return ResponseResult.failure("保存失败,账号已存在!");
		} else {
			User userInfo = userService.getUserbyUserId(user.getUserId());

			if (null != user.getUserName()) {
				userInfo.setUserName(user.getUserName());
			}
			if (null != user.getName()) {
				userInfo.setName(user.getName());
			}
			userInfo.setStatus(user.getStatus());
			userInfo.setUpdateTime(new Date());
			userService.userSave(userInfo);

			return ResponseResult.success(userInfo);
		}
	}

	/**
	 * 根据userId删除user信息，实际是将user进行伪删除，更新status字段状态即可
	 * 
	 * @param userId
	 * @return 封装的user信息
	 */
	@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult userDelete(@RequestBody List<Long> ids) {
		userService.deleteUserByIds(ids);
		return ResponseResult.success(true);
	}
	
	/**
	 * 重置密码
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult resetPassword(@RequestBody List<Long> ids) {
		String password = new BCryptPasswordEncoder().encode(Constant.DEFAULT_PASSWORD);
		userService.updatePasswordByUserId(ids,password);
		return ResponseResult.success(true);
	}
	
	/**
	 * 更新status
	 * 
	 * @param user基本信息
	 * @return 封装的user信息
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateStatus(UserVo user) {
		User userInfo = userService.getUserbyUserId(user.getUserId());
		if (1 == userInfo.getStatus()) {
			userService.updateUserStatusByUserId(user.getUserId(), 0);
			return ResponseResult.success("账号已启用！");
		}else{
			userService.updateUserStatusByUserId(user.getUserId(), 1);
			return ResponseResult.success("账号已停用！");
		}
	}
}
