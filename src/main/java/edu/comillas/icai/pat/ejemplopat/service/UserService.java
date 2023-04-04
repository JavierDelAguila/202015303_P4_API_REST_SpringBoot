package edu.comillas.icai.pat.ejemplopat.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.comillas.icai.pat.ejemplopat.User.User;

//Es un BEAN de tipo Service, da un servicio no es un componente cualquiera
@Service
public class UserService {
	private final static String DIR_LOG="src/main/resources/databases/log.csv";
    private final static String DIR_USERBASE="src/main/resources/databases/users.csv";
	private final static int USERNAME=0;
	//private final static int PASSWORD=1;
    //private final static int LOCATION=2;
    private final static String SEPARATOR=";";
	private int number_executions=0;


    private ArrayList<String> userlog = new ArrayList<String>();
	private ArrayList<String> log = new ArrayList<String>();


    
	private void importUsers()
	{
		try
		{
			FileReader fr = new FileReader(DIR_USERBASE); //Fichero que apunta a esta direccion
			BufferedReader br = new BufferedReader(fr); //Capaz de leer el fichero

			String row= null;
			while((row = br.readLine()) != null)
			{
//				String s[]= row.split(SEPARATOR);
                userlog.add(row);
			}
			br.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

		

	}



    public void save_Userlog()
    {
		try
		{
			FileWriter fw = new FileWriter(DIR_USERBASE);
            PrintWriter pw = new PrintWriter(fw);
            for(String s:userlog)
			{	
				pw.println(s);
			}
            pw.close();
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        }  	

    }

    public  boolean addUser(User user)
    {
        String log_message=user.toString()+SEPARATOR+System.currentTimeMillis()+SEPARATOR;
        if (number_executions==0) //aun no se ha cargado log, por defecto tiene 1 linea
		{
			importUsers();		
            importLog();			
		}
//number_executions++;
        for(String row : userlog)
        {
            String[] sep= row.split(SEPARATOR);
            if(sep[USERNAME].equals(user.getUsername()))
            {
                user=new User();
				log_message+=HttpStatus.IM_USED.toString();
				log.add(log_message);
				save_log();     
                return false;
            
            }
        }

        userlog.add(user.toString());
        log_message+=HttpStatus.CREATED.toString();
        log.add(log_message);
        save_log(); 
        save_Userlog();
        return true;
    }










    
	private void importLog()
	{
		try
		{
			FileReader fr = new FileReader(DIR_LOG); //Fichero que apunta a esta direccion
			BufferedReader br = new BufferedReader(fr); //Capaz de leer el fichero

			String row= null;
			while((row = br.readLine()) != null)
			{
//				String s[]= row.split(SEPARATOR);
				log.add(row);
			}
			br.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
	}


    public void save_log()
    {
		try
		{
			FileWriter fw = new FileWriter(DIR_LOG);
            PrintWriter pw = new PrintWriter(fw);
            for(String s:log)
			{	
				pw.println(s);
			}
            pw.close();
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        }  		
    }



    
}
