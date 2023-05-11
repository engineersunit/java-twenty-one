package org.sun.ghosh.java21;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Arrays;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Scanner;

public class GenerationalZGarbageCollector {
    private static final int num_of_objects = 10_000_000;

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total memory "+ runtime.totalMemory());
        System.out.println("Free memory before generating objects " + runtime.freeMemory());

        Instant startTime = Instant.now();
        for (int i = 0; i <= num_of_objects; i++) {
            String collectMe = new String
                    ("Garbage collect me please");
        }
        Instant endTime = Instant.now();
        Duration between = Duration.between(startTime, endTime);
        System.out.println(String.format("Took %d seconds and %d nanoseconds",
                between.get(ChronoUnit.SECONDS),
                between.get(ChronoUnit.NANOS)));
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter to proceed to GC");
        String proceed = myObj.nextLine();
        System.out.println("Free memory after generating objects " + runtime.freeMemory());
        System.gc();
        System.out.println("Free memory after running gc " + runtime.freeMemory());

        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
            System.out.println("=================================");
            System.out.println("Name: " + gcMxBean.getName());
            System.out.println("Object Name: " + gcMxBean.getObjectName());
            System.out.println("Collection Count: " + gcMxBean.getCollectionCount());
            System.out.println("Collection Time: " + gcMxBean.getCollectionTime());
            System.out.println("Memory Pool Names: " + Arrays.toString(gcMxBean.getMemoryPoolNames()));
            System.out.println("Valid: " + gcMxBean.isValid());
            System.out.println("=================================");
        }


        System.out.println("Enter to exit");
        String exit = myObj.nextLine();

    }
}