/*User Controller*/
package com.seekandbuy.haveabeer.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seekandbuy.haveabeer.domain.Promotion;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;
import com.seekandbuy.haveabeer.services.UserService; 


@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:4200")
public class UserResources 
{	
	@Autowired
	private UserService userService;
	
	public UserResources(UserService userService) 
	{
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> ListUsers() 
	{
		return ResponseEntity.status(HttpStatus.OK).body(userService.listar());
	}
	
	@PostMapping
	public ResponseEntity<Void> userCreate(@RequestBody User user) 
	{
		user = userService.userCreate(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Optional<User>> findUser(@PathVariable("id") Long id)
	{
		Optional<User> user = null;
		try
		{
			user = userService.findUser(id);
		}catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) 
	{ 
		try
		{
			userService.deleteUser(id);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable("id") Long id)
	{
		user.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			userService.updateUser(user);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<User> findUser(@RequestBody Map<String, Object> credential) {
		User user = null;
		
		String password = (String) credential.get("password");
		String email = (String) credential.get("email");
		
		try
		{
			user = userService.findUser(password, email);
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().build();
			
		}

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
}
