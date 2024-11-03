package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.db.UserInfoDTO;
import kr.com.pkh.batch.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//@Service
@Repository
public class UserInfoDAO {


    @Autowired
    public UserInfoMapper userMapper;

    public ArrayList<UserInfoDTO> selectUserList() throws Exception {
        return userMapper.selectUserList();

    }

}