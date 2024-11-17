package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import bean.ClientBean;
import model.ClientModel;
import utils.Utils;

public class ClientController {
	
    public static void createClient(Connection con) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os dados abaixo para cadastrar um novo cliente:");
        
        System.out.println("Nome completo: ");
        String clientName = input.nextLine();

        System.out.println("CPF: ");
        long clientCpf = input.nextLong();

        System.out.println("Telefone para contato: ");
        String clientPhone = input.next();

        System.out.println("E-mail: ");
        String clientEmail = input.next();

        ClientBean client = new ClientBean(clientName, clientCpf, clientPhone, clientEmail);
        ClientModel.create(client, con);

        System.out.println("Cliente criado com sucesso!");
        
        client = ClientModel.search(clientCpf, null, con).get(0);
        
        System.out.println("Deseja inserir um endereço para esse cliente? S/N");
        String option = input.next();
        
        if(option.equals("S") || option.equals("s")) {
        	AddressController.createAddress(con, client);
        }
    }

    public static void updateClient(Connection con) throws SQLException {
    	ClientBean client = Utils.selectClient(con);
    	
    	if(client == null) {
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
					client.setClientName(name);
					break;
	
				case 2:
					System.out.println("Digite o telefone atualizado: ");
					String phoneNumber = input.next();
					client.setClientPhone(phoneNumber);
					break;
	
				case 3:
					System.out.println("Digite o e-mail atualizado: ");
					String email = input.next();
					client.setClientEmail(email);
					break;
				case 4:
					break;
	
				default:
					System.out.println("Opção inválida!");
					return;
			}

		} while (option < 1 || option > 4);
        
        ClientModel.update(client, con);
        System.out.println("Informações atualizadas com sucesso!");
    }
    
    public static void deleteClient(Connection con) throws SQLException {
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Digite o cpf do cliente que deseja excluir");
    	long cpf = input.nextLong();
    	
    	ClientBean client = Utils.selectClientBySearch(con, cpf, null);
    	
    	if(client == null) {
    		return;
    	}
    	
    	System.out.println("Tem certeza que deseja excluir esse cliente? S/N");
    	String option = input.next();
    	
    	if(option.equals("S") || option.equals("s")) {
    		ClientModel.delete(client, con);
    		
    		System.out.println("Cliente excluído com sucesso!");
    	} else {
    		System.out.println("Operação cancelada");
    	}
    	
    }
    
    public static void listAllClients(Connection con) throws SQLException {
    	ArrayList<ClientBean> clients = ClientModel.listAll(con);
    	
    	if(clients == null || clients.isEmpty()) {
    		System.out.println("Não há nenhum cliente cadastrado");
    		return;
    	}

    	for(ClientBean client : clients) {
    		System.out.println(client.toString());
    	}
    }
    
    public static void listClientsBySeach(Connection con) throws SQLException {
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Qual a característica do cliente você deseja utilizar na busca?\n1 - cpf\n2 - nome");
		
    	int index = Utils.indexSelector(1, 2);
		
		long cpf = 0;
		String name = null;
		
		if(index == 1) {
			System.out.println("Digite o cpf que deseja pesquisar:");
			cpf = input.nextLong();
		} else {
			System.out.println("Digite o nome que deseja pesquisar");
			name = input.next();
		}
		
		ClientController.listClientsBySeach(con, cpf, name);
    }
    
    public static void listClientsBySeach(Connection con, long cpf, String name) throws SQLException {
    	ArrayList<ClientBean> clients = ClientModel.search(cpf, name, con);

    	if(clients == null || clients.isEmpty()) {
    		System.out.println("Não há nenhum cliente cadastrado");
    		return;
    	}

    	for(ClientBean client : clients) {
    		System.out.println(client.toString());
    	}
    }
}
