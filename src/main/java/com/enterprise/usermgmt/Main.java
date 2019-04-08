package com.enterprise.usermgmt;

import com.enterprise.usermgmt.services.users.UsersServer;
import com.enterprise.usermgmt.services.registration.RegistrationServer;

public class Main {

    private static final String REGISTRATION = "registration";
    private static final String USERS = "users";

    public static void main(String[] args) {

        String serverName;

        switch (args.length) {
            case 2:
                System.setProperty("server.port", args[1]);
            case 1:
                serverName = args[0].toLowerCase();
                break;
            default:
                usage();
                return;
        }
        if (REGISTRATION.equals(serverName)) {
            RegistrationServer.main(args);
        } else if (USERS.equals(serverName)) {
            UsersServer.main(args);
        } else {
            System.out.println("Unknown server type: " + serverName);
            usage();
        }
    }

    protected static void usage() {
        System.out.println("Usage: java -jar ... <server-name> [server-port]");
    }
}
