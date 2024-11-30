package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoDatabase;

import bean.AddressBean;
import bean.ClientBean;
import model.AddressModel;
import utils.Utils;

public class AddressController {
	
	public static void createAddress(MongoDatabase connection) throws Exception {
		ClientBean client = Utils.selectClient(connection);
		AddressController.createAddress(connection, client);
	}
	
    public static void createAddress(MongoDatabase connection, ClientBean client) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados abaixo para cadastrar um novo endereço:");
        
        System.out.println("CEP: ");
        int addressCep = input.nextInt();
        input.nextLine();

        System.out.println("Rua: ");
        String addressStreet = input.nextLine();

        System.out.println("Bairro: ");
        String addressNeighborhood = input.nextLine();

        System.out.println("Número: ");
        int addressNumber = input.nextInt();

        System.out.println("Deseja informar complemento? S/N");
        String option = input.next();

        String addressComplement = null;
        if (option.equals("S") || option.equals("s")) {
            System.out.println("Informe o complemento: ");
            addressComplement = input.next();
        }

        AddressBean address = new AddressBean(addressCep, addressStreet, addressNeighborhood, addressNumber, addressComplement, client.getClientId());
        AddressModel.create(address, connection);
        System.out.println("Endereço criado com sucesso!");
    }

    public static void updateAddress(MongoDatabase con) throws Exception {
        Scanner input = new Scanner(System.in);
    	ClientBean client = Utils.selectClient(con);
    	
    	if(client == null) {
    		return;
    	}
    	
    	List<AddressBean> addresses = AddressModel.findAddressByClient(client, con);
    	
    	if(addresses == null || addresses.isEmpty()) {
    		System.out.println("Esse cliente não possui nenhum endereço registrado");
    		return;
    	}
    	
    	System.out.println("Selecione do endereço que deseja atualizar");
    	
    	for(AddressBean address : addresses) {
    		System.out.println(address.toString());
    	}
    	
		int index = Utils.indexSelector(0, addresses.size());
		
		AddressBean address = addresses.get(index);
    	
        System.out.println("O que você deseja atualizar?\n1 - CEP\n2 - Rua\n3 - Bairro\n4 - Número\n5 - Complemento\n6 - Cancelar");

        int option;
        
        do {
        	option = input.nextInt();
        	
            switch (option) {
	            case 1:
	                System.out.print("Digite o novo CEP: ");
	                int cep = input.nextInt();
	                address.setAddressCep(cep);
	                break;
	
	            case 2:
	                System.out.print("Digite o nome da rua atualizado: ");
	                String streetName = input.next();
	                address.setAddressStreet(streetName);
	                break;
	
	            case 3:
	                System.out.print("Digite o nome do bairro atualizado: ");
	                String neighborhoodName = input.next();
	                address.setAddressNeighborhood(neighborhoodName);
	                break;
	
	            case 4:
	                System.out.print("Digite o número da residência atualizado: ");
	                int number = input.nextInt();
	                address.setAddressNumber(number);
	                break;
	
	            case 5:
	                System.out.print("Digite o complemento atualizado: ");
	                String complement = input.next();
	                address.setAddressComplement(complement);
	                break;
	                
	            case 6:
	            	break;
	            
	            default:
	                System.out.println("Opção inválida!");
	                return;
            }
        	
		} while (option < 1 || option > 6);
        
        AddressModel.update(address, con);
        System.out.println("Informações atualizadas com sucesso!");
    }
    
    public static void deleteAddress(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Digite o cpf do cliente que deseja listar os endereços");
    	String cpf = input.next();
    	
    	ClientBean client = Utils.selectClientBySearch(con, cpf, null);
    	
    	if(client == null) {
    		return;
    	}
    	
    	ArrayList<AddressBean> addresses = AddressModel.findAddressByClient(client, con);
    	
    	if(addresses == null || addresses.isEmpty()) {
    		System.out.println("Nenhum endereço encontrado para este cliente");
    		return;
    	}
    	
    	System.out.println("Selecione o endereço que deseja excluir");

    	for(AddressBean address : addresses) {
    		System.out.println(address.toString());
    	}
    	
        int index = Utils.indexSelector(0, addresses.size());
    	
        AddressModel.delete(addresses.get(index), con);
        System.out.println("Endereço excluído com sucesso!");
    }
    
    public static void listAddressesByClient(MongoDatabase con) throws Exception {
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Digite o cpf do cliente que deseja listar os endereços");
    	String cpf = input.next();
    	
    	ClientBean client = Utils.selectClientBySearch(con, cpf, null);
    	
    	if(client == null) {
    		return;
    	}
    	
    	ArrayList<AddressBean> addresses = AddressModel.findAddressByClient(client, con);
    	
    	if(addresses == null || addresses.isEmpty()) {
    		System.out.println("Este cliente não possui nenhum endereço cadastrado");
    		return;
    	}

    	for(AddressBean address : addresses) {
    		System.out.println(address.toString());
    	}
    }
}
