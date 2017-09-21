package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.domain.Gps;
import com.wafer.repository.base.BaseRepository;

public interface GpsRepository extends BaseRepository<Gps, Long> {

  Gps findByCarId(long carId);
  
  Page<Gps> findAll(Specification<Gps> specification, Pageable pageable);

  @Query(value = "from Gps g where g.carId = :carId and g.id != :id")
  Gps getOtherGpsByCarId(@Param("carId") long carId, @Param("id") long id);
  
  @Modifying
  @Query(value = "delete from Gps g where g.id in (:ids)")
  void deleteGpsByIds(@Param(value = "ids") List<Long> ids);

}
