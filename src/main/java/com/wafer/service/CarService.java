package com.wafer.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wafer.domain.Car;
import com.wafer.repository.CarRepository;
import com.wafer.vo.CarSummaryVo;

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

  public Car getCarById(long id) {
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

  @SuppressWarnings("unchecked")
  public List<CarSummaryVo> getCarSummaryList(Map<String, String> param) {
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    String searchText = param.containsKey("searchText") ? param.get("searchText") : null;

    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT c.id, c.car_num carNum, c.operate_num operateNum, ");
    sql.append("     c.owner_name ownerName, c.owner_phone ownerPhone, ");
    sql.append("     i.force_insure forceInsure, i.bus_insure busInsure, ");
    sql.append("     i.out_buy outBuy, i.has_receive hasReceive, i.has_pay hasPay, ");
    sql.append("     o.end_date operateDate, g.end_date gpsDate, ");
    sql.append("     v.violateNum, e.end_date examDate, m.end_date manageDate ");
    sql.append(" FROM ps_car c ");
    sql.append(" LEFT JOIN ps_insure i ");
    sql.append(" ON c.id = i.car_id ");
    sql.append(" LEFT JOIN ps_operate o ");
    sql.append(" ON c.id = o.car_id ");
    sql.append(" LEFT JOIN ps_gps g ");
    sql.append(" ON c.id = g.car_id ");
    sql.append(" LEFT JOIN ( ");
    sql.append("      SELECT car_id,COUNT(1) violateNum ");
    sql.append("      FROM ps_violate ");
    sql.append("      WHERE has_deal = 0 ");
    sql.append("      GROUP BY car_id ");
    sql.append(" ) v ");
    sql.append("  ON c.id = v.car_id ");
    sql.append("  LEFT JOIN ps_exam e ");
    sql.append("  ON c.id = e.car_id ");
    sql.append("  LEFT JOIN ps_manage m ");
    sql.append(" ON c.id = m.car_id ");
    sql.append(" where 1=1 ");
    if (null != searchText && !searchText.isEmpty()) {
      sql.append(" AND (c.car_num like '%").append(searchText.toUpperCase()).append("%' ");
      sql.append(" OR c.operate_num like '%").append(searchText.toUpperCase()).append("%' ");
      sql.append(" OR c.owner_name like '%").append(searchText).append("%') ");
    }
    sql.append(" ORDER BY c.id DESC ");
    Query query = carRepository.createQuery(sql.toString());
    query.setFirstResult((pageNum - 1) * pageSize);
    query.setMaxResults(pageSize);
    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(CarSummaryVo.class));

    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public BigInteger getCarSummaryCount(Map<String, String> param) {
    String searchText = param.containsKey("searchText") ? param.get("searchText") : null;

    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT count(1) total ");
    sql.append(" FROM ps_car c ");
    sql.append(" LEFT JOIN ps_insure i ");
    sql.append(" ON c.id = i.car_id ");
    sql.append(" LEFT JOIN ps_operate o ");
    sql.append(" ON c.id = o.car_id ");
    sql.append(" LEFT JOIN ps_gps g ");
    sql.append(" ON c.id = g.car_id ");
    sql.append(" LEFT JOIN ( ");
    sql.append("      SELECT car_id,COUNT(1) violateNum ");
    sql.append("      FROM ps_violate ");
    sql.append("      WHERE has_deal = 0 ");
    sql.append("      GROUP BY car_id ");
    sql.append(" ) v ");
    sql.append("  ON c.id = v.car_id ");
    sql.append("  LEFT JOIN ps_exam e ");
    sql.append("  ON c.id = e.car_id ");
    sql.append("  LEFT JOIN ps_manage m ");
    sql.append(" ON c.id = m.car_id ");
    sql.append(" where 1=1 ");
    if (null != searchText && !searchText.isEmpty()) {
      sql.append(" AND (c.car_num like '%").append(searchText.toUpperCase()).append("%' ");
      sql.append(" OR c.operate_num like '%").append(searchText.toUpperCase()).append("%' ");
      sql.append(" OR c.owner_name like '%").append(searchText).append("%') ");
    }
    sql.append(" ORDER BY c.id DESC ");
    Query query = carRepository.createQuery(sql.toString());
    query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    Map<String, BigInteger> result = (Map<String, BigInteger>) query.getSingleResult();
    return result.get("total");
  }
}
