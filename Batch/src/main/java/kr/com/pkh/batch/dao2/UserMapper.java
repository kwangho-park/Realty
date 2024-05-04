package kr.com.pkh.batch.dao2;

import kr.com.pkh.batch.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface UserMapper {
    ArrayList<UserInfoDTO> selectUserList();
}
