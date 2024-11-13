

import java.util.*;

public class prioritynonprem {

    // Method to calculate the average waiting time, turnaround time, and print the results
    public static void findAvgTime(int n, int[] arrivalTime, int[] burstTime, int[] priority) {
        int[] startTime = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        boolean[] isCompleted = new boolean[n]; // To check if the process is completed

        Arrays.fill(startTime, -1); // No process has started yet

        List<Integer> processOrder = new ArrayList<>();
        int time = 0, completed = 0;
        int totalWaitTime = 0, totalTurnAroundTime = 0;

        while (completed < n) {
            // Find the highest-priority process that has arrived and is not yet completed
            int current = -1;
            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= time) {
                    if (current == -1 || priority[i] > priority[current]) {
                        current = i;
                    }
                }
            }

            if (current != -1) {
                // Set start time if this is the first time the process is running
                if (startTime[current] == -1) {
                    startTime[current] = time;
                }

                // Process runs to completion
                time += burstTime[current];
                completionTime[current] = time;
                turnAroundTime[current] = completionTime[current] - arrivalTime[current];
                waitingTime[current] = turnAroundTime[current] - burstTime[current];

                totalWaitTime += waitingTime[current];
                totalTurnAroundTime += turnAroundTime[current];

                isCompleted[current] = true;
                completed++;
                processOrder.add(current);  // Track the order of execution
            } else {
                // If no process is ready, advance time to the next arrival
                time++;
            }
        }

        // Output process details and average times
        System.out.println("\nProcess\tArrival Time\tBurst Time\tPriority\tStart Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" + priority[i]
                    + "\t\t" + startTime[i] + "\t\t" + completionTime[i] + "\t\t" + turnAroundTime[i] + "\t\t" + waitingTime[i]);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", (float) totalWaitTime / n);
        System.out.printf("Average Turnaround Time: %.2f\n", (float) totalTurnAroundTime / n);

        System.out.print("Process Order: ");
        for (Integer processIndex : processOrder) {
            System.out.print("P" + (processIndex + 1) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the total number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];

        // Input Arrival Time, Burst Time, and Priority for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the Arrival time, Burst time, and Priority for Process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
            priority[i] = scanner.nextInt();
        }

        // Sort processes by arrival time
        Integer[] processIds = new Integer[n];
        for (int i = 0; i < n; i++) {
            processIds[i] = i;
        }

        Arrays.sort(processIds, Comparator.comparingInt(i -> arrivalTime[i])); // Sort by arrival time

        // Rearranging arrays based on sorted order of processIds
        int[] sortedArrivalTime = new int[n];
        int[] sortedBurstTime = new int[n];
        int[] sortedPriority = new int[n];

        for (int i = 0; i < n; i++) {
            sortedArrivalTime[i] = arrivalTime[processIds[i]];
            sortedBurstTime[i] = burstTime[processIds[i]];
            sortedPriority[i] = priority[processIds[i]];
        }

        // Calculate and print average times and process order
        findAvgTime(n, sortedArrivalTime, sortedBurstTime, sortedPriority);

        scanner.close();
    }
}
