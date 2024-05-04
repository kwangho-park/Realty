package kr.com.pkh.batch.dao2;

import kr.com.pkh.batch.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserInfoService {


    @Autowired
    UserMapper userMapper;

    public ArrayList<UserInfoDTO> selectUserList() throws Exception {
        return userMapper.selectUserList();

    }

}