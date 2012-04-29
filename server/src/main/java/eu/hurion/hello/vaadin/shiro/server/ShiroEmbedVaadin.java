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

package eu.hurion.hello.vaadin.shiro.server;

import com.bsb.common.vaadin.embed.EmbedVaadinConfig;
import com.bsb.common.vaadin.embed.EmbedVaadinServer;
import com.bsb.common.vaadin.embed.application.ApplicationBasedEmbedVaadinTomcat;
import com.bsb.common.vaadin.embed.application.EmbedVaadinApplication;
import com.vaadin.Application;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;

/**
 * Custo;ized version of EmbedVaadinApplication to
 *
 * @author Nicolas Hurion
 */
public class ShiroEmbedVaadin extends EmbedVaadinApplication {
    private EmbedVaadinConfig config;

    /**
     * Creates a new instance for the specified application.
     *
     * @param applicationClass the class of the application to deploy
     */
    public ShiroEmbedVaadin(Class<? extends Application> applicationClass) {
        super(applicationClass);
    }

    @Override
    protected ShiroEmbedVaadin self() {
        return this;
    }

    @Override
    public EmbedVaadinServer build() {
        return new EmbedVaadinWithShiro(getConfig(),getApplicationClass());
    }

    public static ShiroEmbedVaadin forApplication(Class<? extends Application> applicationClass){
        return new ShiroEmbedVaadin(applicationClass);
    }


    /**
     * An {@link EmbedVaadinServer} implementation that starts and use
     * Spring behind the scene.
     */
    private static class EmbedVaadinWithShiro extends ApplicationBasedEmbedVaadinTomcat {


        /**
         * Creates a new instance.
         *
         * @param config           the config to use
         * @param applicationClass the class of the application to handle
         */
        public EmbedVaadinWithShiro(EmbedVaadinConfig config, Class<? extends Application> applicationClass) {
            super(config, applicationClass);
        }

        @Override
        protected void configure() {
            super.configure();
            FilterDef filterDef = new FilterDef();
            filterDef.setFilterName("ShiroFilter");
            filterDef.setFilterClass("org.apache.shiro.web.servlet.IniShiroFilter");
            getContext().addFilterDef(filterDef);
            FilterMap filterMap = new FilterMap();
            filterMap.setFilterName("ShiroFilter");
            filterMap.addURLPattern("/*");
            getContext().addFilterMap(filterMap);
        }
    }

}
