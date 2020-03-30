package io.hastentech.ppmtool.service;

import org.springframework.http.ResponseEntity;

import io.hastentech.ppmtool.domain.Project;

public interface ProjectService {

	Project saveOrUpdateProject(Project project, String username);

	Project getProjectByIdentifier(String projectId, String usrString);

	Iterable<Project> findAllProjects(String username);

	void deleteProjectByIdentifier(String projectId, String username);

}
