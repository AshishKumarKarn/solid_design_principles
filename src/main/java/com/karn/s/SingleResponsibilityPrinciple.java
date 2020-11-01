package com.karn.s;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SingleResponsibilityPrinciple {
    public static void main(String[] args) throws IOException {
        Journal journal = new Journal();
        journal.addJournal("I found a great song today");
        journal.addJournal("I read some new design principles");
        journal.addJournal("this is some junk");
        journal.addJournal("I went to mega mart today");
        journal.removeJournal(2);
        journal.printJournals();
        String fileWithPath="E:\\Ashish\\files\\abc.txt";
      // journal.saveToFile(fileWithPath,true);
      // journal.openFileWindows(fileWithPath);
        Persistence p =new Persistence();
        p.saveToFile(journal, fileWithPath, true);
        p.openFileWindows(fileWithPath);

    }
}

class Journal {
    private final List<String> journals = new ArrayList<>();
    private static int count = 0;

    public void addJournal(String text) {
        journals.add((++count) + "   " + text);
    }

    public void removeJournal(int index) {
        journals.remove(index);
    }

    public void printJournals() {
        this.journals.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return journals.stream().map(a->"\n"+a).collect(Collectors.joining());
    }

    /*
    * Violation of SRP
    *
    public void saveToFile(String filePathWithFileName, boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(filePathWithFileName).exists()) {
            try (PrintStream stream = new PrintStream(filePathWithFileName)) {
                stream.println(toString());
            }
        }

    }
    public void openFileWindows(String filePathWithFileName) throws IOException {
        Runtime.getRuntime().exec("notepad.exe "+filePathWithFileName);
    }
     */
}

class Persistence {
    //Correcting SRP
    public void saveToFile(Journal j,String filePathWithFileName, boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(filePathWithFileName).exists()) {
            try (PrintStream stream = new PrintStream(filePathWithFileName)) {
                stream.println(j.toString());
            }
        }

    }
    public void openFileWindows(String filePathWithFileName) throws IOException {
        Runtime.getRuntime().exec("notepad.exe "+filePathWithFileName);
    }

}