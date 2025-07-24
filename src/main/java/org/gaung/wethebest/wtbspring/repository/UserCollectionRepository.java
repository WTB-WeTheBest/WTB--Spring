package org.gaung.wethebest.wtbspring.repository;

import org.gaung.wethebest.wtbspring.model.UserCollection;
import org.gaung.wethebest.wtbspring.model.UserCollectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, UserCollectionId> {
}
