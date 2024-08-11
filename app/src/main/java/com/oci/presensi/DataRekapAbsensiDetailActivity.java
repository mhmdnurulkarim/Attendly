package com.oci.presensi;

import static com.oci.presensi.util.Utils.getPeriode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.oci.presensi.adapter.AdapterDataRekapAbsensiDetail;
import com.oci.presensi.databinding.ActivityDataRekapAbsensiDetailBinding;
import com.oci.presensi.helper.DataHelper;
import com.oci.presensi.model.ModelAbsensi;
import com.oci.presensi.model.ModelAbsensiFetch;
import com.oci.presensi.model.ModelAkun;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRekapAbsensiDetailActivity extends AppCompatActivity {

    private ActivityDataRekapAbsensiDetailBinding binding;
    private DataHelper dbHelper;
    private List<ModelAbsensi> listAbsensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataRekapAbsensiDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        int id = intent.getIntExtra("idUser", 0);

        dbHelper = new DataHelper(this);
        listAbsensi = dbHelper.getAllAbsensiByIdUser(id);
        ModelAkun akun = dbHelper.getAkun(id);

        setTextViews(akun);

        if (!listAbsensi.isEmpty()) {
            setupRecyclerView();
        }

        binding.print.setOnClickListener(v -> {
            if (listAbsensi == null || listAbsensi.isEmpty()) {
                Toast.makeText(this, "Tidak ada data absensi yang tersedia.", Toast.LENGTH_SHORT).show();
            } else {
                savePdf(akun, listAbsensi);
            }
        });

        setupBackPressedHandler();
    }

    private void setTextViews(ModelAkun akun) {
        binding.txtPeriode.setText("Periode " + getPeriode());
        binding.txtIDKaryawan.setText("ID Karyawan : " + akun.getIdUser());
        binding.txtNamaKaryawan.setText("Nama Karyawan : " + akun.getNama());
        binding.txtNikKaryawan.setText("NIK Karyawan : " + akun.getNik());
        binding.txtDivisiKaryawan.setText("Divisi Karyawan : " + akun.getDivisi());
        binding.txtRoleKaryawan.setText("Role Karyawan : " + akun.getId_role());
    }

    private void setupRecyclerView() {
        List<ModelAbsensiFetch> processedData = prepareAbsensiData(listAbsensi);
        AdapterDataRekapAbsensiDetail itemList = new AdapterDataRekapAbsensiDetail(processedData, dbHelper);
        binding.rvDataRekapAbsensiDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDataRekapAbsensiDetail.setAdapter(itemList);
    }

    private List<ModelAbsensiFetch> prepareAbsensiData(List<ModelAbsensi> rawAbsensiList) {
        Map<Integer, ModelAbsensiFetch> absensiMap = new HashMap<>();

        for (ModelAbsensi absensi : rawAbsensiList) {
            int userId = absensi.getId_user();
            ModelAbsensiFetch absensiFetch = absensiMap.get(userId);

            if (absensiFetch == null) {
                if (absensi.getKeterangan().equalsIgnoreCase("DATANG")) {
                    absensiFetch = new ModelAbsensiFetch(userId, absensi.getTimestamp(), null);
                } else if (absensi.getKeterangan().equalsIgnoreCase("PULANG")) {
                    absensiFetch = new ModelAbsensiFetch(userId, null, absensi.getTimestamp());
                }
                absensiMap.put(userId, absensiFetch);
            } else {
                if (absensi.getKeterangan().equalsIgnoreCase("DATANG")) {
                    absensiFetch = new ModelAbsensiFetch(userId, absensi.getTimestamp(), absensiFetch.getPulangTimestamp());
                } else if (absensi.getKeterangan().equalsIgnoreCase("PULANG")) {
                    absensiFetch = new ModelAbsensiFetch(userId, absensiFetch.getDatangTimestamp(), absensi.getTimestamp());
                }
                absensiMap.put(userId, absensiFetch);
            }
        }

        return new ArrayList<>(absensiMap.values());
    }

    private void savePdf(ModelAkun akun, List<ModelAbsensi> listAbsensi) {
        try {
            File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            String fileName = "RekapAbsensi_" + akun.getNama() + ".pdf";
            File file = new File(downloadDir, fileName);

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Rekap Absensi"));
            document.add(new Paragraph("Nama: " + akun.getNama()));
            document.add(new Paragraph("ID: " + akun.getIdUser()));
            document.add(new Paragraph("Divisi: " + akun.getDivisi()));
            document.add(new Paragraph("Periode: " + getPeriode()));
            document.add(new Paragraph(" "));

            for (ModelAbsensi absensi : listAbsensi) {
                document.add(new Paragraph("Tanggal: " + absensi.getTimestamp() +
                        " - Keterangan: " + absensi.getKeterangan()));
            }

            document.close();
            Toast.makeText(this, "PDF disimpan di " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal membuat PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void setupBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }
}