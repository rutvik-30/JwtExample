package com.example.JwtExample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import com.example.JwtExample.dao.UserDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserDao userDao;


	@Override
	public UserDetails loadUserByUsername(String username)  {
		User user= userDao.getUser(username);
		//System.out.println(user.toString());
		if(user!=null) {
			System.out.println(user.toString());
			return user;
		}
		else {
			throw new UsernameNotFoundException("username not found");
		}
		
	}

}
