package com.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJF {
    public static String run(List<Process> processes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Algoritmul 2: Cel mai scurt proces primul (SJF)\n");

        // sort by duration ascending
        Collections.sort(processes, Comparator.comparingDouble(Process::getDuration));

        sb.append("Ordinea de realizare: ");
        for (int i = 0; i < processes.size(); i++) {
            sb.append(processes.get(i).getPid());
            if (i < processes.size() - 1) sb.append(" -> ");
        }
        sb.append("\n");

        // durata medie
        double sum = 0;
        for (Process p: processes) sum += p.getDuration();
        double avg = sum / processes.size();
        sb.append("Durata medie: " + String.format("%.2f", avg) + " unitati.\n");

        // Timp de realizare
        double cumulative = 0;
        sb.append("\nTimpul de realizare pentru fiecare proces:\n");
        for (int i = 0; i < processes.size(); i++) {
            cumulative += processes.get(i).getDuration();
            processes.get(i).setFinishTime(cumulative);
            sb.append("Procesul " + (i+1) + " (" + processes.get(i).getPid() + ") - in " 
                      + String.format("%.2f", cumulative) + " unitati\n");
        }

        // Timp mediu de rulare
        int n = processes.size();
        double weightedSum = 0;
        for (int i = 0; i < n; i++) {
            double d = processes.get(i).getDuration();
            weightedSum += (n - i) * d;
        }
        double timpMediuRulare = weightedSum / n;
        sb.append("\nTimpul mediu de rulare: " + String.format("%.2f", timpMediuRulare) + " unitati\n");
        return sb.toString();
    }
}
