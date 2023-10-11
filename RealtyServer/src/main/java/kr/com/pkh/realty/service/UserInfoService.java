package kr.com.pkh.realty.service;

import java.util.ArrayList;
import java.util.List;

import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.repository.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.com.pkh.realty.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    private ModelMapper modelMapper;
    
    public List<UserInfoDTO> getUserList(){
        List<UserInfoDTO> userInfoList = new ArrayList<UserInfoDTO>();

        List<UserInfoEntity> userInfoEntityList = this.userInfoRepository.findAll();

        // Entity -> DTO 파싱
        for(UserInfoEntity userInfoEntity:userInfoEntityList){
            userInfoList.add(modelMapper.map(userInfoEntity, UserInfoDTO.class));
        }

        return userInfoList;
    }
    
    public UserInfoDTO getUser(){
    	List<UserInfoEntity> userInfoEntities = userInfoRepository.findAll();
    	
    	UserInfoDTO userInfoDto = modelMapper.map(userInfoEntities, UserInfoDTO.class);
    	
    	return userInfoDto;
    }
}
