#UbicateUV - Proyecto de Inteligencia Artificial#

La Universidad del Valle ha construido un prototipo de Robot llamado Ubicame en UV, cuya tarea es ubicar a las personas cuando desean buscar algún profesor o sitio de la Universidad, el Robot tiene información del ambiente que consiste en los obstáculos
que hay, su ubicación inicial y su objetivo. El ambiente es representado con una matriz de enteros de tamaño nxn la cual tiene los siguientes elementos:

* Inicio, es representada con el número 0.
* Pared, es representado con el número 1.
* Espacio disponible es representado con el número 2.
* Piso resbaloso, es representado con el número 3.
* Lugar con alto flujo de personas, con el número 4.
* Lugar restringido con el numero 5.
* Lugar para recarga del robot con el número 6.
* Meta con el número 7.

Los costos para pasar por cada lugar del mapa son:

* Espacio disponible: 1
* Piso resbaloso: 3
* Lugar con alto flujo de personas: 4
* Lugar restringido: 6
* Lugar para recarga: 5

El robot lamentablemente es un prototipo y sólo puede avanzar 6 casillas antes de necesitar recargar, si no encuentra un lugar para recargar entonces este se detiene sin remedio. El robot puede realizar cuatro movimientos en el ambiente, correspondientes a: arriba, abajo, izquierda y derecha.

## Entradas y Salidas ##
### Formato de Entrada###
Es necesario su aplicación permita leer una entrada por archivo de texto, la entrada tiene el siguiente formato:

La primera linea contiene el tamaño del ambiente n. Las siguientes n lineas contienen la matriz del ambiente.

* 5
* 0 1 1 3 1
* 2 2 4 5 1
* 3 4 6 6 2
* 4 5 5 5 6
* 6 6 7 1 1

### Formato de salida ###

Se espera que la salida sea un archivo de texto o en consola con la siguiente información:

Nodos expandidos: X
Nodos creados: Y
Costo total solución: Z
Factor ramificación: W