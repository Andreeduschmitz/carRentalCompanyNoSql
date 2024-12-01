package controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoDatabase;

import bean.RentalBean;
import bean.VehicleBean;
import enums.VehicleCategory;
import model.RentalModel;
import model.VehicleModel;
import utils.Utils;

public class VehicleController {
    
	public static void createVehicle(MongoDatabase con) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados abaixo para cadastrar um novo veículo:");
        
        System.out.print("Placa do veículo: ");
        String vehiclePlate = input.next();

        System.out.print("Modelo do veículo: ");
        String vehicleModel = input.next();

        System.out.print("Ano de lançamento: ");
        int vehicleLaunchYear = input.nextInt();

        System.out.println("Categoria do veículo: ");
        VehicleCategory vehicleCategory = Utils.vehicleCategorySelector();

        System.out.print("Marca do veículo: ");
        String vehicleBrand = input.next();

        System.out.print("Valor da diária: ");
        double dailyValue = Utils.safeInputDouble();

        VehicleBean vehicle = new VehicleBean(vehiclePlate, vehicleModel, vehicleLaunchYear, vehicleBrand,vehicleCategory, dailyValue);
        VehicleModel.create(vehicle, con);
        System.out.println("Veículo cadastrado com sucesso!");
    }
	

    public static void updateVehicle(MongoDatabase con) throws Exception {
    	VehicleBean vehicle = Utils.selectVehicle(con);
    	
    	if(vehicle == null) {
    		return;
    	}
    	
        Scanner input = new Scanner(System.in);
        System.out.println("O que você deseja atualizar?\n1 - Placa\n2 - Valor da diária\n3 - Cancelar");

        int option;
        
        do {
        	option = Utils.indexSelector(1, 3);
        	
            switch (option) {
	            case 1:
	                System.out.println("Digite a nova placa: ");
	                String plate = input.next();
	                vehicle.setVehiclePlate(plate);
	                break;
	
	            case 2:
	                System.out.println("Digite o novo valor da diária: ");
	                double value = input.nextDouble();
	                vehicle.setDailyValue(value);
	                break;
	                
	            case 3:
	            	break;
            }
        	
        } while (option < 1 || option > 3);
        
        VehicleModel.update(vehicle, con);
        System.out.println("Informações atualizadas com sucesso!");
    }
    
    public static void deleteVehicle(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	
    	VehicleBean vehicle = Utils.selectVehicle(con);
    	
    	if(vehicle == null) {
    		return;
    	}
    	
    	if(Utils.isVehicleInUse(con, vehicle)) {
    		System.out.println("Este veículo está atualmente em uso e não pode ser excluído");
    		return;
    	}
    	
    	System.out.println("Tem certeza que deseja excluir esse veículo? S/N");
    	
    	String option = input.next();
    	
    	if(option.equals("S") || option.equals("s")) {
    		VehicleModel.delete(vehicle, con);
    		System.out.println("Veículo excluído com sucesso");
    	} else {
    		System.out.println("Operação cancelada");
    	}
    }
    
    public static void listAll(MongoDatabase con) throws Exception {
    	ArrayList<VehicleBean> vehicles = VehicleModel.listAll(con);
    	
    	if(vehicles == null || vehicles.isEmpty()) {
    		System.out.println("Não há nenhum veículo cadastrado");
    		return;
    	}
    	
    	for(VehicleBean vehicle : vehicles) {
    		System.out.println(vehicles.toString());
    	}
    }
    
    public static void listVehiclesBySearch(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	VehicleBean vehicleSearch = new VehicleBean();
    	
    	System.out.println("Digite o índice da característica do veículo a qual deseja utilizar na pesquisa:");
    	System.out.println("1 - Placa\n2 - Modelo\n3 - Categoria\n4 - Valor máximo da diária\n5 - Marca");
    	
        int index = Utils.indexSelector(1, 5);
        
        switch(index) {
	        case 1:
	            System.out.println("Digite a placa do veículo:");
	            vehicleSearch.setVehiclePlate(input.next());
	            break;
	        case 2:
	            System.out.println("Digite o modelo do veículo:");
	            vehicleSearch.setVehicleModel(input.next());
	            break;
	        case 3:
	            System.out.println("Digite a categoria do veículo:");
	            vehicleSearch.setVehicleCategory(Utils.vehicleCategorySelector());
	            break;
	        case 4:
	            System.out.println("Digite o valor máximo desejado:");
	            vehicleSearch.setDailyValue(input.nextDouble());
	            break;
	        case 5:
	            System.out.println("Digite a marca do veículo:");
	            vehicleSearch.setVehicleBrand(input.next());
	            break;
        }
    	
    	ArrayList<VehicleBean> vehicles = VehicleModel.listBySearch(vehicleSearch, con);
    	
    	if(vehicles == null ||  vehicles.isEmpty()) {
    		System.out.println("Não há nenhum veículo com essa característica cadastrado");
    		return;
    	}
    	
    	for(VehicleBean vehicle : vehicles) {
    		System.out.println(vehicle.toString());
    	}
    }
    
    public static void vehicleTotalBillingInPeriod(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	VehicleBean vehicle = Utils.selectVehicle(con);
    	
    	if(vehicle == null) {
    		return;
    	}
    	
		System.out.println("Digite a data inicial do período (formato dd/MM/yyyy):");
        Date startDate = Utils.safeDateInput();
        
        System.out.println("Digite a data final do período (formato dd/MM/yyyy):");
        Date endDate = Utils.safeDateInput();
        
        List<RentalBean> rentals = RentalModel.searchRentalByVehicleAndPeriod(vehicle, startDate, endDate, con);
        
        if(rentals == null | rentals.isEmpty()) {
        	System.out.println("Não há nenhuma alocação para este veículo nesse período");
        	return;
        }
        
        long daysAmount = 0l;
        
        for(RentalBean rental : rentals) {
        	daysAmount += Utils.calculateDaysBetweenDates(rental.getStartDate(), rental.getEndDate());
        }
        
        Double billing = daysAmount * vehicle.getDailyValue();
        
        System.out.println("O valor total de faturamento do veículo no período foi de R$" + billing);
    }
}
