package com.wafer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
import com.wafer.repository.CarRepository;

@Service
public class CarService {

  @Autowired
  private CarRepository carRepository;

  public Car findByOperateNum(String operateNum) {
    return carRepository.findByOperateNum(operateNum);
  }

  public Page<Car> getCarList(Map<String, String> param) {
    Sort sort = new Sort(Sort.Direction.DESC, "id");
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    final String operateNum = param.containsKey("operateNum") ? param.get("operateNum") : null;
    final String carNum = param.containsKey("carNum") ? param.get("carNum") : null;
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
    return carRepository.findAll(new Specification<Car>() {
      @Override
      public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (null != operateNum && !operateNum.isEmpty()) {
          predicates.add(cb.like(root.<String>get("operateNum"), "%" + operateNum + "%"));
        }
        if (null != carNum && !carNum.isEmpty()) {
          predicates.add(cb.like(root.<String>get("carNum"), "%" + carNum + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    }, pageable);
  }

  public void carSave(Car car) {
    carRepository.save(car);
  }

  public Car getInsureById(long id) {
    return carRepository.findOne(id);
  }

  public void updateInsureById(Car car) {
    carRepository.save(car);
  }

  public Car getOtherCarByOperateNum(String operateNum, long id) {
    return carRepository.getOtherCarByOperateNum(operateNum, id);
  }

  public void deleteCarByIds(List<Long> ids) {
    carRepository.deleteCarByIds(ids);
  }
  
  @SuppressWarnings("rawtypes")
  public List getDateBySql() {
    String sql =
        "SELECT c.* FROM ps_car c LEFT JOIN ps_insure1 ins ON c.id = ins.car_id WHERE ins.id IS NULL";
    return carRepository.listBySql(sql);
  }
}
