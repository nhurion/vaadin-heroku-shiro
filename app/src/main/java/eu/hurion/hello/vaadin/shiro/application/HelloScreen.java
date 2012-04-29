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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author Nicolas Hurion
 */
@SuppressWarnings("serial")
public class HelloScreen extends VerticalLayout {
    private final HerokuShiroApplication app;

    public HelloScreen(final HerokuShiroApplication app) {
        this.app = app;
        setSizeFull();
        final Subject currentUser = SecurityUtils.getSubject();
        final Panel welcomePanel = new Panel();

        final Label label = new Label("Logged in as " + currentUser.getPrincipal().toString());

        Button logout = new Button("logout");
        logout.addListener(new HerokuShiroApplication.LogoutListener(this.app));
        welcomePanel.addComponent(label);
        welcomePanel.addComponent(logout);
        welcomePanel.setWidth("400px");
        welcomePanel.setHeight("200px");
        addComponent(welcomePanel);
        setComponentAlignment(welcomePanel, Alignment.MIDDLE_CENTER);
        HorizontalLayout footer = new HorizontalLayout();
        footer.setHeight("50px");
        addComponent(footer);

        final Button adminButton = new Button("For admin only");
        adminButton.setEnabled(currentUser.hasRole("admin"));
        adminButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getWindow().showNotification("you're an admin");
            }
        });
        welcomePanel.addComponent(adminButton);

        final Button userButton = new Button("For all users");
        userButton.setEnabled(currentUser.isPermitted("permission_1"));
        userButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getWindow().showNotification("you've permission 1");
            }
        });
        welcomePanel.addComponent(userButton);

    }
}
