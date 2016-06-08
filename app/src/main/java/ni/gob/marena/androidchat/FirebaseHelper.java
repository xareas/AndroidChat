package ni.gob.marena.androidchat;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Creado por xareas
 * Fecha 6/7/16.
 */
public class FirebaseHelper {
    private Firebase dataReference;
    private static final String SEPARATOR = "___";
    private static final String CHATS_PATH = "chats";
    private static final String USERS_PATH = "users";
    private static final String CONTACTS_PATH ="contacts";
    private static final String FIREBASE_URL ="https://android-chat-example.firebaseio.com";

    private static class SinglentonHolder {
        private static final FirebaseHelper instance = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance(){
        return SinglentonHolder.instance;
    }

    public FirebaseHelper(){
        this.dataReference = new Firebase(FIREBASE_URL);
    }


    public Firebase getDataReference(){
        return dataReference;
    }

    public String getAuthUserEmail(){
        AuthData authData = dataReference.getAuth();
        String email = null;
        if(authData!=null){
            Map<String,Object> providerData = authData.getProviderData();
            email = providerData.get("email").toString();
        }
        return email;
    }

    public Firebase getUserReference(String email){
        Firebase userReference = null;
        if(email!=null){
            String emailKey = email.replace('.','_');
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public Firebase getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    public Firebase getContactsReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public Firebase getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public Firebase getOneContactReference(String mainEmail,String childEmail){
        String childKey = childEmail.replace('.','_');
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public Firebase getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace('.','_');
        String keyReceiver = receiver.replace('.','_');
        String keyChat = keySender + SEPARATOR + keySender;
        if(keySender.compareTo(keyReceiver)>0){
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

   public void changeUserConnectionStatus(boolean online){
       if(getMyUserReference()!=null){
           Map<String,Object> updates = new HashMap<String, Object>();
           updates.put("online",online);
           getMyUserReference().updateChildren(updates);
           notifyContactsOfConnectionChange(online);
       }
   }

    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online,false);
    }

    public void signOff(){
        notifyContactsOfConnectionChange(false,true);
    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean singoff) {
        final String myEmail = getAuthUserEmail();
        getMyUserReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String email = child.getKey();
                    Firebase reference = getOneContactReference(email,myEmail);
                    reference.setValue(online);
                }
                if(singoff){
                    dataReference.unauth();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });

    }

}
