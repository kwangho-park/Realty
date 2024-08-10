package kr.com.pkh.realty.dao;


import kr.com.pkh.realty.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserInfoMapper {

    String selectUserNameById2(@Param("id") String id);

    UserInfoDTO selectUserInfo(UserInfoDTO userInfo);
}
