package kr.com.pkh.realty.repository;

import kr.com.pkh.realty.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// DAO 와 동일
// JPA 구현체 :Hibernate (ORM)
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {

    Optional<UserInfoEntity> findByUserId(String userId);
    
}
