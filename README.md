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

## Aufgabe 2
Machen Sie die Anwendung offline fähig. Geladene Posts sollen bei jedem erfolgreichen Abruf auch lokal in einer Datenbank gespeichert werden. Hierfür kann die Bibliothek [Room] verwendet werden.  Wenn das Handy nicht mit dem Internet verbunden ist, dann sollen stattdessen die lokalen Daten angezeigt werden. Damit das Laden aus der Datenbank

Zum Überprüfen ob Internet verfügbar ist, ist die Berechtigung ÀCCESS_NETWORK_STATE erforderlich. Für die Prüfung selbst kann folgende Code-Snippet genutzt werden (einfach in das Projekt kopieren):

``` Java
public static boolean isInternetAvailable(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
    return isConnected;
}
```

1. Fügen Sie Room als Dependency der App hinzu: implementation "androidx.room:room-runtime:$room_version"
2. Erstellen sie ein `persistance` package, dem die benötigten Klassen für Room hinzugefügt werden:
  * PostEntity
  * PostDao
  * AppDatabase (erbt von `RoomDatabase`)
3. Erstellen Sie ein package `app` und darin die Klasse `PostApplication`, die von `Application` abgeleitet wird. Überschreiben Sie schließlich `onCreate`. Diese muss anschließend in der AndroidManifest als attribute `name` im Tag der application gesetzt werden.
4. Um Zugriff auf das `AppDatabase` Objekt zu erhalten, wird in der onCreate Methode der `PostApplication` die AppDatabase erstellt und als statische Methode verfügbar gemacht.
5. Laden Sie nun im MainViewModel die Daten entweder über Retrofit, oder alternativ aus Room. Um im `MainViewModel` das Internet zu prüfen wird der `Context` benötigt. Hierfür kann man stattdessen von `AndroidViewModel` ableiten.
6. Damit auch beim Laden aus der DB der UI-Thread nicht blockier twird, muss das Laden in einem `AsyncTask` ausgeführt werden.
7. Wenn die Daten erfolgreich von der Api geladen wurden müssen diese noch in der Datenbank gespeichert werden.

[Android-Studio]: https://developer.android.com/studio
[Github]: https://github.com/af-llo/android-workshop
[Retrofit]: https://square.github.io/retrofit/
[Gson]: https://github.com/google/gson
[LoggingInterceptor]: https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
[Room]: https://developer.android.com/training/data-storage/room/index.html
[AsyncTask]: https://developer.android.com/reference/android/os/AsyncTask