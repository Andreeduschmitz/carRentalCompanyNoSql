package controller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoDatabase;

import bean.ClientBean;
import bean.RentalBean;
import bean.SellerBean;
import bean.VehicleBean;
import model.RentalModel;
import utils.Utils;

public class RentalController {

    public static void createRental(MongoDatabase con) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados abaixo para cadastrar uma nova locação:");
        
        System.out.println("Data de início (formato dd/MM/yyyy):");
        Date startDate = Utils.safeDateInput();

        System.out.println("Data de término (formato dd/MM/yyyy): ");
        Date endDate = Utils.safeDateInput();
        
        System.out.println("Selecione o vendedor responsável:");
        SellerBean seller = Utils.selectSeller(con);
        
        if(seller == null) {
        	return;
        }
        
        System.out.println("Selecione o veículo que será alugado");
        VehicleBean vehicle = Utils.selectVehicle(con);
        
        if(vehicle == null) {
        	return;
        }
        
        System.out.println("Selecione o cliente que irá alugar o veículo");
        ClientBean client = Utils.selectClient(con);
        
        if(client == null) {
        	return;
        }

        RentalBean rental = new RentalBean(startDate, endDate, null, vehicle.getId().toHexString(), seller.getId().toHexString(), client.getId().toHexString());
        RentalModel.createRental(rental, con);
        System.out.println("Locação criada com sucesso!");
    }
    
    public static void createRenovation(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Insira os dados abaixo para renovar sua locação");
    	
    	System.out.println("Digite o cpf do titular da locação a ser renovada:");
    	String cpf = input.next();
    	ClientBean client = Utils.selectClientBySearch(con, cpf, null);
    	
    	if(client == null) {
    		return;
    	}
    	
    	System.out.println("Selecione a locação a ser renovada");
    	RentalBean rental = Utils.selectRentalByClient(con, client);
    	
    	if(rental == null) {
    		return;
    	}
    	
    	System.out.println("Selecione o vendedor responsável pela renovação");
    	SellerBean seller = Utils.selectSeller(con);
    	
    	if(seller == null) {
    		return;
    	}
    	
        System.out.println("Data de início (formato dd/MM/yyyy):");
        Date startDate = Utils.safeDateInput();

        System.out.println("Data de término (formato dd/MM/yyyy): ");
        Date endDate = Utils.safeDateInput();
        
        RentalBean renovatedRental = new RentalBean(startDate, endDate, rental.getId().toHexString(), rental.getVehicleId(), seller.getId().toHexString(), client.getId().toHexString());
        RentalModel.createRenovation(renovatedRental, con);
        System.out.println("Locação renovada com sucesso!");
    }
    
	public static void listRentalsBySearch(MongoDatabase con) throws Exception {
		Scanner input = new Scanner(System.in);
		List<RentalBean> rentals = null;

		System.out.println("Digite o índice da informação a qual deseja utilizar na pesquisa:");
		System.out.println("1 - veículo\n2 - cliente\n3 - data da locação");

		int index = Utils.indexSelector(1, 3);

		switch (index) {
			case 1:
				System.out.println("Selecione o veículo que deseja buscar o aluguel:");
				VehicleBean vehicle = Utils.selectVehicle(con);
				
				if(vehicle == null) {
					return;
				}
				
				rentals = RentalModel.searchRentalByVehicle(vehicle, con);
				break;
			case 2:
		        System.out.println("Selecione o cliente que deseja buscar o aluguel");
		        ClientBean client = Utils.selectClient(con);
		        
		        if(client == null) {
		        	return;
		        }
		        
		        rentals = RentalModel.searchRentalByClient(client, con);
				break;
			case 3:
				System.out.println("Digite a data inicial da busca (formato dd/MM/yyyy):");
		        Date startDate = Utils.safeDateInput();
		        
		        System.out.println("Digite a data final da busca (formato dd/MM/yyyy):");
		        Date endDate = Utils.safeDateInput();
		        
		        rentals = RentalModel.searchRentalByDatePeriod(startDate, endDate, con);
				break;
		}
		
		
		if(rentals == null || rentals.isEmpty()) {
			System.out.println("Nenhum aluguel encontrado");
			return;
		}
		
		for(RentalBean rental : rentals) {
			System.out.println(rental.toString());
		}
	}
	
	public static void countAssociatedRentals(MongoDatabase con) throws Exception {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Para realizar essa operação, é necessário buscar uma alocação");
		System.out.println("Você deseja realizar a busca dessa alocação de qual forma?");
		System.out.println("1 - por cliente\n2 - por período\n3 - por veículo");
		
		int index = Utils.indexSelector(1, 3);
		
		List<RentalBean> rentals = null;
		
		switch(index) {
			case 1:
				ClientBean client = Utils.selectClient(con);
				
				if(client == null) {
					return;
				}
				
				rentals = RentalModel.searchRentalByClient(client, con);

				break;
			case 2:
		        System.out.println("Data de início (formato dd/MM/yyyy):");
		        Date startDate = Utils.safeDateInput();

		        System.out.println("Data de término (formato dd/MM/yyyy): ");
		        Date endDate = Utils.safeDateInput();

		        rentals = RentalModel.searchRentalByDatePeriod(startDate, endDate, con);
		        
		        break;
			case 3:
				VehicleBean vehicle = Utils.selectVehicle(con);
				
				rentals = RentalModel.searchRentalByVehicle(vehicle, con);
				
				break;
		}
		
		if(rentals == null || rentals.isEmpty()) {
			System.out.println("Não foi encontrado nenhuma alocação");
			return;
		}
		
		System.out.println("Selecione o número da alocação desejada");
		int number = 1;
		for(RentalBean rental : rentals) {
			System.out.println(number + " - " + rental.toString());
			number++;
		}
		
		int rentalIndex = Utils.indexSelector(1, rentals.size() + 1);
		
		RentalBean rental = rentals.get(rentalIndex - 1);
		long associatedRentalsAmount = RentalModel.countAssociatedRentals(rental, con);
		
		System.out.println("A quantidade de alocações/renovações associadas a essa alocação é de: " + (associatedRentalsAmount - 1));
	}
}
