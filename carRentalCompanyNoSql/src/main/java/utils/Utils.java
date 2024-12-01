package utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoDatabase;

import bean.ClientBean;
import bean.RentalBean;
import bean.SellerBean;
import bean.VehicleBean;
import enums.VehicleCategory;
import model.ClientModel;
import model.RentalModel;
import model.SellerModel;
import model.VehicleModel;

public class Utils {

    public static Date addOneDayToDate(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return new Date(calendar.getTimeInMillis());
    }
    
    public static VehicleCategory vehicleCategorySelector() {
    	VehicleCategory[] categorys = VehicleCategory.values();
    	
    	for(int i = 0; i < categorys.length; i++) {
    		System.out.println(i + 1 + " - " + categorys[i].toString());
    	}
    	
    	Scanner input = new Scanner(System.in);
    	int option = input.nextInt();
    	
    	return VehicleCategory.fromOrdinal(option - 1);
    }
    
    public static SellerBean selectSeller(MongoDatabase con) throws Exception {
    	ArrayList<SellerBean> sellers = SellerModel.listAll(con);
    	
    	if(sellers == null || sellers.isEmpty()) {
    		System.out.println("Não há nenhum vendedor cadastrado");
    		return null;
    	}
    	
    	System.out.println("Selecione o número do vendedor");

    	int number = 1;
    	for(SellerBean seller : sellers) {
    		System.out.println(number + " - " + seller.toString());
    		number++;
    	}
    	
        Scanner input = new Scanner(System.in);
        int index = Utils.indexSelector(1, sellers.size() + 1);

        return sellers.get(index - 1);
    }
    
    public static VehicleBean selectVehicle(MongoDatabase con) throws Exception {
    	ArrayList<VehicleBean> vehicles = VehicleModel.listAll(con);
    	
    	if(vehicles == null || vehicles.isEmpty()) {
    		System.out.println("Não há nenhum veículo cadastrado");
    		return null;
    	}
    	
    	System.out.println("Selecione o número do veículo");

    	int number = 1;
    	for(VehicleBean vehicle : vehicles) {
    		System.out.println(number + " - " + vehicle.toString());
    		number++;
    	}
    	
        Scanner input = new Scanner(System.in);
        int index = Utils.indexSelector(1, vehicles.size() + 1);

        return vehicles.get(index - 1);
    }
    
    public static ClientBean selectClient(MongoDatabase con) throws Exception {
    	List<ClientBean> clients = ClientModel.listAll(con);
    	
    	if(clients == null || clients.isEmpty()) {
    		System.out.println("Não há nenhum cliente cadastrado");
    		return null;
    	}
    	
    	System.out.println("Selecione o número do cliente");
    	
    	int number = 1;
    	for(ClientBean client : clients) {
    		System.out.println(number + " - " + client.toString());
    		number++;
    	}
    	
        Scanner input = new Scanner(System.in);
        int index = Utils.indexSelector(1, clients.size() + 1);
        
        return clients.get(index - 1);
    }
    
    public static ClientBean selectClientBySearch(MongoDatabase con, String cpf, String name) throws Exception {
    	ArrayList<ClientBean> clients = ClientModel.search(cpf, name, con);
    	
    	if(clients == null || clients.isEmpty()) {
    		System.out.println("Nenhum cliente encontrado");
    		return null;
    	}
    	
    	System.out.println("Selecione o número do cliente");
    	
    	int number = 1;
    	for(ClientBean client : clients) {
    		System.out.println(number + " - " + client.toString());
    		number++;
    	}
    	
        Scanner input = new Scanner(System.in);
        int index = Utils.indexSelector(1, clients.size() + 1);
        
        return clients.get(index - 1);
    }

	public static RentalBean selectRentalByClient(MongoDatabase con, ClientBean client) throws Exception {
		ArrayList<RentalBean> rentals = RentalModel.searchRentalByClient(client, con);
		
		if(rentals == null || rentals.isEmpty()) {
			System.out.println("Esse cliente não possui nenhuma locação");
			return null;
		}
		
		System.out.println("Selecione o número da locação");
		
		int number = 1;
		for(RentalBean rental : rentals) {
			System.out.println(number + " - " + rental.toString());
			number++;
		}
		
        Scanner input = new Scanner(System.in);
        int index = Utils.indexSelector(1, rentals.size() + 1);

        return rentals.get(index - 1);
	}
	
	public static Boolean isVehicleInUse(MongoDatabase con, VehicleBean vehicle) throws Exception {
		List<RentalBean> vehicleActiveRental = RentalModel.searchRentalByVehicle(vehicle, con);
		
		if(vehicleActiveRental == null || !vehicleActiveRental.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public static int indexSelector(int startIndex, int endIndex) {
		Scanner input = new Scanner(System.in);
		int index = -1;

		while (true) {
			try {
				index = input.nextInt();
				if (index >= startIndex && index <= endIndex) {
					break;
				} else {
					System.out.println("Índice fora do intervalo. Tente novamente.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Digite um número inteiro.");
				input.next();
			}
		}
		
		return index;
	}
	
	public static long calculateDaysBetweenDates(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		start.setTime(startDate);
		end.setTime(endDate);
		
		long diffInMillis = end.getTimeInMillis() - start.getTimeInMillis();
		long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
		
		return diffInDays;
	}
	
    public static Date safeDateInput() {
        Scanner input = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        Date date = null;
        boolean valid = false;

        while (!valid) {
            String dateString = input.next();

            try {
                Date utilDate = dateFormat.parse(dateString);
                date = new Date(utilDate.getTime());
                valid = true;
            } catch (ParseException e) {
                System.out.println("Data inválida, tente novamente.");
            }
        }

        return date;
    }
    
    public static double safeInputDouble() {
    	Scanner input = new Scanner(System.in);
    	boolean valid = false;
    	Double utilDouble = 0d;
    	
        while (!valid) {
            String doubleString = input.next();
            doubleString = doubleString.replace(",", ".");

            try {
                utilDouble = Double.parseDouble(doubleString);
                valid = true;
            } catch (Exception e) {
                System.out.println("Valor inválido, tente novamente.");
            }
        }
        
        return utilDouble;
    }
}