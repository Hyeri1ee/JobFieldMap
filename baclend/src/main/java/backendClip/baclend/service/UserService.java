package backendClip.baclend.service;

import backendClip.baclend.dto.user.UserJoinRequest;
import backendClip.baclend.dto.user.UserJoinResponse;

public interface UserService {
  public UserJoinResponse join(UserJoinRequest request);
}
