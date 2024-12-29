package kr.com.pkh.realty.dao;


import kr.com.pkh.realty.dto.db.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {

    String selectUserNameById2(@Param("id") String id);

    UserInfoDTO selectUserInfo(UserInfoDTO userInfo);

    int insertUserInfo(UserInfoDTO userInfoDTO);

    String checkUserIdExists(String userId);
}
