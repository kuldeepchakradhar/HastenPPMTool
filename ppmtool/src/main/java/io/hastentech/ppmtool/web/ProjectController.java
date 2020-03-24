package io.hastentech.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.hastentech.ppmtool.domain.Project;
import io.hastentech.ppmtool.service.MapValidationErrorResult;
import io.hastentech.ppmtool.service.ProjectService;
	
@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorResult mapValidationErrorResult;
	
	@PostMapping
	public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult results){
		
		ResponseEntity<?> errorMap = mapValidationErrorResult.mapValidationErrorResult(results);
		if(errorMap!=null)return errorMap;
		
		Project proj = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(proj, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId){
		
		Project project = projectService.getProjectByIdentifier(projectId);
		
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> findAllProjects(){
		return projectService.findAllProjects();
	}
	
	
	@RequestMapping(value="/{projectId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProjectById(@PathVariable String projectId) {
		projectService.deleteProjectByIdentifier(projectId);
		
		return new ResponseEntity<String>("Prject with id '"+projectId+"' is deleted", HttpStatus.OK);
	}
	
}
