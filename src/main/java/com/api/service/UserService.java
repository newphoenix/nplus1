package com.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.custom.OrderUserId;
import com.api.custom.UserOrderData;
import com.api.dto.OrderDto;
import com.api.dto.UserDto;
import com.api.entity.User;
import com.api.repository.OrderRepository;
import com.api.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
//@Slf4j
public class UserService {

	private UserRepository userRepository;
	private OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();

		users.forEach(user -> {
			UserDto userDto = UserDto.builder().name(user.getName()).surname(user.getSurname()).age(user.getAge())
					.build();

			List<OrderDto> orderDtolist = user.getOrders().stream().map(
					order -> OrderDto.builder().quantity(order.getQuantity()).totalPrice(order.getTotalPrice()).build())
					.collect(Collectors.toList());

			userDto.setOrders(orderDtolist);

			userDtoList.add(userDto);
		});

		return userDtoList;
	}

	@Transactional(readOnly = true)
	public List<UserDto> getAllUsersSolution1() {

		List<User> users = userRepository.findAll();

		List<Integer> userIds = users.stream().map(user -> user.getId()).collect(Collectors.toList());

		List<OrderUserId> orders = orderRepository.findOrdersByUserId(userIds);

		Map<Integer, List<OrderDto>> orderDtoMap = orders.stream()
				.collect(
						Collectors
								.groupingBy(order -> order.getUserId(),
										Collectors.mapping(
												order -> OrderDto.builder().quantity(order.getQuantity())
														.totalPrice(order.getTotalPrice()).build(),
												Collectors.toList())));

		List<UserDto> userDtoList = users.stream().map(user -> {

			UserDto userDto = UserDto.builder().name(user.getName()).surname(user.getSurname()).age(user.getAge())
					.build();

			userDto.setOrders(orderDtoMap.getOrDefault(user.getId(), new ArrayList<>()));

			return userDto;
		}).collect(Collectors.toList());

		return userDtoList;
	}

	@Transactional(readOnly = true)
	public List<UserDto> getAllUsersSolution2() {

		List<UserOrderData> orders = orderRepository.findUserAndOrderData();

		// orders.forEach(data -> log.info(data.getName() + " " +
		// data.getTotalPrice()));

		Map<UserDto, List<OrderDto>> UserDto_ListorderDtoMap = orders.stream()
				.collect(
						Collectors
								.groupingBy(
										userOrder -> UserDto.builder().name(userOrder.getName())
												.surname(userOrder.getSurname()).age(userOrder.getAge()).build(),
										Collectors.mapping(userOrder -> {

											OrderDto orderDto = null;
											if (userOrder.getQuantity() != null) {
												orderDto = OrderDto.builder().quantity(userOrder.getQuantity())
														.totalPrice(userOrder.getTotalPrice()).build();
											}
											return orderDto;

										}, Collectors.toList())));

		List<UserDto> userDtoList = UserDto_ListorderDtoMap.keySet().stream().map(userDto -> {
			userDto.setOrders(UserDto_ListorderDtoMap.get(userDto).get(0) != null ? UserDto_ListorderDtoMap.get(userDto)
					: new ArrayList<>());
			return userDto;
		}).collect(Collectors.toList());

		return userDtoList;
	}

}
