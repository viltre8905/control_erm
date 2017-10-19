package com.verynet.gcint.api.auth;

import com.verynet.gcint.api.context.Context;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by day on 05/04/2017.
 */
public class SSAuthenticationProvider extends DaoAuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        try {

            Authentication auth = super.authenticate(authentication);

            //if corrent password, reset the user_attempts
            Context.getUserAttemptsService().resetFailAttempts(auth.getName());

            return auth;

        } catch (BadCredentialsException e) {
            //invalid login, update user_attempts, set attempts+1
            Context.getUserAttemptsService().updateFailAttempts(authentication.getName());
            throw new BadCredentialsException("Credenciales no válidas");

        } catch (LockedException e) {
            throw new LockedException("Usuario bloqueado<br>Contacte con el administrador");
        } catch (DisabledException e) {
            throw new LockedException("El usuario esta desabilitado ");
        }

    }
}
