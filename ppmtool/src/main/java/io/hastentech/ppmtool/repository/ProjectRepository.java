package io.hastentech.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.hastentech.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	Project findByProjectIdentifier(String projectId);
	
	Iterable<Project> findAll();
	
    Iterable<Project> findAllByProjectLeader(String username);
    
}
