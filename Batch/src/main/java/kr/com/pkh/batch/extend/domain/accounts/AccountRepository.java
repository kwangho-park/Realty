package kr.com.pkh.batch.extend.domain.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
}
