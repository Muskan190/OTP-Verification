package com.example.liveasy.Verification;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liveasy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class OTPFragment extends Fragment {
    private EditText input1,input2,input3,input4,input5,input6;
    TextView request;
    private TextView code;
    private AppCompatButton verifyandctnbtn;
    private ImageView backarrow;
    private String verificationId;
    private String res;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_t_p, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        finnnd(view);
        clicks();
        res= this.getArguments().getString("mobile");
       verificationId=this.getArguments().getString("id");
       if(res!=null)
       {
           code.setText("Code is sent to "+res);
       }
       else {
           Toast.makeText(requireContext(), "num is empty", Toast.LENGTH_SHORT).show();
       }
       setUpOTPInputs();
    }
    private void setUpOTPInputs() {
        setupautoadvance(input1,input2);
        setupautoadvance(input2,input3);
        setupautoadvance(input3,input4);
        setupautoadvance(input4,input5);
        setupautoadvance(input5,input6);
    }
    private void setupautoadvance(EditText input1, EditText input2) {
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                      if(charSequence.length()==1)
                      {
                          input2.requestFocus();
                      }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void clicks() {
        //verifyandctnbtn.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace());
        backarrow.setOnClickListener(view->requireActivity().getSupportFragmentManager().popBackStack());
        verifyandctnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(input1.getText().toString().isEmpty()||input2.getText().toString().isEmpty()|| input3.getText().toString().isEmpty()||input4.getText().toString().isEmpty()||input5.getText().toString().isEmpty()||input6.getText().toString().isEmpty())
               {
                   Toast.makeText(requireContext(), "Fill all the blanks", Toast.LENGTH_SHORT).show();
               }
               String code=input1.getText().toString()+input2.getText().toString()+input3.getText().toString()+input4.getText().toString()+input5.getText().toString()+input6.getText().toString();
               if(verificationId!=null)
               {
                   //verifyandctnbtn.setVisibility(View.INVISIBLE);
                   PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(verificationId,code);
                   FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new ProfileFragment()).addToBackStack(null).commit();
                            }
                            else {
                                Toast.makeText(requireContext(), "unsuccessfull task", Toast.LENGTH_SHORT).show();
                            }
                       }
                   });
               }
            }
        });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + res, 60, TimeUnit.SECONDS, requireActivity(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                       verificationId=s;
                        Toast.makeText(requireContext(), "OTP sent again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void finnnd(View view) {
        verifyandctnbtn = view.findViewById(R.id.verifyandctnbtn);
        backarrow=view.findViewById(R.id.backarrow);
        code=view.findViewById(R.id.code);
        input1=view.findViewById(R.id.input1);
        input2=view.findViewById(R.id.input2);
        input3=view.findViewById(R.id.input3);
        input4=view.findViewById(R.id.input4);
        input5=view.findViewById(R.id.input5);
        input6=view.findViewById(R.id.input6);
        request=view.findViewById(R.id.request);
    }
}