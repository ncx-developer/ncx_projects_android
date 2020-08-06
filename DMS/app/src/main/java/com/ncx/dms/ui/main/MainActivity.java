package com.ncx.dms.ui.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.R;
import com.ncx.dms.core.BaseActivity;
import com.ncx.dms.data.MechanicService;
import com.ncx.dms.enums.ApiRequest;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.ResultDto;
import com.ncx.dms.remote.dto.UserDto;
import com.ncx.dms.ui.services.ServiceRecordFragment;
import com.ncx.dms.ui.services.WarrantyRegisterFragment;

import java.util.List;

import retrofit2.Response;

public class MainActivity extends BaseActivity implements CallActivityMethod, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToogle;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    TextView tvFullName;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setFullScreen();
        setKeepScreenOn();
        //setLandscape();
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        mToogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void apiResult(int requestId, Response result) {
        if (requestId == ApiRequest.GETALLDEALERS){
            ResultDto<List<DealerDto>> resultDto = (ResultDto<List<DealerDto>>)result.body();
            ((App)getApplication()).setCurrentDealers(resultDto.getResultObject());
        }
    }

    @Override
    public void apiError(int requestId, String result) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (mToogle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.warranty_register){
            setActivityTitle("Warranty Register");
            openFragment(new WarrantyRegisterFragment());
        }
        if  (item.getItemId() == R.id.service_record){
            setActivityTitle("Service Record");
            openFragment(new ServiceRecordFragment());
        }

        return false;
    }

    private void openFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.container_fragment);

        if (currentFragment == null){
            fragmentTransaction.add(R.id.container_fragment, fragment);
        }else if (currentFragment.getClass() != fragment.getClass()){
            fragmentTransaction.replace(R.id.container_fragment, fragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void initService() {

    }

    @Override
    public void findViewById() {

        tvFullName = navigationView.getHeaderView(0).findViewById(R.id.tvFullName);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initFillData() {
        MechanicService mechanicService = new MechanicService((App) getApplication(), this);
        mechanicService.getAllDealers(ApiRequest.GETALLDEALERS);

        if (app.getCurrentUser() != null) {
            tvFullName.setText(app.getCurrentUser().getFullName());
        }
    }

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public List<DealerDto> getCurrentDealers() {
        return null;
    }

    @Override
    public List<McSparePart> getMcSpareParts() {
        return null;
    }
}
