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
import com.wafer.domain.Insure;
import com.wafer.repository.InsureRepository;
import com.wafer.utils.DateUtils;

@Service
public class InsureService {

  @Autowired
  private InsureRepository insureRepository;

  public Insure findByCarId(long carId) {
    return insureRepository.findByCarId(carId);
  }

  public Page<Insure> getInsureList(Map<String, String> param) {
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
    return insureRepository.findAll(new Specification<Insure>() {
      @Override
      public Predicate toPredicate(Root<Insure> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Insure,Car> join = root.join("car", JoinType.LEFT);
        if (null != operateNum && !operateNum.isEmpty()) {
          predicates.add(cb.like(join.<String>get("operateNum"), "%" + operateNum + "%"));
        }
        if (null != carNum && !carNum.isEmpty()) {
          predicates.add(cb.like(join.<String>get("carNum"), "%" + carNum + "%"));
        }
        if (null != deadline && !deadline.isEmpty()) {
          if ("0".equals(deadline)) {// 未领取
            predicates.add(cb.equal(root.get("outBuy"), "1"));
          } else if ("1".equals(deadline)) {// 未领取
            predicates.add(cb.equal(root.get("hasReceive"), "0"));
          } else if ("2".equals(deadline)) {// 未缴费
            predicates.add(cb.equal(root.get("hasPay"), "0"));
          } else if ("3".equals(deadline)) {// 即将过期
            Date afterDate = DateUtils.formatSqlDate(2);
            Date nowDate = DateUtils.formatSqlDate(0);
            predicates.add(cb.or(cb.between(root.<Date>get("busInsure"), nowDate, afterDate),
                cb.between(root.<Date>get("forceInsure"), nowDate, afterDate)));
          } else if ("4".equals(deadline)) {// 已经过期
            Date nowDate = DateUtils.formatSqlDate(0);
            predicates.add(cb.or(cb.lessThan(root.<Date>get("busInsure"), nowDate),
                cb.lessThan(root.<Date>get("forceInsure"), nowDate)));
          }
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);
  }

  public void insureSave(Insure insure) {
    insureRepository.save(insure);
  }

  public Insure getInsureById(long id) {
    return insureRepository.findOne(id);
  }

  public void updateInsureById(Insure insure) {
    insureRepository.save(insure);
  }

  public Insure getOtherInsureByCarId(long carId, long id) {
    return insureRepository.getOtherInsureByCarId(carId, id);
  }

  public void deleteInsureByIds(List<Long> ids) {
    insureRepository.deleteInsureByIds(ids);
  }
}
