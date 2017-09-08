package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cms.domain.ResponseParam;
import com.cms.repository.base.BaseRepository;

public interface ResponseParamRepository extends BaseRepository<ResponseParam, Long>{
	@Query(value="from ResponseParam rp where rp.interfaceId = :interfaceId ")
	List<ResponseParam> getResponseParamByInterfaceId(@Param("interfaceId") long interfaceId);

	void deleteResponseParamByResponseParamId(long responseParamId);

	ResponseParam findResponseParamByResponseParamId(long responseParamId);
}
