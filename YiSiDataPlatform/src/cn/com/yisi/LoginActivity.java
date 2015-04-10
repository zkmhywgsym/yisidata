package cn.com.yisi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import cn.com.yisi.util.Constants;
import cn.com.yisi.util.ExitManager;
import cn.com.yisi.util.MD5Utils;
import cn.com.yisi.util.WebHelper;
import cn.com.yisi.widget.ActionProcessButton;
import cn.com.ysdp.R;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText userNameET, pwdET;
	private ActionProcessButton loginBtn;
	private CheckBox cbAuto, cbRem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_layout);
		ExitManager.getInstance().addActivity(this);
		initView();
		login();
	}

	private void initView() {
		userNameET = (EditText) findViewById(R.id.editUserName);
		userNameET.setOnClickListener(this);
		pwdET = (EditText) findViewById(R.id.editPassword);
		pwdET.setOnClickListener(this);
		cbAuto = (CheckBox) findViewById(R.id.auto_login_check);
		cbRem = (CheckBox) findViewById(R.id.remember_pwd_check);
		cbAuto.setOnCheckedChangeListener(new Checked());
		cbRem.setOnCheckedChangeListener(new Checked());
		loginBtn = (ActionProcessButton) findViewById(R.id.bt_login);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				login();
			}
		});
		checkBtn();
	}

	public void login() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String user = userNameET.getText().toString().trim();
		String pwd = pwdET.getText().toString().trim();
		// params.add(new BasicNameValuePair("option", "login"));
		params.add(new BasicNameValuePair("loginName", user));
		params.add(new BasicNameValuePair("loginPwd", MD5Utils.Md5(pwd)));
		AsyncTask<List<NameValuePair>, Integer, String> task = new AsyncTask<List<NameValuePair>, Integer, String>() {

			@Override
			protected void onPreExecute() {
				YisiApp.showProgressDialog(LoginActivity.this, "登陆中……",
						"正在登陆中，请稍候……");
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(List<NameValuePair>... params) {
				WebHelper<String> webHelper = new WebHelper<String>();
				String result = webHelper.getResult(Constants.URL_LOGIN,
						params[0]);
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				if (result == null || "".equals(result.trim())) {
					loginBtn.setText("连接失败");
				} else {
					result = JSONObject.parseObject(result).getString(
							"loginStatus");
					if (Constants.LOGIN_STATUS_SUCCESS.equals(result)) {
						loginSuccess();
						loginBtn.setProgress(100);
					} else if (Constants.LOGIN_STATUS_OTHER.equals(result)) {
						loginBtn.setText("未知错误");
					} else {
						loginBtn.setProgress(-1);
					}
				}
				YisiApp.disMissProgressDialog();
				super.onPostExecute(result);
			}

		};
		task.execute(params);
	}

	@Override
	protected void onResume() {
		if (loginBtn != null) {
			loginBtn.setProgress(0);
		}
		super.onResume();
	}

	public void loginSuccess() {
		saveValue();
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	// 保存登陆数据
	private void saveValue() {
		YisiApp.setValue(Constants.LOGIN_REPWD, cbRem.isChecked() + "");
		YisiApp.setValue(Constants.LOGIN_AUTO, cbAuto.isChecked() + "");
		if (cbRem.isChecked()) {
			YisiApp.setValue(Constants.LOGIN_ACCOUNT, userNameET.getText()
					.toString().trim());
			YisiApp.setValue(Constants.LOGIN_PWD, pwdET.getText().toString()
					.trim());
		} else {
			YisiApp.setValue(Constants.LOGIN_ACCOUNT, "");
			YisiApp.setValue(Constants.LOGIN_PWD, "");
		}
	}

	// 检查自动登陆
	private void checkBtn() {
		if (Boolean.valueOf(YisiApp.getValue(Constants.LOGIN_REPWD, "false"))) {
			cbRem.setChecked(true);
			userNameET.setText(YisiApp.getValue(Constants.LOGIN_ACCOUNT, ""));
			pwdET.setText(YisiApp.getValue(Constants.LOGIN_PWD, ""));
		} else {
			cbRem.setChecked(false);
			cbAuto.setChecked(false);
			userNameET.setText("");
			pwdET.setText("");
		}
		if (Boolean.valueOf(YisiApp.getValue(Constants.LOGIN_AUTO, "false"))) {
			cbAuto.setChecked(true);
			cbRem.setChecked(true);
		} else {
			cbAuto.setChecked(false);
		}
	}

	/** 自动登陆和记住密码想对应 */
	private class Checked implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (buttonView.equals(cbRem) && isChecked == false) {
				cbAuto.setChecked(false);
			} else if (buttonView.equals(cbAuto) && isChecked == true) {
				cbRem.setChecked(true);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editUserName:
		case R.id.editPassword:
			loginBtn.setProgress(0);
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		ExitManager.getInstance().exit();
		super.onBackPressed();
	}
}
