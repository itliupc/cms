package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.domain.User;
import com.cms.repository.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  public User findByUserName(String account) {
    return userRepository.findByUserName(account);
  }
  
  public List<User> getUserList(){
    return userRepository.findAll();
  }
  
  public void userSave(User user){
    userRepository.save(user);
  }
  
  public User getUserbyUserId(long userId){
    return userRepository.findOne(userId);
  }
  
  public void updateUserbyUserId(User user){
    userRepository.save(user);
  }

  public void updateUserStatusByUserId(long userId) {
    userRepository.updateUserStatusByUserId(userId);
  }

  public User getUserbyEmail(String email) {
    return userRepository.getUserbyEmail(email);
  }

  public User getOtherUserbyEmail(String email, long userId) {
    return userRepository.getOtherUserbyEmail(email, userId);
  }

  public User getOtherUserbyUserName(String userName, long userId) {
    return userRepository.getOtherUserbyUserName(userName, userId);
  }
}
