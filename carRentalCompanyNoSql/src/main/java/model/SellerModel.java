package model;

import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import bean.SellerBean;

public class SellerModel {
	
    public static String SELLER_COLLECTION_NAME = "seller";
    
    public static String SELLER_COLUMN_ID = "_id";
    
    public static String SELLER_COLUMN_NAME = "sellerName";
    public static String SELLER_COLUMN_PHONE = "sellerPhone";
    public static String SELLER_COLUMN_EMAIL = "sellerEmail";
    public static String SELLER_COLUMN_IS_ACTIVE = "active";

    public static void create(SellerBean seller, MongoDatabase connection) throws Exception {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        InsertOneResult result = sellerCollection.insertOne(seller);

        if (result.getInsertedId() == null) {
        	throw new Exception("Ocorreu um erro ao inserir o vendedor no banco de dados.");
        }
    }
	
    public static void update(SellerBean seller, MongoDatabase connection) throws Exception {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        Bson filter = Filters.eq(SELLER_COLUMN_ID, seller.getId());

        Bson update = Updates.combine(
            Updates.set(SELLER_COLUMN_NAME, seller.getSellerName()),	
            Updates.set(SELLER_COLUMN_PHONE, seller.getSellerPhone()),
            Updates.set(SELLER_COLUMN_EMAIL, seller.getSellerEmail()),
            Updates.set(SELLER_COLUMN_IS_ACTIVE, true)
        );

        long modifiedCount = sellerCollection.updateOne(filter, update).getModifiedCount();

        if (!(modifiedCount > 0)) {
        	throw new Exception("Ocorreu um erro ao atualizar o vendedor no banco de dados.");
        }
    }
	
    public static void delete(SellerBean seller, MongoDatabase connection) throws Exception {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        Bson filter = Filters.eq(SELLER_COLUMN_ID, seller.getId());
        Bson update = Updates.set(SELLER_COLUMN_IS_ACTIVE, false);

        long modifiedCount = sellerCollection.updateOne(filter, update).getModifiedCount();

        if (!(modifiedCount > 0)) {
        	throw new Exception("Ocorreu um erro ao remover o vendedor no banco de dados.");
        }
    }
	
    public static ArrayList<SellerBean> listAll(MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        ArrayList<SellerBean> list = new ArrayList<>();
        Bson filter = Filters.eq(SELLER_COLUMN_IS_ACTIVE, true);

        for (SellerBean seller : sellerCollection.find(filter)) {
            list.add(seller);
        }

        return list;
    }

    public static ArrayList<SellerBean> searchByName(String name, MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        ArrayList<SellerBean> list = new ArrayList<>();
        Bson filter = Filters.and(
                Filters.regex(SELLER_COLUMN_NAME, ".*" + name + ".*", "i"),
                Filters.eq(SELLER_COLUMN_IS_ACTIVE, true)
        );

        for (SellerBean seller : sellerCollection.find(filter)) {
            list.add(seller);
        }

        return list;
    }
}
