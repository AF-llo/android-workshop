# android-workshop
Umsetzung einer einfachen Android App zur Anbindung einer REST-API und speichern der Daten in einer DB

## Vorbereitung
1. [Android-Studio] installieren
2. Android projekt von [Github] clonen
3. Projekt im Android-Studio öffnen
4. App auf Emulator starten

## Aufgabe 1
Laden Sie die Posts von der API. Zum Laden der Posts sollte Retrofit als REST-Client genutzt werden. Zum Parsen der JSON-Struktur kann die Bibliothek GSON verwendet werden. Falls ein Fehler beim Laden auftritt, dann soll eine Info als `Toast` angezeigt werden. Der Fehler soll ebenso wie das Laden oder die Posts als `LiveData` event übermittelt werden. Gehen Sie dabei wie folgt vor:

1. Fügen Sie die benötigte Berechtigung für Internet in der `AndroidManifest` hinzu.

2. Binden Sie die erforderlichen Bibliotheken in die `build.gradle` des app modules ein.
  * [Retrofit] -> `com.squareup.retrofit2:retrofit:2.6.0`
  * [Gson] -> `com.google.code.gson:gson:2.8.5`
  * Gson-Converter -> `com.squareup.retrofit2:converter-gson:2.6.`
  * Optional: [LoggingInterceptor] -> `com.squareup.okhttp3:logging-interceptor:4.0.0`

3. Erstellen Sie die Klassen `ApiPost` im package network.

4. Erstellen Sie das Interface für den Retrofit-Service und fügen sie die GET-Methode zum Laden der Posts hinzu. Es muss der Header `Authorization` gesetzt sein und als Wert der Token in der Form `Bearer [ACCESS_TOKEN]`.

5. Erstellen Sie eine Klasse `ApiProvider`, die eine Instanz des Retrofit-Service zurück liefert.

6. Ersetzen Sie das Laden der Posts in der Klasse `MainViewModel` und laden Sie stattdessen die Daten mit Retrofit von der API. Nutzen Sie zum Laden die Asynchrone methode `Call.enqueue(Callback)`

Info - Anzeigen eines Toasts:
``` Java
Toast.makeText(Context context, String message, int duration)
```

[Android-Studio]: https://developer.android.com/studio
[Github]: https://github.com/af-llo/android-workshop
[Retrofit]: https://square.github.io/retrofit/
[Gson]: https://github.com/google/gson
[LoggingInterceptor]: https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor