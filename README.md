# SafetyNet_Alerts

Système d’envoi d’informations aux systèmes de services d’urgence

## Infos
-  ** Java version :** 17
-  ** Spring Boot version :** 3.0.4
- Connexion avec Log4j
- Architecture MVC

## Paramètres
- Fichier données en entrée format JSON *data.json* situé dans le répertoire *ressources*. Le nom et l'emplacement du référentiel de données peut être adapté dans la section *data.jsonFilePath* du fichier de configuration application.properties

                         `data.jsonFilePath = src/main/resources/data.json`

- Les requêtes et réponses associées sont logguées dans dans le log *SafetyLog.txt* situé à la racine de l'application

- Le port local localhost http par défaut est 8080 et peut être adapté  depuis la section *server.port* du fichier de configuration application.properties

                         `server.port=8080`

## URLS Spécifiques
`http://localhost:8080/firestation?stationNumber=<station_number>`  
Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1. La liste
doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone. De plus,
elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
moins) dans la zone desservie.  

`http://localhost:8080/childAlert?address=<address>`  
Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
membres du foyer. 


`http://localhost:8080/phoneAlert?firestation=<firestation_number>`  
Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
pompiers.  


`http://localhost:8080/fire?address=<address>`  
Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
médicaux (médicaments, posologie et allergies) de chaque personne.  


`http://localhost:8080/flood/stations?stations=<a_list_of_station_numbers>`  
Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
faire figurer leurs antécédents médicaux (médicaments, posologie et allergies).  


`http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>`  
Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
toutes apparaître.  


`http://localhost:8080/communityEmail?city=<city>`  
Cette url doit retourner les adresses mail de tous les habitants de la ville