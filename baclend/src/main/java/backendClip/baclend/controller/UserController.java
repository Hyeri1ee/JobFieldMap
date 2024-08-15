package backendClip.baclend.controller;

import backendClip.baclend.dto.user.UserJoinRequest;
import backendClip.baclend.dto.user.UserJoinResponse;
import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import backendClip.baclend.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userServiceImpl) {
    this.userService = userServiceImpl;
  }

  //register
  @PostMapping("/register")
  public ResponseEntity<UserJoinResponse> registerUser(@RequestBody UserJoinRequest request){
    UserJoinResponse userJoinResponse = userService.register(request);
    return ResponseEntity.status(HttpStatus.OK)
            .body(userJoinResponse);
  }
  //login

  //logout

  //update info
  @PutMapping("/update")
  public ResponseEntity<UserUpdateResponse> updateUser(@RequestParam Long id, @RequestBody UserUpdateRequest request){
    UserUpdateResponse userUpdateResponse = userService.update(id,request);
    return ResponseEntity.status(HttpStatus.OK)
            .body(userUpdateResponse);
  }

  //withdraw
  @DeleteMapping("/remove")
  public ResponseEntity removeUser(@RequestParam Long id){
    String message =  userService.remove(id);
    return ResponseEntity.status(HttpStatus.OK).body(message);
  }
}
