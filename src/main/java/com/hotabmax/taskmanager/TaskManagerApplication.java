package com.hotabmax.taskmanager;

import com.hotabmax.taskmanager.models.*;
import com.hotabmax.taskmanager.services.*;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;


@SpringBootApplication
public class TaskManagerApplication {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRolesService userRolesService;
	@Autowired
	private StatusService statusService;
	@Autowired
	private PriorityService priorityService;
	@Autowired
	private TasksService tasksService;


	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

	@Bean
	InitializingBean initializingDatabase() throws PSQLException{
		return () -> {
			try {
				userRolesService.createUserRole(new UserRoles("ROLE_CUSTOMER"));
				userRolesService.createUserRole(new UserRoles("ROLE_EXECUTOR"));
				userService.createUser(new User("Иванов", "Иван", "Иваович",
						"ivanov.ivan.87@gmail.com",
						"$2a$10$EVAf6.61lGl27fEgop7EReWsuXHfspHwG44JnKDw1snd4cluewDQG",
						userRolesService.findByName("ROLE_CUSTOMER").getId()));
				userService.createUser(new User("Петров", "Петр", "Петрович",
						"petrov.petr.83@gmail.com",
						"$2a$10$G5LG9UCLmFjyJfMer.8znup1wsivpZOD0qLFX2Jufx5jZmKauZ/kW",
						userRolesService.findByName("ROLE_EXECUTOR").getId()));
				userService.createUser(new User("Семенов", "Семен", "Семенович",
						"semionov.semion.81@gmail.com",
						"$2a$10$G5LG9UCLmFjyJfMer.8znup1wsivpZOD0qLFX2Jufx5jZmKauZ/kW",
						userRolesService.findByName("ROLE_EXECUTOR").getId()));

				statusService.createStatus(new Status("Не готов"));
				statusService.createStatus(new Status("Готов"));
				priorityService.createStatus(new Priority("Низкий"));
				priorityService.createStatus(new Priority("Средний"));
				priorityService.createStatus(new Priority("Высокий"));
				tasksService.createTask(new Tasks("Сделать инвентаризаацию", "Необходимо выполнить инвентаризацию оборудования. Срок неделя",
						statusService.findByName("Не готов").getId(), priorityService.findByName("Средний").getId(),
						userService.findByEmail("ivanov.ivan.87@gmail.com").getId(), userService.findByEmail("semionov.semion.81@gmail.com").getId()));
				tasksService.createTask(new Tasks("Сделать закупку", "Необходимо закупить новое оборудование. Срок три дня",
						statusService.findByName("Не готов").getId(), priorityService.findByName("Высокий").getId(),
						userService.findByEmail("ivanov.ivan.87@gmail.com").getId(), userService.findByEmail("petrov.petr.83@gmail.com").getId()));
			} catch (DataIntegrityViolationException e){
				System.out.println("Данные уже созданы");
			}
		};
	}
}
