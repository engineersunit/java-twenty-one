/*
 * JEP 445: Unnamed Classes and Instance Main Methods (Preview)
 * https://openjdk.org/jeps/445
 * JIRA: JEP 445: Unnamed Classes and Instance Main Methods (Preview)
 * https://bugs.openjdk.org/browse/JDK-8302326
 * GitHub Pull
 * https://github.com/openjdk/jdk/pull/13689
 */
//package org.sun.ghosh.java21;

/*public class SimpleHelloWorld {
    public static void main(String[] args) {
        System.out.println("""
                           Hello,
                             Complex
                             World!""");
    }
}*/
    void main(){
        System.out.println("""
                           Hello,
                             Simple
                             World!""");
    }
    /*
     * First, the protocol was enhanced by which
     * Java programs are launched - to allow:

          "instance main methods"

     * Such methods are:
     * -x- not "static"
     * -x- "public" (optional)
     * -x- "String[] args parameter" (optional)

     * So we have:
     */

/*
class HelloWorld {
    void main() {
        String s = """
                   Hello, World!
                   I am running in a main() without keywords
                   - public
                   - static
                   - String[] args - method parameter
                   """;
        System.out.println(s);
    }
}
*/
    /*
     * Second, introduce

            "unnamed classes"

     * to make the class declaration implicit

     * So we have:
     */

        /*
        void main() {
         String s = """
           Hello, World!
           I am running in a main()
           - without class
           - without keywords
              - public
              - static
           - String[] args parameter
           """;
         System.out.println(s);
         System.out.println(this.getClass().getName());
        }

    */

    /*
    java --source 21 --enable-preview SimpleHelloWorld.java
     */

/*
 * Allow the main method of a launched class to have
 * public, protected, or default (i.e., package) access
 */

/*
        public void main() {
            System.out.println("Hello, World! - Public");
        }
*/

/*
        protected void main() {
            System.out.println("Hello, World! - Protected");
        }
*/
/*
        void main() {
            System.out.println("Hello, World! - Default");
        }
*/

/*
 * If a launched class contains no static main method
 * with a String[] parameter but does contain a
 * static main method with no parameters,
 * then invoke that method
 */

/*
        static void main(String[] args) {
            System.out.println("Hello, World! with Args");
        }
*/
/*
        static void main() {
            System.out.println("Hello, World! without Args");
        }
*/


/*
 Unnamed classes
   In the Java language, every class resides
   in a package and every package resides in a module
 */

/* An unnamed class resides in the unnamed package,
   and the unnamed package resides in the unnamed module
 */

/*
 * While there can be only one unnamed package and
 * only one unnamed module, there can be multiple
 * unnamed classes in the unnamed module
 */

/*
 * A source file named SimpleHelloWorld.java containing
 * an unnamed class can be launched
 * with the source-code launcher, like so:
 *
 * $ java SimpleHelloWorld.java
 *
 * The Java compiler will compile that file
 * to the launchable class file SimpleHelloWorld.class.
 *
 * In this case the compiler chooses SimpleHelloWorld
 * for the class name as an implementation detail,
 * but that name still cannot be used
 * directly in Java source code.
 */

/*
 * Eliminating the main method altogether may seem
 * like the natural next step, but it would work against
 * the goal of gracefully evolving a first Java program
 * to a larger one and would impose some non-obvious restrictions.
 * Dropping the void modifier would similarly create a distinct Java dialect.
 */