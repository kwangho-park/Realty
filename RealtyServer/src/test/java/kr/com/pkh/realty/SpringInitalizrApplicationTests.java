package kr.com.pkh.realty;

import kr.com.pkh.realty.entity.UserInfoEntity;
import kr.com.pkh.realty.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringInitalizrApplicationTests {


    @Autowired
    private UserInfoRepository userRepository;


    @Test
    void createUser(){
        UserInfoEntity userEntity = new UserInfoEntity();
        //userEntity.setUI_ID(1);
        userEntity.setUserId("test");
        userEntity.setUserPw("테스트 패스워드");
        userEntity.setUserName("테스트 계정");

        this.userRepository.save(userEntity);

    }

    @Test
    void readUserList(){
        List<UserInfoEntity> all = this.userRepository.findAll();

        for(int loop=0;loop<all.size();loop++){
            UserInfoEntity q = all.get(loop);
            System.out.println("Id : "+ q.getId());
            System.out.println("user Id : "+ q.getUserId());
            System.out.println("user pw : "+ q.getUserPw());
            System.out.println("user name : "+ q.getUserName());
        }
    }


    @Test
    void readUser(){
        Optional<UserInfoEntity> user = this.userRepository.findById("1");

        if(user.isPresent()){
            UserInfoEntity userEntity = user.get();
            System.out.println("Id : "+ userEntity.getId());
            System.out.println("user Id : "+ userEntity.getUserId());
            System.out.println("user pw : "+ userEntity.getUserPw());
            System.out.println("user name : "+ userEntity.getUserName());
        }
    }


    @Test
    void readUserId(){
        Optional<UserInfoEntity> user = this.userRepository.findByUserId("test");

        if(user.isPresent()){
            UserInfoEntity userEntity = user.get();
            System.out.println("Id : "+ userEntity.getId());
            System.out.println("user Id : "+ userEntity.getUserId());
            System.out.println("user pw : "+ userEntity.getUserPw());
            System.out.println("user name : "+ userEntity.getUserName());
        }
    }


}
