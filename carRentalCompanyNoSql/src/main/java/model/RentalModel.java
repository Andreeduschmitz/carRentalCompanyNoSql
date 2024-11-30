package model;

import java.sql.Date;
import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
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

        Bson filter = Filters.eq(ClientModel.CLIENT_COLUMN_ID, client.getClientId());
        FindIterable<RentalBean> result = rentalCollection.find(filter);
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : result) {
            rentals.add(rental);
        }

        return rentals;
    }

    public static ArrayList<RentalBean> searchRentalByVehicle(VehicleBean vehicle, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.eq(VehicleModel.VEHICLE_COLUMN_ID, vehicle.getVehicleId());
        FindIterable<RentalBean> result = rentalCollection.find(filter);
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : result) {
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
        FindIterable<RentalBean> result = rentalCollection.find(filter);
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : result) {
            rentals.add(rental);
        }

        return rentals;
    }

    public static ArrayList<RentalBean> searchRentalByDatePeriodAndSeller(Date startDate, Date endDate, SellerBean seller, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.and(
            Filters.eq(SellerModel.SELLER_COLUMN_ID, seller.getSellerId()),
            Filters.gte(RENTAL_COLUMN_START_DATE, startDate),
            Filters.lte(RENTAL_COLUMN_END_DATE, endDate)
        );
        FindIterable<RentalBean> result = rentalCollection.find(filter);
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : result) {
            rentals.add(rental);
        }

        return rentals;
    }
	
    public static ArrayList<RentalBean> searchRentalByVehicleAndPeriod(VehicleBean vehicle, Date startDate, Date endDate, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.and(
            Filters.eq(VehicleModel.VEHICLE_COLUMN_ID, vehicle.getVehicleId()),
            Filters.gte(RENTAL_COLUMN_START_DATE, startDate),
            Filters.lte(RENTAL_COLUMN_END_DATE, endDate)
        );
        FindIterable<RentalBean> result = rentalCollection.find(filter);
        ArrayList<RentalBean> rentals = new ArrayList<>();

        for (RentalBean rental : result) {
            rentals.add(rental);
        }

        return rentals;
    }
	
    //TODO essa ainda não está certa a lógica
    public static long countAssociatedRentals(RentalBean rental, MongoDatabase connection) {
    	MongoCollection<RentalBean> rentalCollection = connection.getCollection(RENTAL_COLLECTION_NAME, RentalBean.class);

        Bson filter = Filters.eq(RentalModel.RENTAL_COLUMN_RENOVATION_ID, rental.getRentalId());
        long count = rentalCollection.countDocuments(filter);
        
        return count;
    }
}