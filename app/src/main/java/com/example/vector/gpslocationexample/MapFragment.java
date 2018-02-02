package com.example.vector.gpslocationexample;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private SupportMapFragment mapFragment;

    private GoogleMap map;

    private Button goToLocationBtn;
    private EditText longitude;
    private EditText latitude;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        longitude = rootView.findViewById(R.id.fragment_main_edtLongitude);
        latitude = rootView.findViewById(R.id.fragment_main_edtLatitude);
        goToLocationBtn = rootView.findViewById(R.id.fragment_main_btnLocation);
        goToLocationBtn.setOnClickListener(this);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onClick(View view) {
        if (!latitude.getText().toString().isEmpty() && !longitude.getText().toString().isEmpty()) {
            map.clear();//haritayı temizleme
            //Dışarıdan girilen konumu oluşturma
            LatLng myLoc = new LatLng(StringToDouble(latitude.getText().toString()), StringToDouble(longitude.getText().toString()));
            //Haritaya marker ekleme
            map.addMarker(new MarkerOptions().position(myLoc).title("My Count"));
            //Markere odaklanma
            map.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
            //Marker a yaklaşma
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
            //Klayveyi kapatma
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

//            latitude.setText("");
//            longitude.setText("");
        } else {
            Toast.makeText(getContext(), "Long veya Lat Boş Geçilemez", Toast.LENGTH_LONG).show();
        }

    }

    public double StringToDouble(String temp) {
        return Double.parseDouble(temp);
    }
}
