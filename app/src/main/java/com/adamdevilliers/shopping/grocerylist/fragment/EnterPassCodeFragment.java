package com.adamdevilliers.shopping.grocerylist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adamdevilliers.shopping.grocerylist.SegmentedCode;
import com.adamdevilliers.shopping.grocerylist.GrocerySharedPrefs;
import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.activity.GroceryListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnterPassCodeFragment extends Fragment {

    View rootView;

    @BindView(R.id.passcode_enter)
    SegmentedCode passcodeEntered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.enter_passcode_fragment, container, false);
        ButterKnife.bind(this, rootView);

        passcodeEntered.requestFocus();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        passcodeEntered.requestFocus();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.login)
    public void onLoginClicked(){
        if(passcodeEntered.getText().equals(GrocerySharedPrefs.getPassCode(getContext()))){
            startActivity(new Intent(getActivity(), GroceryListActivity.class));
            getActivity().finish();
        }else{
            Toast.makeText(getContext(), getString(R.string.incorrect_passcode_message),Toast.LENGTH_LONG).show();
        }
    }
}
