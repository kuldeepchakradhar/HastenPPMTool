package io.hastentech.ppmtool.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationErrorResult {

	ResponseEntity<?> mapValidationErrorResult(BindingResult results);
}
