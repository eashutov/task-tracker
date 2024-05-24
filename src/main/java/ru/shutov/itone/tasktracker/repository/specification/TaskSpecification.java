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
            if (taskRequest == null) {
                return null;
            }

            if (taskRequest.getAuthor() != null) {
                predicates.add(cb.equal(root.get("author").get("id"), taskRequest.getAuthor()));
            }
            if (taskRequest.getAssignee() != null) {
                predicates.add(cb.equal(root.get("assignee").get("id"), taskRequest.getAssignee()));
            }
            if (taskRequest.getTaskType() != null) {
                predicates.add(cb.equal(root.get("taskType"), taskRequest.getTaskType()));
            }
            if (taskRequest.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), taskRequest.getPriority()));
            }
            if (taskRequest.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), taskRequest.getStatus()));
            }
            if (taskRequest.getEpic() != null) {
                predicates.add(cb.equal(root.get("epic").get("id"), taskRequest.getEpic()));
            }
            if (taskRequest.getCreatedAtFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), taskRequest.getCreatedAtFrom()));
            }
            if (taskRequest.getCreatedAtTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), taskRequest.getCreatedAtTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
