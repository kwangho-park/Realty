package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.UserInfoDTO;
import kr.com.pkh.batch.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserInfoDAO {


    @Autowired
    UserInfoMapper userMapper;

    public ArrayList<UserInfoDTO> selectUserList() throws Exception {
        return userMapper.selectUserList();

    }

}