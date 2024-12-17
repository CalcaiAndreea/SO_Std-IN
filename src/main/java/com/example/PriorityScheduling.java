package com.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduling {
    public static String run(List<Process> processes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Algoritmul 5: Planificare pe Bază de Prioritate\n");

        sb.append("Prioritățile proceselor: ");
        for (int i = 0; i < processes.size(); i++) {
            sb.append(processes.get(i).getPid() + "-" + processes.get(i).getPriority());
            if (i < processes.size() - 1) sb.append(" ");
        }
        sb.append("\n");

        Collections.sort(processes, Comparator.comparingInt(Process::getPriority).reversed());

        sb.append("Ordinea de realizare: ");
        for (int i = 0; i < processes.size(); i++) {
            sb.append(processes.get(i).getPid());
            if (i < processes.size() - 1) sb.append(" -> ");
        }
        sb.append("\n");

        double sum = 0;
        for (Process p : processes) {
            sum += p.getDuration();
        }
        double avg = sum / processes.size();
        sb.append("Durata medie: " + String.format("%.2f", avg) + " unitati.\n");

        double cumulative = 0;
        sb.append("\nTimpul de realizare pentru fiecare proces:\n");
        for (int i = 0; i < processes.size(); i++) {
            cumulative += processes.get(i).getDuration();
            processes.get(i).setFinishTime(cumulative);
            sb.append("Procesul " + processes.get(i).getPid() + " - in " 
                      + String.format("%.2f", cumulative) + " unitati\n");
        }

        int n = processes.size();
        double weightedSum = 0;
        for (int i = 0; i < n; i++) {
            weightedSum += (n - i) * processes.get(i).getDuration();
        }
        double timpMediuRulare = weightedSum / n;
        sb.append("\nTimpul mediu de rulare: " + String.format("%.2f", timpMediuRulare) + " unitati\n");

        return sb.toString();
    }
}
