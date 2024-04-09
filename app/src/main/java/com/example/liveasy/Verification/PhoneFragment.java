package com.example.liveasy.Verification;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.liveasy.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {
    private ImageView cross;
    private EditText mobilenumber;
    private AppCompatButton ctnbtn;
    private String verificationcode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cross=view.findViewById(R.id.cross);
        mobilenumber=view.findViewById(R.id.mobilenumber);
        ctnbtn=view.findViewById(R.id.ctnbtn);
        onclicklisteners();

    }
    private void onclicklisteners() {
        cross.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });
        ctnbtn.setOnClickListener(view -> {
            String mobile=mobilenumber.getText().toString();
            if(!mobile.isEmpty())
            {
                //ctnbtn.setVisibility(View.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile, 60, TimeUnit.SECONDS, requireActivity(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        ctnbtn.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        ctnbtn.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        ctnbtn.setVisibility(View.VISIBLE);
                        OTPFragment otpFragment=new OTPFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("mobile",mobilenumber.getText().toString());
                        bundle.putString("id",s);
                        otpFragment.setArguments(bundle);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(android.R.id.content,otpFragment)
                                .addToBackStack(null).commit();
                    }
                });
            }
            else {
                Toast.makeText(requireContext(), "Enter Mobile number Please", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}