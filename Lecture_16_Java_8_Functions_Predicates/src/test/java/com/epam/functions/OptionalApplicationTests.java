package com.epam.functions;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class OptionalApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test(expected = NullPointerException.class)
    public void testNullPointerExcetion() {
        final String name = null;
        Optional<String> nameOptional = Optional.of(name);
    }

//    @Test
    public void testNullAble() {
        final String name = null;
        Optional<String> nameOptional = Optional.ofNullable(name);
        assertTrue(nameOptional.get() == null);
    }

    @Test
    public void getNotNull() {
        final String name = null;
        String nameOptional = Optional.ofNullable(name).orElseGet(() -> "Joe Doe");
        assertEquals(nameOptional, "Joe Doe");

        Integer object = null;
        Integer derived = Optional.ofNullable(object).orElseGet(this::culcNumber);
        System.out.println("Number: " + derived);

        String brand = null;
        String result = Optional.ofNullable(brand).orElseGet(this::getDefuault);
        assertNotNull(result);
    }


    @Test
    public void filteringTestAkaStreams() {
        Item item = new Item(10);
        Item item2 = new Item(9);
        Item item3 = new Item(11);
        Item item4 = new Item(null);

        List<Item> items = Lists.newArrayList(
                item, item2, item3, item4
        );

        Collection<Item> result = items.stream().filter(this::filterInRange).collect(Collectors.toList());
        Collection<Item> result2 = items.stream().filter(this::filterInRangeOptional).collect(Collectors.toList());

        assertEquals(1, result.size());
        assertEquals(1, result2.size());
    }

    class Product {
        String name;

        public Product(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }
    }

    @Test
    public void processUnwrapProducts() {
        Collection<Product> products = Lists.newArrayList(new Product("Sergey"), new Product(""));
        Collection<String> strs = products.stream()
                .map(Product::getName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(item -> item.length() > 0)
                .collect(Collectors.toList());
        assertEquals(1, strs.size());
    }

    private boolean filterInRange(Item item) {
        if (item == null)
            return false;
        if (item.price >= 11) return false;
        if (item.price <= 9) return false;
        if (item.price == 10) return true;

        return false;
    }

    private boolean filterInRangeOptional(Item item) {
        boolean result = Optional.ofNullable(item).
                map(Item::getPrice)
                .filter(price -> !(price >= 11))
                .filter(price -> !(price <= 9))
                .filter(price -> (price == 10))
                .isPresent();
        return result;
    }

    /**
     * Grouping with JsonObjects
     */
    @Test
    public void grouping() {
        List<Item> items = Arrays.asList(
                new Item(1, RequestType.ONE),
                new Item(2, RequestType.ONE),
                new Item(3, RequestType.TWO),
                new Item(4, RequestType.TWO)
        );

        Map<RequestType, List<Item>> map = items.stream().collect(groupingBy(Item::getType));
        assertEquals(2, map.size());
        assertEquals(2, map.values().size());

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();


        map.forEach((key, value) -> jsonObjectBuilder.add(key.name(), Joiner.on(", ").join(value)));
        System.out.println("Result : " + jsonObjectBuilder.build());

    }


    enum RequestType {
        ONE,
        TWO
    }

    class Item {
        Integer price;
        RequestType type;

        public Item(Integer price, RequestType type) {
            this.price = price;
            this.type = type;
        }

        public Item(Integer price) {
            this.price = price;
        }

        public Integer getPrice() {
            return price;
        }

        public RequestType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "price=" + price +
                    ", type=" + type +
                    '}';
        }
    }

    private String getDefuault() {
        return "Adidas";
    }

    private Integer culcNumber() {
        return new Random().nextInt(2);
    }

}
