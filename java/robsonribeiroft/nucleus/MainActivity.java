package robsonribeiroft.nucleus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import modelo.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import robsonribeiroft.nucleus.DAO.UserDAO;
import robsonribeiroft.nucleus.adapter.UserAdapter;
import robsonribeiroft.nucleus.singleton.Singleton;
import userAPI.UserAPI;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private Retrofit retrofit;
    private UserAPI userAPI;

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

        Log.i("Check", "carrega lista");
        retrofit  = new Retrofit.Builder().baseUrl(UserAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        userAPI = retrofit.create(UserAPI.class);
        Call<List<User>> requestListaUsers = userAPI.getListaUsers();

        requestListaUsers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.i("Check", "Lista Users");

                UserDAO dao = new UserDAO(MainActivity.this);

                Toast.makeText(MainActivity.this, "Sincronizando", Toast.LENGTH_SHORT).show();

                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, " Lista get - Erro de conexão com o banco", Toast.LENGTH_SHORT).show();
                }else{

                    List<User> listUsersAPI = response.body();
                    List<User> listUsersDAO = dao.getLista();


                    if (listUsersDAO.size() < listUsersAPI.size()){
                        Log.i("Check", "<");
                        dao.insereLista(listUsersAPI);
                        lista.setAdapter(new UserAdapter(listUsersDAO, MainActivity.this));
                    }
                    else if (listUsersDAO.size() == listUsersAPI.size()){
                        Log.i("Check", "==");
                        for (final User user : listUsersDAO){
                            if (user.get_id() == null){
                                Call<User> requestPostUser = userAPI.postUser(user);
                                requestPostUser.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        if (!response.isSuccessful()){
                                            Log.i("Check", "Sem id - error servidor");
                                        }else {
                                            Log.i("Check", "Sem id - Deu certo");
                                            new UserDAO(MainActivity.this).deletar(user);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Log.i("Check", "Sem id - sem internet");
                                    }
                                });
                            }
                        }
                        lista.setAdapter(new UserAdapter(listUsersDAO, MainActivity.this));




                    } else{
                        Log.i("Check", ">");
                        for (User user : listUsersDAO){
                            Call<User> requestPostUser = userAPI.postUser(user);
                            requestPostUser.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (!response.isSuccessful()){
                                        Log.i("Check", "Lista Users - Erro de acesso ao banco" + response.code());
                                    } else {
                                        Log.i("Check", "Lista Users - " +response.code());
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.i("Check", "Lista user - sem internet " + t.getMessage());
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.i("Check", t.getMessage());
            }
        });

        UserDAO dao = new UserDAO(MainActivity.this);
        List<User> listUsersDAO = dao.getLista();
        UserAdapter adapter = new UserAdapter(listUsersDAO, MainActivity.this);
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
                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                UserAPI userAPI = retrofit.create(UserAPI.class);
                Call<User> requestDeleteUser = userAPI.deleteUser(Singleton.getInstance().currentUser.get_id());

                requestDeleteUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()){
                            Log.i("Check", "delete - Erro no banco");
                        } else{
                            UserDAO dao = new UserDAO(MainActivity.this);
                            dao.deletar(Singleton.getInstance().currentUser);
                            dao.close();
                            carregarLista();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.i("Check", "delete - " + t.getMessage());
                    }
                });


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
