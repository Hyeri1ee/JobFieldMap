package backendClip.baclend.service;

import backendClip.baclend.dto.user.UserJoinRequest;
import backendClip.baclend.dto.user.UserJoinResponse;
import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import backendClip.baclend.entity.User;
import backendClip.baclend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserJoinResponse register(UserJoinRequest request) {
    User user = User.builder()
            .name(request.getName())
            .password(request.getPassword())
            .email(request.getEmail())
            .build();
    userRepository.save(user);
    return new UserJoinResponse(user);
  }

  public UserUpdateResponse update(Long id, UserUpdateRequest request) {
    User user = userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("user not found. id= " + id));
    UserUpdateResponse response = user.update(request);
    userRepository.save(user);
    return response;
  }

  public String remove(Long id) {
    User user = userRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("user not found. id= " + id));
    userRepository.delete(user);
    return "user is deleted";
  }
}