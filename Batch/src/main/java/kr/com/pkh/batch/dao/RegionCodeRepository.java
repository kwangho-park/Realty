package kr.com.pkh.batch.dao;


import kr.com.pkh.batch.dto.RegionCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public interface RegionCodeRepository extends JpaRepository<RegionCodeEntity, Integer> {

     List<RegionCodeEntity> findAllByOrderByIdAsc();

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<RegionCodeEntity> findAllDescOrder() {
//        TypedQuery<RegionCodeEntity> query = entityManager.createQuery(
//                "SELECT rc FROM RegionCodeEntity rc ORDER BY rc.id", RegionCodeEntity.class);
//        return query.getResultList();
//    }
}
