package net.engineeringdigest.JournalApp.Repository;

import net.engineeringdigest.JournalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalAppRepository extends MongoRepository<JournalEntry, ObjectId> {
}
