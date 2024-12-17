package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerApp { // Redenumit pentru a evita conflictele
    public static void startServer(int port) throws Exception {
        Server server = new Server(port);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");

        // Servim resursele statice din /public
        ServletHolder defaultServlet = new ServletHolder("default", DefaultServlet.class);
        defaultServlet.setInitParameter("resourceBase", ServerApp.class.getResource("/public").toExternalForm());
        defaultServlet.setInitParameter("dirAllowed", "false");
        handler.addServlet(defaultServlet, "/");

        // Servlet pentru procesare
        handler.addServlet(SchedulingServlet.class, "/schedule");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
