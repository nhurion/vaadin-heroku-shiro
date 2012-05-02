Vaadin on Heroku with Shiro as Security provider
================================================

This project is a skeleton application with multiple modules and a simple configuration of Apache Shiro for authentication/authorization.
It's ready to be deployed on [Heroku](http://www.heroku.com).

It's based on [vaadin-heroku-multi](https://github.com/nhurion/vaadin-heroku-multi) and adding [Shiro](http://shiro.apache.org) to the stack.

The Shiro integration is largelly based on [vaadin-shiro-simple](https://github.com/eneuwirt/vaadin-shiro-simple)

As an example, this application is deployed on heroku and accessible at http://vaadin-heroku-shiro.herokuapp.com/

You can log-in as admin/admin or demo/demo

What's in there?
----------------

In the server module, there a customized version of EmbedForVaadin to add the Shiro servlet Filter from shiro-web.
In the app module, a login screen, a screen to show after login. There is also the shiro.ini file that contain user/roles/permissions definition.