package com.divakrishnam.kasirindodesember.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.divakrishnam.kasirindodesember.model.Barang;
import com.divakrishnam.kasirindodesember.model.DetailTransaksi;
import com.divakrishnam.kasirindodesember.model.Kasir;
import com.divakrishnam.kasirindodesember.model.Kategori;
import com.divakrishnam.kasirindodesember.model.Transaksi;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IndoDesemberDB.db";

    private static final String TABLE_BARANG = "Barang";
    private static final String TABLE_KASIR = "Kasir";
    private static final String TABLE_KATEGORI = "Kategori";
    private static final String TABLE_TRANSAKSI = "Transaksi";
    private static final String TABLE_DETAIL_TRANSAKSI = "DetailTransaksi";

    private static final String TRIGGER_BARANG = "TriggerBarang";

    private static final String BARANG_ID = "BarangId";
    private static final String BARANG_NAMA = "BarangNama";
    private static final String BARANG_ID_KATEGORI = "BarangIdKategori";
    private static final String BARANG_STOK = "BarangStok";
    private static final String BARANG_HARGA = "BarangHarga";

    private static final String KASIR_ID = "KasirId";
    private static final String KASIR_NAMA = "KasirNama";
    private static final String KASIR_USERNAME = "KasirUsername";
    private static final String KASIR_PASSWORD = "KasirPassword";

    private static final String KATEGORI_ID = "KategoriId";
    private static final String KATEGORI_NAMA = "KategoriNama";

    private static final String TRANSAKSI_ID = "TransaksiId";
    private static final String TRANSAKSI_ID_KASIR = "TransaksiIdKasir";
    private static final String TRANSAKSI_WAKTU = "TransaksiWaktu";
    private static final String TRANSAKSI_TOTAL_BELANJA = "TransaksiTotalBelanja";

    private static final String DETAIL_TRANSAKSI_ID = "DetailTransaksiId";
    private static final String DETAIL_TRANSAKSI_ID_BARANG = "DetailTransaksiIdBarang";
    private static final String DETAIL_TRANSAKSI_JUMLAH_BARANG = "DetailTransaksiJumlahBarang";
    private static final String DETAIL_TRANSAKSI_TOTAL_HARGA = "DetailTransaksiTotalHarga";



    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_BARANG = "CREATE TABLE "
                + TABLE_BARANG
                + " ("
                + BARANG_ID
                + " TEXT PRIMARY KEY,"
                + BARANG_NAMA
                + " TEXT,"
                + BARANG_ID_KATEGORI
                + " TEXT,"
                + BARANG_STOK
                + " INTEGER,"
                + BARANG_HARGA
                + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_BARANG);

        String CREATE_TABLE_KASIR = "CREATE TABLE "
                + TABLE_KASIR
                + " ("
                + KASIR_ID
                + " TEXT PRIMARY KEY,"
                + KASIR_NAMA
                + " TEXT,"
                + KASIR_USERNAME
                + " TEXT,"
                + KASIR_PASSWORD
                + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_KASIR);

        String CREATE_TABLE_KATEGORI = "CREATE TABLE "
                + TABLE_KATEGORI
                + " ("
                + KATEGORI_ID
                + " TEXT PRIMARY KEY,"
                + KATEGORI_NAMA
                + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_KATEGORI);

        String CREATE_TABLE_TRANSAKSI = "CREATE TABLE "
                + TABLE_TRANSAKSI
                + " ("
                + TRANSAKSI_ID
                + " INTEGER PRIMARY KEY,"
                + TRANSAKSI_ID_KASIR
                + " TEXT,"
                + TRANSAKSI_WAKTU
                + " TEXT,"
                + TRANSAKSI_TOTAL_BELANJA
                + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSAKSI);

        String CREATE_TABLE_DETAIL_TRANSAKSI = "CREATE TABLE "
                + TABLE_DETAIL_TRANSAKSI
                + " ("
                + DETAIL_TRANSAKSI_ID
                + " INTEGER,"
                + DETAIL_TRANSAKSI_ID_BARANG
                + " TEXT,"
                + DETAIL_TRANSAKSI_JUMLAH_BARANG
                + " INTEGER,"
                + DETAIL_TRANSAKSI_TOTAL_HARGA
                + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_DETAIL_TRANSAKSI);

        String CREATE_TRIGGER_BARANG = "CREATE TRIGGER "
                + TRIGGER_BARANG
                + " AFTER INSERT ON "
                + TABLE_DETAIL_TRANSAKSI
                + " BEGIN"
                + " UPDATE "+TABLE_BARANG+" SET "+BARANG_STOK+"=OLD."+BARANG_STOK+"-NEW."+DETAIL_TRANSAKSI_JUMLAH_BARANG
                + " WHERE OLD."+BARANG_ID+"=NEW."+DETAIL_TRANSAKSI_ID_BARANG+";"
                + " END;";
        sqLiteDatabase.execSQL(CREATE_TRIGGER_BARANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //BARANG

    public List<Barang> loadBarangHandler(){
        String query = "SELECT * FROM "+TABLE_BARANG+" INNER JOIN "+TABLE_KATEGORI+" ON "+BARANG_ID_KATEGORI+" = "+KATEGORI_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Barang> barangs = new ArrayList<>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()) {
                Barang barang = new Barang();
                barang.setBarangId(cursor.getString(0));
                barang.setBarangNama(cursor.getString(1));
                barang.setBarangKategori(cursor.getString(6));
                barang.setBarangStok(cursor.getString(3));
                barang.setBarangHarga(cursor.getString(4));
                barangs.add(barang);
                cursor.moveToNext();
            }
            cursor.close();
        }else{
            barangs = null;
        }

        db.close();
        return barangs;
    }

    public void addBarangHandler(Barang barang){
        ContentValues values = new ContentValues();
        values.put(BARANG_ID, barang.getBarangId());
        values.put(BARANG_NAMA, barang.getBarangNama());
        values.put(BARANG_ID_KATEGORI, barang.getBarangKategori());
        values.put(BARANG_STOK, barang.getBarangStok());
        values.put(BARANG_HARGA, barang.getBarangHarga());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_BARANG, null, values);
        db.close();
    }

    public Barang findBarangHandler(String search){
        String query = "SELECT * FROM "+TABLE_BARANG+" WHERE "+BARANG_NAMA+" LIKE "+"'%"+search+"%' OR "+BARANG_ID+" LIKE "+"'%"+search+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Barang barang = new Barang();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            barang.setBarangId(cursor.getString(0));
            barang.setBarangNama(cursor.getString(1));
            barang.setBarangKategori(cursor.getString(6));
            barang.setBarangStok(cursor.getString(3));
            barang.setBarangHarga(cursor.getString(4));

            cursor.close();
        }else{
            barang = null;
        }

        db.close();
        return barang;
    }

    public boolean deleteBarangHandler(String id){
        boolean result = false;
        String query = "SELECT * FROM "+TABLE_BARANG+" WHERE "+BARANG_ID+" = '"+id+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Barang barang = new Barang();

        if (cursor.moveToFirst()){
            barang.setBarangId(cursor.getString(0));
            db.delete(TABLE_BARANG, BARANG_ID+"=?", new String[]{
                    String.valueOf(barang.getBarangId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateBarangHandler(String id, String nama, String kategori, String stok, String harga){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(BARANG_ID, id);
        args.put(BARANG_NAMA, nama);
        args.put(BARANG_ID_KATEGORI, kategori);
        args.put(BARANG_STOK, stok);
        args.put(BARANG_HARGA, harga);
        return db.update(TABLE_BARANG, args, BARANG_ID+"="+id, null)>0;
    }

    //KASIR

    public void registrasiHandler(Kasir kasir){
        ContentValues values = new ContentValues();
        values.put(KASIR_ID, kasir.getKasirId());
        values.put(KASIR_NAMA, kasir.getKasirNama());
        values.put(KASIR_USERNAME, kasir.getKasirUsername());
        values.put(KASIR_PASSWORD, kasir.getKasirPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_KASIR, null, values);
        db.close();
    }

    public Kasir loginHandler(String username, String password){
        String query = "SELECT * FROM "+TABLE_KASIR+" WHERE "+KASIR_USERNAME+" = "+"'"+username+"' AND "+KASIR_PASSWORD+" = "+"'"+password+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Kasir kasir = new Kasir();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            kasir.setKasirId(cursor.getString(0));
            kasir.setKasirNama(cursor.getString(1));
            kasir.setKasirUsername(cursor.getString(2));
            kasir.setKasirPassword(cursor.getString(3));
            cursor.close();
        }else{
            kasir = null;
        }

        db.close();
        return kasir;
    }

    public Kasir loadKasirHandler(){
        String query = "SELECT * FROM "+TABLE_KASIR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Kasir kasir = new Kasir();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            kasir.setKasirId(cursor.getString(0));
            kasir.setKasirNama(cursor.getString(1));
            cursor.close();
        }else{
            kasir = null;
        }

        db.close();
        return kasir;
    }

    public Kasir findKasirHandler(String search){
        String query = "SELECT * FROM "+TABLE_KASIR+" WHERE "+KASIR_NAMA+" LIKE "+"'%"+search+"%' OR "+KASIR_ID+" LIKE "+"'%"+search+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Kasir kasir = new Kasir();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            kasir.setKasirId(cursor.getString(0));
            kasir.setKasirNama(cursor.getString(1));
            cursor.close();
        }else{
            kasir = null;
        }

        db.close();
        return kasir;
    }

    public boolean deleteKasirHandler(String id){
        boolean result = false;
        String query = "SELECT * FROM "+TABLE_KASIR+" WHERE "+KASIR_ID+" = '"+id+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Kasir kasir = new Kasir();

        if (cursor.moveToFirst()){
            kasir.setKasirId(cursor.getString(0));
            db.delete(TABLE_KASIR, KASIR_ID+"=?", new String[]{
                    String.valueOf(kasir.getKasirId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateKasirHandler(String id, String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KASIR_ID, id);
        args.put(KASIR_NAMA, nama);
        return db.update(TABLE_KASIR, args, KASIR_ID+"="+id, null)>0;
    }

    //KATEGORI

    public List<Kategori> loadKategoriHandler(){
        String query = "SELECT * FROM "+TABLE_KATEGORI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Kategori> kategoris = new ArrayList<>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()) {
                Kategori kategori = new Kategori();
                kategori.setKategoriId(cursor.getString(0));
                kategori.setKategoriNama(cursor.getString(1));
                kategoris.add(kategori);
                cursor.moveToNext();
            }
            cursor.close();
        }else{
            kategoris = null;
        }

        db.close();
        return kategoris;
    }

    public void addKategoriHandler(Kategori kategori){
        ContentValues values = new ContentValues();
        values.put(KATEGORI_ID, kategori.getKategoriId());
        values.put(KATEGORI_NAMA, kategori.getKategoriNama());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_KATEGORI, null, values);
        db.close();
    }

    public Kategori findKategoriHandler(String search){
        String query = "SELECT * FROM "+TABLE_KATEGORI+" WHERE "+KATEGORI_NAMA+" LIKE "+"'%"+search+"%' OR "+KATEGORI_ID+" LIKE "+"'%"+search+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Kategori kategori = new Kategori();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            kategori.setKategoriId(cursor.getString(0));
            kategori.setKategoriNama(cursor.getString(1));
            cursor.close();
        }else{
            kategori = null;
        }

        db.close();
        return kategori;
    }

    public boolean deleteKategoriHandler(String id){
        boolean result = false;
        String query = "SELECT * FROM "+TABLE_KATEGORI+" WHERE "+KATEGORI_ID+" = '"+id+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Kategori kategori = new Kategori();

        if (cursor.moveToFirst()){
            kategori.setKategoriId(cursor.getString(0));
            db.delete(TABLE_KATEGORI, KATEGORI_ID+"=?", new String[]{
                    String.valueOf(kategori.getKategoriId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateKategoriHandler(String id, String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KATEGORI_ID, id);
        args.put(KATEGORI_NAMA, nama);
        return db.update(TABLE_KATEGORI, args, KATEGORI_ID+"="+id, null)>0;
    }

    //TRANSAKSI

    public Transaksi loadTransaksiHandler(){
        String query = "SELECT * FROM "+TABLE_TRANSAKSI+" INNER JOIN "+TABLE_KASIR+" ON "+TRANSAKSI_ID_KASIR+" = "+KASIR_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaksi transaksi = new Transaksi();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            transaksi.setTransaksiId(cursor.getString(0));
            transaksi.setTransaksiKasir(cursor.getString(5));
            transaksi.setTransaksiWaktu(cursor.getString(2));
            transaksi.setTransaksiTotalBelanja(cursor.getString(3));
            cursor.close();
        }else{
            transaksi = null;
        }

        db.close();
        return transaksi;
    }

    public void addTransaksiHandler(Transaksi transaksi){
        ContentValues values = new ContentValues();
        values.put(TRANSAKSI_ID, transaksi.getTransaksiId());
        values.put(TRANSAKSI_ID_KASIR, transaksi.getTransaksiKasir());
        values.put(TRANSAKSI_WAKTU, transaksi.getTransaksiWaktu());
        values.put(TRANSAKSI_TOTAL_BELANJA, transaksi.getTransaksiTotalBelanja());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TRANSAKSI, null, values);
        db.close();
    }

    public Transaksi findTransaksiHandler(String search){
        String query = "SELECT * FROM "+TABLE_TRANSAKSI+" INNER JOIN "+TABLE_KASIR+" ON "+TRANSAKSI_ID_KASIR+" = "+KASIR_ID+" WHERE "+TRANSAKSI_ID+" LIKE "+"'%"+search+"%' OR "+KASIR_NAMA+" LIKE "+"'%"+search+"%' OR "+TRANSAKSI_WAKTU+" LIKE "+"'%"+search+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Transaksi transaksi = new Transaksi();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            transaksi.setTransaksiId(cursor.getString(0));
            transaksi.setTransaksiKasir(cursor.getString(5));
            transaksi.setTransaksiWaktu(cursor.getString(2));
            transaksi.setTransaksiTotalBelanja(cursor.getString(3));
            cursor.close();
        }else{
            transaksi = null;
        }

        db.close();
        return transaksi;
    }

    //DETAIL TRANSAKSI

    public DetailTransaksi loadDetailTransaksiHandler(){
        String query = "SELECT * FROM "+TABLE_DETAIL_TRANSAKSI+" INNER JOIN "+TABLE_BARANG+" ON "+DETAIL_TRANSAKSI_ID_BARANG+" = "+BARANG_ID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DetailTransaksi detailTransaksi = new DetailTransaksi();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            detailTransaksi.setDetailTransaksiId(cursor.getString(0));
            detailTransaksi.setDetailTransaksiBarang(cursor.getString(5));
            detailTransaksi.setDetailTransaksiJumlahBarang(cursor.getString(2));
            detailTransaksi.setDetailTransaksiTotalHarga(cursor.getString(3));
            cursor.close();
        }else{
            detailTransaksi = null;
        }

        db.close();
        return detailTransaksi;
    }

    public void addDetailTransaksiHandler(DetailTransaksi detailTransaksi){
        ContentValues values = new ContentValues();
        values.put(DETAIL_TRANSAKSI_ID, detailTransaksi.getDetailTransaksiId());
        values.put(DETAIL_TRANSAKSI_JUMLAH_BARANG, detailTransaksi.getDetailTransaksiJumlahBarang());
        values.put(DETAIL_TRANSAKSI_TOTAL_HARGA, detailTransaksi.getDetailTransaksiTotalHarga());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TRANSAKSI, null, values);
        db.close();
    }

    public DetailTransaksi findDetailTransaksiHandler(String search){
        String query = "SELECT * FROM "+TABLE_DETAIL_TRANSAKSI+" INNER JOIN "+TABLE_BARANG+" ON "+DETAIL_TRANSAKSI_ID_BARANG+" = "+BARANG_ID+" WHERE "+DETAIL_TRANSAKSI_ID+" LIKE "+"'%"+search+"%' OR "+BARANG_NAMA+" LIKE "+"'%"+search+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DetailTransaksi detailTransaksi = new DetailTransaksi();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            detailTransaksi.setDetailTransaksiId(cursor.getString(0));
            detailTransaksi.setDetailTransaksiBarang(cursor.getString(5));
            detailTransaksi.setDetailTransaksiJumlahBarang(cursor.getString(2));
            detailTransaksi.setDetailTransaksiTotalHarga(cursor.getString(3));
            cursor.close();
        }else{
            detailTransaksi = null;
        }

        db.close();
        return detailTransaksi;
    }
}
