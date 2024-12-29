package kr.com.pkh.realty.service;

import kr.com.pkh.realty.dao.UserInfoMapper;
import kr.com.pkh.realty.dto.db.UserInfoDTO;
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

    // 사용자 회원가입
    // 1 : 회원가입 성공
    // 2 : 회원가입 실패
    // 3 : 이미존재하는 아이디
    public String registUser(UserInfoDTO dto) {

        String result = "";
        String insertResult="";

        // id 중복검사
        String count = userInfoMapper.checkUserIdExists(dto.getUserId());

        if(count.equals("0")){

            insertResult = String.valueOf(userInfoMapper.insertUserInfo(dto) );

            if(insertResult.equals("1")){        // 회원가입 성공
                result="1";
            }else{                      // 회원가입 실패
                result="2";
            }
        }else{
            result="3";       // 이미 존재하는 user id
        }

        return result;


    }

}
