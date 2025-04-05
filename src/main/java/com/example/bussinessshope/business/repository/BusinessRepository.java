package com.example.bussinessshope.business.repository;

import com.example.bussinessshope.business.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Long > {

    List<BusinessEntity> findAllByUserId(long id);
    Optional<BusinessEntity> findByIdAndUserId(Long id, Long userId);

}
