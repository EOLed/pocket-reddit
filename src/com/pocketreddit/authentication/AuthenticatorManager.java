package com.pocketreddit.authentication;

import com.pocketreddit.library.authentication.AuthenticationException;
import com.pocketreddit.library.authentication.Authenticator;
import com.pocketreddit.library.authentication.LiveAuthenticator;
import com.pocketreddit.library.authentication.LoginResult;

public class AuthenticatorManager {
    private Authenticator authenticator;
    private Delegate delegate;

    public interface Delegate {
        public void onAuthenticate(LoginResult loginResult);

        public void onAuthenticateFailed(AuthenticationException e);
    }

    public AuthenticatorManager(Delegate delegate) {
        authenticator = new LiveAuthenticator();
        this.delegate = delegate;
    }

    public boolean isLoggedIn() {
        return authenticator.isLoggedIn();
    }

    public void authenticate(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    delegate.onAuthenticate(authenticator.authenticate(username, password));
                } catch (AuthenticationException e) {
                    delegate.onAuthenticateFailed(e);
                }
            }
        }).start();
    }
}
