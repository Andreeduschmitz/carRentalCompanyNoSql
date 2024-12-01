import connection.Connection;
import menu.MainMenu;

public class Main {
	public static void main(String[] args) throws Exception {
        MainMenu.mainMenu(Connection.getDatabase());
        Connection.closeConnection();
	}
}