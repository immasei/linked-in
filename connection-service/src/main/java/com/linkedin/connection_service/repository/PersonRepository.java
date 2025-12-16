package com.linkedin.connection_service.repository;

import com.linkedin.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> getByName(String name);

    @Query("MATCH (p1:Person) -[:CONNECTED_TO]- (p2:Person) " +
            "WHERE p1.userId = $userId " +
            "RETURN p2")
    List<Person> getFirstDegreeConnections(Long userId);

    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "RETURN count(r) > 0")
    boolean connectionRequestExists(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person)-[r:CONNECTED_TO]-(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "RETURN count(r) > 0")
    boolean alreadyConnected(Long senderId, Long receiverId);

    @Query("MERGE (p1:Person {userId: $senderId}) " +
            "MERGE (p2:Person {userId: $receiverId}) " +
            "MERGE (p1)-[:REQUESTED_TO]->(p2)")
    void addConnectionRequest(Long senderId, Long receiverId);

    @Query("""
    MATCH (p1:Person {userId: $senderId})
    MATCH (p2:Person {userId: $receiverId})
    OPTIONAL MATCH (p1)-[r:REQUESTED_TO]-(p2)
    DELETE r
    MERGE (p1)-[:CONNECTED_TO]-(p2)
    """)
    void acceptConnectionRequest(Long senderId, Long receiverId);

    @Query("MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person) " +
            "WHERE p1.userId = $senderId AND p2.userId = $receiverId " +
            "DELETE r")
    void rejectConnectionRequest(Long senderId, Long receiverId);

}