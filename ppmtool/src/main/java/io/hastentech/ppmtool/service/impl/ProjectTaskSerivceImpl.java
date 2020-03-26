package io.hastentech.ppmtool.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hastentech.ppmtool.domain.Backlog;
import io.hastentech.ppmtool.domain.Project;
import io.hastentech.ppmtool.domain.ProjectTask;
import io.hastentech.ppmtool.exceptions.ProjectNotFoundException;
import io.hastentech.ppmtool.repository.BacklogRepository;
import io.hastentech.ppmtool.repository.ProjectRepository;
import io.hastentech.ppmtool.repository.ProjectTaskRepository;
import io.hastentech.ppmtool.service.ProjectTaskSerivce;

@Service
public class ProjectTaskSerivceImpl implements ProjectTaskSerivce {

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
			// checking backlog exists or not
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			// setting the backlog to project task
			projectTask.setBacklog(backlog);

			// setting up backlog sequence
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;

			backlog.setPTSequence(backlogSequence);

			// seting up backlog sequence and projectidentifier to proejct task

			projectTask.setProjectSequence(projectIdentifier + "_" + backlogSequence);
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
	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		Project project = projectRepository.findByProjectIdentifier(backlog_id.toUpperCase());

		if (project == null) {
			throw new ProjectNotFoundException("Project with ID : '" + backlog_id + "' does not exist");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	@Override
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
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
	public ProjectTask updateByProjectSequence(ProjectTask updateProject, String backlog_id, String pt_id) {

		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

		projectTask = updateProject;

		return projectTaskRepository.save(projectTask);
	}

	@Override
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		// TODO Auto-generated method stub
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);		
		projectTaskRepository.delete(projectTask);

	}

}
