package io.hastentech.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.hastentech.ppmtool.domain.User;
import io.hastentech.ppmtool.payload.JWTLoginSucessReponse;
import io.hastentech.ppmtool.payload.LoginRequest;
import io.hastentech.ppmtool.security.JwtTokenProvider;
import io.hastentech.ppmtool.security.SecurityConstants;
import io.hastentech.ppmtool.service.MapValidationErrorResult;
import io.hastentech.ppmtool.service.UserService;
import io.hastentech.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

	@Autowired
	private MapValidationErrorResult mapValidationErrorResult; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		
		ResponseEntity<?> errorMap = mapValidationErrorResult.mapValidationErrorResult(result);
		if(errorMap != null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSucessReponse(true, token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult results){
		
		userValidator.validate(user, results);
		
		ResponseEntity<?> errorMap = mapValidationErrorResult.mapValidationErrorResult(results);
		if(errorMap != null) return errorMap;
				
		
		User user1 = userService.createUser(user);
		
		return new ResponseEntity<User>(user1, HttpStatus.CREATED);
	}
}
