package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface UserInfoMapper {
    ArrayList<UserInfoDTO> selectUserList();
}
