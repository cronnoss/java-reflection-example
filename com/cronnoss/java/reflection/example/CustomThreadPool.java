package com.cronnoss.java.reflection.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomThreadPool {

    private final List<Worker> workers;
    private final LinkedList<Runnable> taskQueue;
    private volatile boolean isShutdown = false;

    public CustomThreadPool(int capacity) {
        taskQueue = new LinkedList<>();
        workers = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutting down, cannot accept new tasks");
        }
        taskQueue.add(task); // Add task to the queue
        notify(); // Notify threads that there is a new task
    }

    public synchronized void shutdown() {
        isShutdown = true;
        notifyAll(); // Wake up all threads so they can complete
    }

    public void awaitTermination() {
        for (Worker worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (CustomThreadPool.this) {
                    while (taskQueue.isEmpty()) {
                        if (isShutdown) {
                            return; // Terminate the thread if shutdown() is called and there are no more tasks
                        }
                        try {
                            CustomThreadPool.this.wait(); // We expect new tasks to appear
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    task = taskQueue.poll();
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        CustomThreadPool threadPool = new CustomThreadPool(3);

        for (int i = 0; i < 5; i++) {
            int taskNumber = i;
            threadPool.execute(() -> {
                System.out.println("Executing task " + taskNumber);
                try {
                    Thread.sleep(1000); // Simulate task execution
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination();
        System.out.println("All tasks completed.");
    }
}
