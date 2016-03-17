package com.theleader.app.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.theleader.app.R;
import com.theleader.app.customview.ContactListItem;

import java.util.ArrayList;

import R.helper.BaseActivity;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactListActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ContactListAdapter dataAdapter = null;

    @Bind(R.id.progressBar)
    View loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWithResult(true);
            }
        });

        ButterKnife.bind(this);

        loadingBar.setVisibility(View.VISIBLE);

        askForPermission(new String[]{"android.permission.READ_CONTACTS"}, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    finishWithResult(false);
                } else {
                    getLoaderManager().initLoader(0, null, ContactListActivity.this);
                }
            }
        });
    }

    public void finishWithResult(boolean success) {
        if (success) {
            Bundle conData = new Bundle();
            ArrayList<ContactInfo> contactList = dataAdapter.getContactList();
            String delim = "";
            StringBuilder sb = new StringBuilder();
            for (ContactInfo c : contactList) {
                if (c.selected) {
                    sb.append(delim);
                    sb.append(c.getName());
                    sb.append("|");
                    sb.append(c.getEmail());
                    delim = "\n";
                }
            }

            conData.putString("result", sb.toString());
            Intent intent = new Intent();
            intent.putExtras(conData);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Bundle conData = new Bundle();
            conData.putString("result", "");
            Intent intent = new Intent();
            intent.putExtras(conData);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    static class ContactInfo {
        String name;
        String email;
        boolean selected = false;

        public ContactInfo(String name, String email, boolean selected) {
            this.name = name;
            this.email = email;
            this.selected = selected;
        }

        public void setSelected(Boolean b) {
            selected = b;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    private class ContactListAdapter extends ArrayAdapter<ContactInfo> {

        private ArrayList<ContactInfo> contactList;

        public ArrayList<ContactInfo>getContactList() {
            return contactList;
        }

        public ContactListAdapter(Context context, ArrayList<ContactInfo> contactList) {
            super(context, 0, contactList);
            this.contactList = new ArrayList<ContactInfo>();
            this.contactList.addAll(contactList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ContactListItem(getContext());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactInfo c = contactList.get(position);
                        c.setSelected(!c.selected);
                        v.setSelected(c.selected);
                    }
                });
            }
            ContactListItem v = (ContactListItem)convertView;
            ContactInfo c = contactList.get(position);
            v.setData(c.getName(), c.getEmail(), c.isSelected());
            return convertView;

        }

    }


    //------------------------------------------------------
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME,
    };

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        return new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Put the result Cursor in the adapter for the ListView
        //mCursorAdapter.swapCursor(cursor);

        ArrayList<ContactInfo> contactList = new ArrayList<ContactInfo>();
        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(2);
                String email = "";

                Cursor emailCur = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id},null);
                if (emailCur != null) {
                    if (emailCur.moveToFirst()) {
                        email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    emailCur.close();
                }
                if (!email.isEmpty()) {
                    ContactInfo contact = new ContactInfo(name, email, false);
                    contactList.add(contact);
                }
                dataAdapter = new ContactListAdapter(this, contactList);
                ListView listView = (ListView) findViewById(R.id.listView1);
                listView.setAdapter(dataAdapter);
            } while(cursor.moveToNext());
        }
        setTimeout(new Runnable() {
            @Override
            public void run() {
                loadingBar.setVisibility(View.GONE);
            }
        });

        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        //mCursorAdapter.swapCursor(null);
    }
}
