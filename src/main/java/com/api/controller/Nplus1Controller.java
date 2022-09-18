package com.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.UserDto;
import com.api.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/nplus1")
@AllArgsConstructor
public class Nplus1Controller {

	private UserService userService;

	@GetMapping("problem")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("solution1")
	public ResponseEntity<List<UserDto>> getAllUsersSolution1() {
		return ResponseEntity.ok(userService.getAllUsersSolution1());
	}
	
	@GetMapping("solution2")
	public ResponseEntity<List<UserDto>> getAllUsersSolution2() {
		return ResponseEntity.ok(userService.getAllUsersSolution2());
	}

}
