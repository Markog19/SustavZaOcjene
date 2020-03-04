# SustavZaOcjene
kada korisnik upise svoje podatke ime se salje na drugi kontroler
i preko tog imena trazimo ID u bazi te usporedjujemo sa IDKorisnik u 
tablici ocjene i preko tog ID-a ispisivamo sve ostale podatke

delete i edit opcija za profesora

## Promjene 03.03.2020.

Dodavanje public static int ID unutar LoginController (LoginCntroller.ID = "id iz baze")
Dohvaćanje LoginController.ID
Dodavanje u listOcjene id parametra kako bi se mogao izvršiti upit "SELECT * FROM ocjene WHERE IDKorisnik=?"
 ## Plan
 
 Dodati opcije za edit, delete, filtriranje po pojedinacnom predmetu
