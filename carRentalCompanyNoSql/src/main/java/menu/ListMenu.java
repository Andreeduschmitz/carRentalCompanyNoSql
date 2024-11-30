package menu;

import com.mongodb.client.MongoDatabase;

import controller.AddressController;
import controller.ClientController;
import controller.RentalController;
import controller.SellerController;
import controller.VehicleController;
import utils.Utils;

public class ListMenu {

	public static void listMenu(MongoDatabase con) throws Exception {
    	int opcao;

    	do {
	        System.out.println("Qual tipo de listagem você deseja realizar?");
	        System.out.println("1 - Clientes");
	        System.out.println("2 - Vendedor");
	        System.out.println("3 - Veículo");
	        System.out.println("4 - Endereço");
	        System.out.println("5 - Locação");
	        System.out.println("6 - Retornar ao menu principal");
	
	        opcao = Utils.indexSelector(1, 6);
	        
			switch (opcao) {
				case 1:
					ClientController.listClientsBySeach(con);
					return;
				case 2:
					SellerController.listSellersByName(con);
					return;
				case 3:
					VehicleController.listVehiclesBySearch(con);
					return;
				case 4:
					AddressController.listAddressesByClient(con);
					return;
				case 5:
					RentalController.listRentalsBySearch(con);
					return;
				case 6:
					return;
				default:
					System.out.println("Opção inválida");
					break;
			}
    	} while(opcao != 6);
    }
}
