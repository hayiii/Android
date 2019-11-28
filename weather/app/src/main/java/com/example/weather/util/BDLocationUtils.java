package com.example.weather.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BDLocationUtils {
    public Context context;
    public static String City = "";
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public BDLocationUtils(Context context){
        this.context = context;
    }

    public void doLocation(){
        mLocationClient = new LocationClient(context.getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //获得定位结果
            City = bdLocation.getCityCode();
        }
    }
}
