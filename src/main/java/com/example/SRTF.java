package com.example;

import java.util.ArrayList;
import java.util.List;

public class SRTF {
    public static String run(List<Process> processes, double K) {
        StringBuilder sb = new StringBuilder();
        sb.append("Algoritmul 3: SRTF (Cel mai scurt timp de realizare rămas)\n");
        sb.append("Cuantul de timp K = " + K + "\n");

        List<Process> active = new ArrayList<>(processes);
        for (Process p : active) {
            p.setRemainingTime(p.getDuration());
        }

        double time = 0;
        List<String> executionOrder = new ArrayList<>();

        while (true) {
            // Alege procesul cu remaining time minim
            Process shortest = null;
            for (Process p : active) {
                if (p.getRemainingTime() > 0) {
                    if (shortest == null || p.getRemainingTime() < shortest.getRemainingTime()) {
                        shortest = p;
                    }
                }
            }
            if (shortest == null) break;

            double execTime = Math.min(K, shortest.getRemainingTime());
            shortest.setRemainingTime(shortest.getRemainingTime() - execTime);
            time += execTime;

            if (shortest.getRemainingTime() == 0) {
                shortest.setFinishTime(time);
                executionOrder.add(shortest.getPid());
            } else {
                executionOrder.add(shortest.getPid() + "(" + execTime + ")");
            }

            boolean allDone = true;
            for (Process p : active) {
                if (p.getRemainingTime() > 0) {
                    allDone = false;
                    break;
                }
            }
            if (allDone) break;
        }

        sb.append("Ordinea de realizare: ");
        for (int i = 0; i < executionOrder.size(); i++) {
            sb.append(executionOrder.get(i));
            if (i < executionOrder.size() - 1) sb.append(" -> ");
        }
        sb.append("\n");

        double sum = 0;
        for (Process p : processes) sum += p.getDuration();
        double avg = sum / processes.size();
        sb.append("Durata medie: " + String.format("%.2f", avg) + " unitati.\n");

        sb.append("\nTimpul de realizare pentru fiecare proces:\n");
        for (Process p : processes) {
            sb.append("Procesul " + p.getPid() + " - in " + String.format("%.2f", p.getFinishTime()) + " unitati\n");
        }

        // Timp mediu de rulare
        int n = executionOrder.size();
        double weightedSum = 0;
        for (int i = 0; i < n; i++) {
            String step = executionOrder.get(i);
            double exec_time;
            if (step.contains("(")) {
                int idx = step.indexOf("(");
                String timeStr = step.substring(idx+1, step.length()-1);
                exec_time = Double.parseDouble(timeStr);
            } else {
                // complet final
                Process found = null;
                for (Process px : processes) {
                    if (px.getPid().equals(step)) {
                        found = px;
                        break;
                    }
                }
                exec_time = (found != null) ? found.getDuration() : 0;
            }
            int factor = (n - i);
            weightedSum += factor * exec_time;
        }
        double timpMediuRulare = weightedSum / n;
        sb.append("\nTimpul mediu de rulare: " + String.format("%.2f", timpMediuRulare) + " unitati\n");

        return sb.toString();
    }
}
