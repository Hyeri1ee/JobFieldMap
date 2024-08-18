package backendClip.baclend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
public class MainController {

  @GetMapping("/")
  public String mainP(){ //세션이 유지되는 동안은 jwt 를 통해서 사용자 정보를 추출할 수 있음

    //세션에서 name 추출
    String name = SecurityContextHolder.getContext().getAuthentication().getName();

    //세션에서 role 추출
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iter = authorities.iterator();
    GrantedAuthority auth= iter.next();
    String role = auth.getAuthority();
    
    return "Main Controller " + name + ":" + role;
  }
}
