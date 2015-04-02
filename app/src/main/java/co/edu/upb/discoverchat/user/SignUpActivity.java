package co.edu.upb.discoverchat.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.upb.discoverchat.MainActivity;
import co.edu.upb.discoverchat.R;
import co.edu.upb.discoverchat.data.web.UserWeb;


public class SignUpActivity extends ActionBarActivity {

    ProgressDialog serverStatus;
    boolean errors = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText _email = (EditText)findViewById(R.id.etxtRegisterEmail);
        final EditText _phone = (EditText)findViewById(R.id.etxtRegisterPhone);
        final EditText _passwd = (EditText)findViewById(R.id.etxtRegisterPasswd);
        final EditText _confirmPasswd = (EditText)findViewById(R.id.etxtRegisterConfirmPasswd);


        Button send = (Button)findViewById(R.id.btnRegisterSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errors = false;
                String email = _email.getText().toString();
                String phone = _phone.getText().toString();
                String passwd = _passwd.getText().toString();
                String confirmPasswd = _confirmPasswd.getText().toString();
                prepareProgressDialog();
                validData(email, phone, passwd, confirmPasswd);
                if(!errors) {
                    registerUser(email,phone,passwd,confirmPasswd);
                }
            }
        });

        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(intent);
            }
        });
    }
    public void registerUser(String email, String phone, String passwd, String confirmPasswd){
        UserWeb web = new UserWeb();
        String googleCloudMessage ="";
        web.registerNewUser(email,phone,passwd,confirmPasswd,googleCloudMessage);
        serverStatus.cancel();
    }
    private void prepareProgressDialog() {

        serverStatus = new ProgressDialog(SignUpActivity.this);
        serverStatus.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        serverStatus.setMessage("Espere un momento");
        serverStatus.setCanceledOnTouchOutside(false);
        serverStatus.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean validData(String email,String phone, String passwd, String confirmPasswd){
        UserTools.Email emailStatus;
        UserTools.Phone phoneStatus;
        UserTools.Password passwdStatus;

        UserTools tools = new UserTools();
        emailStatus = tools.validEmail(email);
        phoneStatus = tools.validPhone(phone);
        passwdStatus = tools.ValidPasswords(passwd, confirmPasswd);

        switch (emailStatus){
            case WRONG_EMAIL:
                badEmail();
                break;
        }
        switch (phoneStatus){
            case TOO_LONG:
                badPhone(UserTools.Phone.TOO_LONG.name());
                break;
            case TOO_SHORT:
                badPhone(UserTools.Phone.TOO_SHORT.name());
                break;
        }
        switch (passwdStatus){
            case PASSWORD_TOO_SHORT:
                badPassword("La contraseña es demasiado corta");
                break;
            case PASSWORDS_NOT_MATCH:
                badPassword("Las contraseñas no coinciden");
                break;
        }
        return errors;
    }

    public void badEmail(){
        Toast.makeText(SignUpActivity.this, "El correo electrónico está mal escrito",Toast.LENGTH_LONG).show();
        applyReset();
    }
    public void badPhone(String message){
        String text = "El numero es: "+message;
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
        applyReset();
    }
    public void badPassword(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        applyReset();
    }
    public void applyReset(){
        serverStatus.cancel();
        errors = true;
    }

    // End of the main class
    public static class UserTools {
        public static enum Password {
            PASSWORDS_NOT_MATCH,
            PASSWORD_TOO_SHORT,
            OK
        };

        public static enum Email{
            WRONG_EMAIL,
            OK;
        }
        public static enum Phone{
            OK,
            TOO_LONG,
            TOO_SHORT
        }
        private static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public Password ValidPasswords(String password, String confirmPassword){
            if(!password.equals(confirmPassword))
                return Password.PASSWORDS_NOT_MATCH;
            if(password.length()<8)
                return Password.PASSWORD_TOO_SHORT;
            return Password.OK;
        }
        public Email validEmail(String email){
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches())
                return Email.OK;
            return Email.WRONG_EMAIL;
        }
        public Phone validPhone(String phone){
            phone = phone.replaceAll(" ", "");
            phone = phone.replaceAll("-", "");
            if(phone.length()<10)
                return Phone.TOO_SHORT;
            if(phone.length()>10)
                return Phone.TOO_LONG;
            return Phone.OK;
        }

    }
}
