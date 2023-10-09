package kr.com.pkh.realty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.com.pkh.realty.entity.AppInfoEntity;

public interface AppInfoRepository extends JpaRepository<AppInfoEntity, String>{

}
