package net.engineeringdigest.JournalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Builder
@Document(collection = "user")
@Data
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @DBRef
    List<JournalEntry> journalEntries=new ArrayList<>();

    private List<String> role;


}
