

import java.util.*;

public class priorpemtive {

    // Method to calculate the average waiting time, turnaround time, and print the results
    public static void findAvgTime(int n, int[] arrivalTime, int[] burstTime, int[] priority) {
        int[] startTime = new int[n];
        int[] completionTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] remainingTime = new int[n];  // Remaining Burst Time for Preemption
        boolean[] isCompleted = new boolean[n]; // To check if the process is completed

        Arrays.fill(startTime, -1); // No process has started yet
        Arrays.fill(remainingTime, 0); // Initially remaining time is 0
        Arrays.fill(isCompleted, false);

        List<Integer> processOrder = new ArrayList<>();
        Deque<Integer> readyQueue = new LinkedList<>();
        int time = 0, completed = 0;
        int totalWaitTime = 0, totalTurnAroundTime = 0;
        
        int i = 0; // Index to track unprocessed processes

        while (completed < n) {
            // Add all processes that have arrived by current time
            while (i < n && arrivalTime[i] <= time) {
                readyQueue.add(i);
                i++;
            }

            // Sort the ready queue by priority
            List<Integer> sortedQueue = new ArrayList<>(readyQueue);
            sortedQueue.sort((a, b) -> priority[b] - priority[a]); // Sorting by priority (higher priority first)

            if (!sortedQueue.isEmpty()) {
                int current = sortedQueue.get(0);
                readyQueue.remove(current);

                processOrder.add(current);

                if (startTime[current] == -1) {
                    startTime[current] = time;  // Set start time for the first execution
                }

                // Process one unit of time
                remainingTime[current] -= 1;
                time++;

                // If the process finishes
                if (remainingTime[current] == 0) {
                    completionTime[current] = time;
                    turnAroundTime[current] = completionTime[current] - arrivalTime[current];
                    waitingTime[current] = turnAroundTime[current] - burstTime[current];

                    totalWaitTime += waitingTime[current];
                    totalTurnAroundTime += turnAroundTime[current];

                    isCompleted[current] = true;
                    completed++;
                }

                // If the process is not completed, push it back to the ready queue
                if (!isCompleted[current] && remainingTime[current] > 0) {
                    readyQueue.add(current);
                }
            } else {
                // If no process is ready, advance time to the next arrival
                if (i < n) time = arrivalTime[i];
            }
        }

        // Output process details and average times
        System.out.println("\nProcess\tArrival Time\tBurst Time\tPriority\tStart Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int j = 0; j < n; j++) {
            System.out.println("P" + (j + 1) + "\t\t" + arrivalTime[j] + "\t\t" + burstTime[j] + "\t\t" + priority[j]
                    + "\t\t" + startTime[j] + "\t\t" + completionTime[j] + "\t\t" + turnAroundTime[j] + "\t\t" + waitingTime[j]);
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
