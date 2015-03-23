package cn.com.jdsc;

import cn.com.jdsc.widget.ActionProcessButton;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginActivity extends Activity{
	private EditText userNameET,pwdET;
	private ActionProcessButton loginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		initView();
	}
	private void initView(){
		userNameET=(EditText) findViewById(R.id.editUserName);
		pwdET=(EditText) findViewById(R.id.editPassword);
		loginBtn=(ActionProcessButton) findViewById(R.id.btnSignIn);
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				login();
			}
		});
	}
	public void login(){
		String user=userNameET.getText().toString().trim();
		String pwd=pwdET.getText().toString().trim();
		if ("admin".equals(user)&&"123".equals(pwd)) {
			Intent intent=new Intent(this,MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else{
			loginBtn.setProgress(-1);
		}
	}
}
