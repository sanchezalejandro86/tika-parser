Proyecto para evaluar el potencial de la librería de Apache Tika http://tika.apache.org/

# Pasos para correrlo:

Los siguientes comandos compilarán y ejecutarán el Tika-Parser que directamente se encargará de parsear todos los archivos que se encuentren en el directorio './cvs' y convertirlos a txt en el directorio './results':
'''
> ./gradlew build

> java -jar ./build/libs/tika-parser-0.0.1-SNAPSHOT.jar 
'''

