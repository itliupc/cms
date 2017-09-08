package com.cms.repository;

import java.util.List;

import com.cms.domain.DeptUser;
import com.cms.repository.base.BaseRepository;

public interface DeptUserRepository extends BaseRepository<DeptUser, Long> {

  DeptUser getDeptUserByUserId(long userId);
  
  List<DeptUser> getDeptUserByDeptId(long userId);

  void removeDeptUserByUserId(long userId);
  
}
