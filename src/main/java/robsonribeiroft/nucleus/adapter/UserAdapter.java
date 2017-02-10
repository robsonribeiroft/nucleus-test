package robsonribeiroft.nucleus.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import modelo.User;
import robsonribeiroft.nucleus.DAO.UserDAO;
import robsonribeiroft.nucleus.R;

public class UserAdapter extends BaseAdapter {


    private final List<User> users;
    private Activity activity;

    public UserAdapter(List<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = users.get(position);

        LayoutInflater inflater = activity.getLayoutInflater();
        View linha = inflater.inflate(R.layout.item_list, null);

        TextView nome = (TextView) linha.findViewById(R.id.ILnome);
        TextView email = (TextView) linha.findViewById(R.id.ILemail);
        ImageView foto = (ImageView) linha.findViewById(R.id.ILfoto);

        nome.setText(user.getNome());
        email.setText(user.getEmail());

        if (user.getCaminhoFoto() != null){
            Bitmap imagem = BitmapFactory.decodeFile(user.getCaminhoFoto());
            Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
            foto.setImageBitmap(imagemReduzida);
        } else {
            foto.setImageResource(R.mipmap.ic_launcher);
        }

        return linha;
    }
}
