package com.example.phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SymbolTable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    RecyclerContactAdapter adapter;

ArrayList<ContactModel> arrContacts = new ArrayList<ContactModel> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        RecyclerView recyclerView = findViewById ( R.id.recyclerContact );
        checkPermission (  );

        recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );

          adapter = new RecyclerContactAdapter ( this,arrContacts );
recyclerView.setAdapter ( adapter );
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission ( MainActivity.this, Manifest.permission.READ_CONTACTS )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions ( MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},100 );
        }
        else
        {
            getContactList();
        }
    }

    private void getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";
        Cursor cursor = getContentResolver ().query ( uri,null,null,null ,sort);
        if (cursor.getCount ()>0)
        {
            while (cursor.moveToNext (  )) {
                @SuppressLint("Range") String id = cursor.getString ( cursor.getColumnIndex ( ContactsContract.Contacts._ID ) );
                 @SuppressLint("Range") String name = cursor.getString ( cursor.getColumnIndex ( ContactsContract.Contacts.DISPLAY_NAME ) );
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID +"=";
                Cursor phoneCursor = getContentResolver ().query( uriPhone, null, id, null, selection);


                if (phoneCursor.moveToNext ()) {
                    @SuppressLint("Range") String number = phoneCursor.getString ( phoneCursor.getColumnIndex (ContactsContract.CommonDataKinds.Phone.NUMBER) );


       ContactModel model = new ContactModel();
                    model.setName ( name );
                    model.setNumber ( number);
                    arrContacts.add ( model);
                   phoneCursor.close ();


                }}

              cursor.close ();
            }

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        if (requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            getContactList ();
        }
        else{
            Toast.makeText ( MainActivity.this, "permission denied", Toast.LENGTH_SHORT ).show ();
        }
    }
}