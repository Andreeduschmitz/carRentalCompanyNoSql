package model;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import bean.ClientBean;

public class ClientModel {
	public static String CLIENT_COLLECTION_NAME = "client";
	
	public static String CLIENT_COLUMN_ID = "_id";
	
	public static String CLIENT_COLUMN_NAME = "clientName";
	public static String CLIENT_COLUMN_CPF = "clientCpf";
	public static String CLIENT_COLUMN_PHONE= "clientPhone";
	public static String CLIENT_COLUMN_EMAIL = "clientEmail";
	public static String CLIENT_COLUMN_IS_ACTIVE = "active";
	
	public static void create(ClientBean client, MongoDatabase connection) {
		MongoCollection<ClientBean> clientCollection = connection.getCollection(CLIENT_COLLECTION_NAME, ClientBean.class);
		
		InsertOneResult result = clientCollection.insertOne(client);
		
		if(result.getInsertedId() != null) {
			System.out.println("Usuario inserido com sucesso!!");			
		} else {
			System.out.println("Não foi possivel inserir");
		}
 	}
	
	public static void update(ClientBean client, MongoDatabase connection) {
		MongoCollection<ClientBean> clientCollection = connection.getCollection(CLIENT_COLLECTION_NAME, ClientBean.class);
		
		Bson filter = Filters.eq(CLIENT_COLUMN_ID, client.getId());

		Bson updates = Updates.combine(
			Updates.set(CLIENT_COLUMN_NAME, client.getClientName()),
			Updates.set(CLIENT_COLUMN_CPF, client.getClientCpf()),
			Updates.set(CLIENT_COLUMN_PHONE, client.getClientPhone()),
			Updates.set(CLIENT_COLUMN_EMAIL, client.getClientEmail()),
			Updates.set(CLIENT_COLUMN_IS_ACTIVE, true)
		);

		long modifiedCount = clientCollection.updateOne(filter, updates).getModifiedCount();

		if (modifiedCount > 0) {
			System.out.println("Usuário atualizado com sucesso!");
		} else {
			System.out.println("Nenhum usuário foi atualizado.");
		}
	}
	
    public static void delete(ClientBean client, MongoDatabase connection) {
    	MongoCollection<ClientBean> clientCollection = connection.getCollection(CLIENT_COLLECTION_NAME, ClientBean.class);
    	
        Bson filter = Filters.eq(CLIENT_COLUMN_ID, client.getId());
        Bson update = Updates.set(CLIENT_COLUMN_IS_ACTIVE, false);

        long modifiedCount = clientCollection.updateOne(filter, update).getModifiedCount();

        if (modifiedCount > 0) {
            System.out.println("Usuário desativado com sucesso!");
        } else {
            System.out.println("Nenhum usuário foi desativado.");
        }
    }
	
    public static List<ClientBean> listAll(MongoDatabase connection) {
    	MongoCollection<ClientBean> clientCollection = connection.getCollection(CLIENT_COLLECTION_NAME, ClientBean.class);

        List<ClientBean> list = new ArrayList<>();
        
        Bson filter = Filters.eq(CLIENT_COLUMN_IS_ACTIVE, true);

        for (ClientBean client : clientCollection.find(filter)) {
            list.add(client);
        }

        return list;
    }

	
    public static ArrayList<ClientBean> search(String cpf, String name, MongoDatabase connection) {
    	MongoCollection<ClientBean> clientCollection = connection.getCollection(CLIENT_COLLECTION_NAME, ClientBean.class);

        ArrayList<ClientBean> list = new ArrayList<>();
        ArrayList<Bson> filtersList = new ArrayList<>();
        
        if (name != null && !name.isEmpty()) {
            filtersList.add(Filters.regex(CLIENT_COLUMN_NAME, ".*" + name + ".*", "i"));
        }

        if (cpf != null && !cpf.isEmpty()) {
            filtersList.add(Filters.eq(CLIENT_COLUMN_CPF, cpf));
        }

        filtersList.add(Filters.eq(CLIENT_COLUMN_IS_ACTIVE, true));

        Bson filter = filtersList.isEmpty() ? Filters.empty() : Filters.and(filtersList);

        for (ClientBean client : clientCollection.find(filter)) {
            list.add(client);
        }

        return list;
    }
}