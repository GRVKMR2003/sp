package memoryManag;
import java.util.Scanner;

public class WorstFit {
    static void worstFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n]; // Array to store allocation of each process
        for (int i = 0; i < n; i++) {
            allocation[i] = -1; // Initialize allocation array with -1 (indicating no allocation)
        }

        // Loop over each process to allocate memory
        for (int i = 0; i < n; i++) {
            int worstIdx = -1;

            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (worstIdx == -1 || blockSize[j] > blockSize[worstIdx]) {
                        worstIdx = j;
                    }
                }
            }

            if (worstIdx != -1) {
                allocation[i] = worstIdx + 1; // Allocate block worstIdx to process i
                blockSize[worstIdx] -= processSize[i]; // Reduce available block size
            }
        }

        // Output the allocation results
        System.out.println("Process No.\tProcess Size\tBlock No.");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + processSize[i] + "\t\t" + allocation[i]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of blocks and processes: ");
        int m = scanner.nextInt();
        int n = scanner.nextInt();

        int[] blockSize = new int[m];
        int[] processSize = new int[n];

        System.out.println("Enter the sizes of each block:");
        for (int i = 0; i < m; i++) {
            blockSize[i] = scanner.nextInt();
        }

        System.out.println("Enter the sizes of each process:");
        for (int i = 0; i < n; i++) {
            processSize[i] = scanner.nextInt();
        }

        worstFit(blockSize, m, processSize, n);

        scanner.close();
    }
}
