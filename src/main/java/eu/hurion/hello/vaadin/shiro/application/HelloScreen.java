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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author Nicolas Hurion
 */
@SuppressWarnings("serial")
public class HelloScreen extends VerticalLayout implements View {

    private final Button logout;

    public HelloScreen(final HerokuShiroApplication app) {
        setSizeFull();
        final Subject currentUser = SecurityUtils.getSubject();

        final Panel welcomePanel = new Panel();
        final FormLayout content = new FormLayout();
        final Label label = new Label("Logged in as " + currentUser.getPrincipal().toString());
        logout = new Button("logout");
        logout.addClickListener(new HerokuShiroApplication.LogoutListener(app));

        content.addComponent(label);
        content.addComponent(logout);
        welcomePanel.setContent(content);
        welcomePanel.setWidth("400px");
        welcomePanel.setHeight("200px");
        addComponent(welcomePanel);
        setComponentAlignment(welcomePanel, Alignment.MIDDLE_CENTER);

        final HorizontalLayout footer = new HorizontalLayout();
        footer.setHeight("50px");
        addComponent(footer);

        final Button adminButton = new Button("For admin only");
        adminButton.setEnabled(currentUser.hasRole("admin"));
        adminButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                Notification.show("you're an admin");
            }
        });
        content.addComponent(adminButton);

        final Button userButton = new Button("For users with permission 1");
        userButton.setEnabled(currentUser.isPermitted("permission_1"));
        userButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                Notification.show("you've got permission 1");
            }
        });
        content.addComponent(userButton);

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
        logout.focus();
    }
}
