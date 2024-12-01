package model;

import java.util.ArrayList;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import bean.VehicleBean;

public class VehicleModel {
	
    public static String VEHICLE_COLLECTION_NAME = "vehicle";
    
    public static String VEHICLE_COLUMN_ID = "_id";
    
    public static String VEHICLE_COLUMN_PLATE = "vehiclePlate";
    public static String VEHICLE_COLUMN_MODEL = "vehicleModel";
    public static String VEHICLE_COLUMN_LAUNCH_YEAR = "vehicleLaunchYear";
    public static String VEHICLE_COLUMN_BRAND = "vehicleBrand";
    public static String VEHICLE_COLUMN_CATEGORY = "vehicleCategory";
    public static String VEHICLE_COLUMN_DAILY_VALUE = "dailyValue";
    public static String VEHICLE_COLUMN_IS_ACTIVE = "active";

    public static void create(VehicleBean vehicle, MongoDatabase connection) throws Exception {
    	MongoCollection<VehicleBean> vehicleCollection = connection.getCollection(VEHICLE_COLLECTION_NAME, VehicleBean.class);

        InsertOneResult result = vehicleCollection.insertOne(vehicle);

        if (result.getInsertedId() == null) {
        	throw new Exception("Ocorreu um erro ao inserir o veículo no banco de dados.");
        }
    }
	
    public static void update(VehicleBean vehicle, MongoDatabase connection) throws Exception {
    	MongoCollection<VehicleBean> vehicleCollection = connection.getCollection(VEHICLE_COLLECTION_NAME, VehicleBean.class);

        Bson filter = Filters.eq(VEHICLE_COLUMN_ID, vehicle.getId());
        Bson update = Updates.combine(
                Updates.set(VEHICLE_COLUMN_PLATE, vehicle.getVehiclePlate()),
                Updates.set(VEHICLE_COLUMN_MODEL, vehicle.getVehicleModel()),
                Updates.set(VEHICLE_COLUMN_LAUNCH_YEAR, vehicle.getVehicleLaunchYear()),
                Updates.set(VEHICLE_COLUMN_CATEGORY, vehicle.getVehicleCategory()),
                Updates.set(VEHICLE_COLUMN_DAILY_VALUE, vehicle.getDailyValue()),
                Updates.set(VEHICLE_COLUMN_BRAND, vehicle.getVehicleBrand()),
                Updates.set(VEHICLE_COLUMN_IS_ACTIVE, true)
        );

        long modifiedCount = vehicleCollection.updateOne(filter, update).getModifiedCount();

        if (!(modifiedCount > 0)) {
        	throw new Exception("Ocorreu um erro ao atualizar o veículo no banco de dados.");
        }
    }
	
    public static void delete(VehicleBean vehicle, MongoDatabase connection) throws Exception {
    	MongoCollection<VehicleBean> vehicleCollection = connection.getCollection(VEHICLE_COLLECTION_NAME, VehicleBean.class);

        Bson filter = Filters.eq(VEHICLE_COLUMN_ID, vehicle.getId());
        Bson update = Updates.set(VEHICLE_COLUMN_IS_ACTIVE, false);

        long modifiedCount = vehicleCollection.updateOne(filter, update).getModifiedCount();

        if (!(modifiedCount > 0)) {
        	throw new Exception("Ocorreu um erro ao remover o veículo no banco de dados.");
        }
    }
	
    public static ArrayList<VehicleBean> listAll(MongoDatabase connection) {
    	MongoCollection<VehicleBean> vehicleCollection = connection.getCollection(VEHICLE_COLLECTION_NAME, VehicleBean.class);

        ArrayList<VehicleBean> list = new ArrayList<>();
        Bson filter = Filters.eq(VEHICLE_COLUMN_IS_ACTIVE, true);

        for (VehicleBean vehicle : vehicleCollection.find(filter)) {
            list.add(vehicle);
        }

        return list;
    }

    public static ArrayList<VehicleBean> listBySearch(VehicleBean vehicle, MongoDatabase connection) {
    	MongoCollection<VehicleBean> vehicleCollection = connection.getCollection(VEHICLE_COLLECTION_NAME, VehicleBean.class);

        ArrayList<VehicleBean> list = new ArrayList<>();
        ArrayList<Bson> filtersList = new ArrayList<>();
        filtersList.add(Filters.eq(VEHICLE_COLUMN_IS_ACTIVE, true));

        if (vehicle.getVehiclePlate() != null) {
        	filtersList.add(Filters.and(Filters.regex(VEHICLE_COLUMN_PLATE, ".*" + vehicle.getVehiclePlate() + ".*", "i")));
        } else if (vehicle.getVehicleModel() != null) {
        	filtersList.add(Filters.and(Filters.regex(VEHICLE_COLUMN_MODEL, ".*" + vehicle.getVehicleModel() + ".*", "i")));
        } else if (vehicle.getVehicleCategory() != null) {
        	filtersList.add(Filters.and(Filters.eq(VEHICLE_COLUMN_CATEGORY, vehicle.getVehicleCategory())));
        } else if (vehicle.getDailyValue() != null) {
        	filtersList.add(Filters.and(Filters.lte(VEHICLE_COLUMN_DAILY_VALUE, vehicle.getDailyValue())));
        } else if (vehicle.getVehicleBrand() != null) {
        	filtersList.add(Filters.and(Filters.regex(VEHICLE_COLUMN_BRAND, ".*" + vehicle.getVehicleBrand() + ".*", "i")));
        }
        
        Bson filter = filtersList.isEmpty() ? Filters.empty() : Filters.and(filtersList);

        for (VehicleBean v : vehicleCollection.find(filter)) {
            list.add(v);
        }

        return list;
    }
}