package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.ClientBean;
import bean.RentalBean;
import bean.SellerBean;
import bean.VehicleBean;

public class RentalModel {

	public static void createRental(RentalBean rental, Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("INSERT INTO public.rental("
				+ "	startdate, enddate, vehicleid, sellerid, clientid)"
				+ "	VALUES (?, ?, ?, ?, ?);");
		ps.setDate(1, rental.getStartDate());
		ps.setDate(2, rental.getEndDate());
		ps.setInt(3, rental.getVehicleId());
		ps.setInt(4, rental.getSellerId());
		ps.setInt(5, rental.getClientId());
		
		ps.execute();
		ps.close();
	}
	
	public static void createRenovation(RentalBean rental, Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("INSERT INTO public.rental("
				+ "	startdate, enddate, vehicleid, sellerid, clientid, renovationid)"
				+ "	VALUES (?, ?, ?, ?, ?, ?);");
		ps.setDate(1, rental.getStartDate());
		ps.setDate(2, rental.getEndDate());
		ps.setInt(3, rental.getVehicleId());
		ps.setInt(4, rental.getSellerId());
		ps.setInt(5, rental.getClientId());
		ps.setInt(6, rental.getRenovationId());
		
		ps.execute();
		ps.close();
	}
	
	public static ArrayList<RentalBean> searchRentalByClient(ClientBean client, Connection con) throws SQLException {
		PreparedStatement ps;
		ps = con.prepareStatement("SELECT rentalid, startdate, enddate, renovationid, vehicleid, sellerid, clientid"
				+ "	FROM public.rental"
				+ " WHERE public.rental.clientid=?");
		ps.setInt(1, client.getClientId());
		
		ResultSet result = ps.executeQuery();
		ArrayList<RentalBean> rentals = new ArrayList<RentalBean>();
		
		while(result.next()) {
			rentals.add(new RentalBean(result.getInt(1), result.getDate(2), result.getDate(3), result.getInt(4), result.getInt(5), result.getInt(6), result.getInt(7)));
		}
		
		return rentals;
	}

	public static ArrayList<RentalBean> searchRentalByVehicle(VehicleBean vehicle, Connection con) throws SQLException {
		PreparedStatement ps;
		ps = con.prepareStatement("SELECT rentalid, startdate, enddate, renovationid, vehicleid, sellerid, clientid"
				+ "	FROM public.rental"
				+ " WHERE public.rental.vehicleid=?;");
		ps.setInt(1, vehicle.getVehicleId());
		
		ResultSet result = ps.executeQuery();
		ArrayList<RentalBean> rentals = new ArrayList<RentalBean>();
		
		while(result.next()) {
			rentals.add(new RentalBean(result.getInt(1), result.getDate(2), result.getDate(3), result.getInt(4), result.getInt(5), result.getInt(6), result.getInt(7)));
		}
		
		return rentals;
	}
	
	public static ArrayList<RentalBean> searchRentalByDatePeriod(Date startDate, Date endDate, Connection con) throws SQLException {
		PreparedStatement ps;
		ps = con.prepareStatement("SELECT rentalid, startdate, enddate, renovationid, vehicleid, sellerid, clientid"
				+ "	FROM public.rental"
				+ " WHERE public.rental.startdate BETWEEN ? AND ?");
		ps.setDate(1, startDate);
		ps.setDate(2, endDate);
		
		ResultSet result = ps.executeQuery();
		ArrayList<RentalBean> rentals = new ArrayList<RentalBean>();
		
		while(result.next()) {
			rentals.add(new RentalBean(result.getInt(1), result.getDate(2), result.getDate(3), result.getInt(4), result.getInt(5), result.getInt(6), result.getInt(7)));
		}
		
		return rentals;
	}

	public static ArrayList<RentalBean> searchRentalByDatePeriodAndSeller(Date startDate, Date endDate, SellerBean seller, Connection con) throws SQLException {
		PreparedStatement ps;
		ps = con.prepareStatement("SELECT rentalid, startdate, enddate, renovationid, vehicleid, sellerid, clientid"
				+ "	FROM public.rental"
				+ " WHERE public.rental.sellerid = ? AND public.rental.startdate BETWEEN ? AND ?");
		ps.setInt(1, seller.getSellerId());
		ps.setDate(2, startDate);
		ps.setDate(3, endDate);
		
		ResultSet result = ps.executeQuery();
		ArrayList<RentalBean> rentals = new ArrayList<RentalBean>();
		
		while(result.next()) {
			rentals.add(new RentalBean(result.getInt(1), result.getDate(2), result.getDate(3), result.getInt(4), result.getInt(5), result.getInt(6), result.getInt(7)));
		}
		
		return rentals;
	}
	
	public static ArrayList<RentalBean> searchRentalByVehicleAndPeriod(VehicleBean vehicle, Date startDate, Date endDate, Connection con) throws SQLException {
		PreparedStatement ps;
		ps = con.prepareStatement("SELECT rentalid, startdate, enddate, renovationid, vehicleid, sellerid, clientid"
				+ "	FROM public.rental"
				+ " WHERE public.rental.vehicleid=? AND public.rental.startdate BETWEEN ? AND ?;");
		ps.setInt(1, vehicle.getVehicleId());
		ps.setDate(2, startDate);
		ps.setDate(3, endDate);
		
		ResultSet result = ps.executeQuery();
		ArrayList<RentalBean> rentals = new ArrayList<RentalBean>();
		
		while(result.next()) {
			rentals.add(new RentalBean(result.getInt(1), result.getDate(2), result.getDate(3), result.getInt(4), result.getInt(5), result.getInt(6), result.getInt(7)));
		}
		
		return rentals;
	}
	
	public static int countAssociatedRentals(RentalBean rental, Connection con) throws SQLException {
		PreparedStatement ps;
	    String query = "WITH RECURSIVE Alocacoes AS ("
	                 + "    SELECT rentalid, renovationid "
	                 + "    FROM public.rental "
	                 + "    WHERE rentalid = ? "
	                 + "    UNION ALL "
	                 + "    SELECT r.rentalid, r.renovationid "
	                 + "    FROM public.rental r "
	                 + "    INNER JOIN Alocacoes a ON r.renovationid = a.rentalid"
	                 + ") "
	                 + "SELECT COUNT(*) AS total_alocacoes FROM Alocacoes;";
	    
	    ps = con.prepareStatement(query); 
        ps.setInt(1, rental.getRentalId());
        ResultSet result = ps.executeQuery();
        
        if (result.next()) {
            return result.getInt("total_alocacoes");
        }

	    return 0;
	}

}
