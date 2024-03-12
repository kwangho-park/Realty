package kr.com.pkh.realty.repository;


import kr.com.pkh.realty.entity.UserLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository  extends JpaRepository<UserLogEntity, String> {

}
