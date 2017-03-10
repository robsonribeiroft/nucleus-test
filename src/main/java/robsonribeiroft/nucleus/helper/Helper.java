package robsonribeiroft.nucleus.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import modelo.User;
import robsonribeiroft.nucleus.FormularioActivity;
import robsonribeiroft.nucleus.R;
import robsonribeiroft.nucleus.UsuarioActivity;
import robsonribeiroft.nucleus.singleton.Singleton;

public class Helper {

    private final User user;
    private ImageView campoFoto;
    private EditText campoNome;
    private EditText campoCpf;
    private EditText campoTelefone;
    private EditText campoEmail;
    private EditText campoCompanhia;
    private EditText campoAniversario;
    private EditText campoLocalizacao;
    private EditText campoWebsite;

    private TextView TVnome;
    private TextView TVcpf;
    private TextView TVtelefone;
    private TextView TVemail;
    private TextView TVcompanhia;
    private TextView TVaniversario;
    private TextView TVlocalizacao;
    private TextView TVwebsite;


    public Helper(FormularioActivity activity){
        user = new User();
        campoNome = (EditText) activity.findViewById(R.id.name);
        campoCpf = (EditText) activity.findViewById(R.id.cpf);
        campoTelefone = (EditText) activity.findViewById(R.id.phone);
        campoEmail = (EditText) activity.findViewById(R.id.email);
        campoCompanhia = (EditText) activity.findViewById(R.id.company);
        campoAniversario = (EditText) activity.findViewById(R.id.birthdate);
        campoLocalizacao = (EditText) activity.findViewById(R.id.location);
        campoWebsite = (EditText) activity.findViewById(R.id.url);
        campoFoto = (ImageView) activity.findViewById(R.id.mostrafoto);
    }

    public Helper(UsuarioActivity activity) {
        user = Singleton.getInstance().currentUser;
        TVnome = (TextView) activity.findViewById(R.id.TVnome);
        TVcpf = (TextView) activity.findViewById(R.id.TVcpf);
        TVtelefone = (TextView) activity.findViewById(R.id.TVtelefone);
        TVemail = (TextView) activity.findViewById(R.id.TVemail);
        TVcompanhia = (TextView) activity.findViewById(R.id.TVcompanhia);
        TVaniversario = (TextView) activity.findViewById(R.id.TVaniversario);
        TVlocalizacao = (TextView) activity.findViewById(R.id.TVlocalizacao);
        TVwebsite = (TextView) activity.findViewById(R.id.TVwebsite);

        TVnome.setText(user.getName());
        TVcpf.setText(user.getCpf());
        TVtelefone.setText(user.getPhone());
        TVemail.setText(user.getEmail());
        TVcompanhia.setText(user.getCompany());
        TVaniversario.setText(user.getBirthdate());
        TVlocalizacao.setText(user.getLocation());
        TVwebsite.setText(user.getUrl());
    }



    public User pegaUserDoFormulario(){
        user.setName(campoNome.getText().toString());
        user.setCpf(campoCpf.getText().toString().replaceAll("[.-]", ""));
        user.setPhone(campoTelefone.getText().toString());
        user.setEmail(campoEmail.getText().toString());
        user.setCompany(campoCompanhia.getText().toString());
        user.setBirthdate(campoAniversario.getText().toString());
        user.setLocation(campoLocalizacao.getText().toString());
        user.setUrl(campoWebsite.getText().toString());
        return user;
    }

    public void preencherFormulario(User userParaAlteracao) {

        campoNome.setText(userParaAlteracao.getName());
        campoCpf.setText(userParaAlteracao.getCpf());
        campoTelefone.setText(userParaAlteracao.getPhone());
        campoEmail.setText(userParaAlteracao.getEmail());
        campoCompanhia.setText(userParaAlteracao.getCompany());
        campoAniversario.setText(userParaAlteracao.getBirthdate());
        campoLocalizacao.setText(userParaAlteracao.getLocation());
        campoWebsite.setText(userParaAlteracao.getUrl());
        if (userParaAlteracao.getPhoto() != null){
            carregaImagem(userParaAlteracao.getPhoto());
        }
    }


    public void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
        campoEmail.setText("");
        campoCompanhia.setText("");
        campoAniversario.setText("");
        campoLocalizacao.setText("");
        campoWebsite.setText("");
    }

    public ImageView getFoto(){
        return campoFoto;
    }

    public void carregaImagem(String caminhoFoto) {
        Log.i("IMG", "Imagem a ser tratada");
        /*
        user.setPhoto(caminhoFoto);
        Bitmap imagem = BitmapFactory.decodeFile(caminhoFoto);
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
        campoFoto.setImageBitmap(imagemReduzida);
        */
    }


}