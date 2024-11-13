package memoryManag;
import java.util.Scanner;

public class BestFit {
    
    static void bestFit(int blockSize[], int m, int processSize[], int n) {
        int[] allocation = new int[n]; // Array to store allocation of each process
        for (int i = 0; i < n; i++) {
            allocation[i] = -1; // Initially, no block is allocated
        }

        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSize[j] >= processSize[i]) {
                    if (bestIdx == -1 || blockSize[bestIdx] > blockSize[j]) {
                        bestIdx = j;
                    }
                }
            }

            if (bestIdx != -1) {
                allocation[i] = bestIdx + 1; // Allocate the block and adjust
                blockSize[bestIdx] -= processSize[i];
            }
        }

        // Print the allocation
        System.out.println("Process No\tProcess Size\tBlock No.");
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

        System.out.println("Enter the sizes of each process: ");
        for (int i = 0; i < n; i++) {
            processSize[i] = scanner.nextInt();
        }

        System.out.println("Enter the sizes of each block: ");
        for (int i = 0; i < m; i++) {
            blockSize[i] = scanner.nextInt();
        }

        bestFit(blockSize, m, processSize, n);

        scanner.close();
    }
}
