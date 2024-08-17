package backendClip.baclend.entity;

import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name" , nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  private String role;

  @OneToMany(mappedBy = "user")
  List<Resume> resumes = new ArrayList<>();

  @Builder
  public User(String name, String password, String email, String role){
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
  }

  public UserUpdateResponse update(UserUpdateRequest request) {
    this.name = request.getName();
    this.password = request.getPassword();
    this.email = request.getEmail();
    this.role = request.getRole();

    return UserUpdateResponse.builder()
            .name(this.name)
            .email(this.email)
            .password(this.password)
            .role(this.role)
            .build();
  }

}
