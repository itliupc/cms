package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.domain.Manage;
import com.wafer.repository.base.BaseRepository;

public interface ManageRepository extends BaseRepository<Manage, Long> {

  Manage findByCarId(long carId);
  
  Page<Manage> findAll(Specification<Manage> specification, Pageable pageable);

  @Query(value = "from Manage t where t.carId = :carId and t.id != :id")
  Manage getOtherManageByCarId(@Param("carId") long carId, @Param("id") long id);
  
  @Modifying
  @Query(value = "delete from Manage t where t.id in (:ids)")
  void deleteManageByIds(@Param(value = "ids") List<Long> ids);

}
