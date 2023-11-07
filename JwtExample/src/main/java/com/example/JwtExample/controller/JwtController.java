package com.example.JwtExample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.JwtExample.dao.UserDao;
import com.example.JwtExample.model.userRequest;
import com.example.JwtExample.service.CustomUserDetailsService;
import com.example.JwtExample.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;

@RestController
public class JwtController {
	
	@Autowired
	private CustomUserDetailsService customUserDetails;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/jwt")
	public ResponseEntity<Object> example(){
		return new ResponseEntity<>("Jwt Example checking with userDetails",HttpStatus.OK);
	}
	
	@PostMapping("/token")
	public ResponseEntity<Object> generateToken(@RequestBody userRequest user) throws Exception{
		System.out.println(user.toString());
		try {
			Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
			System.out.println(authenticate.isAuthenticated());
		}
		catch(UsernameNotFoundException e) {
			throw new Exception("username not found");
		}
		
		UserDetails userDetails=customUserDetails.loadUserByUsername(user.getUsername());
		System.out.println(userDetails.toString());
		String token=jwtUtil.generateToken(userDetails);
		System.out.println(token);
		return new ResponseEntity<>(token,HttpStatus.OK);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<Object> registerUser(@RequestBody userRequest user){
		System.out.println(user.toString());
		User addUser=new User(user.getUsername(),user.getPassword(),new ArrayList<>());
		User res=userDao.addUser(addUser);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
}
