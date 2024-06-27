package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.HistoryOwner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HistoryOwnerRepository {
    private final CrudRepository crudRepository;

    public HistoryOwner create(HistoryOwner historyOwner) {
        crudRepository.run(session -> session.persist(historyOwner));
        return historyOwner;
    }

    public void update(HistoryOwner historyOwner) {
        crudRepository.run(session -> session.merge(historyOwner));
    }

    public void delete(int historyOwnerId) {
        crudRepository.run(
                "delete from HistoryOwner where id = :fId",
                Map.of("fId", historyOwnerId)
        );
    }

    public Optional<HistoryOwner> findById(int historyOwnerId) {
        return crudRepository.optional(
                "from HistoryOwner where id = :fId", HistoryOwner.class,
                Map.of("fId", historyOwnerId)
        );
    }

    public List<HistoryOwner> findAll() {
        return crudRepository.query("from HistoryOwner order by id", HistoryOwner.class);
    }
}
