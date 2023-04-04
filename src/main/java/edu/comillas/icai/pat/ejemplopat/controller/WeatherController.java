package edu.comillas.icai.pat.ejemplopat.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


//Con esto creamos algo que pueda manejar
//las peticiones a la base de datos
@RestController
public class WeatherController {

	private final static String DIR_LOCATION="src/main/resources/databases/listado-longitud-latitud-municipios-espana.csv";
	private final static String DIR_LOG="src/main/resources/databases/log.csv";
	private final static int POBL=2;
	private final static int LAT=3;
	private final static int LON=4;
	private int number_executions=0;


	
	private ArrayList<String> log = new ArrayList<String>();
	private ArrayList<String[]> csv_localidades = new ArrayList<String[]> ();

	public static final String SEPARATOR=";";	 

	//@Autowired
	//MyService myService;


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

		/*
		Asi guardo en binario
       	try
        {
            FileOutputStream fos = new FileOutputStream(DIR_LOG); //.ser
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (String s:log)
            {
            	//oos.writeObject(String.join(";",s));
				oos.writeObject(s);
				oos.write("\n");
            }
            oos.close();
            fos.close();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
		*/	
    }

	
	private void leerCSV() throws IOException
	{
		   BufferedReader br = null;
		   try {
			  br =new BufferedReader(new FileReader(DIR_LOCATION));
			  String line = br.readLine();
			  while (null!=line) {
				 String [] fields = line.split(SEPARATOR);
				 csv_localidades.add(fields);				 
				 line = br.readLine();
			  }
			  
		   } catch (Exception e) {
			System.out.println("Mala direccion");
		} 
	}



	private ArrayList<String[]> buscar_loc(String location)
	{
		if (csv_localidades.size()==0)//si esta vacio
		{
			try {
				//System.out.println("leyendo CSV");
				leerCSV();
			} catch (Exception e) {
				System.out.println("Check leerCSV");
			}
		}

		
		ArrayList<String[]> resul= new  ArrayList<String[]>();
		
		for (String[] sentence: csv_localidades) {
			if ( ((String) sentence[POBL]).length() < location.length() )
			{;}//La poblacion buscada tiene mas letras que la que se esta comparando, no es la correcta 
			else
			{
				if( ((String) sentence[POBL].substring(0,location.length()).toLowerCase()).equals(location.toLowerCase()) )
				{
					String[] pos_option= new String[3];
					pos_option[0]= (String)sentence[POBL];
					pos_option[1]= (String)sentence[LAT];
					pos_option[2]= (String)sentence[LON];
					resul.add(pos_option);

					/*
					String posible_loc= (String)sentence[POBL];
					posible_loc+=";"+(String) sentence[LAT];
					posible_loc+=";"+(String) sentence[LON];
					*/
				}
			}
		}

		return resul;
	}



	@GetMapping("/api/{pueblo}")
	public ResponseEntity<ArrayList<String[]>> buscarLocation (@PathVariable("pueblo") String pueblo){
		String location=pueblo;

		String log_message=location+";"+System.currentTimeMillis()+";";
		String abc="abcdefghijklmnñopqrstuvwxyz";
		for(int i=0; i<location.length();i++)
		{
			if ((abc.contains( location.substring(i,i+1).toLowerCase()) )==false)
			{
				location="";
				log_message+=HttpStatus.FORBIDDEN.toString();
				log.add(log_message);
				save_log(); 
				return new ResponseEntity<ArrayList<String[]>>(new ArrayList<String[]>(), HttpStatus.FORBIDDEN);
			}
		}
	
		ArrayList<String[]> resul=buscar_loc(location);
		if (number_executions==0) //aun no se ha cargado log, por defecto tiene 1 linea
		{
			importLog();			
		}
//number_executions++;
		if (resul.size()==0 ) //Si es 0 es que no ha encontrado pueblos
		{
			log_message+=HttpStatus.NO_CONTENT.toString();
			log.add(log_message);
			save_log(); 
		return new ResponseEntity<ArrayList<String[]>>(resul, HttpStatus.NO_CONTENT);
		}
		else {
			log_message+=HttpStatus.OK.toString();
			log.add(log_message);
			save_log();
		return new ResponseEntity<ArrayList<String[]>>(resul, HttpStatus.OK);
		}
	}


}
