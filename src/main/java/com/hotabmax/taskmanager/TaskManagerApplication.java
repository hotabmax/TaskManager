package com.hotabmax.taskmanager;

import com.hotabmax.taskmanager.dtos_database.TaskRequest;
import com.hotabmax.taskmanager.entities.*;
import com.hotabmax.taskmanager.services.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;


@OpenAPIDefinition(
		info = @Info(
				title = "TaksManager - hotabmax",
				version = "1.0",
				license = @License(
						name = "MIT",
						url = "https://github.com/hotabmax/TaskManager/blob/main/LICENSE"
				)

		)
		,
		security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
		name = "bearerAuth",
		description = "authentication by JWT",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
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
				tasksService.createTask(new TaskRequest("Сделать инвентаризаацию", "Необходимо выполнить инвентаризацию оборудования. Срок неделя",
						"Не готов", "Средний", "semionov.semion.81@gmail.com"),"ivanov.ivan.87@gmail.com");
				tasksService.createTask(new TaskRequest("Сделать закупку", "Необходимо закупить новое оборудование. Срок три дня",
						"Не готов", "Высокий", "petrov.petr.83@gmail.com"),"ivanov.ivan.87@gmail.com");
			} catch (DataIntegrityViolationException e){
				System.out.println("Данные уже созданы");
			}
		};
	}
}
