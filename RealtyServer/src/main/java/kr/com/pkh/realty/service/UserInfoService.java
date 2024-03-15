package kr.com.pkh.realty.service;

import java.util.ArrayList;
import java.util.List;

import kr.com.pkh.realty.constants.Result;
import kr.com.pkh.realty.dto.RegisterDTO;
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
    private final ModelMapper modelMapper;


    public UserInfoDTO getUser(){
        UserInfoDTO userInfoDto = null;

        List<UserInfoEntity> userInfoEntities = userInfoRepository.findAll();
        // Entity -> DTO 파싱
        for(UserInfoEntity entity : userInfoEntities){
            userInfoDto = modelMapper.map(entity, UserInfoDTO.class);
        }

        return userInfoDto;
    }

    public Result registerUser(RegisterDTO registerDTO){

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(registerDTO.getUserId());
        userInfoEntity.setUserPw(registerDTO.getUserPw());
        userInfoEntity.setUserName(registerDTO.getUserName());

        UserInfoEntity userInfoEntity1 = userInfoRepository.save(userInfoEntity);

        if(!(userInfoEntity1==null)){
            return Result.SUCCESS;
        }else{
            return Result.FAIL;
        }

    }
}
