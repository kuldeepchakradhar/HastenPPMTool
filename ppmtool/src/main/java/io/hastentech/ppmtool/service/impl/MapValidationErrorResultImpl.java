package io.hastentech.ppmtool.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import io.hastentech.ppmtool.service.MapValidationErrorResult;

@Service
public class MapValidationErrorResultImpl implements MapValidationErrorResult {

	@Override
	public ResponseEntity<?> mapValidationErrorResult(BindingResult results) {
		// TODO Auto-generated method stub
		
		if(results.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
		
			for(FieldError error: results.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
				
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		return null;
		
		
	}

}
