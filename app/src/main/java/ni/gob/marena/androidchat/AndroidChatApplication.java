package ni.gob.marena.androidchat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Creado por xareas
 * Fecha 6/7/16.
 */
public class AndroidChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupFireBase();
    }

    private void setupFireBase(){
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
