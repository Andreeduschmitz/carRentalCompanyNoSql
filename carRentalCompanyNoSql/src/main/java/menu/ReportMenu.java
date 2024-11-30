package menu;

import com.mongodb.client.MongoDatabase;

import controller.RentalController;
import controller.SellerController;
import controller.VehicleController;
import utils.Utils;

public class ReportMenu {

    public static void reportMenu(MongoDatabase con) throws Exception {
    	int opcao;
    	
    	do {
	        System.out.println("Qual relatório você deseja gerar?");
	        System.out.println("1 - Listar o valor total gerado por um veículo em um período de tempo");
	        System.out.println("2 - Listar todas as locações de um vendedor em um período");
	        System.out.println("3 - Listar quantidade de locaçoções/renovações de uma locação");
	        System.out.println("4 - Retornar ao menu principal");

        	opcao = Utils.indexSelector(1, 4);
			switch (opcao) {
				case 1:
					VehicleController.vehicleTotalBillingInPeriod(con);
					return;
				case 2:
					SellerController.listRentalsBySellerInPeriod(con);
					return;
				case 3:
					RentalController.countAssociatedRentals(con);
					return;
				case 4:
					return;
				default:
					System.out.println("Opção inválida");
					break;
			}

    	} while(opcao != 4);
    }
}
