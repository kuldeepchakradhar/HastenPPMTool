package io.hastentech.ppmtool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hastentech.ppmtool.domain.Project;
import io.hastentech.ppmtool.exceptions.ProjectIdException;
import io.hastentech.ppmtool.repository.ProjectRepository;
import io.hastentech.ppmtool.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public Project saveOrUpdateProject(Project project) {
		// TODO Auto-generated method stub
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}catch (Exception e) {
			// TODO: handle exception
			throw new ProjectIdException("Project Id "+project.getProjectIdentifier().toUpperCase()+" is already exixst");
		}
		 
	}

	@Override
	public Project getProjectByIdentifier(String projectId) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project Id "+projectId+" is not exists");
		}
		return project;
	}

}
