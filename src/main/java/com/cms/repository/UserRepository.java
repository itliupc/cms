package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cms.domain.User;
import com.cms.repository.base.BaseRepository;
import com.cms.vo.UserVo;

public interface UserRepository extends BaseRepository<User, Long> {

  User findByUserName(String userName);

  @Modifying
  @Query(value = "Update User u set u.status = 1 where u.userId = :userId")
  void updateUserStatusByUserId(@Param("userId") long userId);

  @Query(value = "from User u where u.email = :email")
  User getUserbyEmail(@Param("email") String email);

  @Query(value = "from User u where u.email = :email and userId != :userId")
  User getOtherUserbyEmail(@Param("email") String email, @Param("userId") long userId);
  
  @Query(value = "from User u where u.userName = :userName and userId != :userId")
  User getOtherUserbyUserName(@Param("userName") String userName, @Param("userId") long userId);
}
