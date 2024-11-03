package kr.com.pkh.batch.mapper;

import kr.com.pkh.batch.dto.db.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface UserInfoMapper {
    ArrayList<UserInfoDTO> selectUserList();
}
