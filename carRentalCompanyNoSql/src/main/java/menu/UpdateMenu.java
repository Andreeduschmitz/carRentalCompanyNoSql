package menu;

import java.sql.Connection;
import java.sql.SQLException;

import controller.AddressController;
import controller.ClientController;
import controller.SellerController;
import controller.VehicleController;
import utils.Utils;

public class UpdateMenu {

	public static void updateMenu(Connection con) throws SQLException {
		int opcao;
		do {
			System.out.println("Qual alteração você deseja realizar?");
			System.out.println("1 - Cliente");
			System.out.println("2 - Vendedor");
			System.out.println("3 - Endereço");
			System.out.println("4 - Veículo");
			System.out.println("5 - Retornar ao menu principal");

			opcao = Utils.indexSelector(1, 5);

			switch (opcao) {
				case 1:
					ClientController.updateClient(con);
					return;
				case 2:
					SellerController.updateSeller(con);
					return;
				case 3:
					AddressController.updateAddress(con);
					return;
				case 4:
					VehicleController.updateVehicle(con);
					return;
				case 5:
					return;
				default:
					System.out.println("Opção inválida");
					break;
			}

		} while (opcao != 5);
	}
}