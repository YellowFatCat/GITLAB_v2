package com;

import com.sun.javafx.UnmodifiableArrayList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class Immutability {

    @Test
    public void testImmutability() {
        Car.CarBuilder builder = Car.CarBuilder.builder();
        builder
                .withName("Vovlva")
                .withOwner(new Owner("Vova", "Vladimirov"));

        Car car = builder.build();
        System.out.println(car);
    }

    @Test
    public void mutableCollections() throws InterruptedException {
        ArrayList<Integer> mutable = new ArrayList<>();

        IntStream.range(0, 10).forEach(mutable::add);

        new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                mutable.add(-1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }).start();


        // concurrent modification in the list
        // prevent from returning list from Immutable Class in multi-threaded apps
        final Iterator<Integer> iterator = mutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Solution 1 - convert to Immutable Collection
    @Test
    public void immutableCollections() throws InterruptedException {
        ArrayList<Integer> mutableList = new ArrayList<>();
        IntStream.range(0, 10).forEach(mutableList::add);
        List<Integer> immutable = Collections.unmodifiableList(mutableList);

        new Thread(() -> {
            IntStream.range(0, 10).forEach((i) -> {
                immutable.add(-1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }).start();


        // concurrent modification in the list
        // prevent from returning list from Immutable Class in multi-threaded apps
        final Iterator<Integer> iterator = immutable.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Solution 2 - convert to CopyOnWriteArrayList
    @Test
    public void syncCollections() throws InterruptedException {
        List<Integer> mutable = new CopyOnWriteArrayList<>();

        IntStream.range(0, 10).forEach(mutable::add);


        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is first");
            IntStream.range(0, 10).forEach((i) -> {
                mutable.add(-1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        });

        thread.start();

        Thread.sleep(100);

        // concurrent modification in the list
        // prevent from returning list from Immutable Class in multi-threaded apps
        final Iterator<Integer> iterator = mutable.iterator();
        System.out.println(Thread.currentThread().getName() + " is first. List size = " + mutable.size()); // size is not defined for 100%
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread.join();
        Thread.sleep(100);
        System.out.println("After modification");
        // add the list to the end after the Iterator is released
        mutable.forEach(System.out::print);
    }
}

// Immutability simplifies Concurrent apps
class Owner {
    private final String name;
    private final String lastName;

    public Owner(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}

class Car {
    private final String name;
    private final Owner owner;

    private Car(String name, Owner owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }

    public static class CarBuilder {
        private String name;
        private Owner owner;

        private CarBuilder() {
        }

        public static CarBuilder builder() {
            return new CarBuilder();
        }

        public CarBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CarBuilder withOwner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Car build() {
            if (name == null) throw new AssertionError();
            if (owner == null) throw new AssertionError();

            return new Car(name, owner);
        }
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }
}