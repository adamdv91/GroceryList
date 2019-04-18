package com.adamdevilliers.shopping.grocerylist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.adamdevilliers.shopping.grocerylist.GrocerySharedPrefs;
import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.activity.GroceryListActivity;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePassCodeFragment extends Fragment implements Validator.ValidationListener {

    @Length(min = 4, message = "PassCode must be 4 characters long")
    @BindView(R.id.passcode_edittext)
    EditText passCodeEditText;

    Validator validator;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.create_passcode_fragment, container, false);

        ButterKnife.bind(this, rootView);

        validator = new Validator(this);
        validator.setValidationListener(this);

        return rootView;
    }

    @OnClick(R.id.button_passcode_submit)
    public void onPassCodeSubmitClicked(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        Pattern mPattern = Pattern.compile(getString(R.string.pattern));

        Matcher matcher = mPattern.matcher(passCodeEditText.getText().toString());
        if(!matcher.find())
        {
            GrocerySharedPrefs.setPassCode(passCodeEditText.getText().toString(), getActivity());
            startActivity(new Intent(getContext(), GroceryListActivity.class));
            getActivity().finish();
        }else{
            passCodeEditText.setError(getString(R.string.insecure_passcode));
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }
}
