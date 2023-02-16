# Vault

Vault es una aplicación Java que te permite almacenar y gestionar tus tarjetas de crédito de forma segura.

## Instalación

Para utilizar Vault, necesitarás tener instalado Java Development Kit (JDK) versión 8 o superior. También necesitarás tener instalado un servidor de base de datos MySQL. 
Vault utiliza la version 3.0.2 de SpringBoot.

## Uso

Vault ofrece una gran cantidad de características y herramientas para añadir tus tarjetas y almacenar la información de forma segura en la base de datos. También puedes editar y eliminar tarjetas según sea necesario.
Vault utiliza un sistema de cifrado seguro para proteger tus tarjetas. Nunca almacenamos información de tarjetas sin cifrar en la base de datos.

## Licencia

Vault se distribuye bajo la licencia MIT. Consulta el archivo LICENSE.txt para obtener más información.

## Contacto

Si tienes preguntas o comentarios, no dudes en ponerte en contacto con nosotros en emarocha4@gmail.com

## Ejemplos de código

Para compilar y ejecutar Vault desde el código fuente, sigue los siguientes pasos:

1) Clona el repositorio de Vault: git clone https://github.com/tu-usuario/vault.git
2) Abre una terminal y navega hasta el directorio raíz del proyecto: cd vault
3) Compila la aplicación: ./gradlew build
4) Ejecuta la aplicación: java -jar build/libs/vault.jar
5) Puedes probar los endpoints usando la herramienta Postman:
 
   Registrar una tarjeta: localhost:8080/cardvault/token

   Solicitar tarjeta asociada a token: localhost:8080/cardvault/number/{token}

