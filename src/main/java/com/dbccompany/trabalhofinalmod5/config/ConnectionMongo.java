package com.dbccompany.trabalhofinalmod5.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class ConnectionMongo {

    public final static String URL = "mongodb+srv://ezeq:HRznroxVyy37xQRw@clusterformyapp.cycds.mongodb.net/recipes_app?retryWrites=true&w=majority";

    public static MongoClient createConnection() {
        return MongoClients.create(URL);
    }

    public static void closeConnection(MongoClient client) {
        client.close();
    }
}
