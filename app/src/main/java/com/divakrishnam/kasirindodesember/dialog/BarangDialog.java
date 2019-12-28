package com.divakrishnam.kasirindodesember.dialog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.activity.BarangActivity;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Barang;
import com.divakrishnam.kasirindodesember.model.Kategori;
import com.google.zxing.Result;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarangDialog extends DialogFragment implements View.OnClickListener, ZXingScannerView.ResultHandler {

    private EditText etId, etNama, etStok, etHarga;
    private Spinner spKategori;
    private Button btnSimpan, btnBatal;
    private DBHandler dbHandler;
    private String[] keys;
    private FrameLayout flScan;

    private ZXingScannerView mScannerView;

    public BarangDialog() {

    }

    public static BarangDialog newInstance(String title) {
        BarangDialog frag = new BarangDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_barang, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flScan = view.findViewById(R.id.fl_scan);
        etId = view.findViewById(R.id.et_id);
        etNama = view.findViewById(R.id.et_nama);
        etHarga = view.findViewById(R.id.et_harga);
        etStok = view.findViewById(R.id.et_stok);

        spKategori = view.findViewById(R.id.sp_kategori);

        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnBatal = view.findViewById(R.id.btn_batal);
        initScannerView();

        dbHandler = new DBHandler(getContext(), null, null, 1);

        if (dbHandler.loadKategoriHandler() != null) {
            spinnerKategori();
        }

        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);
    }

    private void spinnerKategori() {
        int size = dbHandler.loadKategoriHandler().size();
        String[] values = new String[size];
        keys = new String[size];
        HashMap<String, String> map;
        int i = 0;
        map = new HashMap<>();
        for (Kategori kategori : dbHandler.loadKategoriHandler() ) {
            values[i] = kategori.getKategoriNama();
            keys[i] = kategori.getKategoriId();
            map.put(keys[i], values[i]);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);
    }

    private void simpanBarang(){
        String id = etId.getText().toString();
        String nama = etNama.getText().toString();
        String stok = etStok.getText().toString();
        String harga = etHarga.getText().toString();
        String kategori = keys[(int) spKategori.getSelectedItemId()];

        if (!id.isEmpty() && !nama.isEmpty() && !stok.isEmpty() && !harga.isEmpty()) {
            Barang barang = new Barang(id, nama, kategori, stok, harga);
            dbHandler.addBarangHandler(barang);

            BarangDialogListener listener = (BarangDialogListener) getActivity();
            listener.onFinishBarangDialog();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            simpanBarang();
            getDialog().dismiss();
        }else if (view == btnBatal){
            getDialog().dismiss();
        }
    }

    @Override
    public void onStart() {
        doRequestPermission();
        mScannerView.startCamera();
        super.onStart();
        String title = getArguments().getString("title", "Judul");
        getDialog().setTitle(title);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void handleResult(Result result) {
        etId.setText(result.getText());
        mScannerView.resumeCameraPreview(this);
    }

    private void initScannerView(){
        mScannerView = new ZXingScannerView(getContext());
        mScannerView.setAutoFocus(true);
        mScannerView.setResultHandler(this);
        flScan.addView(mScannerView);
    }

    @Override
    public void onPause() {
        mScannerView.stopCamera();
        super.onPause();
    }

    private void doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface BarangDialogListener {
        void onFinishBarangDialog();
    }
}
