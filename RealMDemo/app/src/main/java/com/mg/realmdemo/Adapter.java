package com.mg.realmdemo;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class Adapter extends RecyclerView.Adapter<Adapter.View_Holder> {

    List<getSetData> list = Collections.emptyList();
    Context activity;
    Realm realm;

    public Adapter(List<getSetData> list, Context context) {
        this.list = list;
        this.activity = context;

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_listdata, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        final getSetData products = list.get(position);
        holder.txtId.setText(String.valueOf(products.getId()));
        holder.txtCity.setText(products.getCity());
        holder.txtName.setText(products.getName());
        holder.txtMobile.setText(products.getMobile());

        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RealmResults<getSetData> result = realm.where(getSetData.class).equalTo("id",products.getId()).findAll();
                realm.beginTransaction();

                result.deleteAllFromRealm();
                notifyDataSetChanged();

                realm.commitTransaction();

                return false;
            }
        });


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(final Realm realm) {

                        Button btnInsert;
                        final EditText name,mobile,city;

                        final Dialog dialog = new Dialog(activity);
                        dialog.setContentView(R.layout.layout_update_data);

                        btnInsert=(Button)dialog.findViewById(R.id.btn_add);
                        name=(EditText)dialog.findViewById(R.id.name);
                        mobile=(EditText)dialog.findViewById(R.id.mobileno);
                        city=(EditText)dialog.findViewById(R.id.city);
                        final long id = products.getId();
                        name.setText(products.getName());
                        mobile.setText(products.getMobile());
                        city.setText(products.getCity());

                        btnInsert.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                               /* List<getSetData> cities = new ArrayList<>();
                                getSetData getSetData= new getSetData();

                                getSetData.setId(id);
                                getSetData.setName(name.getText().toString().trim());
                                getSetData.setMobile(mobile.getText().toString().trim());
                                getSetData.setCity(city.getText().toString().trim());

                                cities.add(getSetData);

                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(cities);
                                realm.commitTransaction();*/


                                getSetData toEdit = realm.where(getSetData.class)
                                        .equalTo("id", products.getId()).findFirst();
                                realm.beginTransaction();
                                toEdit.setName(name.getText().toString().trim());
                                toEdit.setMobile(mobile.getText().toString().trim());
                                toEdit.setCity(city.getText().toString().trim());
                                realm.commitTransaction();

                                notifyDataSetChanged();

                                dialog.dismiss();

                            }
                        });

                        dialog.show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        TextView txtCity,txtName,txtMobile,txtId;
        Button btnDelete,btnUpdate;
        LinearLayout mainLayout;
        public View_Holder(View itemView) {
            super(itemView);

            txtCity = (TextView) itemView.findViewById(R.id.txt_city);
            txtId = (TextView) itemView.findViewById(R.id.txt_id);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtMobile = (TextView) itemView.findViewById(R.id.txt_mobile);
            btnDelete=(Button)itemView.findViewById(R.id.btn_delete);
            btnUpdate=(Button)itemView.findViewById(R.id.btn_update);
            mainLayout=(LinearLayout)itemView.findViewById(R.id.main_layout);
            realm = Realm.getDefaultInstance();

        }
    }

    @Override
    public void onViewDetachedFromWindow(View_Holder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}



