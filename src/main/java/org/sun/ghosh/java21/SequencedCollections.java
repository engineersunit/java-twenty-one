package org.sun.ghosh.java21;

import java.util.List;
import java.util.ArrayList;

public class SequencedCollections {
    public static void main(String[] args) {
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