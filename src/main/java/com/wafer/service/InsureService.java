package com.wafer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
    Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
    return insureRepository.findAll(new Specification<Insure>() {
      @Override
      public Predicate toPredicate(Root<Insure> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (null != operateNum && !operateNum.isEmpty()) {
          predicates.add(cb.like((Path) root.get("operateNum"), "%" + operateNum + "%"));
        }
        if (null != carNum && !carNum.isEmpty()) {
          predicates.add(cb.like((Path) root.get("carNum"), "%" + carNum + "%"));
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
