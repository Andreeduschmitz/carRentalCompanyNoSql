package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bean.RentalBean;
import bean.SellerBean;
import model.RentalModel;
import model.SellerModel;
import utils.Utils;

public class SellerController {
    
	public static void createSeller(Connection con) throws SQLException {
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
	
    public static void updateSeller(Connection con) throws SQLException {
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
					input.nextLine();
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
    
    public static void deleteSeller(Connection con) throws SQLException {
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
    
    public static void listAllSellers(Connection con) throws SQLException {
    	ArrayList<SellerBean> sellers = SellerModel.listAll(con);
    	
    	if(sellers == null || sellers.isEmpty()) {
    		System.out.println("Não há nenhum vendedor cadastrado");
    	}
    	
    	for(SellerBean seller : sellers) {
    		System.out.println(seller.toString());
    	}
    }
    
    public static void listSellersByName(Connection con) throws SQLException {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Digite o nome do vendedor que deseja buscar");
    	String name = input.next();
    	SellerController.listSellersByName(con, name);
    }
    
    public static void listSellersByName(Connection con, String name) throws SQLException {
    	ArrayList<SellerBean> sellers = SellerModel.searchByName(con, name);
    	
    	if(sellers == null || sellers.isEmpty()) {
    		System.out.println("Não há nenhum vendedor cadastrado");
    	}
    	
    	for(SellerBean seller : sellers) {
    		System.out.println(seller.toString());
    	}
    }
    
    public static void listRentalsBySellerInPeriod(Connection con) throws SQLException {
    	Scanner input = new Scanner(System.in);
    	SellerBean seller = Utils.selectSeller(con);
    	
    	if(seller == null) {
    		return;
    	}
    	
		System.out.println("Digite a data inicial do período (formato dd-MM-yyyy):");
        Date startDate = Utils.safeDateInput();
        
        System.out.println("Digite a data final do período (formato dd-MM-yyyy):");
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