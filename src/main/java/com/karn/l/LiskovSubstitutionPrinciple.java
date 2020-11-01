package com.karn.l;

public class LiskovSubstitutionPrinciple {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(2, 3);
        modifyRectangleAndPrintArea(rectangle);
        rectangle = new Square(3);
        //here the value will change as the LSP fails
        modifyRectangleAndPrintArea(rectangle);
    }

    /**
     * Below method violets the Liskov Substitution Principle
     * as when a square object is passed, it misbehaves
     *
     * As per Liskov Substitution Principle, a method should be able to work properly fine for all sub-type
     * class or you should be able to pass the subtype in places where base class is used as argument
     */
    private static void modifyRectangleAndPrintArea(Rectangle rectangle) {
        int width = rectangle.getWidth();
        rectangle.setLength(10);
        System.out.println("Area should be " + (10 * width) + " but got " + rectangle.getArea());
    }


}

class Rectangle {
    private int width;
    private int length;

    public Rectangle() {
    }

    public Rectangle(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getArea() {
        return length * width;
    }
}

class Square extends Rectangle {
    public Square(int length) {
        super(length, length);
    }

    public Square() {
    }

    @Override
    public void setLength(int length) {
        super.setLength(length);
        super.setWidth(length);
    }

    @Override
    public void setWidth(int width) {
        super.setLength(width);
        super.setWidth(width);
    }
}