package robsonribeiroft.nucleus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import autentica.ValidaCPF;
import autentica.ValidaEmail;
import modelo.User;
import robsonribeiroft.nucleus.DAO.UserDAO;
import robsonribeiroft.nucleus.helper.Helper;
import robsonribeiroft.nucleus.singleton.Singleton;


public class FormularioActivity extends AppCompatActivity {

    private static final int GALLERY_IMAGE_REQUEST = 1;
    private Helper helper;
    private User user = new User();
    private String caminhoFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        helper = new Helper(FormularioActivity.this);

        if(Singleton.getInstance().currentUser != null){
            helper.preencherFormulario(Singleton.getInstance().currentUser);
        } else{
            Toast.makeText(FormularioActivity.this, "Campos com * são obrigatórios!", Toast.LENGTH_SHORT).show();
        }

        final ImageButton imageButton = (ImageButton) findViewById(R.id.escolher_foto);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selecionarFoto = new Intent();
                selecionarFoto.setType("image/*");
                selecionarFoto.setAction(Intent.ACTION_GET_CONTENT);

                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".png";
                File arquivo = new File(caminhoFoto);

                Uri localFoto = Uri.fromFile(arquivo);

                selecionarFoto.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                //startActivityForResult(Intent.createChooser(selecionarFoto, "selecionarFoto"), GALLERY_IMAGE_REQUEST);
                startActivityForResult(selecionarFoto, GALLERY_IMAGE_REQUEST);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.salvarCadastro:


                final Helper helper = new Helper(FormularioActivity.this);
                user = helper.pegaUserDoFormulario();
                UserDAO dao = new UserDAO(FormularioActivity.this);

                if (user.getNome().equals("") || user.getTelefone().equals("") || user.getEmail().equals("") || user.getCpf().equals("")){
                    Toast.makeText(FormularioActivity.this, "Preencher campos obrigatórios", Toast.LENGTH_SHORT).show();
                }
                else if (!ValidaEmail.isEmailValid(user.getEmail())){
                    Toast.makeText(FormularioActivity.this, "Email inválido", Toast.LENGTH_SHORT).show();
                }
                else if (!ValidaCPF.eValido(user.getCpf())){
                    Toast.makeText(FormularioActivity.this, "CPF inválido", Toast.LENGTH_SHORT).show();
                } else {

                    user = helper.pegaUserDoFormulario();

                    if (Singleton.getInstance().currentUser != null){
                        dao.atualizar(user);
                        dao.close();
                        onBackPressed();
                    } else {
                        if(dao.cpfCadastrado(user)){
                            Toast.makeText(FormularioActivity.this, "CPF já cadastrado!", Toast.LENGTH_SHORT).show();
                        }else {
                            dao.insere(user);
                            dao.close();
                            onBackPressed();
                        }

                    }

                }

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST){
            try{
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageView = (ImageView) findViewById(R.id.mostrafoto);
                imageView.setImageBitmap(bitmap);


                helper.carregaImagem(caminhoFoto);

            }
            catch (NullPointerException e){
                caminhoFoto = null;
            }
            catch (FileNotFoundException e) {
                Toast.makeText(FormularioActivity.this, "Foto não encontrada", Toast.LENGTH_SHORT).show();
                caminhoFoto = null;
            }
            catch (IOException e) {
                caminhoFoto = null;
            }
        }
    }

}
