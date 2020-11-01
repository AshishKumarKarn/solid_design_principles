package com.karn.o;


import java.util.List;
import java.util.stream.Stream;

public class OpenClosePrinciple {

    /**
     * A class should be Open for extension and Closed for modification
     */
    public static void main(String[] args) {
        Product product1 = new Product("Apple", Size.SMALL, Color.GREEN, Price.HIGH);
        Product product2 = new Product("Strawberry", Size.SMALL, Color.RED, Price.MEDIUM);
        Product product3 = new Product("WaterMellon", Size.LARGE, Color.GREEN, Price.LOW);
        Product product4 = new Product("Mango", Size.MEDIUM, Color.GREEN, Price.HIGH);
        Product product5 = new Product("Banana", Size.MEDIUM, Color.GREEN, Price.LOW);
        Product product6 = new Product("CustardApple", Size.MEDIUM, Color.GREEN, Price.LOW);
        Product product7 = new Product("Blueberry", Size.SMALL, Color.BLUE, Price.HIGH);
        Product product8 = new Product("Litchi", Size.SMALL, Color.RED, Price.MEDIUM);
        Product product9 = new Product("Guava", Size.MEDIUM, Color.GREEN, Price.LOW);
        Product product10 = new Product("Pomegranate", Size.MEDIUM, Color.RED, Price.HIGH);
        Product product11 = new Product("Kiwi", Size.SMALL, Color.GREEN, Price.HIGH);
        Product product12 = new Product("Papaya", Size.LARGE, Color.GREEN, Price.LOW);


        List<Product> products = List.of(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11, product12);
        OpenCloseViolatingProductFilter oldFilter = new OpenCloseViolatingProductFilter();
        System.out.println("GREEN products");
        oldFilter.filterProductByColor(products, Color.GREEN).forEach(System.out::println);

        System.out.println("GREEN products using recommended filter");
        RecommendedProductFilter recommendedProductFilter = new RecommendedProductFilter();
        recommendedProductFilter.filterItem(products, new ColorSpecification(Color.GREEN)).forEach(System.out::println);

        //the recommended filter is open for extension but closed for modification
        System.out.println("High price products using recommended filter");
        recommendedProductFilter.filterItem(products, s -> s.getPrice() == Price.HIGH).forEach(System.out::println);

        //Practically you really don't need any of these filters here but you can use existing one.
        //The above codes are just to demo the concept
        System.out.println("High price Blue products using in-built stream filter");
        products.stream().filter(p -> p.getPrice() == Price.HIGH && p.getColor() == Color.BLUE).forEach(System.out::println);

    }
}

/**
 * Below class violates the open-close principle
 * <p>
 * As if there is some new requirement for filtering based on price
 */
class OpenCloseViolatingProductFilter {
    public Stream<Product> filterProductBySize(List<Product> list, Size size) {
        return list.stream().filter(p -> p.getSize() == size);
    }

    public Stream<Product> filterProductByColor(List<Product> list, Color color) {
        return list.stream().filter(p -> p.getColor() == color);
    }
}
/****************************************************************************************/

/********************************Recommended code below**********************************/
interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filterItem(List<T> products, Specification<T> specification);
}

class ColorSpecification implements Specification<Product> {

    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.getColor() == color;
    }
}

class SizeSpecification implements Specification<Product> {

    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.getSize() == size;
    }
}
// Now, if tomorrow there is some new filter(let's say for price) is required, you can
// implement new specification or use lambda to define specification implementation and you
// don't need to modify existing implementation

class RecommendedProductFilter implements Filter<Product> {
    @Override
    public Stream<Product> filterItem(List<Product> products, Specification<Product> specification) {
        return products.stream().filter(p -> specification.isSatisfied(p));
    }
}

class Product {

    private String productName;
    private Size size;
    private Color color;
    private Price price;

    public Product() {
    }

    public Product(String productName, Size size, Color color, Price price) {
        this.productName = productName;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return productName + " ";
    }
}

enum Size {
    SMALL, MEDIUM, LARGE
}

enum Color {
    RED, GREEN, BLUE
}

enum Price {
    LOW, MEDIUM, HIGH
}