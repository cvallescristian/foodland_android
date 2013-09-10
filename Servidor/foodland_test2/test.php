<?php

//pasar a valor absoluto cuando se entregan al servicio
$latitud =-'33.5353543';
$longitud = '177.2313123';

$username = "root";
$password = "88209716";
$hostname = "localhost"; 


//connection to the database
$db = new PDO('mysql:host=localhost;dbname=coord_database;charset=utf8', $username, $password);
echo "Connected to MySQL<br>";

//obtiene la menor diferencia (longitud_sector - longitud_gps) y retorna diferencia y id del sector
$stmt = $db->query("SELECT id_sector,ABS(longitud_inicial -".$longitud.") AS diff   
					FROM  `coordenadas` 
					ORDER BY  diff ASC 
					LIMIT 1");

//introduce resultado a variable
$row = $stmt->fetch(PDO::FETCH_ASSOC); 
$lon_id = $row{'id_sector'};
$lon_diff = $row{'diff'};

//obtiene la menor diferencia (latitud_sector - latitud_gps) y retorna diferencia y id del sector
$stmt = $db->query("SELECT id_sector,ABS(latitud_inicial -".$latitud.") AS diff   
					FROM  `coordenadas` 
					ORDER BY  diff ASC 
					LIMIT 1");

//introduce resultado a variable
$row = $stmt->fetch(PDO::FETCH_ASSOC); 
$lat_id = $row{'id_sector'};
$lat_diff = $row{'diff'};


if($lon_diff < $lat_diff)
	$query_id  = $lon_id;
else
	$query_id  = $lat_id;	

//selecciona lista de productos
$stmt = $db->query("SELECT *    
					FROM  `comida`
					WHERE id_sector = ".$query_id);

while($row = $stmt->fetch(PDO::FETCH_ASSOC)){

	echo $row{'ID'}." ".$row{'id_sector'}." ".$row{'nombre'}." ".$row{'descripcion'}."<br>"; 


}

					

//cierra coneccion
$db = null; 

?>