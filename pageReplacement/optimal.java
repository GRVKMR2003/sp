
import java.util.*;

public class optimal {

    // Predict the page to replace by finding the page that will not be used for the longest time
    public static int predictPageToReplace(List<Integer> pages, List<Integer> frame, int currentIndex) {
        int maxIndex = -1, pageToReplaceIndex = -1;

        // Check each page in the frame to see when it will be used next
        for (int i = 0; i < frame.size(); i++) {
            int j;
            for (j = currentIndex; j < pages.size(); j++) {
                if (frame.get(i) == pages.get(j)) {
                    if (j > maxIndex) {
                        maxIndex = j;
                        pageToReplaceIndex = i;
                    }
                    break;
                }
            }

            // If a page is not used again, it’s a good candidate to replace
            if (j == pages.size()) return i;
        }
        return pageToReplaceIndex;
    }

    // Main method for the optimal page replacement algorithm
    public static void optimalPageReplacement(List<Integer> pages, int numFrames) {
        List<Integer> frame = new ArrayList<>();
        int pageFaults = 0;
        int[] pageFaultsArray = new int[pages.size()];
        int[] pageHitsArray = new int[pages.size()];

        for (int i = 0; i < pages.size(); i++) {
            int currentPage = pages.get(i);

            // If the page is already in the frame (page hit)
            if (frame.contains(currentPage)) {
                pageHitsArray[i] = 1;  // Page hit
                System.out.print("Page " + currentPage + " hit. Frame: ");
            } else {
                pageFaults++;
                pageFaultsArray[i] = 1;  // Page fault

                if (frame.size() < numFrames) {
                    frame.add(currentPage);
                } else {
                    // Find the page to replace using the optimal strategy
                    int replaceIndex = predictPageToReplace(pages, frame, i + 1);
                    frame.set(replaceIndex, currentPage);
                }
                System.out.print("Page " + currentPage + " fault. Frame: ");
            }

            // Print current frame contents
            for (int page : frame) {
                System.out.print(page + " ");
            }
            System.out.println();
        }

        // Display the results
        System.out.print("\nPage Faults Array: ");
        for (int fault : pageFaultsArray) {
            System.out.print(fault + " ");
        }
        System.out.print("\nPage Hits Array: ");
        for (int hit : pageHitsArray) {
            System.out.print(hit + " ");
        }

        // Total hits and faults
        System.out.println("\nTotal Hits = " + (pages.size() - pageFaults));
        System.out.println("Total Faults = " + pageFaults);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of pages
        System.out.print("Enter the number of pages: ");
        int numPages = scanner.nextInt();
        List<Integer> pages = new ArrayList<>();

        // Input page numbers
        System.out.println("Enter the page numbers:");
        for (int i = 0; i < numPages; i++) {
            pages.add(scanner.nextInt());
        }

        // Input number of frames
        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();

        // Run the optimal page replacement algorithm
        optimalPageReplacement(pages, numFrames);

        scanner.close();
    }
}
