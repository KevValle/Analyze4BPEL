typedef string (values={"Manuel", "Maria", "Antonio", "Juan"}) FirstName;
typedef string (values={"Perez", "Gomez", "Sanchez", "Gomez"}) SurName;
typedef string (values={"low", "high"}) Risk;
typedef string (values={"true", "false"}) Accepted;
typedef int (min=0, max = 1000000) Quantity;

FirstName firstName;
SurName surName;
Quantity cantidad;
Accepted accepted;
Risk riskLevel;

