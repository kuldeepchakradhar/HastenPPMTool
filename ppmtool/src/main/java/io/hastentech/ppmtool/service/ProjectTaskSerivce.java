package io.hastentech.ppmtool.service;

import javax.validation.Valid;

import io.hastentech.ppmtool.domain.ProjectTask;

public interface ProjectTaskSerivce {

	ProjectTask addProjectTask(String backlog_id, ProjectTask projectTask);

    Iterable<ProjectTask> findBacklogById(String backlog_id);

	ProjectTask findPTByProjectSequence(String backlog_id, String pt_id);

	ProjectTask updateByProjectSequence(ProjectTask updateProject, String backlog_id, String pt_id);

	void deletePTByProjectSequence(String backlog_id, String pt_id);
}
