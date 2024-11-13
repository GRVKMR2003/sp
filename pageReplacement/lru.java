import java.util.*;

public class lru {
    static void pageFaults(int[] pages, int capacity) {
        List<Integer> frames = new ArrayList<>();       // Stores pages in memory
        Deque<Integer> order = new LinkedList<>();      // Tracks LRU order
        int pageFaults = 0;                             // Count of page faults
        int[] pageHits = new int[pages.length];         // 1 for hit, 0 for miss
        int[] pageFaultsArr = new int[pages.length];    // 1 for fault, 0 for hit

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            // If the page is not found in frames (page miss)
            if (!frames.contains(page)) {
                pageFaults++;
                pageFaultsArr[i] = 1; // Mark as page fault

                // If frames are full, replace the least recently used page
                if (frames.size() == capacity) {
                    int lru = order.poll(); // Remove the least recently used page
                    frames.set(frames.indexOf(lru), page); // Replace with the current page
                } else {
                    // If there's still space, add the page
                    frames.add(page);
                }

                // Add the current page to the LRU order
                order.add(page);
            } else {
                // Page hit
                pageHits[i] = 1; // Mark as page hit

                // Update LRU order
                order.remove(page);
                order.add(page);
            }

            // Display the current contents of frames
            System.out.print("Frame after accessing page " + page + ": ");
            for (int p : frames) {
                System.out.print(p + " ");
            }
            System.out.println();
        }

        // Display page hits and faults arrays
        System.out.print("\nPage Hits Array: ");
        for (int hit : pageHits) {
            System.out.print(hit + " ");
        }
        System.out.print("\nPage Faults Array: ");
        for (int fault : pageFaultsArr) {
            System.out.print(fault + " ");
        }

        // Total page faults
        System.out.println("\nTotal Page Faults: " + pageFaults);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of pages: ");
        int n = scanner.nextInt();
        int[] pages = new int[n];

        System.out.println("Enter the page numbers:");
        for (int i = 0; i < n; i++) {
            pages[i] = scanner.nextInt();
        }

        System.out.print("Enter the number of frames: ");
        int capacity = scanner.nextInt();

        pageFaults(pages, capacity);

        scanner.close();
    }
}
