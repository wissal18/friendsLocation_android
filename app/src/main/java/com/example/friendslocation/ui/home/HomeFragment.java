package com.example.friendslocation.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.friendslocation.JSONParser;
import com.example.friendslocation.MyLocation;
import com.example.friendslocation.R;
import com.example.friendslocation.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    ArrayList<MyLocation> data=new ArrayList<MyLocation>();
    ArrayAdapter ad;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        ad=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,data);
        binding.lvPositions.setAdapter(ad);
        
        
        

       // final TextView textView = binding.textHome;
        binding.btnDownload.setOnClickListener(view -> {
            Telechargement t=new Telechargement(getActivity());
            t.execute();
        });
       
        return root;
    }
    class Telechargement extends AsyncTask {
        Context context;
        AlertDialog alert;

        public Telechargement(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Téléchargement:");
            builder.setMessage("Veuillez patientez...");
            alert=builder.create();
            alert.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            ad.notifyDataSetChanged();
            alert.dismiss();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String ip="192.168.1.126:3011";
            String url="http://"+ip+"/servicephp/getAll.php";

            JSONParser parser=new JSONParser();
            JSONObject response=parser.makeHttpRequest(url,"GET",null);
            try {
                int success=response.getInt("success");
                if(success==0){
                    String msg= response.getString("message");

                }else{
                    data.clear();
                    JSONArray tableau=response.getJSONArray("Ami");
                    for(int i=0;i<tableau.length();i++){
                        JSONObject ligne=tableau.getJSONObject(i);
                        String nom=ligne.getString("nom");
                        String numero=ligne.getString("numero");
                        String longitude=ligne.getString("longitude");
                        String latitude=ligne.getString("latitude");
                        data.add(new MyLocation(nom,numero,longitude,latitude));
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}