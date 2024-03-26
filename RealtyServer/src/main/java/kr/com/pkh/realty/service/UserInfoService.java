package kr.com.pkh.realty.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import kr.com.pkh.realty.constants.Result;
import kr.com.pkh.realty.dto.RegisterDTO;
import kr.com.pkh.realty.dto.UserInfoDTO;
import kr.com.pkh.realty.entity.UserLogEntity;
import kr.com.pkh.realty.repository.UserInfoRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import kr.com.pkh.realty.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@RequiredArgsConstructor    // final 선언된 멤버변수의 생성자를 생성 및 DI
@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserLogService userLogService;
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

    @Transactional
    public Result registerUser(RegisterDTO registerDTO){

        try {
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUserId(registerDTO.getUserId());
            userInfoEntity.setUserPw(registerDTO.getUserPw());
            userInfoEntity.setUserName(registerDTO.getUserName());

            // [추후] 등록된 사용자 체크 로직 추가예정

            // 사용자 등록 //
            UserInfoEntity userInfo = userInfoRepository.save(userInfoEntity);

            // 사용자 로그 생성 //
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            UserLogEntity userLogEntity = new UserLogEntity();
            userLogEntity.setUserId(userInfo.getUserId());
            userLogEntity.setUserName(userInfo.getUserName());
            userLogEntity.setAction("C");
            userLogEntity.setDatetime(formattedDateTime);

            userLogService.login(userLogEntity);

            return Result.SUCCESS;

        }catch(Exception e){
            // JPA save() 실패시 발생하는 예외 : ConstraintViolationException, DataAccessException, JpaSystemException, HibernateException

            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  // catch 문에서 예외를 처리하는 경우 명시적으로 롤백필요
            return Result.FAIL;
        }

    }
}
