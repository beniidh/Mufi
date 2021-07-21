package com.c.dompetabata.TopUpSaldoku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.PengajuanLimit.PengajuanDompet;
import com.c.dompetabata.PengajuanLimit.SendPengajuan;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModalPinTopUpSaldoku extends BottomSheetDialogFragment {

    GpsTracker gpsTracker;
    PengajuanDompet pengajuanDompet;
    PinEditText pinpengajuan;
    TextView idCancelPengajuan;
    Button idPinPengajuanButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_pinpengajuan, container, false);
        getLocation();

        pinpengajuan = v.findViewById(R.id.pinpengajuan);
        idCancelPengajuan = v.findViewById(R.id.idCancelPengajuan);
        idPinPengajuanButton = v.findViewById(R.id.idPinPengajuanButton);
        idCancelPengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        idPinPengajuanButton.setOnClickListener(v1 -> {

            if (pinpengajuan.length() < 6) {
                StyleableToast.makeText(getContext(), "Lengkapi PIN", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            } else {

                String pinn = utils.hmacSha(pinpengajuan.getText().toString());

                String jumlahpengajuan = Preference.getSaldoku(getContext());
                double pengajuan = Double.valueOf(jumlahpengajuan);
                ajukanlimitt(pinn, getMacAddress(), getIPaddress(), getUserAgent(), gpsTracker.getLatitude(), gpsTracker.getLongitude(), pengajuan);
            }
        });

        return v;

    }

    private void ajukanlimitt(String pin, String mac_address, String ip_address, String user_agent, double latitude, double longitude, double amount) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getContext());

        ReqSaldoku reqSaldoku = new ReqSaldoku(pin, mac_address, ip_address, user_agent, latitude, longitude, amount);

        Call<ReqSaldoku> call = api.AddRequestSaldoku(token, reqSaldoku);
        call.enqueue(new Callback<ReqSaldoku>() {
            @Override
            public void onResponse(Call<ReqSaldoku> call, Response<ReqSaldoku> response) {
                String code = response.body().getCode();

                if (code.equals("200")) {
                    String kode = getArguments().getString("kode");

                    if(kode.equals("bank")){
                        StyleableToast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast3).show();
                        dismiss();

                    }else {
                        StyleableToast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast3).show();
                        BayarSales.a.finish();
                        topup_saldoku_activity.b.finish();
                        dismiss();
                    }

                } else {
                    StyleableToast.makeText(getContext(), "Pin anda salah", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<ReqSaldoku> call, Throwable t) {
                StyleableToast.makeText(getContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            }
        });

    }

    private String getUserAgent() {

        String ua = new WebView(getContext()).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }

    private String getMacAddress() {
        String MAC = utils.getMACAddress("wlan0");//phone if pc use eth0 if phone wlan0
        return MAC;

    }

    public void getLocation() {
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

}