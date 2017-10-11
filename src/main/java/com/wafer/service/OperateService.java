package com.wafer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wafer.domain.Car;
import com.wafer.domain.Operate;
import com.wafer.repository.OperateRepository;
import com.wafer.utils.DateUtils;

@Service
public class OperateService {

  @Autowired
  private OperateRepository operateRepository;

  public Operate findByCarId(long carId) {
    return operateRepository.findByCarId(carId);
  }

  public Page<Operate> getOperateList(Map<String, String> param) {
    Sort sort = null;
    if (param.containsKey("sort")) {
      sort = new Sort(
          param.get("order").equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
          param.get("sort"));
    } else {
      sort = new Sort(Sort.Direction.DESC, "id");
    }
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    final String operateNum = param.containsKey("operateNum") ? param.get("operateNum") : null;
    final String carNum = param.containsKey("carNum") ? param.get("carNum") : null;
    final String deadline = param.containsKey("deadline") ? param.get("deadline") : null;
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
    return operateRepository.findAll(new Specification<Operate>() {
      @Override
      public Predicate toPredicate(Root<Operate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Operate, Car> join = root.join("car", JoinType.LEFT);
        if (null != operateNum && !operateNum.isEmpty()) {
          predicates.add(cb.like(join.<String>get("operateNum"), "%" + operateNum + "%"));
        }
        if (null != carNum && !carNum.isEmpty()) {
          predicates.add(cb.like(join.<String>get("carNum"), "%" + carNum + "%"));
        }
        if (null != deadline && !deadline.isEmpty()) {
          if ("1".equals(deadline)) {// 即将过期
            Date afterDate = DateUtils.formatSqlDate(2);
            Date nowDate = DateUtils.formatSqlDate(0);
            predicates.add(cb.between(root.<Date>get("endDate"), nowDate, afterDate));
          } else if ("2".equals(deadline)) {// 已经过期
            Date nowDate = DateUtils.formatSqlDate(0);
            predicates.add(cb.lessThan(root.<Date>get("endDate"), nowDate));
          }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);
  }

  public void operateSave(Operate operate) {
    operateRepository.save(operate);
  }

  public Operate getOperateById(long id) {
    return operateRepository.findOne(id);
  }

  public void updateOperateById(Operate operate) {
    operateRepository.save(operate);
  }

  public Operate getOtherOperateByCarId(long carId, long id) {
    return operateRepository.getOtherOperateByCarId(carId, id);
  }

  public void deleteOperateByIds(List<Long> ids) {
    operateRepository.deleteOperateByIds(ids);
  }
}
