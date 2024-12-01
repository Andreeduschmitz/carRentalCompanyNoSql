# carRentalCompanyNoSql

O "D&N Car - Locações" é um sistema voltado para o gerenciamento de locações de veículos. O sistema foi desenvolvido com foco em facilitar a gestão de uma empresa de aluguel de veículos, automatizando processos relacionados à locação, renovação de locações, cadastro e listagem de clientes e de funcionários. Esse trabalho faz parte da fase 2 do projeto da matéria de Banco de dados 2.

Instruções para compilação:

1 - Copie o repositório para o seu local.

2 - Certifique-se de que há alguma versão do java (8 ou superior) instalado na sua máquina.

3 - Certifique-se de que você possui o Maven instalado. Tutorial de instalação: https://maven.apache.org/install.html

4 - Certifique-se de que você possui o MongoDb instalado. Tutorial de instalação: https://www.mongodb.com/pt-br/docs/manual/installation/

5 - Entre no diretório carRentalCompanyNoSql/carRentalCompanyNoSql/src/main/java/connection e configure a conexão com seu MongoDB local na classe Connection.java.

6 - Utilize os arquivos dump do repositório presentes na pasta "dump MongoDb" e importe os mesmos em 5 collections pré-criadas com os seguintes nomes: address, client, rental, seller e vehicle. Os nomes de cada arquivo json presente na pasta "dump MongoDb" sugerem a collection em que eles devem ser importados.

7 - Forneça permissão de execução ao arquivo run.sh do repositório com o comando "chmod +x run.sh".

8 - Execute via terminal na pasta raíz do projeto o ./run.sh.
