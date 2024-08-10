package kr.com.pkh.realty.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sun.jna.platform.win32.Netapi32Util;
import kr.com.pkh.realty.constants.Result;
import kr.com.pkh.realty.dto.RegisterDTO;
import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.dao.UserInfoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@RequiredArgsConstructor    // final 선언된 멤버변수의 생성자를 생성 및 DI
@Service
public class UserInfoService {

   // private final UserInfoRepository userInfoRepository;
    private final UserLogService userLogService;
    private final ModelMapper modelMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfoDTO getUser(){
        UserInfoDTO userInfoDto = null;

        return userInfoDto;
    }



    public UserInfoDTO getUserInfo(UserInfoDTO dto) {
        return userInfoMapper.selectUserInfo(dto);
    }
}
