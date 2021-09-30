package com.example.newspring.controllers;

import com.example.newspring.domain.Customer;
import com.example.newspring.domain.Task;
import com.example.newspring.repo.TaskRepository;

import com.example.newspring.repo.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "/todo/{customerId}") //получение по юзеру
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Iterable<Task> getTasksByCustomer(@PathVariable(value = "customerId") Integer custId) {
        var tasks = taskRepository.findByCustomerId(custId);
        if (tasks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User don't have tasks");
        }
        return tasks;
    }

    @PostMapping(path = "/todo/{customerId}") //добавить задачу
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<Task> addTasksByCustomer(@PathVariable(value = "customerId") Integer custId,
                                            @RequestBody Task task) {
        Customer customer = new Customer();
        customer.setId(custId);
        task.setCustomer(customer);
        return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
    }

    @DeleteMapping(path = "/todo/{customerId}") //удалить задачу
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    void DeleteTaskById(@PathVariable(value = "customerId") Integer custId,
                        @RequestBody Task task) {
        taskRepository.delete(task);
    }

    @PutMapping(path = "/todo/{customerId}") //обновить задачу
    public @ResponseBody
    void updateTasksByCustomer(@PathVariable(value = "customerId") Integer custId,
                                         @RequestBody Task task) {
        List<Task> tasks = taskRepository.findByCustomerId(custId);
        Task findTask = tasks.stream().filter(a -> a.getId() == task.getId()).findFirst().get();
        findTask.setName(task.getName());
        taskRepository.save(findTask);
    }


}
