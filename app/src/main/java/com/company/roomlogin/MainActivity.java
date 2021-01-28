package com.company.roomlogin;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.company.roomlogin.databinding.ActivityMainBinding;
import com.company.roomlogin.databinding.DrawerHeaderBinding;
import com.company.roomlogin.model.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    PerfilFragment perfilFragment = new PerfilFragment();
    MapsFragment mapsFragment = new MapsFragment();

    HistoryFragment historyFragment = new HistoryFragment();
    ActivityMainBinding binding;

    DrawerHeaderBinding drawerHeaderBinding;

    AutenticacionViewModel autenticacionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        drawerHeaderBinding = DrawerHeaderBinding.bind(binding.navView.getHeaderView(0));

        autenticacionViewModel = new ViewModelProvider(this).get(AutenticacionViewModel.class);

        setSupportActionBar(binding.toolbar);



        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.mapsFragment
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();


        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);




        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                Log.e("Navegando hacia: ", (String) destination.getLabel());

                if (destination.getId() == R.id.iniciarSesionFragment
                || destination.getId() == R.id.registroFragment
                || destination.getId() == R.id.splashFragment) {
                    binding.navView.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                    binding.bottomAppBar.setVisibility(View.GONE);
//                    binding.fab.setVisibility(View.GONE);
                } else {
                    binding.toolbar.setVisibility(View.VISIBLE);
                    binding.navView.setVisibility(View.VISIBLE);
                    binding.bottomAppBar.setVisibility(View.VISIBLE);
//                    binding.fab.setVisibility(View.VISIBLE);

                }


                if ( destination.getId() == R.id.mapsFragment) {
                    binding.navView.setVisibility(View.GONE);
                    binding.toolbar.setVisibility(View.GONE);
                }
            }
        });

        autenticacionViewModel.usuarioAutenticado.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null){
                    drawerHeaderBinding.username.setText(usuario.username);
                }
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomAppBar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener
            navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(
                @NonNull MenuItem item)
        {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.mapsFragment:
                    selectedFragment = new MapsFragment();
                    break;
                case R.id.perfilFragment:
                    selectedFragment = new PerfilFragment();
                    break;

                case R.id.historyFragment:
                    selectedFragment = new HistoryFragment();
                    break;

                case R.id.infoFragment:
                    selectedFragment = new InfoFragment();
                    break;
            }
            // It will help to replace the one fragment to other.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.nav_host_fragment,
                            selectedFragment)
                    .commit();
            return true;
        }
    };



}


