package kr.com.pkh.batch.extend.job.DbDataReadWrite.orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
