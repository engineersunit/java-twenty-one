import java.util.Properties;
import java.util.stream.IntStream;

void main(){
        print("Scheduling Virtual Threads");
    /*
        12th Gen Intel(R) Core(TM) i7-1270P   2.20 GHz
        https://www.intel.com/content/www/us/en/products/sku/226255/intel-core-i71270p-processor-18m-cache-up-to-4-80-ghz/specifications.html
        Total Cores 12
        # of Performance-cores 4
        # of Efficient-cores 8
        Total Threads 16
    */

        int availableProcessors=Runtime.getRuntime().availableProcessors();
        print(availableProcessors); // 16

        Properties systemProperties=System.getProperties();
        /*
         * The number of platform threads available for scheduling virtual threads.
         * It defaults to the number of available processors.
         */
        systemProperties.put("jdk.virtualThreadScheduler.parallelism","1");
        /*
         * The maximum number of platform threads available to the scheduler.
         * It defaults to 256.
         */
        systemProperties.put("jdk.virtualThreadScheduler.maxPoolSize","1");
        systemProperties.put("jdk.virtualThreadScheduler.minRunnable","1");
//    print(systemProperties);
        System.setProperties(systemProperties);


        print(System.getProperty("jdk.virtualThreadScheduler.parallelism"));
        print(System.getProperty("jdk.virtualThreadScheduler.maxPoolSize"));
        print(System.getProperty("jdk.virtualThreadScheduler.minRunnable"));

        IntStream.rangeClosed(1,availableProcessors)
        .forEach((i)->
        Thread.ofVirtual()
        .name(String.valueOf(i))
        .start(
        ()->{
        print(Thread.currentThread());
        try{
        Thread.sleep(500);
        }catch(InterruptedException e){}
        }
        ));


        }


        void print(Object object){
        System.out.println(object);
        }