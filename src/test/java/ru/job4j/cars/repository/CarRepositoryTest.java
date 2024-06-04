package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.Config;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.HistoryOwner;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarRepositoryTest {
    private static SessionFactory sf;
    private static CrudRepository crudRepository;
    private static CarRepository carRepository;
    private static Set<HistoryOwner> historyOwners;

    @BeforeAll
    public static void initRepositories() {
        sf = new Config().sessionFactory();
        crudRepository = new CrudRepository(sf);
        carRepository = new CarRepository(crudRepository);
    }

    @AfterAll
    public static void close() {
        sf.close();
    }

    @Test
    public void whenCreateCar() {
        String brand = "Geely";
        Engine engine = new Engine(1, "V6");
        Car car = new Car(1, engine, historyOwners, brand);
        var rsl = carRepository.create(car);
        assertThat(rsl.getBrand()).isEqualTo(car.getBrand());
    }
}
