package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wafer.domain.Car;
import com.wafer.repository.base.BaseRepository;

public interface CarRepository extends BaseRepository<Car, Long> {

  Car findByOperateNum(String operateNum);
  
  Page<Car> findAll(Specification<Car> specification, Pageable pageable);

  @Query(value = "from Car c where c.operateNum = :operateNum and c.id != :id")
  Car getOtherCarByOperateNum(@Param("operateNum") String operateNum, @Param("id") long id);
  
  @Modifying
  @Transactional 
  @Query(value = "delete from Car c where c.id in (:ids)")
  void deleteCarByIds(@Param(value = "ids") List<Long> ids);

}
