package com.api.custom;

import java.math.BigDecimal;

public interface UserOrderData {
	
	String getName();
	String getSurname();
	Integer getAge();
	Integer getQuantity();
	BigDecimal getTotalPrice();
	Integer getUserId();

}
