package backendClip.baclend.entity;

import backendClip.baclend.dto.user.UserUpdateRequest;
import backendClip.baclend.dto.user.UserUpdateResponse;
import jakarta.persistence.*;
import lombok.*;

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

  @OneToMany(mappedBy = "user")
  List<Resume> resumes = new ArrayList<>();

  public User update(String name, String password, String email){
    this.name = name;
    this.password = password;
    this.email = email;
    return this;
  }

  @Builder
  public User(String name, String password, String email){
    this.email = email;
    this.password = password;
    this.name = name;
  }

  public UserUpdateResponse update(UserUpdateRequest request) {
    this.name = request.getName();
    this.password = request.getPassword();
    this.email = request.getEmail();

    return UserUpdateResponse.builder()
            .name(this.name)
            .email(this.email)
            .password(this.password)
            .build();
  }
}
