package com.example.bussinessshope.order.repository;

import com.example.bussinessshope.order.entity.OrderEntity;
import com.example.bussinessshope.shared.entity.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUserIdAndSituation(Long userId, Situation situation);
    List<OrderEntity> findByUserIdAndSituationNotOrderByCheckFactorTimeDesc(Long userId, Situation situation);
}
