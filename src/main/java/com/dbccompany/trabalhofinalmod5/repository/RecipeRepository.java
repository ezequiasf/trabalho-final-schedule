package com.dbccompany.trabalhofinalmod5.repository;

import com.dbccompany.trabalhofinalmod5.config.ConnectionMongo;
import com.dbccompany.trabalhofinalmod5.dto.RecipeShowDTO;
import com.dbccompany.trabalhofinalmod5.entity.Classification;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RecipeRepository {

    private final static String DATABASE = "recipes_app";
    private final static String COLLECTION = "recipes";

    public RecipeEntity findById(String hexId) {
        MongoClient client = ConnectionMongo.createConnection();

        Document docRecipe = getCollectionRecipe(client).find(new Document("_id", new ObjectId(hexId))).first();

        return convertDocument(docRecipe);
    }

    public String saveRecipe(RecipeEntity recipe) {
        MongoClient client = ConnectionMongo.createConnection();

        BsonValue objId = getCollectionRecipe(client).insertOne(
                new Document("author", recipe.getAuthor())
                        .append("recipeName", recipe.getRecipeName())
                        .append("prepareRecipe", recipe.getPrepareRecipe())
                        .append("prepareTime", recipe.getPrepareTime())
                        .append("price", recipe.getPrice())
                        .append("calories", recipe.getCalories())
                        .append("ingredients", recipe.getIngredients())
        ).getInsertedId();

        ConnectionMongo.closeConnection(client);
        return objId.asObjectId().getValue().toHexString();
    }

    public void updateRecipe(String hexId, RecipeEntity recipe) {
        MongoClient client = ConnectionMongo.createConnection();

        getCollectionRecipe(client).updateOne(new Document("_id", new ObjectId(hexId)),
                new Document("$set", convertRecipeEntity(recipe)));

        ConnectionMongo.closeConnection(client);
    }

    public void updateClassifications(String hexId, RecipeShowDTO recipeShowDTO) {
        MongoClient client = ConnectionMongo.createConnection();

        getCollectionRecipe(client).updateOne(new Document("_id", new ObjectId(hexId)),
                new Document("$set", new Document("classifications", recipeShowDTO.getClassifications()
                        .stream().map(classification -> new Document("authorClass", classification.getAuthorClass())
                                .append("rating", classification.getRating())
                                .append("coment", classification.getComent())).collect(Collectors.toList()))));

        ConnectionMongo.closeConnection(client);
    }

    public RecipeEntity findByRecipeName(String recipeName) throws RecipeNotFoundException {
        MongoClient client = ConnectionMongo.createConnection();

        Document docRecipe = getCollectionRecipe(client)
                .find(new Document("recipeName", recipeName)).first();

        ConnectionMongo.closeConnection(client);

        if (docRecipe != null) {
            return convertDocument(docRecipe);
        }
        throw new RecipeNotFoundException("Recipe not found!");
    }

    public void deleteRecipe(String hexId) {
        MongoClient client = ConnectionMongo.createConnection();

        getCollectionRecipe(client).findOneAndDelete(new Document("_id", new ObjectId(hexId)));

        ConnectionMongo.closeConnection(client);
    }

    private RecipeEntity convertDocument(Document docRecipe) {
        RecipeEntity recipeEntity = RecipeEntity.builder().objectId(docRecipe.getObjectId("_id").toString())
                .recipeName(docRecipe.getString("recipeName"))
                .author(docRecipe.getString("author"))
                .calories(docRecipe.getDouble("calories"))
                .prepareRecipe(docRecipe.getString("prepareRecipe"))
                .price(docRecipe.getDouble("price"))
                .prepareTime(docRecipe.getInteger("prepareTime"))
                .ingredients(docRecipe.getList("ingredients", String.class))
                .build();
        List<Document> docClass = docRecipe.getList("classifications", Document.class);
        if (docClass != null) {
            recipeEntity.setClassifications(docClass.stream()
                    .map(doc -> Classification.builder()
                            .authorClass(doc.getString("authorClass"))
                            .rating(doc.getDouble("rating"))
                            .coment(doc.getString("coment"))
                            .build()).collect(Collectors.toList()));
        }
        return recipeEntity;
    }

    private Document convertRecipeEntity(RecipeEntity recipe) {
        return new Document("author", recipe.getAuthor())
                .append("recipeName", recipe.getRecipeName())
                .append(("prepareRecipe"), recipe.getPrepareRecipe())
                .append("prepareTime", recipe.getPrepareTime())
                .append("price", recipe.getPrice())
                .append("calories", recipe.getCalories())
                .append("ingredients", recipe.getIngredients());
    }

    public MongoCollection<Document> getCollectionRecipe(MongoClient client) {
        return client.getDatabase(DATABASE).getCollection(COLLECTION);
    }

    public void deleteClassification(String author, String objectIdRecipe) {
        MongoClient client = ConnectionMongo.createConnection();

        BasicDBObject objectId = new BasicDBObject("_id", new ObjectId(objectIdRecipe));
        BasicDBObject objClass = new BasicDBObject("classifications"
                , new BasicDBObject("authorClass", author));
        BasicDBObject update = new BasicDBObject("$pull", objClass);

        getCollectionRecipe(client).updateOne(objectId, update);

        ConnectionMongo.closeConnection(client);
    }
}
