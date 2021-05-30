package ru.ifmo.se.kastricyn.lab6.server;

import ru.ifmo.se.kastricyn.lab6.lib.exception.NotFoundPropertyException;

public final class Properties extends ru.ifmo.se.kastricyn.lab6.lib.utility.Properties {
    public static final Properties properties = new Properties();

    private Properties() {
        super();
    }

    public static Properties getProperties() {
        return properties;
    }

    public String getDBUrl() throws NotFoundPropertyException {
        //todo
        String db_url = prop.getProperty("db_url", "");
        if (db_url == null)
            throw new NotFoundPropertyException();
        return db_url;
    }

    public int getDBPort() {
        return Integer.parseInt(prop.getProperty("db_port", "5432"));
    }

    public String getDBLogin() throws NotFoundPropertyException {
        String db_login = prop.getProperty("db_login");
        if (db_login == null) throw new NotFoundPropertyException();
        return db_login;
    }

    public String getDBPass() throws NotFoundPropertyException {
        String str = prop.getProperty("db_pass");
        if (str == null) throw new NotFoundPropertyException();
        return str;
    }
}