package model;

import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import bean.AddressBean;
import bean.ClientBean;

public class AddressModel {
	public static String ADDRESS_COLLECTION_NAME = "address";
	
	public static String ADDRESS_COLUMN_ID = "_id";
	
	public static String ADDRESS_COLUMN_CEP = "addressCep";
	public static String ADDRESS_COLUMN_STREET = "addressStreet";
	public static String ADDRESS_COLUMN_NEIGHBORHOOD = "addressNeighborhood";
	public static String ADDRESS_COLUMN_NUMBER = "addressNumber";
	public static String ADDRESS_COLUMN_COMPLEMENT = "addressComplement";
	public static String ADDRESS_COLUMN_CLIENT_ID = "clientId";

    public static void create(AddressBean address, MongoDatabase connection) throws Exception {
    	MongoCollection<AddressBean> addressCollection = connection.getCollection(ADDRESS_COLLECTION_NAME, AddressBean.class);
    	
        InsertOneResult result = addressCollection.insertOne(address);

        if (result.getInsertedId() == null) {
            throw new Exception("Ocorreu um erro ao inserir o endereço no banco de dados.");
        }
    }
	
    public static void update(AddressBean address, MongoDatabase connection) throws Exception {
    	MongoCollection<AddressBean> addressCollection = connection.getCollection(ADDRESS_COLLECTION_NAME, AddressBean.class);
    	
        Bson filter = Filters.eq(ADDRESS_COLUMN_ID, address.getId());

        Bson updateOperation = Updates.combine(
            Updates.set(ADDRESS_COLUMN_CEP, address.getAddressCep()),
            Updates.set(ADDRESS_COLUMN_STREET, address.getAddressStreet()),
            Updates.set(ADDRESS_COLUMN_NEIGHBORHOOD, address.getAddressNeighborhood()),
            Updates.set(ADDRESS_COLUMN_NUMBER, address.getAddressNumber()),
            Updates.set(ADDRESS_COLUMN_COMPLEMENT, address.getAddressComplement())
        );

        addressCollection.updateOne(filter, updateOperation);
    }

	
    public static void delete(AddressBean address, MongoDatabase connection) throws Exception {
    	MongoCollection<AddressBean> addressCollection = connection.getCollection(ADDRESS_COLLECTION_NAME, AddressBean.class);
    	
        Bson filter = Filters.eq(ADDRESS_COLUMN_ID, address.getId());
        addressCollection.deleteOne(filter);
    }

	
    public static ArrayList<AddressBean> findAddressByClient(ClientBean client, MongoDatabase connection) throws Exception {
    	MongoCollection<AddressBean> addressCollection = connection.getCollection(ADDRESS_COLLECTION_NAME, AddressBean.class);
    	
        Bson filter = Filters.eq(ADDRESS_COLUMN_CLIENT_ID, client.getId().toHexString());
        ArrayList<AddressBean> addresses = new ArrayList<>();
        
        for (AddressBean address : addressCollection.find(filter)) {
            addresses.add(address);
        }
        
        return addresses;
    }
}