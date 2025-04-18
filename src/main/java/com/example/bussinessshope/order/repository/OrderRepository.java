package com.example.bussinessshope.order.repository;

import com.example.bussinessshope.order.entity.OrderEntity;
import com.example.bussinessshope.shared.entity.Situation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUserIdAndSituation(Long userId, Situation situation);
    List<OrderEntity> findByUserIdAndSituationNotOrderByCheckFactorTimeDesc(Long userId, Situation situation);
    @Modifying
    @Transactional
    @Query(value = """
    DELETE FROM order_product op
    USING orders o
    WHERE op.order_id = o.id
      AND o.situation = 'PENDING'
      AND o.user_id_fk = :userId
      AND op.product_id = :productId
""", nativeQuery = true)
    void deleteProductFromUserPendingOrders(@Param("userId") Long userId, @Param("productId") Long productId);
}
