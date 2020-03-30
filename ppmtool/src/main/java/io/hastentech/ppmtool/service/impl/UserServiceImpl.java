package io.hastentech.ppmtool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.hastentech.ppmtool.domain.User;
import io.hastentech.ppmtool.exceptions.UserAlreadyExistException;
import io.hastentech.ppmtool.repository.UserRepository;
import io.hastentech.ppmtool.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setUsername(user.getUsername());
			user.setConfirmPassword("");
			return userRepository.save(user);
			
		} catch (Exception e) {
			throw new UserAlreadyExistException("Username with email Id "+user.getUsername()+" is already exist");
		}
		
	}
	
	
}
