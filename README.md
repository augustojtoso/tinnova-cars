# tinnova-cars
Java API for cars database

Note: Since I am not well knowlaged on front-end development I decided to skip the Single Page Application.
I could just use some online template, however I found this somewhat cheating. 
So this application will only work as a backend API

## Project

The project specification can be found on the pdf file: avaliacao_tinnova_vG.pdf

## Installation

Use Maven (https://maven.apache.org/download.cgi) to install tinnova-cars.

```bash
mvn install 
```

## Usage

start server runing the generated jar file:

```bash
java -jar tinnova-cars-0.0.1.jar
```

You can test the backend service using postman

* get: localhost:8080/veiculos 
to list of all cars

* get: localhost:8080/veiculos/{id} 
to get info about a specific car. 

	If not present on database, a empty page is shown.

* delete: localhost:8080/veiculos/{id} 
to delete a specific car. 

* post: localhost:8080/veiculos 
to add a new veiculo to database

* patch: localhost:8080/veiculos/{id} 
to update some fields of a veiculo. 

* put: localhost:8080/veiculos/{id} 
to update all fields of a veiculo

Example body (post,patch,put): 

		{
	    "veiculo":"carro",
	    "marca":"FORD",
	    "ano":"2022",
	    "descrição":"bonito"
	    "vendido:true"
		}

*Notes about request parameters:

	"vendido" has a default value of false.


	Acceptable "marca" values are:
	    AUDI,
	    VOLKSWAGEN,
	    HYUNDAI,
	    GM,
	    FORD,
	    NISSAN,
	    HONDA,
	    FIAT,
	    RENAULT,
	    MERCEDES,
	    PEUGEOT,
	    BMW; 


* get: localhost:8080/veiculos/find?q=disponiveis to list the number o availabe (vendido=false) veiculos.

* get: localhost:8080/veiculos/find?q=decada to list the number of veiculos by decada.

* get: localhost:8080/veiculos/find?q=fabricante to list the number of veiculos by fabricante.
