package com.cms.repository;

import java.util.List;

import com.cms.domain.InterfaceTestCase;
import com.cms.repository.base.BaseRepository;

public interface InterfaceTestCaseRepository extends BaseRepository<InterfaceTestCase, Long> {
  
  public List<InterfaceTestCase> findByInterfaceId(long faceId);
  
  public List<InterfaceTestCase> findByInterfaceIdOrderByInterfaceTestCaseIdDesc(long faceId);
  
  public List<InterfaceTestCase> findByInterfaceIdAndIsRun(long faceId, int isRun);
  
}
