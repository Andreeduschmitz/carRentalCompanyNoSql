package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.ClientBean;

public class ClientModel {

	public static void create(ClientBean client, Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("INSERT INTO public.client("
				+ "	clientname, clientcpf, clientphone, clientemail, isActive)"
				+ "	VALUES (?, ?, ?, ?, ?);");
		ps.setString(1, client.getClientName());
		ps.setLong(2, client.getClientCpf());
		ps.setString(3, client.getClientPhone());
		ps.setString(4, client.getClientEmail());
		ps.setBoolean(5, true);
		ps.execute();
		ps.close();
	}
	
	public static void update(ClientBean client, Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("UPDATE public.client"
				+ "	SET clientname=?, clientcpf=?, clientphone=?, clientemail=?"
				+ "	WHERE clientid=?;");
		ps.setString(1, client.getClientName());
		ps.setLong(2, client.getClientCpf());
		ps.setString(3, client.getClientPhone());
		ps.setString(4, client.getClientEmail());
		ps.setInt(5, client.getClientId());
		ps.execute();
		ps.close();
	}
	
	public static void delete(ClientBean client, Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("UPDATE public.client"
				+ " SET isActive = ?"
				+ "	WHERE clientid=?;");
		ps.setBoolean(1, false);
		ps.setInt(2, client.getClientId());
		ps.execute();
		ps.close();
	}
	
    public static ArrayList<ClientBean> listAll(Connection connectino) throws SQLException {
        Statement st = (Statement) connectino.createStatement();;
        ArrayList<ClientBean> list = new ArrayList<ClientBean>();
        String sql = "SELECT clientid, clientname, clientcpf, clientphone, clientemail"
        		+ " FROM public.client WHERE public.client.isactive = true;";
        ResultSet result = st.executeQuery(sql);

        while(result.next()) {
        	list.add(new ClientBean(result.getInt(1), result.getString(2), result.getLong(3), result.getString(4), result.getString(5)));
        }

        return list;
    }
	
	public static ArrayList<ClientBean> search(long cpf, String name, Connection connection) throws SQLException {
		PreparedStatement ps;
		ArrayList<ClientBean> list = new ArrayList<ClientBean>();
		String selectClause = "SELECT clientid, clientname, clientcpf, clientphone, clientemail FROM public.client";
		String whereClause = " WHERE ";

		if(name != null) {
	        whereClause += "public.client.clientname ILIKE ? AND public.client.isactive = true;";
	        ps = connection.prepareStatement(selectClause + whereClause);
	        ps.setString(1, "%" + name + "%");
		} else {
			whereClause += "public.client.clientcpf = ? AND public.client.isactive = true;";
			ps = connection.prepareStatement(selectClause + whereClause);
			ps.setLong(1, cpf);
		}
		
        ResultSet result = ps.executeQuery();
        
        while(result.next()) {
            list.add(new ClientBean(result.getInt(1), result.getString(2), result.getLong(3), result.getString(4), result.getString(5)));
        }
		return list;
	}
}