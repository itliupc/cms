package com.wafer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wafer.domain.Exam;
import com.wafer.repository.base.BaseRepository;

public interface ExamRepository extends BaseRepository<Exam, Long> {

  Exam findByCarId(long carId);
  
  Page<Exam> findAll(Specification<Exam> specification, Pageable pageable);

  @Query(value = "from Exam t where t.carId = :carId and t.id != :id")
  Exam getOtherExamByCarId(@Param("carId") long carId, @Param("id") long id);
  
  @Modifying
  @Query(value = "delete from Exam t where t.id in (:ids)")
  void deleteExamByIds(@Param(value = "ids") List<Long> ids);

}
