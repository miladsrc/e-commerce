package com.example.bussinessshope.business.repository;

import com.example.bussinessshope.business.entity.BusinessEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long > {

    List<BusinessEntity> findAllByUserId(long id);
    @Query(value = "SELECT * FROM business WHERE id = :businessId AND user_id_fk = :userId", nativeQuery = true)
    Optional<BusinessEntity> findByIdAndUserId(@Param("businessId") Long businessId, @Param("userId") Long userId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE business SET deleted = true WHERE id = :id", nativeQuery = true)
    void softDeleteBusinessEntityById(@Param("id") Long id);
}
