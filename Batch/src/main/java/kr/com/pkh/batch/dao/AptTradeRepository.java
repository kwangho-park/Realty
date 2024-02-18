package kr.com.pkh.batch.dao;

import kr.com.pkh.batch.dto.AptTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AptTradeRepository  extends JpaRepository<AptTradeEntity, Integer> {

}
