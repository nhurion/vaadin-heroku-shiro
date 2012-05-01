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

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nicolas Hurion
 */
public class HerokuShiroApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(HerokuShiroApplication.class);

    @Override
    public void init() {
        final Window window = new Window();
        setMainWindow(window);
        window.setContent(new LoginScreen(this));

    }

    public void login(final String username, final String password) {
        LOGGER.debug("trying to log as " + username);
        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        final Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }


    public void logout() {
        getMainWindow().getApplication().close();
        final Subject currentUser = SecurityUtils.getSubject();
        LOGGER.debug("User "+ currentUser.getPrincipal() + " logging out");

        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
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
