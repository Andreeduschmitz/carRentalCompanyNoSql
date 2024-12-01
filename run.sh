#!/bin/bash

# Caminho para o diretório do projeto
PROJECT_DIR="carRentalCompanyNoSql"
cd "$PROJECT_DIR"

# Inicia o MongoDB
echo "Iniciando o MongoDB..."
sudo systemctl start mongod

# Verifica se o MongoDB foi iniciado corretamente
if ! pgrep -x "mongod" > /dev/null
then
    echo "Falha ao iniciar o MongoDB. Verifique o status do serviço."
    exit 1
else
    echo "MongoDB iniciado com sucesso."
fi

# Verifica se o Maven está instalado
if ! command -v mvn &> /dev/null
then
    echo "Maven não está instalado. Instale o Maven e tente novamente."
    exit 1
fi

# Compila o projeto com Maven
echo "Compilando o projeto..."
mvn clean compile

# Verifica se a compilação foi bem-sucedida
if [ $? -ne 0 ]; then
    echo "Erro durante a compilação. Verifique os erros e tente novamente."
    exit 1
fi

# Executa o projeto usando Maven
clear
echo "Executando o projeto..."
mvn exec:java -Dexec.mainClass="Main"
