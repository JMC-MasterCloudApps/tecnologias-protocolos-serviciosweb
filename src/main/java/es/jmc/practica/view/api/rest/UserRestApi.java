package es.jmc.practica.view.api.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.jmc.practica.model.User;
import es.jmc.practica.view.api.dtos.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

interface UserRestApi {

	@Operation(summary = "Get all users")
	@ApiResponse (
			description = "Users returned", 
			responseCode = "200",
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class)))
	Collection<User> getUsers();
	
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
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class)))
	ResponseEntity<User> createUser(@RequestBody UserRequest userRequest);

	@Operation(summary = "Delete user")
	@ApiResponse(description = "User deleted", responseCode = "200", content = @Content)
	ResponseEntity<Void> deleteUser
			(@Parameter(description = "ID of the user")
			@PathVariable long id);
	
	@Operation(summary = "Update user's email.")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "UserId and email to be updated.", 
			required = true,
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRequest.class)))
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
	ResponseEntity<User> updateUserEmail();
}
