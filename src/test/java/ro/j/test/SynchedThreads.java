package ro.j.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/** From Lasse Koskela's Test Driven, Practical TDD and Acceptance TDD for Java Developers */
public class SynchedThreads extends Thread
{
    private CyclicBarrier entryBarrier;
    private CyclicBarrier exitBarrier;

    public SynchedThreads(Runnable runnable, CyclicBarrier entryBarrier, CyclicBarrier exitBarrier)
    {
        super(runnable);
        this.entryBarrier = entryBarrier;
        this.exitBarrier = exitBarrier;
    }

    @Override
    public void run()
    {
        try
        {
            entryBarrier.await();
            super.run();
            exitBarrier.await();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public static Launcher launch(Runnable task)
    {
        return new Launcher(task);
    }
    
    public static class Launcher
    {
        private final Runnable task;
        private CyclicBarrier entryBarrier;
        private CyclicBarrier exitBarrier;

        Launcher(Runnable task)
        {
            this.task = task;
        }

        public Launcher times(int n)
        {
            this.entryBarrier = new CyclicBarrier(n + 1);
            this.exitBarrier = new CyclicBarrier(n + 1);
            for(int i = 0; i < n; i++)
            {
                new SynchedThreads(task, entryBarrier, exitBarrier).start();
            }
            return this;
        }
        
        public void await() throws InterruptedException, BrokenBarrierException
        {
            entryBarrier.await();
            exitBarrier.await();
        }
    }
    
}