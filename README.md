# Volunteer Platform


## What do we do?

Our solution offers sustainable, fundraising and communication platform connecting volunteers and non-profit organisations. By being operated in form of a non-profit organisation, we will always keep up living up to our promise: non-profits before profit. 

## How do we do it?

We build an open source platform together, by volunteers, where this vision can come true.
We believe that technology is a great tool in our hands, it can serve our goal, the synergy of communities.


## Configuration

### Spring / Hybernate

The application.properties uses a default configuration for development, as the following:

Setup a new postgresql database:

```
psql -c "CREATE DATABASE volunti_dev"
```

Setup a new user with the same username and password, and grant access:

```
createuser -P -e volunti_dev
psql -U postgres -c "grant all privileges on database volunti_dev to volunti_dev"
```

## Install

#### Technical requirements

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Lombok plugin](https://dzone.com/articles/tired-getters-and-setters) in IntelliJ:
    - File → Settings → Plugins → Browse Repositories (button) → Search Lombok → Install  this plugin and Restart IntelliJ 
- [Spring Integration Mail Support](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-email.html)


