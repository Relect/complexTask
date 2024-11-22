package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {

    public void executeTasks(int numberOfTasks) {
        ComplexTask task = new ComplexTask();
        Runnable runnable = () -> {
            int result = task.getAtomicInteger();
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " result:" + result);
        };
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, runnable);

        ExecutorService service = Executors.newFixedThreadPool(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            service.submit(() -> {
                task.execute();
                String threadName = Thread.currentThread().getName();
                System.out.println("waiting " + threadName);
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        service.shutdown();
    }
}
