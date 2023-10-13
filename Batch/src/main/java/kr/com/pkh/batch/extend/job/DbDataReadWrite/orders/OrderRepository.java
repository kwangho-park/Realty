package kr.com.pkh.batch.extend.job.DbDataReadWrite.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
