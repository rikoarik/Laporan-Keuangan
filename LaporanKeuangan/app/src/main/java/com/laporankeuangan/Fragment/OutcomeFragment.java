package com.laporankeuangan.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.laporankeuangan.Model.OutcomeModel;
import com.laporankeuangan.Model.OutcomeModel;
import com.laporankeuangan.R;
import com.laporankeuangan.ViewModel.OutcomeViewModel;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.TimeZone;


public class OutcomeFragment extends Fragment {
    private EditText mjumlah, mnotes, imgname;

    private TextView tvDateResult;
    private ImageButton btDatePicker, btGaleri, btCamera;
    private ImageView img;
    private Uri imageUri=null;
    private final int REQ_GALERI_CODE = 100;
    private final int REQ_CAMERA_CODE = 101;
    Spinner spinner;


    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseFirestore db;



    OutcomeViewModel viewModel = new OutcomeViewModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outcome, container, false);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        img = (ImageView) view.findViewById(R.id.imageout);
        btGaleri = view.findViewById(R.id.fileout);
        btCamera = view.findViewById(R.id.cameraout);
        mjumlah = (EditText) view.findViewById(R.id.saldoout);
        mnotes = (EditText) view.findViewById(R.id.notesaddout);
        tvDateResult = (TextView) view.findViewById(R.id.dateout);
        btDatePicker = (ImageButton) view.findViewById(R.id.btdateout);
        spinner = view.findViewById(R.id.kategoriout);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.kategorioutcome, R.layout.spinner_layout_color);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button btSave = view.findViewById(R.id.btsaveout);



        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvDateResult.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Pilih tanggal", Toast.LENGTH_SHORT).show();
                } else if (mjumlah.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Masukkan Amount", Toast.LENGTH_SHORT).show();
                } else if (mnotes.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Masukkan notes", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                }
            }
        });
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        tvDateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        btGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQ_GALERI_CODE);

            }
        });
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivityForResult(cameraIntent, REQ_CAMERA_CODE);
                }


            }
        });

        return view;
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth ) {

            tvDateResult.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                    + "/" + String.valueOf(year));
        }
    };
    private void uploadImage() {

        if(imageUri  != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            String filename = (String) tvDateResult.getText().toString().trim();

            StorageReference ref = storageReference.child("images/Outcome" + imageUri.getLastPathSegment());

            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri url = uri;
                                    OutcomeModel OutcomeModel = new OutcomeModel();
                                    OutcomeModel.setimgurl(url.toString());
                                    OutcomeModel.setKategori(spinner.getSelectedItem().toString());
                                    OutcomeModel.setTanggal(tvDateResult.getText().toString());
                                    OutcomeModel.setJumlah(Integer.parseInt(mjumlah.getText().toString()));
                                    OutcomeModel.setNotes(mnotes.getText().toString());
                                    viewModel.saveOutcome(getActivity(), OutcomeModel);


                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Data Outcome Tersimpan", Toast.LENGTH_LONG).show();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setTitle("Save Your Outcome");
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");



                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == REQ_GALERI_CODE && resultCode == RESULT_OK){
            if(data == null || data.getData() == null)
                return;
            try {
                imageUri= data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                img.setImageBitmap(bitmap);
            }catch (Exception e){

            }

        }else if(reqCode == REQ_CAMERA_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(bitmap);
            imageUri=getImageUri(getContext().getApplicationContext(), bitmap);
        }
    }
    private Uri getImageUri(Context context, Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),bitmap,"title", null);
        return Uri.parse(path);
    }
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(getActivity(), new String[] {  Manifest.permission.READ_EXTERNAL_STORAGE }, REQ_GALERI_CODE);
        }else if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {  Manifest.permission.CAMERA }, REQ_CAMERA_CODE);

        }
        else {
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }




}



