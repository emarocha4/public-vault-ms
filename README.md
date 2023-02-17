# API REST para almacenamiento de números de tarjeta PCI

Esta API está desarrollada en Java con SpringBoot, y su objetivo es almacenar números de tarjeta siguiendo los protocolos PCI.

## Requisitos

- Java Development Kit (JDK) versión 8 o superior. 
- Servidor de base de datos MySQL. 
- SpringBoot 3.0.2.
- Variable de entorno KEY-VAULT con una key de 32 bytes.


## Ejecutando el Proyecto

Para compilar y ejecutar desde el código fuente, sigue los siguientes pasos:

1) Clona el repositorio de Vault: git clone https://github.com/emarocha4/public-vault-ms
2) Abre una terminal y navega hasta el directorio raíz del proyecto: cd vault
3) Compila la aplicación: ./gradlew build
4) Ejecuta la aplicación: java -jar build/libs/vault.jar 

Ejemplos de endpoints disponibles:
    
   - POST obtener token

   curl --location 'localhost:8081/pci-vault/card' \
   --header 'Content-Type: application/json' \
   --data '{
   "cardNumber":"1234567895789415"
   }'
   
   - GET obtener numero de tarjeta

   curl --location 'localhost:8081/pci-vault/card/4ef16573-bcc1-4576-9505-d183fe5c0ef0'

## Contacto

Si tienes preguntas, comentarios, contribuciones o sugerencias, no dudes en ponerte en contacto en emarocha4@gmail.com.

