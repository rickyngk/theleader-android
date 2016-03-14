package com.theleader.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.theleader.app.R;
import com.theleader.app.customview.ContactListItem;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    ContactListAdapter dataAdapter = null;
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
                finishWithResult();
            }
        });

        displayListView();
    }

    public void finishWithResult() {
        Bundle conData = new Bundle();
        ArrayList<ContactInfo> contactList = dataAdapter.getContactList();
        String delim = "";
        StringBuilder sb = new StringBuilder();
        for (ContactInfo c:contactList) {
            if (c.selected) {
                sb.append(delim);
                sb.append(c.getName());
                sb.append("|");
                sb.append(c.getName());
                delim = "\n";
            }
        }

        conData.putString("result", sb.toString());
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void displayListView() {
        ArrayList<ContactInfo> contactList = new ArrayList<ContactInfo>();
        ContactInfo contact = new ContactInfo("AFG","Afghanistan",false);
        contactList.add(contact);
        contact = new ContactInfo("ALB","Albania",true);
        contactList.add(contact);
        contact = new ContactInfo("DZA","Algeria",false);
        contactList.add(contact);
        contact = new ContactInfo("ASM","American Samoa",true);
        contactList.add(contact);
        contact = new ContactInfo("AND","Andorra",true);
        contactList.add(contact);
        contact = new ContactInfo("AGO","Angola",false);
        contactList.add(contact);
        contact = new ContactInfo("AIA","Anguilla",false);
        contactList.add(contact);

        dataAdapter = new ContactListAdapter(this, contactList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);

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

}
