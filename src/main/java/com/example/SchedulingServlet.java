package com.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchedulingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String algo = req.getParameter("algorithm");
        int n = Integer.parseInt(req.getParameter("n"));
        String quantumStr = req.getParameter("quantum");
        String prioritiesStr[] = req.getParameterValues("priority[]");

        String[] pids = req.getParameterValues("pid[]");
        String[] durations = req.getParameterValues("duration[]");

        List<Process> processes = new ArrayList<>();
        try {
            if ("Priority".equals(algo) && prioritiesStr != null) {
                for (int i = 0; i < n; i++) {
                    String pid = pids[i].trim();
                    double duration = Double.parseDouble(durations[i]);
                    int priority = Integer.parseInt(prioritiesStr[i]);
                    processes.add(new Process(pid, duration, priority));
                }
            } else {
                for (int i = 0; i < n; i++) {
                    String pid = pids[i].trim();
                    double duration = Double.parseDouble(durations[i]);
                    processes.add(new Process(pid, duration));
                }
            }
        } catch (NumberFormatException e) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<html><body><h1>Eroare de input!</h1><a href='/index.html'>Înapoi</a></body></html>");
            return;
        }

        String result = "";
        switch (algo) {
            case "FCFS":
                result = FCFS.run(processes);
                break;
            case "SJF":
                result = SJF.run(processes);
                break;
            case "SRTF":
                if (quantumStr != null && !quantumStr.isEmpty()) {
                    double q = Double.parseDouble(quantumStr);
                    result = SRTF.run(processes, q);
                } else {
                    result = "Quantum lipsă pentru SRTF!";
                }
                break;
            case "RoundRobin":
                if (quantumStr != null && !quantumStr.isEmpty()) {
                    double q = Double.parseDouble(quantumStr);
                    result = RoundRobin.run(processes, q);
                } else {
                    result = "Quantum lipsă pentru RoundRobin!";
                }
                break;
            case "Priority":
                result = PriorityScheduling.run(processes);
                break;
            case "ShortestNext":
                if (quantumStr != null && !quantumStr.isEmpty()) {
                    double q = Double.parseDouble(quantumStr);
                    result = ShortestNextScheduling.run(processes, q);
                } else {
                    result = "Quantum lipsă pentru algoritmul 'Cel mai scurt proces este următorul'!";
                }
                break;
            default:
                result = "Algoritm necunoscut!";
        }

        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println("<html><head><link rel='stylesheet' href='style.css'></head><body>");
        resp.getWriter().println("<h1>Rezultat</h1>");
        resp.getWriter().println("<pre>" + result + "</pre>");
        resp.getWriter().println("<a href='/index.html'>Înapoi</a>");
        resp.getWriter().println("</body></html>");
    }
}
