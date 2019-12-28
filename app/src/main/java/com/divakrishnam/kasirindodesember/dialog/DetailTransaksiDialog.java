package com.divakrishnam.kasirindodesember.dialog;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.divakrishnam.kasirindodesember.R;
import com.divakrishnam.kasirindodesember.handler.DBHandler;
import com.divakrishnam.kasirindodesember.model.Barang;
import com.divakrishnam.kasirindodesember.model.DetailTransaksi;
import com.divakrishnam.kasirindodesember.util.SharedPrefManager;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class DetailTransaksiDialog extends DialogFragment implements View.OnClickListener, ZXingScannerView.ResultHandler {

    private FrameLayout flScan;
    private EditText etId, etNama, etJumlah;
    private Button btnPlus, btnMinus, btnSimpan, btnBatal;
    private ZXingScannerView mScannerView;
    private DBHandler dbHandler;

    private int jumlah = 0;
    SharedPrefManager pref;

    Barang barang;

    public DetailTransaksiDialog() {
    }

    public static DetailTransaksiDialog newInstance(String title) {
        DetailTransaksiDialog frag = new DetailTransaksiDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_detail_transaksi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        flScan = view.findViewById(R.id.fl_scan);
        etId = view.findViewById(R.id.et_id);
        etNama = view.findViewById(R.id.et_nama);
        etJumlah = view.findViewById(R.id.et_jumlah);
        btnPlus = view.findViewById(R.id.btn_plus);
        btnMinus = view.findViewById(R.id.btn_minus);
        btnSimpan = view.findViewById(R.id.btn_simpan);
        btnBatal = view.findViewById(R.id.btn_batal);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        btnBatal.setOnClickListener(this);

        setJumlah(String.valueOf(jumlah));

        pref = SharedPrefManager.getInstance(getContext());

        initScannerView();
        dbHandler = new DBHandler(getContext(), null, null, 1);
    }

    private void initScannerView(){
        mScannerView = new ZXingScannerView(getContext());
        mScannerView.setAutoFocus(true);
        mScannerView.setResultHandler(this);
        flScan.addView(mScannerView);
    }

    @Override
    public void onStart() {
        mScannerView.startCamera();
        doRequestPermission();
        super.onStart();
        String title = getArguments().getString("title", "Judul");
        getDialog().setTitle(title);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

    @Override
    public void onClick(View view) {
        if (view == btnPlus){
            jumlah++;
            setJumlah(String.valueOf(jumlah));
        }else if (view == btnMinus){
            if (jumlah>0){
                jumlah--;
            }
            setJumlah(String.valueOf(jumlah));
        }else if (view == btnSimpan){
            simpanDetailTransaksi(jumlah);
            getDialog().dismiss();
        }else if(view == btnBatal){
            getDialog().dismiss();
        }
    }

    private void simpanDetailTransaksi(int jumlah){
        int total = jumlah * Integer.valueOf(barang.getBarangHarga());

        List<DetailTransaksi> detailTransaksis = pref.getDetailTransaksi();
        if(detailTransaksis != null){
            detailTransaksis.add(new DetailTransaksi(dbHandler.setIdTransaksiHandler(), barang.getBarangId(), barang.getBarangNama(), jumlah, String.valueOf(total)));
            pref.setDetailTransaksi(detailTransaksis);

            DetailTransaksiDialogListener listener = (DetailTransaksiDialogListener) getActivity();
            listener.onFinishDetailTransaksiDialog();
        }else {
            detailTransaksis = new ArrayList<>();
            detailTransaksis.add(new DetailTransaksi(dbHandler.setIdTransaksiHandler(), barang.getBarangId(),  barang.getBarangNama(), jumlah, String.valueOf(total)));
            pref.setDetailTransaksi(detailTransaksis);

            DetailTransaksiDialogListener listener = (DetailTransaksiDialogListener) getActivity();
            listener.onFinishDetailTransaksiDialog();
        }
    }

    private void setJumlah(String jumlah){
        etJumlah.setText(jumlah);
    }

    @Override
    public void handleResult(Result result) {
        barang = dbHandler.findBarangHandler(result.getText());
        if(barang != null){
            etId.setText(barang.getBarangId());
            etNama.setText(String.valueOf(barang.getBarangNama()));
        }
        mScannerView.resumeCameraPreview(this);
    }

    public interface DetailTransaksiDialogListener {
        void onFinishDetailTransaksiDialog();
    }
}
