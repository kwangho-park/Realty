package kr.com.pkh.realty.service;

import java.util.ArrayList;
import java.util.List;

import kr.com.pkh.realty.dto.AppInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AppInfoService {


	//public Page<AppInfoDTO> getAppList(int page){

	//	Pageable pageable = PageRequest.of(page, 5);        // 페이징 갯수

		//Page<AppInfoEntity> appInfoEntities = appInfoRepository.findAll(pageable);

		/*// entity -> dto 파싱
		Page<AppInfoDTO> appInfoDtoPage = appInfoEntities.map(app -> {
			AppInfoDTO appInfoDto = new AppInfoDTO();
			appInfoDto.setId(app.getId());
			appInfoDto.setAppName(app.getAppName());
			appInfoDto.setAppIp(app.getAppIp());
			appInfoDto.setAppPort(app.getAppPort());
			appInfoDto.setAppProtocol(app.getAppProtocol());
			appInfoDto.setAppURI(app.getAppURI());
			appInfoDto.setAppDescription(app.getAppDescription());
			return appInfoDto;
		});*/

		//return appInfoDtoPage;
		//return null;
	//}
	
}
