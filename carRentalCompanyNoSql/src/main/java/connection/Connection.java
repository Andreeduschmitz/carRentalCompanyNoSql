package connection;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Connection {
	private static MongoClient connection;

	public static MongoDatabase getConnection() throws Exception {

		try {
			if (connection == null) {
				connection = MongoClients.create();
			}

			CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			MongoDatabase dtb = connection.getDatabase("AulaMongo").withCodecRegistry(pojoCodecRegistry);
			return dtb;

		} catch (Exception e) {
			throw e;
		}
	}

	public static void closeConnection() {
		if (connection != null) {
			connection.close();
		}
	}
}
