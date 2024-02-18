package com.karn.d;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {
        Person person = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");
        RelationshipViolatingDIP relationshipViolatingDIP = new RelationshipViolatingDIP();
        relationshipViolatingDIP.addParentAndChild(person, child1);
        relationshipViolatingDIP.addParentAndChild(person, child2);

        new ResearchRelations(relationshipViolatingDIP);

        RelationshipBrowser relationshipFollowingDIP = new RelationshipFollowingDIP();
        relationshipFollowingDIP.addParentAndChild(person, child1);
        relationshipFollowingDIP.addParentAndChild(person, child2);

        new ResearchRelations(relationshipFollowingDIP);


    }
}
class Person{
    private final String name;

    Person(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

enum Relation {
    PARENT,
    CHILD
}
class ResearchRelations {//High level module

    ResearchRelations(RelationshipViolatingDIP relationshipViolatingDIP){//directly depending on low level module
        relationshipViolatingDIP.getRelations()
                .stream()
                .filter(triplet ->
                        triplet.getLeft().getName().equals("John")
                        && triplet.getMiddle().equals(Relation.PARENT)
                )
                .forEach(
                        personRelationPersonTriple ->
                                System.out.println("For parent name John, found child with name "+ personRelationPersonTriple.getRight().getName())
                );
    }

    ResearchRelations(RelationshipBrowser relationshipBrowser){
        relationshipBrowser.getChildrenByParentName("John")
                .forEach(person ->
                    System.out.println("For parent name John, found child with name "+ person.getName())
                );
    }

}

class RelationshipViolatingDIP { //LOW LEVEL MODULE
 private final List<Triple<Person, Relation, Person>> relations = new ArrayList<>();
 public void addParentAndChild(Person parent, Person child) {
     relations.add(new Triple<>(parent, Relation.PARENT, child));
     relations.add(new Triple<>(child, Relation.CHILD, parent));
 }

    public List<Triple<Person, Relation, Person>> getRelations() {
        return relations;
    }
}



//SOLUTION
interface RelationshipBrowser {
    List<Person> getChildrenByParentName(String name);

    void addParentAndChild(Person parent, Person child);
}
class RelationshipFollowingDIP implements RelationshipBrowser {

    @Override
    public List<Person> getChildrenByParentName(String name) {
        return relations.stream()
                .filter(personRelationPersonTriple ->
                        personRelationPersonTriple.getLeft().getName().equals("John")
                                &&
                        personRelationPersonTriple.getMiddle().equals(Relation.PARENT))
                .map(Triple::getRight)
                .collect(Collectors.toList());
    }
    private final List<Triple<Person, Relation, Person>> relations = new ArrayList<>();
    @Override
    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triple<>(parent, Relation.PARENT, child));
        relations.add(new Triple<>(child, Relation.CHILD, parent));
    }
}
class Triple<L,M,R> {

    private final L left;
    private final M middle;
    private final R right;
    Triple(L l, M m, R r){
        this.left = l;
        this.middle = m;
        this.right = r;
    }

    public L getLeft() {
        return left;
    }

    public M getMiddle() {
        return middle;
    }

    public R getRight() {
        return right;
    }
}