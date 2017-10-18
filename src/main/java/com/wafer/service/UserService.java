package com.wafer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wafer.domain.User;
import com.wafer.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  public Page<User> getUserList(Map<String, String> param) {
    Order order = null;
    if (param.containsKey("sort")) {
      order = new Order(
          param.get("order").equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
          param.get("sort"),
          NullHandling.NULLS_LAST);
    } else {
      order = new Order(Sort.Direction.DESC, "userId");
    }
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    final String name = param.containsKey("name")?param.get("name"):null;
    final String userName = param.containsKey("userName")?param.get("userName"):null;
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, new Sort(order));
    return userRepository.findAll(new Specification<User>() {
      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("userAuthority"), 1));
        if(null != name && !name.isEmpty()){
          predicates.add(cb.like(root.<String>get("name"), "%"+name+"%"));
        }
        if(null != userName && !userName.isEmpty()){
          predicates.add(cb.like(root.<String>get("userName"), "%"+userName+"%"));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);
  }

  public void userSave(User user) {
    userRepository.save(user);
  }

  public User getUserbyUserId(long userId) {
    return userRepository.findOne(userId);
  }

  public void updateUserbyUserId(User user) {
    userRepository.save(user);
  }

  public void updateUserStatusByUserId(long userId, int status) {
    userRepository.updateUserStatusByUserId(userId, status);
  }

  public User getOtherUserbyUserName(String userName, long userId) {
    return userRepository.getOtherUserbyUserName(userName, userId);
  }

  public void deleteUserByIds(List<Long> ids) {
    userRepository.deleteUserByIds(ids);
  }

  public void updatePasswordByUserId(List<Long> ids, String password) {
	userRepository.updatePasswordByUserId(ids, password);
  }
}
