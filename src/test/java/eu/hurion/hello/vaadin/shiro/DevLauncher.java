package eu.hurion.hello.vaadin.shiro;

import eu.hurion.hello.vaadin.shiro.application.Launcher;

import static eu.hurion.vaadin.heroku.VaadinForHeroku.localServer;


public class DevLauncher {
    public static void main(final String[] args) {
        localServer(Launcher.baseServer()).start();
    }
}
