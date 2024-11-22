package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    private CyclicBarrier barrier;
    private ComplexTask task;

    public ComplexTaskExecutor(int numTask) {
        Runnable runnable = () -> {
            int result = task.getAtomicInteger();
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " result:" + result);
        };
        this.barrier = new CyclicBarrier(numTask, runnable);
        this.task = new ComplexTask();
    }

    public void executeTasks(int numberOfTasks) {
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
    }
}
