package io.hastentech.ppmtool.service;

import org.springframework.http.ResponseEntity;

import io.hastentech.ppmtool.domain.Project;

public interface ProjectService {

	Project saveOrUpdateProject(Project project);

	Project getProjectByIdentifier(String projectId);

	Iterable<Project> findAllProjects();

	void deleteProjectByIdentifier(String projectId);

}
