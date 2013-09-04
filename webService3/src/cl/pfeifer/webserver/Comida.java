package cl.pfeifer.webserver;


/*
 * Luego que se recibe el json del servidor cada objeto de la lista
 * se inicializa como objeto de la actividad
 * 
 * */
public class Comida {
	
	private String id;
	private String idSector;
	private String name;
	private String description;
	
	
	public Comida(String id, String idSector, String name, String description){
		this.id = id;
		this.idSector = idSector;
		this.name = name;
		this.description = description;
	}
	
	
	
	//GET
	public String getId(){ 							return id;}
	public String getIdSector(){						return idSector;}
	public String getName(){ 						return name;}
	public String getDescription(){ 				return description; }
	
	
	

}
