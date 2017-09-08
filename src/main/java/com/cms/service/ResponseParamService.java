package com.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.domain.ResponseParam;
import com.cms.repository.ResponseParamRepository;

@Service
public class ResponseParamService {

	@Autowired
	ResponseParamRepository responseParamRepository;

	public List<ResponseParam> getResponseParamByInterfaceId(long interfaceId) {
		return responseParamRepository.getResponseParamByInterfaceId(interfaceId);
	}

	public void deleteResponseParamByResponseParamId(long responseParamId) {
		responseParamRepository.deleteResponseParamByResponseParamId(responseParamId);
	}

	public ResponseParam findResponseParamById(long responseParamId) {
		return responseParamRepository.findResponseParamByResponseParamId(responseParamId);
	}

	public ResponseParam saveResponseParam(ResponseParam rP) {
		return responseParamRepository.save(rP);
	}
}
