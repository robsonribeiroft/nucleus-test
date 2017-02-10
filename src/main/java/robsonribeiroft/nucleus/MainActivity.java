package robsonribeiroft.nucleus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.List;

import modelo.User;
import robsonribeiroft.nucleus.DAO.UserDAO;
import robsonribeiroft.nucleus.adapter.UserAdapter;
import robsonribeiroft.nucleus.singleton.Singleton;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);
        ImageButton btnNovoCliente = (ImageButton) findViewById(R.id.novoCliente);

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Singleton.getInstance().currentUser = (User) adapterView.getItemAtPosition(position);
                Intent vaiParaUsuarioAcitivy = new Intent(MainActivity.this, UsuarioActivity.class);
                startActivity(vaiParaUsuarioAcitivy);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Singleton.getInstance().currentUser = (User) adapterView.getItemAtPosition(position);
                return false;
            }
        });

        btnNovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.getInstance().currentUser = null;
                Intent vaiParaFormularioActivity = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(vaiParaFormularioActivity);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        UserDAO dao = new UserDAO(MainActivity.this);
        List<User> users = dao.getLista();
        UserAdapter adapter = new UserAdapter(users, MainActivity.this);
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem editar = menu.add("Editar");
        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                UserDAO dao = new UserDAO(MainActivity.this);
                dao.deletar(Singleton.getInstance().currentUser);
                dao.close();
                carregarLista();
                return false;
            }
        });

        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent vaiParaFormularioEditar = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(vaiParaFormularioEditar);
                return false;
            }
        });



    }
}
