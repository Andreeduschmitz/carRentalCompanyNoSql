package model;

import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import bean.SellerBean;

public class SellerModel {
	
    public static String SELLER_COLLECTION_NAME = "seller";
    
    public static String SELLER_COLUMN_ID = "";
    
    public static String SELLER_COLUMN_NAME = "sellerName";
    public static String SELLER_COLUMN_PHONE = "sellerPhone";
    public static String SELLER_COLUMN_EMAIL = "sellerEmail";
    public static String SELLER_COLUMN_IS_ACTIVE = "isActive";

    public static void create(SellerBean seller, MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        InsertOneResult result = sellerCollection.insertOne(seller);

        if (result.getInsertedId() != null) {
            System.out.println("Vendedor inserido com sucesso!");
        } else {
            System.out.println("Não foi possível inserir o vendedor.");
        }
    }
	
    public static void update(SellerBean seller, MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        Bson filter = Filters.eq(SELLER_COLUMN_ID, seller.getSellerId());
        Bson update = Updates.combine(
            Updates.set(SELLER_COLLECTION_NAME, seller.getSellerName()),
            Updates.set(SELLER_COLUMN_PHONE, seller.getSellerPhone()),
            Updates.set(SELLER_COLUMN_EMAIL, seller.getSellerEmail()),
            Updates.set(SELLER_COLUMN_IS_ACTIVE, true)
        );

        long modifiedCount = sellerCollection.updateOne(filter, update).getModifiedCount();

        if (modifiedCount > 0) {
            System.out.println("Vendedor atualizado com sucesso!");
        } else {
            System.out.println("Nenhum vendedor foi atualizado.");
        }
    }
	
    public static void delete(SellerBean seller, MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        Bson filter = Filters.eq(SELLER_COLUMN_ID, seller.getSellerId());
        Bson update = Updates.set(SELLER_COLUMN_IS_ACTIVE, false);

        long modifiedCount = sellerCollection.updateOne(filter, update).getModifiedCount();

        if (modifiedCount > 0) {
            System.out.println("Vendedor desativado com sucesso!");
        } else {
            System.out.println("Nenhum vendedor foi desativado.");
        }
    }
	
    public static ArrayList<SellerBean> listAll(MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        ArrayList<SellerBean> list = new ArrayList<>();
        Bson filter = Filters.eq(SELLER_COLUMN_IS_ACTIVE, true);
        FindIterable<SellerBean> result = sellerCollection.find(filter);

        for (SellerBean seller : result) {
            list.add(seller);
        }

        return list;
    }

    public static ArrayList<SellerBean> searchByName(String name, MongoDatabase connection) {
    	MongoCollection<SellerBean> sellerCollection = connection.getCollection(SELLER_COLLECTION_NAME, SellerBean.class);

        ArrayList<SellerBean> list = new ArrayList<>();
        Bson filter = Filters.and(
                Filters.regex(SELLER_COLLECTION_NAME, ".*" + name + ".*", "i"),
                Filters.eq(SELLER_COLUMN_IS_ACTIVE, true)
        );
        FindIterable<SellerBean> result = sellerCollection.find(filter);

        for (SellerBean seller : result) {
            list.add(seller);
        }

        return list;
    }
}
