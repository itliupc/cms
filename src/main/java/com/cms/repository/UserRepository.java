package com.cms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cms.domain.User;
import com.cms.repository.base.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {

  User findByUserName(String userName);
  
  Page<User> findAll(Specification<User> specification, Pageable pageable);

  @Modifying
  @Query(value = "Update User u set u.status = :status where u.userId = :userId")
  void updateUserStatusByUserId(@Param("userId") long userId, @Param("status") int status);

  @Query(value = "from User u where u.userName = :userName and userId != :userId")
  User getOtherUserbyUserName(@Param("userName") String userName, @Param("userId") long userId);
  
  @Modifying
  @Query(value = "delete from User u where u.userId in (:ids)")
  void deleteUserByIds(@Param(value = "ids") List<Long> ids);

  @Modifying
  @Query(value = "Update User u set u.password = :password where u.userId in (:ids)")
  void updatePasswordByUserId(@Param(value = "ids") List<Long> ids, @Param(value = "password") String password);
}
