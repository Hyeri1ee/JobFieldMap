package backendClip.baclend.controller;

import backendClip.baclend.dto.user.UserJoinRequest;
import backendClip.baclend.dto.user.UserJoinResponse;
import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import backendClip.baclend.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userServiceImpl) {
    this.userService = userServiceImpl;
  }

  //register
  @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity registerUser(@RequestBody UserJoinRequest request){
    UserJoinResponse userJoinResponse = userService.register(request);
    Map<String, Object> result = new HashMap<>();
    result.put("response", userJoinResponse);
    return ResponseEntity.ok(result);
  }
  //login

  //logout

  //update info
  @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
    UserUpdateResponse userUpdateResponse = userService.update(id, request);
    Map<String, Object> result = new HashMap<>();
    result.put("response", userUpdateResponse);
    return ResponseEntity.ok(result);
  }

  //withdraw
  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity removeUser(@PathVariable("id") Long id){
    String message =  userService.remove(id);
    return ResponseEntity.status(HttpStatus.OK).body(message);
  }
}
