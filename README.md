# AppMaterias

Por favor tener en cuenta las versiones del SDK Manager con las que actualmente corre perfectamente el programa en el Android Studio, si las tienen instaladas no deberia presentar problema para abrirlas:

- Android SDK Tools 24.4.1
- Android SDK Platform-tools 23.0.1
- Android SDK Build-tools 23.0.2
- Toda la API 23 y Toda la API 17 (sin las imagenes de sistema)


La actividad de login no es la que trae el Android Studio ya que despues de analizarla trae mucha logica consigo y en este momento necesitamos un login sencillo, basicamente lo que hace es tomar las cadenas de los EditText y mandarlas a la consulta en la base de datos.

Para mantener la sesión del usuario se estan utilizando SharedPreferences (recurso: http://www.sgoliver.net/blog/preferencias-en-android-i-shared-preferences/) el nombre de la shared preference usada es "usuario" y alli se almacena toda la informacion que se obtiene de la base de datos.

Como se propuso un encabezado en el que siempre aparezca el nombre del usuario, el programa, el rol y la foto entonces creé un fragmento, para utilizar el fragmento se llama a la etiqueta <fragment> y se referencia el fragmento que queremos insertar. Desde el punto de vista del codigo hay que implementar la interfaz EncabezadoFragment.OnFragmentInteractionListener (el entorno de desarrollo saca error y dice que debe implementar un metodo entonces hay que poner dentro de la actividad el codigo:

@Override
    public void onFragmentInteraction(Uri uri) {

    }

Ya con esto funcionaría correctamente el Fragmento.

Mas recursos: 
- http://stackoverflow.com/questions/10600670/sqlitedatabase-query-method
- http://www.sgoliver.net/blog/fragments-en-android/
