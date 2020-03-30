package io.hastentech.ppmtool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hastentech.ppmtool.domain.Backlog;
import io.hastentech.ppmtool.domain.Project;
import io.hastentech.ppmtool.domain.ProjectTask;
import io.hastentech.ppmtool.exceptions.ProjectNotFoundException;
import io.hastentech.ppmtool.repository.BacklogRepository;
import io.hastentech.ppmtool.repository.ProjectRepository;
import io.hastentech.ppmtool.repository.ProjectTaskRepository;
import io.hastentech.ppmtool.service.ProjectService;
import io.hastentech.ppmtool.service.ProjectTaskSerivce;

@Service
public class ProjectTaskSerivceImpl implements ProjectTaskSerivce {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;

	@Override
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

		try {
			// checking backlog exists or not
//			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			Backlog backlog = projectService.getProjectByIdentifier(projectIdentifier, username).getBacklog();

			// setting the backlog to project task
			projectTask.setBacklog(backlog);

			// setting up backlog sequence
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;

			backlog.setPTSequence(backlogSequence);

			// seting up backlog sequence and projectidentifier to proejct task

			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);

			// Initial priority when it's null

			if (projectTask.getPriority() == null)
				projectTask.setPriority(3);

			// Initial status when it's null
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepository.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}
	}

	@Override
	public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
		/*
		 * Project project =
		 * projectRepository.findByProjectIdentifier(backlog_id.toUpperCase());
		 * 
		 * if (project == null) { throw new
		 * ProjectNotFoundException("Project with ID : '" + backlog_id +
		 * "' does not exist"); }
		 */
		
		projectService.getProjectByIdentifier(backlog_id, username);
		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	@Override
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
		// TODO Auto-generated method stub
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id.toUpperCase());
		
		if (backlog == null) {
			throw new ProjectNotFoundException(("Project with ID : '" + backlog_id + "' does not exist"));
		}

		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id.toUpperCase());
		if (projectTask == null) {
			throw new ProjectNotFoundException(("ProjectTask with ID : '" + pt_id + "' does not exist"));
		}

		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					("ProjectTask with ID : '" + pt_id + "' does not exist with project " + backlog_id));
		}

		return projectTask;
	}

	@Override
	public ProjectTask updateByProjectSequence(ProjectTask updateProject, String backlog_id, String pt_id, String username) {

		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);

		projectTask = updateProject;

		return projectTaskRepository.save(projectTask);
	}

	@Override
	public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
		// TODO Auto-generated method stub
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);		
		projectTaskRepository.delete(projectTask);

	}

}
