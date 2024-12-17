package com.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin {
    public static String run(List<Process> processes, double K) {
        StringBuilder sb = new StringBuilder();
        sb.append("Algoritmul 4: Round Robin\n");
        sb.append("Cuantul de timp K = " + K + "\n");

        Queue<Process> queue = new LinkedList<>(processes);
        for (Process p : processes) {
            p.setRemainingTime(p.getDuration());
        }

        double time = 0;
        List<String> executionOrder = new LinkedList<>();

        while (!queue.isEmpty()) {
            Process current = queue.poll();
            double execTime = Math.min(K, current.getRemainingTime());
            current.setRemainingTime(current.getRemainingTime() - execTime);
            time += execTime;
            executionOrder.add(current.getPid() + "(" + execTime + ")");
            if (current.getRemainingTime() > 0) {
                queue.add(current);
            } else {
                current.setFinishTime(time);
            }
        }

        double sum = 0;
        for (Process p : processes) sum += p.getDuration();
        double avg = sum / processes.size();

        sb.append("Ordinea de realizare: ");
        for (int i = 0; i < executionOrder.size(); i++) {
            sb.append(executionOrder.get(i));
            if (i < executionOrder.size() - 1) sb.append(" -> ");
        }
        sb.append("\nDurata medie: " + String.format("%.2f", avg) + " unitati.\n");

        sb.append("\nTimpul de realizare pentru fiecare proces:\n");
        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            sb.append("Procesul " + (i+1) + " (" + p.getPid() + ") - in " 
                      + String.format("%.2f", p.getFinishTime()) + " unitati\n");
        }

        int n = executionOrder.size();
        double weightedSum = 0;
        for (int i = 0; i < n; i++) {
            String step = executionOrder.get(i);
            int idx = step.indexOf("(");
            String timeStr = step.substring(idx+1, step.length()-1);
            double exec_time = Double.parseDouble(timeStr);
            int factor = (n - i);
            weightedSum += factor * exec_time;
        }
        double timpMediuRulare = weightedSum / n;
        sb.append("\nTimpul mediu de rulare: " + String.format("%.2f", timpMediuRulare) + " unitati\n");

        return sb.toString();
    }
}
