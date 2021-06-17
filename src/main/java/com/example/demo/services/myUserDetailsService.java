package com.example.demo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;

@Service
public class myUserDetailsService implements UserDetailsService  {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		return new User("foo", "foo", new ArrayList<>());
		
		User user = userRepository.findByUsername(username);
		UserPrincipal myUserDetails = new UserPrincipal(user);
		return myUserDetails;

	}

}
