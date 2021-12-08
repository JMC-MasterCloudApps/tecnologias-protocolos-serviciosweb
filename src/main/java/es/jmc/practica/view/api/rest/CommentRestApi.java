package es.jmc.practica.view.api.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.jmc.practica.model.Comment;
import es.jmc.practica.view.api.dtos.CommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

interface CommentRestApi {

	@Operation(summary = "Get comment by ID")
	@ApiResponses({
		@ApiResponse(
				description = "Comment returned", 
				responseCode = "200", 
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))), 
		@ApiResponse(
				description = "Comment not found", 
				responseCode = "204", 
				content = @Content)		
	})
	ResponseEntity<Comment> getComment(
			@Parameter(description = "ID of the comment to be searched") 
			@PathVariable long id);
	
	@Operation(summary = "Create new comment")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Comment to be created", 
			required = true,
			content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class)))
	@ApiResponses({	
		@ApiResponse(
				description = "Comment created", 
				responseCode = "201", 
				content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = Comment.class))),
		@ApiResponse(
				description = "Book not found", 
				responseCode = "204", 
				content = @Content)})	
	ResponseEntity<Comment> createComment(@RequestBody CommentDTO dto);

	@Operation(summary = "Delete comment")
	@ApiResponse(description = "Comment deleted", responseCode = "200", content = @Content)
	ResponseEntity<Void> deleteComment(
			@Parameter(description = "ID of the comment to be deleted") 
			@PathVariable long id);

}
