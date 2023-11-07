package com.example.JwtExample.dao;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class UserDao {
		
	List<User> users=new ArrayList<>();
	
	public User addUser(User user) {
		boolean addUser=users.add(user);
		
		if(addUser) {
			return user;
		}
		else {
			return null;
		}
	}
	
	public User getUser(String username) {
		return users.stream().filter(i->i.getUsername().equals(username)).findAny().orElse(null);
	}
}
