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
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import com.seekandbuy.haveabeer.domain.Product;
import com.seekandbuy.haveabeer.domain.User;
import com.seekandbuy.haveabeer.exceptions.UserNotFoundException;
import com.seekandbuy.haveabeer.services.UserService; 


@RestController
@RequestMapping("/users")
@CrossOrigin(origins="http://localhost:4200")
public class UserResources implements GenericResources<User>
{	
	@Autowired
	private UserService userService;
	
	public UserResources(UserService userService) 
	{
		this.userService = userService;
	}

	@Override
	public ResponseEntity<List<User>> listItem() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.listItem());
	}

	@Override
	public ResponseEntity<Void> createItem(User user) {
		user = userService.createItem(user);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@Override
	public ResponseEntity<Optional<User>> findItem(Long id) {
		Optional<User> user = null;
		try
		{
			user = userService.findItem(id);
		}catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@Override
	public ResponseEntity<Void> deleteItem(Long id) {
		try
		{
			userService.deleteItem(id);
		}
		catch(UserNotFoundException e)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Void> updateItem(User user, Long id) {
		user.setId(id); // Garantir que o que vai ser atualizado é o que está vindo na URI
		try
		{
			userService.updateItem(user);
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
