package io.hastentech.ppmtool.repository;

import io.hastentech.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.hastentech.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String backlog_id);

	ProjectTask findByProjectSequence(String pt_id);
}
