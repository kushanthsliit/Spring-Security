package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Util.JwtUtil;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.AuthenticationRequest;
import com.example.demo.response.AuthenticationResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.services.myUserDetailsService;

@RestController
public class HelloController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private myUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@GetMapping("/hello")
	public String hello() {
		return "Welcome to the app...! ";
	}
	
	@PostMapping("/hi")
	public String hi(@AuthenticationPrincipal UserPrincipal currentUser) {
//		User user = userRepository.findByUsername(currentUser.getUsername());
		return "Hello User Name is : " + currentUser.getUsername();
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
		}
		catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("Incorrect username or password");
		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(request.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
