package com.karn.i;

public class InterfaceSegregationPrinciple {
}
/**
 * Violets the Interface Segregation Principle as there are many functionality a
 * user needs to implement to use this interface. Better to segregate it.
 *
 * YAGNI -  You Ain't Gonna Need It
 *
 * */
interface Machine {
    void print();
    void scan();
    void fax();
}
//Any machin implementing the interface needs to implement these methods
class OldestMachine implements Machine{

    @Override
    public void print() {
        // Meaning Full code
    }

    @Override
    public void scan() {
        // doesn't know the implementation but forced to implement it or throw exception here which is not good
    }

    @Override
    public void fax() {
        // doesn't know the implementation but forced to implement it or throw exception here which is not good
    }
}
//Solution is to break the machine interface into multiple sub interfaces
interface Printer{
    void print();
}

interface Scanner{
    void scan();
}

interface FaxIt{
    void fax();
}

//Now a old machine can also be implemented
class OldMachine implements Printer{

    @Override
    public void print() {
        //Need to implement only Printer code
    }
}