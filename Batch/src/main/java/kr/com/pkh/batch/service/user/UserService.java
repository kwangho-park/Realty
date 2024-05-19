package kr.com.pkh.batch.service.user;

import kr.com.pkh.batch.mapper.user.UserMapper;
import kr.com.pkh.batch.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {


    @Autowired
    UserMapper userMapper;

    public ArrayList<UserInfoDTO> selectUserList() throws Exception {
        return userMapper.selectUserList();

    }

}