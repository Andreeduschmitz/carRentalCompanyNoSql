package model;

import java.sql.Date;
import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import bean.ClientBean;
import bean.RentalBean;
import bean.SellerBean;
import bean.VehicleBean;

public class RentalModel {
	
	public static String RENTAL_COLLECTION_NAME = "rental";
	
	public static String RENTAL_COLUMN_ID = "_id";

	public static String RENTAL_COLUMN_START_DATE = "startDate";
	public static String RENTAL_COLUMN_END_DATE = "endDate";
	public static String RENTAL_COLUMN_RENOVATION_ID = "renovationId";
	public static String RENTAL_COLUMN_VEHICLE_ID = "vehicleId";
	public static String RENTAL_COLUMN_SELLER_ID = "sellerId";
	public static String RENTAL_COLUMN_CLIENT_ID = "clientId";

	public static void createRental(RentalBean rental, MongoDatabase connection) throws Exception {
		MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

	    InsertOneResult result = rentalCollection.insertOne(rental);
	    
	    if(result.getInsertedId() != null) {
	        System.out.println("Aluguel inserido com sucesso!!");
	    } else {
	        System.out.println("Não foi possível inserir o aluguel");
	    }
	}
	
	public static void createRenovation(RentalBean rental, MongoDatabase connection) throws Exception {
		RentalModel.createRental(rental, connection);
	}
	
    public static ArrayList<RentalBean> searchRentalByClient(ClientBean client, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.eq(RENTAL_COLUMN_CLIENT_ID, client.getId().toHexString());
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : rentalCollection.find(filter)) {
            rentals.add(rental);
        }

        return rentals;
    }

    public static ArrayList<RentalBean> searchRentalByVehicle(VehicleBean vehicle, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.eq(RENTAL_COLUMN_VEHICLE_ID, vehicle.getVehicleId().toHexString());
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : rentalCollection.find(filter)) {
            rentals.add(rental);
        }

        return rentals;
    }
	
    public static ArrayList<RentalBean> searchRentalByDatePeriod(Date startDate, Date endDate, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.and(
            Filters.gte(RENTAL_COLUMN_START_DATE, startDate),
            Filters.lte(RENTAL_COLUMN_END_DATE, endDate)
        );
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : rentalCollection.find(filter)) {
            rentals.add(rental);
        }

        return rentals;
    }

    public static ArrayList<RentalBean> searchRentalByDatePeriodAndSeller(Date startDate, Date endDate, SellerBean seller, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.and(
            Filters.eq(RENTAL_COLUMN_SELLER_ID, seller.getSellerId().toHexString()),
            Filters.gte(RENTAL_COLUMN_START_DATE, startDate),
            Filters.lte(RENTAL_COLUMN_END_DATE, endDate)
        );
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : rentalCollection.find(filter)) {
            rentals.add(rental);
        }

        return rentals;
    }
	
    public static ArrayList<RentalBean> searchRentalByVehicleAndPeriod(VehicleBean vehicle, Date startDate, Date endDate, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.and(
            Filters.eq(RENTAL_COLUMN_VEHICLE_ID, vehicle.getVehicleId().toHexString()),
            Filters.gte(RENTAL_COLUMN_START_DATE, startDate),
            Filters.lte(RENTAL_COLUMN_END_DATE, endDate)
        );
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : rentalCollection.find(filter)) {
            rentals.add(rental);
        }

        return rentals;
    }
	
    //TODO essa ainda não está certa a lógica
    public static long countAssociatedRentals(RentalBean rental, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.eq(RENTAL_COLUMN_RENOVATION_ID, rental.getRentalId().toHexString());
        long count = rentalCollection.countDocuments(filter);
        
        return count;
    }
}