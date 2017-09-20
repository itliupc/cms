package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.domain.Violate;
import com.wafer.repository.base.BaseRepository;

public interface ViolateRepository extends BaseRepository<Violate, Long> {

  Violate findByCarId(long carId);
  
  Page<Violate> findAll(Specification<Violate> specification, Pageable pageable);

  @Query(value = "from Violate vio where vio.carId = :carId and vio.id != :id")
  Violate getOtherViolateByCarId(@Param("carId") long carId, @Param("id") long id);
  
  @Modifying
  @Query(value = "delete from Violate vio where vio.id in (:ids)")
  void deleteViolateByIds(@Param(value = "ids") List<Long> ids);

}
