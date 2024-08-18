package backendClip.baclend.service;

import backendClip.baclend.dto.JoinDTO;
import backendClip.baclend.entity.UserEntity;
import backendClip.baclend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public String joinProcess(JoinDTO joinDTO){
    String username = joinDTO.getUsername();
    String password = joinDTO.getPassword();

    Boolean isExist = userRepository.existsByUsername(username);

    if(isExist){

      return null;
    }
    UserEntity data = new UserEntity();
    data.setUsername(username);
    data.setPassword(bCryptPasswordEncoder.encode(password)); //password encode해서 넣기
    System.out.println(data.getPassword());
    data.setRole("ROLE_ADMIN");

    userRepository.save(data);
    return username;
  }
}
