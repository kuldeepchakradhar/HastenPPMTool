package io.hastentech.ppmtool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hastentech.ppmtool.domain.Backlog;
import io.hastentech.ppmtool.domain.Project;
import io.hastentech.ppmtool.domain.User;
import io.hastentech.ppmtool.exceptions.ProjectIdException;
import io.hastentech.ppmtool.exceptions.ProjectNotFoundException;
import io.hastentech.ppmtool.repository.BacklogRepository;
import io.hastentech.ppmtool.repository.ProjectRepository;
import io.hastentech.ppmtool.repository.UserRepository;
import io.hastentech.ppmtool.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Project saveOrUpdateProject(Project project, String username) {
		// TODO Auto-generated method stub
		// project != null
		if(project.getId()!= null) {
			Project existProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existProject != null && (!existProject.getProjectLeader().equals(username))) {
				throw new ProjectIdException("Project is not in your account");
			}else if(existProject == null) {
				throw new ProjectNotFoundException("Project with Id '"+project.getProjectIdentifier()+"' does not exist");
			}
				
		}
		
		
		try {
			User user = userRepository.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId() != null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			return projectRepository.save(project);
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ProjectIdException("Project Id "+project.getProjectIdentifier().toUpperCase()+" is already exixst");
		}
		 
	}

	@Override
	public Project getProjectByIdentifier(String projectId, String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project Id "+projectId+" is not exists");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project is not exixt in you account");
		}
		return project;
	}

	@Override
	public Iterable<Project> findAllProjects(String username) {
		// TODO Auto-generated method stub
		return projectRepository.findAllByProjectLeader(username);
	}

	@Override
	public void deleteProjectByIdentifier(String projectId, String username) {
		// TODO Auto-generated method stub
		
		projectRepository.delete(getProjectByIdentifier(projectId, username));
	}

}
