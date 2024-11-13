import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RW {

    private Semaphore writeLock = new Semaphore(1);
    private Lock readLock = new ReentrantLock();
    private static int readcount = 0;

    private void Reader(int id){
        try{
            readLock.lock();
            readcount++;
            if(readcount == 1){
                writeLock.acquire();
            }

            readLock.unlock();

            System.out.println("Reader " + id + " is reading.");
            Thread.sleep(1000);
            System.out.println("Reader "+ id + " has finished reading.");
            
            readLock.lock();
            readcount--;
            if(readcount == 0){
                writeLock.release();
            }

            readLock.unlock();

        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
    }

    private void Writer(int id){
        try{
            writeLock.acquire();

            System.out.println("Writer "+ id + " is writing.");
            Thread.sleep(1000);
            System.out.println("Writer "+ id + " has finished writing.");


            writeLock.release();

        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }

    }

    public static void main(String args[]){
        RW rw = new RW();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes : ");

        int n = sc.nextInt();
        System.out.println("Enter 1 for reading and 0 for writing : ");

        int[] seq = new int[n];
        for(int i=0;i<n;i++){
            seq[i] = sc.nextInt();
        }

        for(int i=0;i<n;i++){
            int id = i+1;
            if(seq[i] == 1){
                new Thread(()->rw.Reader(id)).start();
            }else{
                new Thread(()->rw.Writer(id)).start();
            }
        }

        sc.close();
    }
}