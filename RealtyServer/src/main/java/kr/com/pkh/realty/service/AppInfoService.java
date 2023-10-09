package kr.com.pkh.realty.service;

import java.util.ArrayList;
import java.util.List;

import kr.com.pkh.realty.dto.AppInfoDTO;
import kr.com.pkh.realty.repository.AppInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.com.pkh.realty.entity.AppInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppInfoService {

	private final ModelMapper modelMapper;
	private final AppInfoRepository appInfoRepository;
	
	public List<AppInfoDTO> findAll() throws Exception{
		List<AppInfoEntity> appInfoEntities= appInfoRepository.findAll();
		
		List<AppInfoDTO> list = new ArrayList<>();
		for (AppInfoEntity entity : appInfoEntities) {
			AppInfoDTO appInfoDto = modelMapper.map(entity, AppInfoDTO.class);
			list.add(appInfoDto);
		}
		return list;
	}
	
}
