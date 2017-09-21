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
import com.wafer.domain.Gps;
import com.wafer.repository.GpsRepository;
import com.wafer.utils.DateUtils;

@Service
public class GpsService {

  @Autowired
  private GpsRepository gpsRepository;

  public Gps findByCarId(long carId) {
    return gpsRepository.findByCarId(carId);
  }

  public Page<Gps> getGpsList(Map<String, String> param) {
    Sort sort = new Sort(Sort.Direction.DESC, "id");
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    final String operateNum = param.containsKey("operateNum") ? param.get("operateNum") : null;
    final String carNum = param.containsKey("carNum") ? param.get("carNum") : null;
    final String deadline = param.containsKey("deadline") ? param.get("deadline") : null;
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
    return gpsRepository.findAll(new Specification<Gps>() {
      @Override
      public Predicate toPredicate(Root<Gps> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Gps, Car> join = root.join("car", JoinType.LEFT);
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

  public void gpsSave(Gps gps) {
    gpsRepository.save(gps);
  }

  public Gps getGpsById(long id) {
    return gpsRepository.findOne(id);
  }

  public void updateGpsById(Gps gps) {
    gpsRepository.save(gps);
  }

  public Gps getOtherGpsByCarId(long carId, long id) {
    return gpsRepository.getOtherGpsByCarId(carId, id);
  }

  public void deleteGpsByIds(List<Long> ids) {
    gpsRepository.deleteGpsByIds(ids);
  }
}
