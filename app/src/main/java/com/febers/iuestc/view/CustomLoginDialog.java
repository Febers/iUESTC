package com.febers.iuestc.view;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.febers.iuestc.R;

public class CustomLoginDialog extends AlertDialog{

    private Context context;
    private TextInputEditText tieUserId, tieUserPw;
    private TextView tvTitle;
    private AlertDialog dialog;
    private View view;
    private Button btCancel, btEnter;
    private OnLoginListener loginListener = null;
    private String stId = "";
    private String stPw = "";

    public CustomLoginDialog(Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        tvTitle = view.findViewById(R.id.tv_dialog_title);
        tieUserId = view.findViewById(R.id.tie_user_id);
        tieUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stId = s.toString();
            }
        });
        tieUserPw = view.findViewById(R.id.tie_user_pw);
        tieUserPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stPw = s.toString();
            }
        });
        btEnter = view.findViewById(R.id.bt_dialog_login_enter);
        btCancel = view.findViewById(R.id.bt_dialog_login_cancel);
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.onClick(view);
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.onClick(view);
            }
        });
        dialog.setView(view);
    }

    public String getStId() {
        return stId;
    }

    public String getStPw() {
        return stPw;
    }

    public TextInputEditText getTieUserId() {
        return tieUserId;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setShowTitle(Boolean show) {
        if (show) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.INVISIBLE);
        }
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.dismiss();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    //定义点击监听接口
    public interface OnLoginListener {
        void onClick(View v);
    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        loginListener = onLoginListener;
    }
}
