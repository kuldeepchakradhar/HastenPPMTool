package io.hastentech.ppmtool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hastentech.ppmtool.domain.Project;
import io.hastentech.ppmtool.repository.ProjectRepository;
import io.hastentech.ppmtool.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public Project saveOrUpdateProject(Project project) {
		// TODO Auto-generated method stub
		
		return projectRepository.save(project); 
	}

}
