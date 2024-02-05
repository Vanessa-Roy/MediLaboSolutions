package com.medilabosolutions.noteService.configuration;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class config {

    @Value("user.username")
    String username;

    @Value("user.password")
    String password;

    @Value("user.dataBase")
    String dataBase;

    public void createUser() {
        MongoClient mongoClient = new MongoClientImpl(MongoClientSettings.builder().build(), MongoDriverInformation.builder().build());
        final MongoDatabase db = mongoClient.getDatabase(dataBase);
        final BasicDBObject createUserCommand = new BasicDBObject("createUser", username).append("pwd", password).append("roles",
                Collections.singletonList(new BasicDBObject("role", "dbOwner").append("db", dataBase)));
        db.runCommand(createUserCommand);
    }
}
