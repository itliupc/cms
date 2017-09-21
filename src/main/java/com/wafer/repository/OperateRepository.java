package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.domain.Operate;
import com.wafer.repository.base.BaseRepository;

public interface OperateRepository extends BaseRepository<Operate, Long> {

  Operate findByCarId(long carId);
  
  Page<Operate> findAll(Specification<Operate> specification, Pageable pageable);

  @Query(value = "from Operate t where t.carId = :carId and g.id != :id")
  Operate getOtherOperateByCarId(@Param("carId") long carId, @Param("id") long id);
  
  @Modifying
  @Query(value = "delete from Operate t where t.id in (:ids)")
  void deleteOperateByIds(@Param(value = "ids") List<Long> ids);

}
