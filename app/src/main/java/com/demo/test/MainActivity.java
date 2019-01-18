package com.demo.test;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_MAIN)) {
            return;
        }
        Uri uri = intent.getClipData().getItemAt(0).getUri();
        //        ContentResolver contentResolver = getContentResolver();
        //        Cursor c = contentResolver.query(uri, null, null, null, null);
        //        c.moveToFirst();

        //        System.out.println(getContactPhone(c));

        try {
            exportedVCF(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //获取联系人电话
    private String getContactPhone(Cursor cursor) {
        //        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts
        // .HAS_PHONE_NUMBER);
        //        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        //        //System.out.print(phoneNum);
        //        if (phoneNum > 0) {
        // 获得联系人的ID号
        int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        String contactId = cursor.getString(idColumn);
        //        // 获得联系人的电话号码的cursor;
        //        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
        //                .CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
        // " = " +
        //                contactId, null, null);
        //        if (phones.moveToFirst()) {
        //            // 遍历所有的电话号码
        //            for (; !phones.isAfterLast(); phones.moveToNext()) {
        //                int index = phones.getColumnIndex(ContactsContract.CommonDataKinds
        // .Phone.NUMBER);
        //                int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds
        // .Phone.TYPE);
        //                int phone_type = phones.getInt(typeindex);
        //                String phoneNumber = phones.getString(index);
        //                switch (phone_type) {
        //                    case 2:
        //                        phoneResult = phoneNumber;
        //                        break;
        //                }
        //            }
        //            if (!phones.isClosed()) {
        //                phones.close();
        //            }
        //        }
        try {
            exportContacts(contactId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        }
        return phoneResult;
    }


    /**
     * Exporting contacts from the phone
     */
    public void exportContacts(String contactId) throws Exception {
        String path = Environment.getExternalStorageDirectory().getPath() + "/contacts_";


        ContentResolver cr = getContentResolver();
        Cursor cur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone
                .CONTACT_ID + " = " + contactId, null, null);
        int index = cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
        //        String name = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds
        // .Phone.name);
        //        path = path + name + ".vcf";

        FileOutputStream fout = new FileOutputStream(path);
        byte[] data = new byte[1024 * 1];
        while (cur.moveToNext()) {
            String lookupKey = cur.getString(index);
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
            AssetFileDescriptor fd = this.getContentResolver().openAssetFileDescriptor(uri, "r");
            FileInputStream fin = fd.createInputStream();
            int len = -1;
            while ((len = fin.read(data)) != -1) {
                fout.write(data, 0, len);
            }
            fin.close();
        }
        fout.close();
    }


    public void exportedVCF(Uri uri) throws IOException {
        //        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI,
        // lookupKey);
        String path = Environment.getExternalStorageDirectory().getPath() + "/contacts_";

        byte[] data = new byte[1024 * 1];
        AssetFileDescriptor fd = this.getContentResolver().openAssetFileDescriptor(uri, "r");

        FileInputStream fin = fd.createInputStream();
        StringBuffer stringBuffer = new StringBuffer();
        String name = VCF_Util.importVCFFileContact(fin, stringBuffer);
        fin.close();
        name = name.replace(" ", "");
        path += name + ".vcf";
        FileOutputStream fout = new FileOutputStream(path);
        fout.write(stringBuffer.toString().getBytes());
        fout.close();
    }

    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.main_button_1:
                startActivity(new Intent(this, ShadowActivity.class));

                break;
            case R.id.main_button_print_pdf:

                startActivity(new Intent(this, ShadowActivity.class));

                break;
            case R.id.main_button_fix:

                //                startActivity(new Intent(this, FixMainActivity.class));

                break;
            case R.id.main_button_data_ri:

                startActivity(new Intent(this, DataCalendarActivity.class));

                break;
            case R.id.main_button_float_window:

                startActivity(new Intent(this, FloatWindowActivity.class));

                break;

        }

    }

}