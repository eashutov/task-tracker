package ru.shutov.itone.tasktracker.repository.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.shutov.itone.tasktracker.dto.request.TaskRequest;
import ru.shutov.itone.tasktracker.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> specificationFor(TaskRequest taskRequest) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (taskRequest.author() != null) {
                predicates.add(cb.equal(root.get("author"), taskRequest.author()));
            }
            if (taskRequest.assignee() != null) {
                predicates.add(cb.equal(root.get("assignee"), taskRequest.assignee()));
            }
            if (taskRequest.taskType() != null) {
                predicates.add(cb.equal(root.get("taskType"), taskRequest.taskType()));
            }
            if (taskRequest.priority() != null) {
                predicates.add(cb.equal(root.get("priority"), taskRequest.priority()));
            }
            if (taskRequest.status() != null) {
                predicates.add(cb.equal(root.get("status"), taskRequest.status()));
            }
            if (taskRequest.epic() != null) {
                predicates.add(cb.equal(root.get("epic"), taskRequest.epic()));
            }
            if (taskRequest.createdAtFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), taskRequest.createdAtFrom()));
            }
            if (taskRequest.createdAtTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), taskRequest.createdAtTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
