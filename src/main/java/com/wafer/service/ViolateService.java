package com.wafer.service;

import java.util.ArrayList;
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
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wafer.domain.Car;
import com.wafer.domain.Violate;
import com.wafer.repository.ViolateRepository;

@Service
public class ViolateService {

  @Autowired
  private ViolateRepository violateRepository;

  public Violate findByCarId(long carId) {
    return violateRepository.findByCarId(carId);
  }

  public Page<Violate> getViolateList(Map<String, String> param) {
    Order order = null;
    if (param.containsKey("sort")) {
      order = new Order(
          param.get("order").equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
          param.get("sort"),
          NullHandling.NULLS_LAST);
    } else {
      order = new Order(Sort.Direction.DESC, "id");
    }
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    final String operateNum = param.containsKey("operateNum") ? param.get("operateNum") : null;
    final String carNum = param.containsKey("carNum") ? param.get("carNum") : null;
    final String hasDeal = param.containsKey("hasDeal") ? param.get("hasDeal") : null;
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, new Sort(order));
    return violateRepository.findAll(new Specification<Violate>() {
      @Override
      public Predicate toPredicate(Root<Violate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Violate,Car> join = root.join("car", JoinType.LEFT);
        if (null != operateNum && !operateNum.isEmpty()) {
          predicates.add(cb.like(join.<String>get("operateNum"), "%" + operateNum + "%"));
        }
        if (null != carNum && !carNum.isEmpty()) {
          predicates.add(cb.like(join.<String>get("carNum"), "%" + carNum + "%"));
        }
        if (null != hasDeal && !hasDeal.isEmpty()) {
          if ("0".equals(hasDeal)) {// 未缴费
            predicates.add(cb.equal(root.get("hasDeal"), "0"));
          } else {// 已缴费
            predicates.add(cb.equal(root.get("hasDeal"), "1"));
          }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);
  }

  public void violateSave(Violate violate) {
    violateRepository.save(violate);
  }

  public Violate getViolateById(long id) {
    return violateRepository.findOne(id);
  }

  public void updateViolateById(Violate violate) {
    violateRepository.save(violate);
  }

  public Violate getOtherViolateByCarId(long carId, long id) {
    return violateRepository.getOtherViolateByCarId(carId, id);
  }

  public void deleteViolateByIds(List<Long> ids) {
    violateRepository.deleteViolateByIds(ids);
  }

  public List<Violate> getViolateListByCarId(Long carId) {
    return violateRepository.findViolateByCarId(carId);
  }
}
