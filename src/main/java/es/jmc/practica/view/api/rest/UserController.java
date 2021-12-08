package es.jmc.practica.view.api.rest;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import es.jmc.practica.controller.UserService;
import es.jmc.practica.model.User;
import es.jmc.practica.view.api.dtos.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

	private final UserService service;

	@GetMapping("/")
	@JsonView(User.Lite.class)
	@Operation(summary = "Get all users")
	@ApiResponse (description = "Users returned", responseCode = "200",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.Lite.class)))
	public Collection<User> getUsers() {

		return service.getUsers();
	}

	@GetMapping("/{id}")
	@JsonView(User.Lite.class)
	@Operation(summary = "Get user by ID")
	@ApiResponse(description = "User returned", responseCode = "200",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.Lite.class)))
	@ApiResponse(description = "User not found", responseCode = "204", content = @Content)
	public ResponseEntity<User> getUser
			(@Parameter(description = "ID of the user to be searched")
			@PathVariable long id) {

		final Optional<User> user = service.getUser(id);

		return user.map(ResponseEntity::ok).orElseGet(() -> noContent().build());
	}


	@PostMapping("/")
	@Operation(summary = "Create new user")
	@ApiResponse(description = "User created", responseCode = "201", content =
	@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to be created", required = true,
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRequest.class)))
	public ResponseEntity<User> createUser
			(@RequestBody UserRequest userRequest) {

		User user = service.create(userRequest);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(location).body(user);
	}

	// TODO
	public ResponseEntity<User> updateUserEmail(String email) {

		return null;
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user")
	@ApiResponse(description = "User deleted", responseCode = "200", content = @Content)
	public ResponseEntity<Void> deleteUser
			(@Parameter(description = "ID of the user")
			@PathVariable long id) {

		service.getUser(id).ifPresent(service::delete);

		return ResponseEntity.ok().build();
	}

}
