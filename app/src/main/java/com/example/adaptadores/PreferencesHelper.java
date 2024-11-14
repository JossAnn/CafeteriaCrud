package com.example.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PreferencesHelper {
    private SharedPreferences sharedPreferences;
    private static final String KEY_ALIAS = "MyAppKeyAlias";
    private static final String AES_MODE = "AES/ECB/PKCS5Padding"; // Modo seguro de encriptación
    private static final String TAG = "PreferencesHelper";

    public PreferencesHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        createKeyIfNecessary();
    }

    public void guardarPreferencia(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String obtenerPreferencia(String key) {
        return sharedPreferences.getString(key, "");
    }

    // Método para encriptar datos
    public String encriptar(String data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, "Error encriptando datos", e);
            return null;
        }
    }

    // Método para desencriptar datos
    public String desencriptar(String data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
            return new String(decryptedBytes);
        } catch (Exception e) {
            Log.e(TAG, "Error desencriptando datos", e);
            return null;
        }
    }

    // Crear clave secreta si aún no existe
    private void createKeyIfNecessary() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                keyGenerator.init(256);
                keyGenerator.generateKey();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al crear clave", e);
        }
    }

    // Obtener la clave secreta
    private SecretKey getSecretKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            return entry.getSecretKey();
        } catch (Exception e) {
            Log.e(TAG, "Error obteniendo clave", e);
            return null;
        }
    }
}

