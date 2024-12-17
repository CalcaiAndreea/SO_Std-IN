package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShortestNextScheduling {
    public static String run(List<Process> processes, double K) {
        StringBuilder sb = new StringBuilder();
        sb.append("Algoritmul: Cel mai scurt proces este următorul\n");
        sb.append("Cuantul de timp K = " + K + "\n\n");

        // Inițializare coadă și setare timp rămas pentru fiecare proces
        List<Process> queue = new ArrayList<>(processes);
        for (Process p : queue) {
            p.setRemainingTime(p.getDuration());
        }

        double time = 0;
        List<String> executionOrder = new ArrayList<>();

        // Algoritmul de planificare
        while (!queue.isEmpty()) {
            // Sortează coada după timpul rămas (cel mai scurt timp rămas primul)
            Collections.sort(queue, Comparator.comparingDouble(Process::getRemainingTime));
            Process current = queue.remove(0);
            double execTime = Math.min(K, current.getRemainingTime());
            current.setRemainingTime(current.getRemainingTime() - execTime);
            time += execTime;
            executionOrder.add(current.getPid() + "(" + (int) execTime + ")");

            if (current.getRemainingTime() > 0) {
                queue.add(current);
            } else {
                current.setFinishTime(time);
            }
        }

        // Afișarea ordinii de execuție
        sb.append("Ordinea de realizare: ");
        for (int i = 0; i < executionOrder.size(); i++) {
            sb.append(executionOrder.get(i));
            if (i < executionOrder.size() - 1) sb.append(" -> ");
        }
        sb.append("\n\n");

        // Calcularea timpului mediu de finalizare (average finish time)
        double sumFinishTimes = 0;
        for (Process p : processes) {
            sumFinishTimes += p.getFinishTime();
        }
        double avgFinishTime = sumFinishTimes / processes.size();
        sb.append("Durata medie de finalizare: " + String.format("%.2f", avgFinishTime) + " unități.\n\n");

        // Calcularea timpului mediu ponderat de rulare (weighted average)
        int totalSteps = executionOrder.size();
        double weightedSum = 0;
        for (int i = 0; i < executionOrder.size(); i++) {
            int weight = totalSteps - i;
            String step = executionOrder.get(i);
            double exec_time = 0;
            if (step.contains("(")) {
                int idx = step.indexOf("(");
                String timeStr = step.substring(idx + 1, step.length() - 1);
                exec_time = Double.parseDouble(timeStr);
            } else {
                exec_time = 0;
            }
            weightedSum += weight * exec_time;
        }
        double avgWeighted = weightedSum / totalSteps;
        sb.append("Timpul mediu ponderat de rulare: " + String.format("%.2f", avgWeighted) + " unități.\n");

        // Afișarea timpului de finalizare pentru fiecare proces
        sb.append("\nTimpul de realizare pentru fiecare proces:\n");
        for (Process p : processes) {
            sb.append("Procesul " + p.getPid() + " - în " + String.format("%.2f", p.getFinishTime()) + " unități\n");
        }

        return sb.toString();
    }
}
