package backendClip.baclend.service;

import backendClip.baclend.dto.user.*;
import backendClip.baclend.entity.User;
import backendClip.baclend.jwt.JwtUtil;
import backendClip.baclend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtUtil jwtUtil;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtUtil = jwtUtil;
  }


  @Override
  public UserJoinResponse join(UserJoinRequest request) {
    User user = User.builder()
            .name(request.getName())
            .password(bCryptPasswordEncoder.encode(request.getPassword())) //비밀번호 암호화
            .email(request.getEmail())
            .role(request.getRole())
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

  public User findById(Long id, String token) {
    if (token == null || jwtUtil.isExpired(token))
      throw new IllegalStateException("token is invalid");
    String name = jwtUtil.getUserName(token);
    User savedOne = userRepository.findById(id).get();
    User user = userRepository.findByName(name);
    if (savedOne.getName().equals(user.getName()))
      return user;
    else
      throw new IllegalArgumentException("Not authorized to access this profile");
  }


}