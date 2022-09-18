package com.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.custom.OrderUserId;
import com.api.custom.UserOrderData;
import com.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {

	@Query("""			
			 SELECT o.quantity as quantity,o.totalPrice as totalPrice, o.user.id as userId
			 FROM Order o WHERE o.user.id IN (:userIds)
			 """)
	List<OrderUserId> findOrdersByUserId(@Param("userIds")List<Integer> userIds);

	@Query("""
			SELECT o.quantity as quantity,o.totalPrice as totalPrice, o.user.id as userId,
			       u.name as name, u.surname as surname, u.age as age 
			FROM User u LEFT OUTER JOIN Order o ON u.id= o.user.id
			""")
	List<UserOrderData> findUserAndOrderData();

}
