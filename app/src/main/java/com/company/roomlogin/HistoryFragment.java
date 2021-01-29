package com.company.roomlogin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.roomlogin.databinding.FragmentHistoryBinding;
import com.company.roomlogin.databinding.FragmentMapsBinding;
import com.company.roomlogin.dummy.DummyContent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramotion.foldingcell.FoldingCell;

/**
 * A fragment representing a list of Items.
 */
public class HistoryFragment extends Fragment {


    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryFragment() {
    }
    LocationManager locationManager;
    String latitude, longitude;
    double lat = 41.457633f;
    double longi = 2.199405f;

    private static final String TAG = MapsFragment.class.getSimpleName();

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (lat > 0) {

                try {


                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = googleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    requireContext(), R.raw.mapstyle));

                    if (!success) {
                        Log.e(TAG, "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e(TAG, "Can't find style. Error: ", e);
                }

                LatLng sydney = new LatLng(lat, longi);
                googleMap.addMarker(new MarkerOptions().position(sydney).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.meiconmap)).title("Este eres tu!"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.4572703, 2.1998943)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusrojo)).title("Este eres tu!"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.45864029494172, 2.19965766341113)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusrojo)).title("Este eres tu!"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.45880110579676, 2.1986491529034415)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusrojo)).title("Este eres tu!"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.457763, 2.1996795)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusamarillo)).title("Es-Positivo"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.4581813, 2.1991225)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusverde)).title("Negativo"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.4581813, 2.1991225)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusverde)).title("Negativo"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.457616, 2.198861)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusverde)).title("Negativo"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.45826238786283, 2.1997649517630116)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusverde)).title("Negativo"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.45811765690835, 2.1988476363409166)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusamarillo)).title("Negativo"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(41.45816992090185, 2.2002799358520435)).icon(bitmapDescriptorFromVector(getActivity(), R.drawable.iconvirusamarillo)).title("Negativo"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String locAddress = marker.getTitle();

                        BottomModalFragment bottomSheet = new BottomModalFragment();
                        bottomSheet.show(getFragmentManager(),
                                "ModalBottomSheet");
                        return true;
                    }
                });
                LatLng coordinate = new LatLng(lat, longi); //Store these lat lng values somewhere. These should be constant.
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        coordinate, 17);
                googleMap.animateCamera(location);

            }

        }
    };


    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HistoryFragment newInstance(int columnCount) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
        }


        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapHisory);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


        return view;
    }
}