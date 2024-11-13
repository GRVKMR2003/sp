import java.util.*;

public class fifoPageReplacement {

    static void fifoPage(int[] pages, int pageCount, int frameCount) {
        List<Integer> frames = new ArrayList<>();
        Queue<Integer> fifoQueue = new LinkedList<>();
        int pageFaults = 0;

        int[] pageFaultsArray = new int[pageCount];
        int[] pageHitsArray = new int[pageCount];

        for (int i = 0; i < pageCount; i++) {
            int currentPage = pages[i];

            // Check if the page is already in the frame (page hit)
            if (!frames.contains(currentPage)) {
                // Page fault
                pageFaults++;
                pageFaultsArray[i] = 1; // Mark as page fault

                if (frames.size() < frameCount) {
                    frames.add(currentPage);
                    fifoQueue.add(currentPage);
                } else {
                    int replacedPage = fifoQueue.poll();

                    // Replace the page in the frames list
                    for (int j = 0; j < frames.size(); j++) {
                        if (frames.get(j) == replacedPage) {
                            frames.set(j, currentPage);
                            break;
                        }
                    }
                    fifoQueue.add(currentPage);
                }
            } else {
                pageHitsArray[i] = 1; // Mark as page hit
            }

            // Print the current state of the frame
            System.out.print("Page " + currentPage + " is in the frame. Frame Contents: ");
            for (int page : frames) {
                System.out.print(page + " ");
            }
            System.out.println();
        }

        // Output the page faults and hits arrays
        System.out.print("\nPage Faults Array: ");
        for (int fault : pageFaultsArray) {
            System.out.print(fault + " ");
        }
        System.out.print("\nPage Hits Array: ");
        for (int hit : pageHitsArray) {
            System.out.print(hit + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of pages: ");
        int pageCount = scanner.nextInt();
        int[] pages = new int[pageCount];

        System.out.println("Enter the page numbers:");
        for (int i = 0; i < pageCount; i++) {
            pages[i] = scanner.nextInt();
        }

        System.out.print("Enter the frame size: ");
        int frameCount = scanner.nextInt();

        fifoPage(pages, pageCount, frameCount);

        scanner.close();
    }
}
