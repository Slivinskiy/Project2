package sviatoslav_slivinskyi_project_2.spring_application.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;


public interface AuthenticationService extends AuthenticationProvider {

   @Override
   Authentication authenticate(Authentication authentication);

   @Override
   boolean supports(Class<?> authentication);
}
