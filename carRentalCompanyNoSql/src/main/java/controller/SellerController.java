package controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoDatabase;

import bean.RentalBean;
import bean.SellerBean;
import model.RentalModel;
import model.SellerModel;
import utils.Utils;

public class SellerController {
    
	public static void createSeller(MongoDatabase con) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados abaixo para cadastrar um novo vendedor:");
        
        System.out.println("Nome completo: ");
        String sellerName = input.nextLine();

        System.out.println("Telefone para contato: ");
        String sellerPhone = input.next();

        System.out.println("E-mail: ");
        String sellerEmail = input.next();

        SellerBean seller = new SellerBean(sellerName, sellerPhone, sellerEmail);
        SellerModel.create(seller, con);
        System.out.println("Vendedor criado com sucesso!");
    }
	
    public static void updateSeller(MongoDatabase con) throws Exception {
    	SellerBean seller = Utils.selectSeller(con);
    	
    	if(seller == null) {
    		return;
    	}
    	
        Scanner input = new Scanner(System.in);
        System.out.println("O que você deseja atualizar?\n1 - Nome\n2 - Telefone\n3 - E-mail\n4 - Cancelar");
        
		int option;

		do {

			option = Utils.indexSelector(1, 4);

			switch (option) {
				case 1:
					System.out.println("Digite o nome completo atualizado: ");
					String name = input.nextLine();
					seller.setSellerName(name);
					break;
	
				case 2:
					System.out.println("Digite o telefone atualizado: ");
					String phoneNumber = input.next();
					seller.setSellerPhone(phoneNumber);
					;
					break;
	
				case 3:
					System.out.println("Digite o e-mail atualizado: ");
					String email = input.next();
					seller.setSellerEmail(email);
					break;
				case 4:
					break;
	
				default:
					System.out.println("Opção inválida!");
					return;
			}

		} while (option < 1 || option > 4);
        
        SellerModel.update(seller, con);
        System.out.println("Informações atualizadas com sucesso!");
    }
    
    public static void deleteSeller(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Digite o nome do vendedor que deseja excluir");
    	SellerBean seller = Utils.selectSeller(con);
    	
    	if(seller == null) {
    		return;
    	}
    	
    	System.out.println("Tem certeza que deseja excluir esse vendedor? S/N");
    	String option = input.next();
    	
    	if(option.equals("S") || option.equals("s")) {
    		SellerModel.delete(seller, con);
    		System.out.println("Vendedor excluído com sucesso!");
    	} else {
    		System.out.println("Operação cancelada");
    	}	
    	
    }
    
    public static void listAllSellers(MongoDatabase con) throws Exception {
    	ArrayList<SellerBean> sellers = SellerModel.listAll(con);
    	
    	if(sellers == null || sellers.isEmpty()) {
    		System.out.println("Não há nenhum vendedor cadastrado");
    	}
    	
    	for(SellerBean seller : sellers) {
    		System.out.println(seller.toString());
    	}
    }
    
    public static void listSellersByName(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Digite o nome do vendedor que deseja buscar");
    	String name = input.nextLine();
    	SellerController.listSellersByName(con, name);
    }
    
    public static void listSellersByName(MongoDatabase con, String name) throws Exception {
    	ArrayList<SellerBean> sellers = SellerModel.searchByName(name, con);
    	
    	if(sellers == null || sellers.isEmpty()) {
    		System.out.println("Não há nenhum vendedor cadastrado com esse nome");
    	}
    	
    	for(SellerBean seller : sellers) {
    		System.out.println(seller.toString());
    	}
    }
    
    public static void listRentalsBySellerInPeriod(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	SellerBean seller = Utils.selectSeller(con);
    	
    	if(seller == null) {
    		return;
    	}
    	
		System.out.println("Digite a data inicial do período (formato dd/MM/yyyy):");
        Date startDate = Utils.safeDateInput();
        
        System.out.println("Digite a data final do período (formato dd/MM/yyyy):");
        Date endDate = Utils.safeDateInput();
        
        List<RentalBean> rentals = RentalModel.searchRentalByDatePeriodAndSeller(startDate, endDate, seller, con);
        
        if(rentals == null || rentals.isEmpty()) {
        	System.out.println("Não há nenhuma locação para esse vendedor nesse período");
        	return;
        }
        
        for(RentalBean rental : rentals) {
        	System.out.println(rental.toString());
        }
    }
}