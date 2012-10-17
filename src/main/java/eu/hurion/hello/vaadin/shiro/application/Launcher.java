package eu.hurion.hello.vaadin.shiro.application;

import eu.hurion.vaadin.heroku.VaadinForHeroku;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;

import static eu.hurion.vaadin.heroku.FilterDefinitionBuilder.filterDefinition;
import static eu.hurion.vaadin.heroku.FilterMapBuilder.mapFilter;
import static eu.hurion.vaadin.heroku.VaadinForHeroku.herokuServer;


public class Launcher {

    public static VaadinForHeroku baseServer(){
        return VaadinForHeroku.forApplication(HerokuShiroApplication.class)
                .withApplicationListener(EnvironmentLoaderListener.class)
                .withFilterDefinition(
                        filterDefinition("ShiroFilter").withFilterClass(ShiroFilter.class))
                .withFilterMapping(
                        mapFilter("ShiroFilter").toUrlPattern("/*"));
    }


    public static void main(final String[] args) {
        herokuServer(baseServer()).start();
    }
}
