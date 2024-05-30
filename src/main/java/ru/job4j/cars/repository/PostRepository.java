package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> getPostLastDay() {
        return crudRepository.query("SELECT FROM Post p WHERE p.created >= :date",
                Post.class, Map.of("date", LocalDateTime.now().minusDays(1)));
    }

    public List<Post> getPostsWithPhotos() {
        return crudRepository.query("SELECT FROM Post p WHERE p.photo is not empty",
                Post.class);
    }

    public List<Post> getPostsByBrand(String brand) {
        return crudRepository.query("SELECT FROM Post p WHERE p.car.brand = :brand",
                Post.class, Map.of("brand", brand));
    }
}
