package com.example.newspring.repo;

import com.example.newspring.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByCustomerId(Integer customer_id);
}