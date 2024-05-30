package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Photo;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class PhotoRepository {
    private final CrudRepository crudRepository;

    public Photo create(Photo photo) {
        crudRepository.run(session -> session.persist(photo));
        return photo;
    }

    public void update(Photo photo) {
        crudRepository.run(session -> session.merge(photo));
    }

    public void delete(int photoId) {
        crudRepository.run(
                "delete from Photo where id = :fId",
                Map.of("fId", photoId)
        );
    }

    public Optional<Photo> findById(int photoId) {
        return crudRepository.optional(
                "from Photo where id = :fId", Photo.class,
                Map.of("fId", photoId)
        );
    }
}
