package kr.com.pkh.realty.service;

import kr.com.pkh.realty.dao.UserInfoMapper;
import kr.com.pkh.realty.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor    // final 선언된 멤버변수의 생성자를 생성 및 DI
@Service
public class UserInfoService {

    private final UserLogService userLogService;

    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfoDTO getUser(){
        UserInfoDTO userInfoDto = null;

        return userInfoDto;
    }




    public UserInfoDTO getUserInfo(UserInfoDTO dto) {
        return userInfoMapper.selectUserInfo(dto);
    }
    public int registUser(UserInfoDTO dto) {
        return userInfoMapper.insertUserInfo(dto);
    }

}
