package com.cms.repository;

import java.util.List;

import com.cms.domain.Module;
import com.cms.repository.base.BaseRepository;

public interface ModuleRepository extends BaseRepository<Module, Long> {
  
  List<Module> findByModuleNameLike(String moduleName);
  
  List<Module> findByProjectId(long projectId);
  
  List<Module> findByProjectIdAndModuleNameLike(long projectId, String moduleName);

  List<Module> findModuleByProjectIdAndIsRun(long projectId, int running); 
}
