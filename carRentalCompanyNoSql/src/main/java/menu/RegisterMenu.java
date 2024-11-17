package menu;

import java.sql.Connection;
import java.sql.SQLException;

import controller.AddressController;
import controller.ClientController;
import controller.RentalController;
import controller.SellerController;
import controller.VehicleController;
import utils.Utils;

public class RegisterMenu {

	public static void registerMenu(Connection con) throws SQLException {
		int opcao;

		do {
			System.out.println("Qual tipo de cadastro você deseja realizar?");
			System.out.println("1 - Cliente");
			System.out.println("2 - Vendedor");
			System.out.println("3 - Veículo");
			System.out.println("4 - Locação");
			System.out.println("5 - Renovação");
			System.out.println("6 - Endereço");
			System.out.println("7 - Retornar ao menu principal");

			opcao = Utils.indexSelector(1, 7);

			switch (opcao) {
				case 1:
					ClientController.createClient(con);
					return;
				case 2:
					SellerController.createSeller(con);
					return;
				case 3:
					VehicleController.createVehicle(con);
					return;
				case 4:
					RentalController.createRental(con);
					return;
				case 5:
					RentalController.createRenovation(con);
					return;
				case 6:
					AddressController.createAddress(con);
					return;
				case 7:
					return;
				default:
					System.out.println("Opção inválida");
					break;
			}
		} while (opcao != 7);
	}
}