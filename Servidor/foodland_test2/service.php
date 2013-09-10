<?php
	require 'functions.php';
	require 'lib/nusoap.php';
	$server = new nusoap_server();
	$server->configureWSDL("foodland_test2","urn:foodland_test2");
	
	$server->register(
				"getproductbygps",	//nombre de la funcion
				array("latitud"=>'xsd:string',"longitud"=>'xsd:string'),	//inputs
				array("return"=>"xsd:string")	//outputs
			);

	$server->register(
				"getSectores",	//nombre de la funcion
				array(),	//inputs
				array("return"=>"xsd:string")	//outputs
			);
	$server->register(
				"getSubsectorsByIdSector",	//nombre de la funcion
				array("id_sector"=>'xsd:string'),	//inputs
				array("return"=>"xsd:string")	//outputs
			);
			
	$server->register(
				"getProductbyIdSubsector",	//nombre de la funcion
				array("subsector"=>'xsd:string'),	//inputs
				array("return"=>"xsd:string")	//outputs
			);
			
	$HTTP_RAW_POST_DATA = isset($HTTP_RAW_POST_DATA) ? $HTTP_RAW_POST_DATA : '';
	$server->service($HTTP_RAW_POST_DATA);
?>