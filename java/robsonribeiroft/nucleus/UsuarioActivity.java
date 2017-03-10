package robsonribeiroft.nucleus;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.LinearLayout;

import robsonribeiroft.nucleus.helper.Helper;
import robsonribeiroft.nucleus.singleton.Singleton;

public class UsuarioActivity extends AppCompatActivity {

    private LinearLayout nome;
    private LinearLayout telefone;
    private LinearLayout website;
    private LinearLayout email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        Helper helper = new Helper(UsuarioActivity.this);

        nome = (LinearLayout) findViewById(R.id.linearNome);
        telefone = (LinearLayout) findViewById(R.id.linearTelefone);
        website = (LinearLayout) findViewById(R.id.linearWebsite);
        email = (LinearLayout) findViewById(R.id.linearEmail);

        registerForContextMenu(nome);
        registerForContextMenu(telefone);
        registerForContextMenu(website);
        registerForContextMenu(email);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v == telefone) {
            Intent vaiParaTelaDeDiscagem = new Intent(Intent.ACTION_DIAL);
            Uri userTelefone = Uri.parse("tel:" + Singleton.getInstance().currentUser.getPhone());
            vaiParaTelaDeDiscagem.setData(userTelefone);
            startActivity(vaiParaTelaDeDiscagem);
        }
        else if (v == website){
            if (Singleton.getInstance().currentUser.getUrl() != null && Singleton.getInstance().currentUser.getUrl() != ""){
                Intent vaiParaWebsite = new Intent(Intent.ACTION_VIEW);
                Uri userWebsite = Uri.parse("http://" + Singleton.getInstance().currentUser.getUrl());
                vaiParaWebsite.setData(userWebsite);
                startActivity(vaiParaWebsite);
            }

        }
        else if (v == email){
                Intent vaiParaEmail = new Intent(Intent.ACTION_SENDTO);
                Uri userEmail = Uri.parse("mailto:" + Singleton.getInstance().currentUser.getEmail());
                vaiParaEmail.setData(userEmail);
                startActivity(vaiParaEmail);
        }
    }
}
