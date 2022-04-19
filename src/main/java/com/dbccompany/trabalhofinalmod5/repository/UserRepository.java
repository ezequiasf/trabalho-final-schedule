package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Projections.fields;

@Repository
public class UserRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "users";

    public UserEntity findById(String hexId) {
        MongoClient client = ConnectionMongo.createConnection();

        Document docUser = getCollectionUser(client).find(new Document("_id", new ObjectId(hexId))).first();

        return convertDocument(docUser);
    }

    public String saveUser(UserEntity user) {
        MongoClient client = ConnectionMongo.createConnection();

        MongoCollection<Document> userCollection = getCollectionUser(client);

        BsonValue objId = userCollection.insertOne(convertUserEntity(user)).getInsertedId();

        ConnectionMongo.closeConnection(client);

        return objId.asObjectId().getValue().toHexString();
    }

    public void updateUser(String hexId, UserEntity newUser) {
        MongoClient client = ConnectionMongo.createConnection();

        getCollectionUser(client).updateOne(new Document("_id", new ObjectId(hexId)),
                new Document("$set", convertUserEntity(newUser)));

        ConnectionMongo.closeConnection(client);
    }

    public void deleteUser(String hexId) {
        MongoClient client = ConnectionMongo.createConnection();

        getCollectionUser(client).updateOne(new Document("_id", new ObjectId(hexId)),
                new Document("$set", new Document("isactive", false)));

        ConnectionMongo.closeConnection(client);
    }


    public UserEntity findByUsername(String username) {
        MongoClient client = ConnectionMongo.createConnection();

        Document docUser = getCollectionUser(client).find(new Document("username", username)).first();

        ConnectionMongo.closeConnection(client);
        if (docUser != null) {
            return convertDocument(docUser);
        }
        return null;
    }

    private MongoCollection<Document> getCollectionUser(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }

    private UserEntity convertDocument(Document docUser) {
        JSONObject jsonObject = new JSONObject(docUser.toJson());
        return UserEntity.builder()
                .objectId(jsonObject.getJSONObject("_id").getString("$oid"))
                .username(docUser.getString("username"))
                .password(docUser.getString("password"))
                .email(docUser.getString("email"))
                .age(docUser.getInteger("age"))
                .isactive(docUser.getBoolean("isactive"))
                .build();
    }

    private Document convertUserEntity(UserEntity user) {
        return new Document("username", user.getUsername())
                .append("email", user.getEmail())
                .append(("age"), user.getAge())
                .append("password", user.getPassword())
                .append("isactive", user.isIsactive());
    }

    public List<UserEntity> aggregatingUser(Bson pipeline, Bson... options) {
        MongoClient client = ConnectionMongo.createConnection();

        List<UserEntity> userEntities = new ArrayList<>();
        List<Bson> aggregation = new ArrayList<>();

        aggregation.add(pipeline);
        aggregation.addAll(Arrays.asList(options));

        getCollectionUser(client).aggregate(aggregation).forEach(doc -> userEntities.add(convertDocument(doc)));

        ConnectionMongo.closeConnection(client);
        return userEntities;
    }


    public List<UserEntity> findAllProjection(String... fields) {
        MongoClient client = ConnectionMongo.createConnection();

        List<UserEntity> userEntities = new ArrayList<>();

        Bson projection = fields(Projections.include(fields), Projections.excludeId());

        FindIterable<Document> users = getCollectionUser(client).find().projection(projection);

        users.forEach(document -> userEntities.add(convertDocument(document)));

        ConnectionMongo.closeConnection(client);
        return userEntities;
    }

}
