/*
 *
 *  Copyright (c) 2012 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package eu.hurion.hello.vaadin.shiro.application;

import com.vaadin.ui.*;
import org.apache.shiro.authc.*;

/**
 * @author Nicolas Hurion
 */
@SuppressWarnings("serial")
public class LoginScreen extends VerticalLayout {


    public LoginScreen(final HerokuShiroApplication app) {
        setSizeFull();

        final Panel loginPanel = new Panel("Login");
        loginPanel.setWidth("400px");

        final LoginForm loginForm = new LoginForm();
        loginForm.setPasswordCaption("Password");
        loginForm.setUsernameCaption("User");
        loginForm.setLoginButtonCaption("Log in");

        loginForm.setHeight("100px");
        loginForm.addListener(new ShiroLoginListener(app, loginForm));

        loginPanel.addComponent(loginForm);

        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setHeight("50px");
        addComponent(footer);
    }

    private static final class ShiroLoginListener implements LoginForm.LoginListener {

        private final HerokuShiroApplication app;
        private final LoginForm loginForm;

        public ShiroLoginListener(final HerokuShiroApplication app, final LoginForm loginForm) {
            this.app = app;
            this.loginForm = loginForm;
        }

        @Override
        public void onLogin(final LoginForm.LoginEvent event) {
            final String username = event.getLoginParameter("username");
            final String password = event.getLoginParameter("password");

            try {
                app.login(username, password);
                app.getMainWindow().setContent(new HelloScreen(app));
            } catch (UnknownAccountException uae) {
                this.loginForm.getWindow().showNotification("Invalid User", Window.Notification.TYPE_ERROR_MESSAGE);
            } catch (IncorrectCredentialsException ice) {
                this.loginForm.getWindow().showNotification("Invalid User", Window.Notification.TYPE_ERROR_MESSAGE);
            } catch (LockedAccountException lae) {
                this.loginForm.getWindow().showNotification("Invalid User", Window.Notification.TYPE_ERROR_MESSAGE);
            } catch (ExcessiveAttemptsException eae) {
                this.loginForm.getWindow().showNotification("Invalid User", Window.Notification.TYPE_ERROR_MESSAGE);
            } catch (AuthenticationException ae) {
                this.loginForm.getWindow().showNotification("Invalid User", Window.Notification.TYPE_ERROR_MESSAGE);
            } catch (Exception ex) {
                this.loginForm.getWindow().showNotification("Exception " + ex.getMessage(), Window.Notification.TYPE_ERROR_MESSAGE);
            }
        }
    }
}
