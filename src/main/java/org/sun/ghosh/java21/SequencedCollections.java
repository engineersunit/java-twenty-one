package org.sun.ghosh.java21;

import java.util.List;
import java.util.ArrayList;

/**
 * JEP 431: Sequenced Collections: https://openjdk.org/jeps/431
 * A sequenced collection is a Collection whose
 * elements have a defined encounter order.
 *
 * JDK 21 Home Page: https://openjdk.org/projects/jdk/21/
 * OpenJDK JDK 21 Early-Access Builds: https://jdk.java.net/21/
 * OpenJDK SequencedCollections Code Changes: https://github.com/openjdk/jdk/commit/17ce0976e442d5fabb14daed40fa9a768989f02e
 */
public class SequencedCollections {
    public static void main(String[] args) {
        /**
         * interface SequencedCollection<E>
         *     extends Collection<E> {
         *
         *     // new method
         *     SequencedCollection<E> reversed();
         *
         *     // methods promoted from Deque
         *     void addFirst(E);
         *     void addLast(E);
         *     E getFirst();
         *     E getLast();
         *     E removeFirst();
         *     E removeLast();
         * }
         */

        List<String> listOfSkills = new ArrayList<>();

        listOfSkills.add("Oracle Database");
        listOfSkills.add("MicroService");
        listOfSkills.add("Shell Scripting");

        listOfSkills.addFirst("Java");
        listOfSkills.addLast("Python");

        System.out.println(
                String.format
                        ("My first skill is *%s* and" +
                                        " last skill is *%s*",
                                listOfSkills.getFirst(),
                                listOfSkills.getLast()));

        List listOfSkillsRev = listOfSkills.reversed();

        System.out.println(
                String.format
                        ("My first skill is *%s* and" +
                                        " last skill is *%s*",
                                listOfSkillsRev.getFirst(),
                                listOfSkillsRev.getLast()));

        listOfSkillsRev.removeFirst();
        listOfSkillsRev.removeLast();

        System.out.println(
                String.format
                        ("My first skill is *%s* and" +
                                        " last skill is *%s*",
                                listOfSkillsRev.getFirst(),
                                listOfSkillsRev.getLast()));


    }
}