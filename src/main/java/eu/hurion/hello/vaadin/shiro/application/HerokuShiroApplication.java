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

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nicolas Hurion
 */
public class HerokuShiroApplication extends UI {

    private static final Logger LOGGER = LoggerFactory.getLogger(HerokuShiroApplication.class);

    @Override
    public void init(final VaadinRequest vaadinRequest) {
        new Navigator(this, this);
        final LoginScreen loginScreen = new LoginScreen(this);
        getUI().getNavigator().addView("Login", loginScreen);


        //
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                final boolean isLoggedIn = SecurityUtils.getSubject().isAuthenticated();
                final boolean isLoginView = event.getNewView() instanceof LoginScreen;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo("Login");
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });

        getUI().getNavigator().navigateTo("Login");
    }

    public void login(final String username, final String password) {
        LOGGER.debug("trying to log as " + username);
        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        final Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        final HelloScreen helloScreen = new HelloScreen(this);

        getUI().getNavigator().addView("Hello", helloScreen);
        getUI().getNavigator().navigateTo("Hello");
    }

    public void logout() {
        getSession().close();
        final Subject currentUser = SecurityUtils.getSubject();
        LOGGER.debug("User "+ currentUser.getPrincipal() + " logging out");

        if (currentUser.isAuthenticated()) {
            currentUser.logout();
            getNavigator().removeView("Hello");
        }
        getUI().getNavigator().navigateTo("Login");
    }

    public static class LogoutListener implements Button.ClickListener {
        private final HerokuShiroApplication app;

        public LogoutListener(final HerokuShiroApplication app) {
            this.app = app;
        }


        @Override
        public void buttonClick(final Button.ClickEvent event) {
            this.app.logout();
        }
    }


}
