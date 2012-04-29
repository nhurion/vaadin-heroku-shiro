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
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Window;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nicolas Hurion
 */
public class HerokuShiroApplication extends Application {

    @Override
    public void init() {
        final Window window = new Window();
        setMainWindow(window);
        window.setContent(new LoginScreen(this));

    }

    public void login(final String username, final String password) {
        final UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        final Subject currentUser = SecurityUtils.getSubject();

        currentUser.login(token);
    }


    public void logout() {
        getMainWindow().getApplication().close();
        final Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            currentUser.logout();
        }
    }

    // Logout Listener is defined for the application
    public static class LogoutListener implements Button.ClickListener {
        private static final long serialVersionUID = 1L;
        private HerokuShiroApplication app;

        public LogoutListener(final HerokuShiroApplication app) {
            this.app = app;
        }


        @Override
        public void buttonClick(final Button.ClickEvent event) {
            this.app.logout();
        }
    }


}
