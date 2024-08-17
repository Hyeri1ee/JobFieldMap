package backendClip.baclend.controller;

import backendClip.baclend.dto.user.*;
import backendClip.baclend.entity.User;
import backendClip.baclend.jwt.JwtUtil;
import backendClip.baclend.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userServiceImpl) {
    this.userService = userServiceImpl;
  }

  //join (로그인 없이는 요기만 access 가능)
  @PostMapping(value = "/join", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity joinUser(@RequestBody UserJoinRequest request){
    UserJoinResponse userJoinResponse = userService.join(request);
    Map<String, Object> result = new HashMap<>();
    result.put("response", userJoinResponse);
    return ResponseEntity.ok(result);
  }
  //profile
  @GetMapping(value = "user/profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity profileUser(@PathVariable("id") Long id, @RequestHeader("Authorization") String token){
    try {
      System.out.println(new JwtUtil().isExpired(token));
      User user = userService.findById(id,token);
      Map<String, Object> result = new HashMap<>();
      result.put("response", user);
      return ResponseEntity.ok(result);
    } catch (IllegalStateException | IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
  }

  //update info
  @PostMapping(value = "user/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
    UserUpdateResponse userUpdateResponse = userService.update(id, request);
    Map<String, Object> result = new HashMap<>();
    result.put("response", userUpdateResponse);
    return ResponseEntity.ok(result);
  }

  //withdraw
  @DeleteMapping(value = "user/delete/{id}")
  public ResponseEntity removeUser(@PathVariable("id") Long id){
    String message =  userService.remove(id);
    return ResponseEntity.status(HttpStatus.OK).body(message);
  }
}
