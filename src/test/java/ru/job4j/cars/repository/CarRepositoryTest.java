package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.Config;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class CarRepositoryTest {
    private static SessionFactory sf;
    private static CrudRepository crudRepository;
    private static CarRepository carRepository;
    private static EngineRepository engineRepository;
    private static HistoryOwnerRepository historyOwnerRepository;

    @BeforeAll
    public static void initRepositories() {
        sf = new Config().sessionFactory();
        crudRepository = new CrudRepository(sf);
        carRepository = new CarRepository(crudRepository);
        engineRepository = new EngineRepository(crudRepository);
        historyOwnerRepository = new HistoryOwnerRepository(crudRepository);
    }

    @AfterAll
    public static void close() {
        sf.close();
    }

    @BeforeEach
    public void clearDatabase() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from HistoryOwner").executeUpdate();
            session.createQuery("delete from Car").executeUpdate();
            session.createQuery("delete from Engine").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    public void whenCreateCar() {
        String brand = "Geely";
        Engine engine = new Engine(0, "V6");
        engine = engineRepository.create(engine);
        Car car = new Car(0, engine, new HashSet<>(), brand);
        var rsl = carRepository.create(car);
        assertThat(rsl.getBrand()).isEqualTo(car.getBrand());
    }

    @Test
    public void whenUpdateCar() {
        String brand = "Geely";
        Engine engine = new Engine(0, "V6");
        engine = engineRepository.create(engine);
        Car car = new Car(0, engine, new HashSet<>(), brand);
        carRepository.create(car);

        car.setBrand("Geely Updated");
        carRepository.update(car);

        Optional<Car> updatedCar = carRepository.findById(car.getId());
        assertThat(updatedCar).isPresent();
        assertThat(updatedCar.get().getBrand()).isEqualTo("Geely Updated");
    }

    @Test
    public void whenDeleteCar() {
        String brand = "Geely";
        Engine engine = new Engine(0, "V6");
        engine = engineRepository.create(engine);
        Car car = new Car(0, engine, new HashSet<>(), brand);
        car = carRepository.create(car);

        carRepository.delete(car.getId());

        Optional<Car> deletedCar = carRepository.findById(car.getId());
        assertThat(deletedCar).isEmpty();
    }

    @Test
    public void whenFindById() {
        String brand = "Geely";
        Engine engine = new Engine(0, "V6");
        engine = engineRepository.create(engine);
        Car car = new Car(0, engine, new HashSet<>(), brand);
        car = carRepository.create(car);

        Optional<Car> foundCar = carRepository.findById(car.getId());
        assertThat(foundCar).isPresent();
        assertThat(foundCar.get().getBrand()).isEqualTo(brand);
    }

    @Test
    public void whenFindAll() {
        String brand1 = "Geely";
        Engine engine1 = new Engine(0, "V6");
        engine1 = engineRepository.create(engine1);
        Car car1 = new Car(0, engine1, new HashSet<>(), brand1);
        carRepository.create(car1);

        String brand2 = "Toyota";
        Engine engine2 = new Engine(0, "V8");
        engine2 = engineRepository.create(engine2);
        Car car2 = new Car(0, engine2, new HashSet<>(), brand2);
        carRepository.create(car2);

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(2);
    }
}
