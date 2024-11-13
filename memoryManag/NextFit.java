package memoryManag;
import java.util.Scanner;

public class NextFit {
    static void nextFit(int[] blockSize, int m, int[] processSize, int n) {
        int[] allocation = new int[n]; // Array to store allocation of each process
        for (int i = 0; i < n; i++) {
            allocation[i] = -1; // Initialize allocation array with -1 (no allocation)
        }

        int j = 0; // To keep track of the block index

        for (int i = 0; i < n; i++) {
            int count = 0; // Counter to ensure we don’t loop indefinitely
            while (count < m) {
                if (blockSize[j] >= processSize[i]) {
                    allocation[i] = j + 1; // Allocate block j to process i
                    blockSize[j] -= processSize[i]; // Reduce the size of the block
                    break; // Break out of the loop once allocated
                }
                j = (j + 1) % m; // Move to the next block, wrap around using modulo
                count++;
            }
        }

        // Print the allocation results
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

        nextFit(blockSize, m, processSize, n);

        scanner.close();
    }
}
