package net.engineeringdigest.JournalApp.Repository;

import net.engineeringdigest.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
            Query query = new Query();
            query.addCriteria(Criteria.where("userName").is("nitish"));
            query.addCriteria(Criteria.where("sentimentalAnalysis").exists(false));
            List<User> users = mongoTemplate.find(query, User.class);
            if (users != null && !users.isEmpty()) {
                return users;
            }

        return null;
    }
}
