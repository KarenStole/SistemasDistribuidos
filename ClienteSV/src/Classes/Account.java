/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import clientesv.ClienteSV;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Karen
 */
public class Account {
    //Registro de usuario

    /**
     *
     */
    public static ClienteSV cliente;

/**
 * Clase encargada de registrar al usuario al sistema, ingresandolo en la base de datos, asi como
 * tambien generando el par de claves unicas para cada usuario.
 * @param UserID
 * @param username
 * @param password
 * @param dire
 * @return boolean
 * @throws Exception 
 */    
public static boolean register(String UserID, String username,String password, String dire) throws Exception
	{
            try{
		String hashPass=Account.hashPass(password);
		String sqlInsertUser = "INSERT INTO public.\"Users\"(userid, username, password, direccion, clavepublica, claveprivada, rol)values (?,?,?,?,?,?,?);";
            	Connection con = Account.getConnection();

            	PreparedStatement ps = con.prepareStatement(sqlInsertUser);
		//insertar tambien las claves publicas y privadas
                RSA.genKeyPair(512);
                String publica = RSA.getPublicKeyString();
                String privada = RSA.getPrivateKeyString();
                ps.setString(1,UserID);
		ps.setString(2,username);
		ps.setString(3,hashPass);
		ps.setString(4,dire);
		ps.setString(5,publica);
		ps.setString(6, privada);
                ps.setInt(7, 2);
		
            	ps.executeUpdate();

		Account.cliente = new ClienteSV(UserID,username,dire,publica, 2);
		

		con.close();
                return true;
            }
            catch(PSQLException ex) {
                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            
            }
		
	}
/**
 * Clase encargada de realizar la conexion a la base de datos.
 * @return Connection
 * @throws Exception 
 */
//Coneccion a la base de datos
private static Connection getConnection() throws Exception
	{
		Class.forName("org.postgresql.Driver");
		Connection connection =DriverManager.getConnection("jdbc:postgresql://localhost/Usuarios?user=postgres&password=people098");
		if(connection==null) throw new Exception("Error de  conexion");
		return connection;
	}

/**
 * Metodo encargado de generar el par de claves publicas y privadas para el algoritmo
 * de encriptacion asimentrica RSA
 * @param uid
 * @param password
 * @return KeyPair
 * @throws Exception 
 */
//Generar las claves
	static public KeyPair genPKI(String uid, String password) throws Exception
	{
		System.out.println("Creando Certificados ...");
		
		// generate a key pair
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048, new SecureRandom());
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                return keyPair;
		
	}

//Login de usuario
/**
 * Clase encargafa de autorizar al usuario al ingreso al sistema mediante la comprobacion
 * le las credenciales brindadas por este.
 * @param username
 * @param password
 * @return boolean
 * @throws Exception 
 */
public static boolean login(String username, String password) throws Exception
	{	

		String hashPass=Account.hashPass(password);
                String sqlLogin = "SELECT * from public.\"Users\" where userid=? AND Password=?";
                Connection con = Account.getConnection();
		
		PreparedStatement ps = con.prepareStatement(sqlLogin);
		ps.setString(1,username);
                ps.setString(2,hashPass);

		ResultSet rs = ps.executeQuery();
		
        	if(rs.next())
		{
			String uid=rs.getString(1);
			String userName= rs.getString(2);
			String dirección = rs.getString(4);
                        String clave = rs.getString(5);
			int rol = rs.getInt(7);
			Account.cliente = new ClienteSV(uid,userName,dirección,clave, rol);
			 con.close();
			 return true;
        	}
        	else return false;

	}
/**
 * Metodo que perminte hashear la password ingresada para ser guardada como hashe en la base de datos, con el
 * fin de tener mas seguridad. Se utiliza el algoritmo de one-way-hash SHA-1
 * @param pass
 * @return String
 * @throws Exception 
 */
    private static String hashPass( String pass) throws Exception
            {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                byte[] array = md.digest(pass.getBytes());
                StringBuffer sb = new StringBuffer();

                for (int i = 0; i < array.length; ++i) 
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));

                return sb.toString();

    }

    /**
     * Clase encargada de obtener la clave privada de un usuario en especifico,
     * siendo este ya registrado en el sistema.
     * @param userID
     * @return String
     * @throws Exception 
     */
    public static String obtererPrivKey(String userID) throws Exception{
            String sqlLogin = "SELECT ClavePrivada from public.\"Users\" where userid=?";
            Connection con = Account.getConnection();
            PreparedStatement ps = con.prepareStatement(sqlLogin);
            ps.setString(1, Account.cliente.getUserID());

            ResultSet rs = ps.executeQuery();
            rs.next();
            String clavePrivada = rs.getString(1);
            return clavePrivada;


    }

}
