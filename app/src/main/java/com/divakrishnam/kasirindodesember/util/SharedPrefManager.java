package com.divakrishnam.kasirindodesember.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.divakrishnam.kasirindodesember.model.Kasir;

public class SharedPrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static SharedPrefManager mInstance;
    private static Context mContext;

    private static final String SHARED_PREF_NAME = "indodesember";

    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_NAMA = "keynama";
    private static final String KEY_ID = "keyid";

    private SharedPrefManager(Context context){
        mContext = context;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void kasirLogin(Kasir kasir){
        editor.putString(KEY_ID, kasir.getKasirId());
        editor.putString(KEY_NAMA, kasir.getKasirNama());
        editor.putString(KEY_USERNAME, kasir.getKasirUsername());
        editor.putString(KEY_PASSWORD, kasir.getKasirPassword());
        editor.apply();
    }

    public boolean isLoggedIn(){
        return pref.getString(KEY_ID, null) != null;
    }

    public Kasir getKasir(){
        return new Kasir(
                pref.getString(KEY_ID, null),
                pref.getString(KEY_NAMA, null),
                pref.getString(KEY_USERNAME, null),
                pref.getString(KEY_PASSWORD, null)
        );
    }

    public void logout(){
        editor.clear();
        editor.apply();
    }
}
