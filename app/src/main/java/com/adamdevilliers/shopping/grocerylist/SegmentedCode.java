package com.adamdevilliers.shopping.grocerylist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SegmentedCode extends LinearLayout {

    private TextView mCode1;
    private TextView mCode2;
    private TextView mCode3;
    private TextView mCode4;
    private EditText mCodeEdit;

    public SegmentedCode(Context context) {
        this(context, null);
    }

    public SegmentedCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentedCode(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = mInflater.inflate(R.layout.segmented_code_view, this, true);

        mCode1 = view.findViewById(R.id.text1);
        mCode2 = view.findViewById(R.id.text2);
        mCode3 = view.findViewById(R.id.text3);
        mCode4 = view.findViewById(R.id.text4);
        mCodeEdit = view.findViewById(R.id.edit1);

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                getFocus();
            }
        };
        mCodeEdit.setOnClickListener(onClickListener);

        mCodeEdit.performClick();

        mCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch (charSequence.length()) {
                    case 0:
                        mCode1.setText("");
                        mCode2.setText("");
                        mCode3.setText("");
                        mCode4.setText("");
                        break;
                    case 1:
                        mCode1.setText("*");
                        mCode2.setText("");
                        mCode3.setText("");
                        mCode4.setText("");
                        break;
                    case 2:
                        mCode2.setText("*");
                        mCode3.setText("");
                        mCode4.setText("");
                        break;
                    case 3:
                        mCode3.setText("*");
                        mCode4.setText("");
                        break;
                    case 4:
                        mCode4.setText("*");
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCode1.setOnClickListener(onClickListener);
        mCode2.setOnClickListener(onClickListener);
        mCode3.setOnClickListener(onClickListener);
        mCode4.setOnClickListener(onClickListener);

        mCode1.setFocusable(false);
        mCode2.setFocusable(false);
        mCode3.setFocusable(false);
        mCode4.setFocusable(false);

        setOnClickListener(onClickListener);
    }


    public void getFocus() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mCodeEdit, InputMethodManager.SHOW_IMPLICIT);
        mCodeEdit.setFocusable(true);
        mCodeEdit.setFocusableInTouchMode(true);
        mCodeEdit.requestFocus();

    }

    public EditText getEditText() {
        return mCodeEdit;
    }

    public TextView getTextView() {
        return mCode1;
    }

    public String getText() {
        return mCodeEdit.getText().toString();
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        mCodeEdit.addTextChangedListener(textWatcher);
    }

    public void setText(String string) {
        mCodeEdit.setText(string);
    }
}
