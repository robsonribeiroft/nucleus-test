package robsonribeiroft.nucleus.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        campoNome = (EditText) activity.findViewById(R.id.nome);
        campoCpf = (EditText) activity.findViewById(R.id.cpf);
        campoTelefone = (EditText) activity.findViewById(R.id.telefone);
        campoEmail = (EditText) activity.findViewById(R.id.email);
        campoCompanhia = (EditText) activity.findViewById(R.id.companhia);
        campoAniversario = (EditText) activity.findViewById(R.id.aniversario);
        campoLocalizacao = (EditText) activity.findViewById(R.id.localizacao);
        campoWebsite = (EditText) activity.findViewById(R.id.website);
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

        TVnome.setText(user.getNome());
        TVcpf.setText(user.getCpf());
        TVtelefone.setText(user.getTelefone());
        TVemail.setText(user.getEmail());
        TVcompanhia.setText(user.getCompanhia());
        TVaniversario.setText(user.getAniversario());
        TVlocalizacao.setText(user.getLocalizacao());
        TVwebsite.setText(user.getWebsite());
    }



    public User pegaUserDoFormulario(){
        user.setNome(campoNome.getText().toString());
        user.setCpf(campoCpf.getText().toString());
        user.setTelefone(campoTelefone.getText().toString());
        user.setEmail(campoEmail.getText().toString());
        user.setCompanhia(campoCompanhia.getText().toString());
        user.setAniversario(campoAniversario.getText().toString());
        user.setLocalizacao(campoLocalizacao.getText().toString());
        user.setWebsite(campoWebsite.getText().toString());
        return user;
    }

    public void preencherFormulario(User userParaAlteracao) {

        campoNome.setText(userParaAlteracao.getNome());
        campoCpf.setText(userParaAlteracao.getCpf());
        campoTelefone.setText(userParaAlteracao.getTelefone());
        campoEmail.setText(userParaAlteracao.getEmail());
        campoCompanhia.setText(userParaAlteracao.getCompanhia());
        campoAniversario.setText(userParaAlteracao.getAniversario());
        campoLocalizacao.setText(userParaAlteracao.getLocalizacao());
        campoWebsite.setText(userParaAlteracao.getWebsite());
        if (userParaAlteracao.getCaminhoFoto() != null){
            carregaImagem(userParaAlteracao.getCaminhoFoto());
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
        user.setCaminhoFoto(caminhoFoto);
        Bitmap imagem = BitmapFactory.decodeFile(caminhoFoto);
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
        campoFoto.setImageBitmap(imagemReduzida);
    }
}