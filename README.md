# Volunteer Platform

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

