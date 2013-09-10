<?php
//paramatros son strings
function getproductbygps($latitud,$longitud)
{
	
	$username = "root";
	$password = "";
	$hostname = "localhost"; 


	//connection to the database
	$db = new PDO('mysql:host=localhost;dbname=foodland_beta;charset=utf8', $username, $password);
	
	
	
	//consulta por id del subsector, y la diferencia entre la pocion-parametro y posicion-subsector
	$stmt = $db->query("SELECT id_subsector_entrega, ABS(ABS(long_subsector) -".abs($longitud).") AS diff_log, ABS(ABS(lat_subsector) -".abs($latitud).") AS diff_lat 
											FROM  `subsector_entrega` 
											WHERE (!(long_subsector = 0) && !(lat_subsector = 0))" );

											
											
	if($stmt == false) 
		return "error longitud";

	$arrayDistance = array();				
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
		
		
		//obtiene distancia 
		$distance = sqrt(pow($row{'diff_log'},2) + pow($row{'diff_lat'},2));
		$arrayDistance[$row{'id_subsector_entrega'}]  = $distance;
		
		
	}			

	//ordena de menor a menor distancia
	asort($arrayDistance);


	//(agregar) si la menor distancia es mayor a un valir limite, se considera que el usuario no
	//esta dentro de ningun subsector y se entrega mensaje para hacerlo saber

	//subsecttor mas cercano
	$keys = array_keys($arrayDistance);
	$subsector = $keys[0];
	
	
	//obtiene primera lista de productos del sector mas cercano
	$stmt = $db->query("SELECT
							producto.id_producto,
							producto.precio,
							producto.descrip_producto,
							producto.titulo_producto,
							producto.numero_personas,
							producto.id_local
						FROM local_subsector_entrega, producto
						WHERE (local_subsector_entrega.id_local = producto.id_local
						AND local_subsector_entrega.id_subsector_entrega =".$subsector.")");

	if($stmt == false) 
		return "error obtener informacion producto subsector<br>";

	$arrayProductos = array();	
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
	
		array_push($arrayProductos,array('id_producto' => $row{'id_producto'},'id_local' => $row{'id_local'} , 'precio' => $row{'precio'} ,'titulo' => $row{'titulo_producto'}, 
			'descripcion' => $row{'descrip_producto'} ,'numero_personas' => $row{'numero_personas'}));
	}	

	
	//obtiene informacion del local 
	$stmt = $db->query("SELECT local.id_local,local.nombre_local, local.telefono_local
						FROM local_subsector_entrega, local
						WHERE (local_subsector_entrega.id_local = local.id_local
						AND local_subsector_entrega.id_subsector_entrega =".$subsector.")" );

	if($stmt == false) 
		return "error obtener informacion locales subsector<br>";

		

	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){

		$i = 0;
		$id_local = $row{'id_local'};
		$local = $row{'nombre_local'};
		$telefono = $row{'telefono_local'};
		
		//agrega datos de telefono y nombre local al arreglo producto
		foreach($arrayProductos as $producto){
			
			if($producto{'id_local'} == $id_local){
				$arrayProductos[$i]['telefono'] = $telefono;
				$arrayProductos[$i]['local'] = $local;
			}
			$i++;
		}
	}

	//obtiene nombre del sector y nombre del subsector para agregar al arreglo de productos
	$stmt = $db->query("SELECT subsector_entrega.nombre_subsector_entrega, sector_entrega.nombre_sector_entrega
						FROM subsector_entrega, sector_entrega
						WHERE (
							subsector_entrega.id_sector_entrega = sector_entrega.id_sector_entrega
							AND subsector_entrega.id_subsector_entrega = ".$subsector.")" );

	if($stmt == false) 
		return "error obtener informacion locales subsector<br>";

		
	//esta informacion es la misma en todo los productos
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){

		$i = 0;
		$nombreSector = $row{'nombre_sector_entrega'};
		$nombreSubsector = $row{'nombre_subsector_entrega'};;
		
		//agrega datos de telefono y nombre local al arreglo producto
		foreach($arrayProductos as $producto){
			
			
			$arrayProductos[$i]['nombreSector'] = $nombreSector;
			$arrayProductos[$i]['nombreSubsector'] = $nombreSubsector;
			
			$i++;
		}
	}
	
	
	
	
	//cierra coneccion
	$db = null; 

	return json_encode($arrayProductos);

}

//funcion obtiene id y nombre de los sectores
function getSectores(){

	$username = "root";
	$password = "";
	$hostname = "localhost"; 


	//connection to the database
	$db = new PDO('mysql:host=localhost;dbname=foodland_beta;charset=utf8', $username, $password);

	//consulta por id del subsector, y la diferencia entre la pocion-parametro y posicion-subsector
	$stmt = $db->query("SELECT id_sector_entrega, nombre_sector_entrega
						FROM sector_entrega");

											
											
	if($stmt == false) 
		return "error longitud";

	$arraySectores = array();				
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
		
		$sector = array('id_sector' => $row{'id_sector_entrega'},'nombre_sector' => $row{'nombre_sector_entrega'});
		array_push($arraySectores,$sector);
	}
	
	$db = null;
	
	return json_encode($arraySectores);
}


//funcion obtiene id y nombre de los subsectores con la id del sector
function getSubsectorsByIdSector($id_sector){
	$username = "root";
	$password = "";
	$hostname = "localhost"; 


	//connection to the database
	$db = new PDO('mysql:host=localhost;dbname=foodland_beta;charset=utf8', $username, $password);

	//consulta por id del subsector, y la diferencia entre la pocion-parametro y posicion-subsector
	$stmt = $db->query("SELECT id_subsector_entrega, nombre_subsector_entrega
						FROM subsector_entrega
						WHERE id_sector_entrega =". $id_sector );

											
											
	if($stmt == false) 
		return "error longitud";

	$arraySubsectores = array();				
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
		
		$subsector = array('id_subsector' => $row{'id_subsector_entrega'},'nombre_subsector' => $row{'nombre_subsector_entrega'});
		array_push($arraySubsectores,$subsector);
		
	}
	
	$db = null;
	
	return json_encode($arraySubsectores);
}


//funcion obtiene productos por id del subsector
function getProductbyIdSubsector($subsector){
	$username = "root";
	$password = "";
	$hostname = "localhost"; 


	//connection to the database
	$db = new PDO('mysql:host=localhost;dbname=foodland_beta;charset=utf8', $username, $password);

	//consulta por id del subsector, y la diferencia entre la pocion-parametro y posicion-subsector
	$stmt = $db->query("SELECT
							producto.id_producto,
							producto.precio,
							producto.descrip_producto,
							producto.titulo_producto,
							producto.numero_personas,
							producto.id_local
						FROM local_subsector_entrega, producto
						WHERE (local_subsector_entrega.id_local = producto.id_local
						AND local_subsector_entrega.id_subsector_entrega =".$subsector.")");


											
											
	if($stmt == false) 
		return "error longitud";

	$arrayProductos = array();				
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){
		
		array_push($arrayProductos,array('id_producto' => $row{'id_producto'},'id_local' => $row{'id_local'} , 'precio' => $row{'precio'} ,'titulo' => $row{'titulo_producto'}, 
			'descripcion' => $row{'descrip_producto'} ,'numero_personas' => $row{'numero_personas'}));
	}
	
	//obtiene informacion del local 
	$stmt = $db->query("SELECT local.id_local,local.nombre_local, local.telefono_local
						FROM local_subsector_entrega, local
						WHERE (local_subsector_entrega.id_local = local.id_local
						AND local_subsector_entrega.id_subsector_entrega =".$subsector.")" );

	if($stmt == false) 
		return "error obtener informacion locales subsector<br>";

		

	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){

		$i = 0;
		$id_local = $row{'id_local'};
		$local = $row{'nombre_local'};
		$telefono = $row{'telefono_local'};
		
		//agrega datos de telefono y nombre local al arreglo producto
		foreach($arrayProductos as $producto){
			
			if($producto{'id_local'} == $id_local){
				$arrayProductos[$i]['telefono'] = $telefono;
				$arrayProductos[$i]['local'] = $local;
			}
			$i++;
		}
	}

	//obtiene nombre del sector y nombre del subsector para agregar al arreglo de productos
	$stmt = $db->query("SELECT subsector_entrega.nombre_subsector_entrega, sector_entrega.nombre_sector_entrega
						FROM subsector_entrega, sector_entrega
						WHERE (
							subsector_entrega.id_sector_entrega = sector_entrega.id_sector_entrega
							AND subsector_entrega.id_subsector_entrega = ".$subsector.")" );

	if($stmt == false) 
		return "error obtener informacion locales subsector<br>";

		
	//esta informacion es la misma en todo los productos
	while($row = $stmt->fetch(PDO::FETCH_ASSOC)){

		$i = 0;
		$nombreSector = $row{'nombre_sector_entrega'};
		$nombreSubsector = $row{'nombre_subsector_entrega'};;
		
		//agrega datos de telefono y nombre local al arreglo producto
		foreach($arrayProductos as $producto){
			
			
			$arrayProductos[$i]['nombreSector'] = $nombreSector;
			$arrayProductos[$i]['nombreSubsector'] = $nombreSubsector;
			
			$i++;
		}
	}
	
	$db = null;
	
	return json_encode($arrayProductos);
}









/*$a = getproductbygps(-33.028989,-71.647055);
$b = getSectores();
echo $b."<br><br>";

$c = getSubsectorsByIdSector(9);
echo $c."<br><br>";

$d = getProductbyIdSubsector(21);
echo $d."<br><br>";

echo $a;*/

?>