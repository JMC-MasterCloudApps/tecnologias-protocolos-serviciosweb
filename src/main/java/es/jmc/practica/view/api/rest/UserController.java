package es.jmc.practica.view.api.rest;

import static es.jmc.practica.view.api.mapper.UserMapper.USER_MAPPER;
import static es.jmc.practica.view.api.mapper.CommentMapper.COMMENT_MAPPER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.jmc.practica.controller.CommentService;
import es.jmc.practica.controller.UserService;
import es.jmc.practica.model.Comment;
import es.jmc.practica.model.User;
import es.jmc.practica.view.api.dtos.CommentRequest;
import es.jmc.practica.view.api.dtos.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserRestApi {

	private final UserService service;
	private final CommentService commentService;

	@GetMapping("/")
	public Collection<UserRequest> getUsers() {

		return USER_MAPPER.user2dto(service.getUsers());
	}
	
	@PostMapping("/")
	public ResponseEntity<UserRequest> createUser(UserRequest userRequest) {

		User user = USER_MAPPER.dto2user(userRequest);
		user = service.create(user);

		URI location = fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

		return ResponseEntity.created(location).body(userRequest);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<UserRequest> updateUserEmail(long id, String userEmail) {

		Optional<User> user = service.getUser(id);
		user.ifPresent(value -> {
			value.setEmail(userEmail);
			service.update(value);
		});
		Optional<UserRequest> result = user.map(USER_MAPPER::user2dto);
		
		return result.map(ResponseEntity::ok).orElseGet(() -> noContent().build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(long id) {

		final Optional<User> user = service.getUser(id);

		return user.map(ResponseEntity::ok).orElseGet(() -> noContent().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(long id) {

		if (commentService.findCommentsByUser(id).isEmpty()) {
			service.getUser(id).ifPresent(service::delete);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@GetMapping("/{id}/comments")
	public Collection<CommentRequest> getCommentsByUser(long id) {

		Collection<Comment> comments = commentService.findCommentsByUser(id);
		
		return COMMENT_MAPPER.comment2dto(comments);
	}
}

interface UserRestApi {

	@Operation(summary = "Get all users")
	@ApiResponse (
			description = "Users returned", 
			responseCode = "200",
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRequest.class)))
	Collection<UserRequest> getUsers();
	
	@Operation(summary = "Get user by ID")
	@ApiResponses({
		@ApiResponse(
				description = "User returned", 
				responseCode = "200",
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
		@ApiResponse(
				description = "User not found", 
				responseCode = "204", 
				content = @Content)
	})

	ResponseEntity<User> getUser
			(@Parameter(description = "ID of the user to be searched")
			@PathVariable long id);

	@Operation(summary = "Create new user")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "User to be created", 
			required = true,
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRequest.class)))
	@ApiResponse(
				description = "User created", 
				responseCode = "201", 
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRequest.class)))
	ResponseEntity<UserRequest> createUser(@RequestBody UserRequest userRequest);

	@Operation(summary = "Delete user")
	@ApiResponse(description = "User deleted", responseCode = "200", content = @Content)
	@ApiResponse(description = "Cannot delete user with existing comments.", responseCode = "409", content = @Content)
	ResponseEntity<Void> deleteUser
			(@Parameter(description = "ID of the user")
			@PathVariable long id);
	
	@Operation(summary = "Update user's email.")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "New email.", 
			required = true,
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class)))
	@ApiResponses({
		@ApiResponse(
				description = "Email updated.",
				responseCode = "200",
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
		@ApiResponse(
				description = "User not found.",
				responseCode = "204",
				content = @Content)
	})
	ResponseEntity<UserRequest> updateUserEmail(
			@Parameter(description = "ID of the user")
			@PathVariable long id,
			@RequestBody String userEmail);

	@Operation(summary = "Get all comments by user.")
	@ApiResponse (
			description = "Comments returned", 
			responseCode = "200",
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CommentRequest.class)))
	Collection<CommentRequest> getCommentsByUser(
			@Parameter(description = "ID of the user")
			@PathVariable long id);
}

