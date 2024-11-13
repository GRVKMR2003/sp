
import java.util.*;

public class roundrobin {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input time quantum and number of processes
        System.out.print("Enter the time quantum: ");
        int tq = scanner.nextInt();
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Arrays to store process details
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] startTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        // Initialize start time array to -1 (indicating not started)
        Arrays.fill(startTime, -1);

        // Input process details
        System.out.println("Enter the arrival time and burst time of the processes:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
        }

        // Time variable and remaining process count
        int time = 0, remain = n;
        int totalTurnaroundTime = 0, totalWaitingTime = 0;

        // Ready queue to store processes
        Deque<Integer> readyQueue = new LinkedList<>();

        // Initially populate ready queue with processes that arrive at time 0
        for (int i = 0; i < n; i++) {
            if (arrivalTime[i] == 0) {
                readyQueue.add(i);
            }
        }

        while (remain > 0) {
            // If the ready queue is empty, increment time until a process arrives
            if (readyQueue.isEmpty()) {
                time++;
                for (int i = 0; i < n; i++) {
                    if (arrivalTime[i] == time && remainingTime[i] > 0) {
                        readyQueue.add(i);
                    }
                }
                continue;
            }

            // Get the front process in the ready queue
            int i = readyQueue.poll();

            // Record start time if the process has never started
            if (startTime[i] == -1) {
                startTime[i] = time;
            }

            // Execute the process for the time quantum or until completion
            if (remainingTime[i] <= tq) {
                time += remainingTime[i];
                remainingTime[i] = 0;
                completionTime[i] = time;

                // Calculate turnaround time and waiting time
                turnaroundTime[i] = completionTime[i] - arrivalTime[i];
                waitingTime[i] = turnaroundTime[i] - burstTime[i];

                totalTurnaroundTime += turnaroundTime[i];
                totalWaitingTime += waitingTime[i];
                remain--;  // Process is completed
            } else {
                time += tq;
                remainingTime[i] -= tq;
            }

            // Add newly arrived processes to the ready queue
            for (int j = 0; j < n; j++) {
                if (arrivalTime[j] <= time && remainingTime[j] > 0 &&
                    !readyQueue.contains(j) && j != i) {
                    readyQueue.add(j);
                }
            }

            // If the current process is not yet complete, add it back to the queue
            if (remainingTime[i] > 0) {
                readyQueue.add(i);
            }
        }

        // Output process information
        System.out.println("\nProcess\tArrival Time\tBurst Time\tStart Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" 
                    + startTime[i] + "\t\t" + completionTime[i] + "\t\t" 
                    + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }

        // Calculate and print average waiting time and turnaround time
        System.out.printf("\nAverage Waiting Time: %.2f\n", (float) totalWaitingTime / n);
        System.out.printf("Average Turnaround Time: %.2f\n", (float) totalTurnaroundTime / n);

        scanner.close();
    }
}
