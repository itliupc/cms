package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.domain.Insure;
import com.wafer.repository.base.BaseRepository;

public interface InsureRepository extends BaseRepository<Insure, Long> {

  Insure findByOperateNum(String operateNum);
  
  Page<Insure> findAll(Specification<Insure> specification, Pageable pageable);

  @Query(value = "from Insure ins where ins.operateNum = :operateNum and ins.id != :id")
  Insure getOtherInsureByOperateNum(@Param("operateNum") String operateNum, @Param("id") long id);
  
  @Modifying
  @Query(value = "delete from Insure ins where ins.id in (:ids)")
  void deleteInsureByIds(@Param(value = "ids") List<Long> ids);

}
