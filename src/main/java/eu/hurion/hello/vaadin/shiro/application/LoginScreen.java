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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

/**
 * @author Nicolas Hurion
 */
@SuppressWarnings("serial")
public class LoginScreen extends VerticalLayout implements View {


    private final TextField user;
    private final PasswordField password;

    public LoginScreen(final HerokuShiroApplication app) {
        setSizeFull();

        final Panel loginPanel = new Panel("Login");
        loginPanel.setWidth("300px");
        final FormLayout content = new FormLayout();
        content.setSizeFull();
        user = new TextField("User");
        content.addComponent(user);
        password = new PasswordField("Password");
        content.addComponent(password);
        final Button loginButton = new Button("Log in");
        content.setHeight("150px");
        loginButton.addClickListener(new ShiroLoginListener(app));
        content.addComponent(loginButton);
        loginPanel.setContent(content);

        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setHeight("50px");
        addComponent(footer);
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
        user.focus();
    }

    private final class ShiroLoginListener implements Button.ClickListener {

        private final HerokuShiroApplication app;

        public ShiroLoginListener(final HerokuShiroApplication app) {
            this.app = app;
        }


        @Override
        public void buttonClick(final Button.ClickEvent event) {
            app.login(user.getValue(), password.getValue());
            password.setValue("");
        }
    }
}
