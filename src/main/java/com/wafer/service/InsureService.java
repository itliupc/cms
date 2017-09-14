package com.wafer.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.wafer.domain.Insure;
import com.wafer.repository.InsureRepository;

@Service
public class InsureService {

  @Autowired
  private InsureRepository insureRepository;

  public Insure findByOperateNum(String operateNum) {
    return insureRepository.findByOperateNum(operateNum);
  }

  public Page<Insure> getInsureList(Map<String, String> param) {
    Sort sort = new Sort(Sort.Direction.DESC, "id");
    int pageNum = Integer.parseInt(String.valueOf(param.get("page")));
    int pageSize = Integer.parseInt(String.valueOf(param.get("rows")));
    final String operateNum = param.containsKey("operateNum") ? param.get("operateNum") : null;
    final String carNum = param.containsKey("carNum") ? param.get("carNum") : null;
    final String outBuy = param.containsKey("outBuy") ? param.get("outBuy") : null;
    final String deadline = param.containsKey("deadline") ? param.get("deadline") : null;
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
    return insureRepository.findAll(new Specification<Insure>() {
      @Override
      public Predicate toPredicate(Root<Insure> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (null != operateNum && !operateNum.isEmpty()) {
          predicates.add(cb.like(root.<String>get("operateNum"), "%" + operateNum + "%"));
        }
        if (null != carNum && !carNum.isEmpty()) {
          predicates.add(cb.like(root.<String>get("carNum"), "%" + carNum + "%"));
        }
        if (null != outBuy && !outBuy.isEmpty()) {
          predicates.add(cb.equal(root.get("outBuy"), outBuy));
        }
        if (null != deadline && !deadline.isEmpty()) {
          if ("1".equals(deadline)) {// 未领取
            predicates
                .add(cb.or(cb.isNull(root.get("busInsure")), cb.isNull(root.get("forceInsure"))));
          } else if ("2".equals(deadline)) {// 即将过期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date afterDate = new Date();
            Date nowDate = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) + 3);
            calendar1.set(Calendar.HOUR_OF_DAY, 0);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.MILLISECOND, 0);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, 0);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);
            calendar2.set(Calendar.MILLISECOND, 0);
            try {
              afterDate = sdf.parse(sdf.format(calendar1.getTime()));
              nowDate = sdf.parse(sdf.format(calendar2.getTime()));
            } catch (ParseException e) {
              e.printStackTrace();
            }
            predicates.add(cb.or(cb.between(root.<Date>get("busInsure"), nowDate, afterDate),
                cb.between(root.<Date>get("forceInsure"), nowDate, afterDate)));
          } else if ("3".equals(deadline)) {// 已经过期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nowDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            try {
              nowDate = sdf.parse(sdf.format(calendar.getTime()));
            } catch (ParseException e) {
              e.printStackTrace();
            }
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

  public Insure getOtherInsureByOperateNum(String operateNum, long id) {
    return insureRepository.getOtherInsureByOperateNum(operateNum, id);
  }

  public void deleteInsureByIds(List<Long> ids) {
    insureRepository.deleteInsureByIds(ids);
  }
}
