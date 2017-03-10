package robsonribeiroft.nucleus.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import modelo.User;

public class UserDAO extends SQLiteOpenHelper{

    private static final String DATABASE = "BancoUserLocal";
    private static final int VERSAO = 1;
    private static final String TABELA = "Users";

    public UserDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "CREATE TABLE " + TABELA + " ("
                + "cpf TEXT PRYMARY KEY UNIQUE, "
                + "nome TEXT NOT NULL, "
                + "telefone TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "companhia TEXT, "
                + "aniversario  TEXT, "
                + "localizacao TEXT, "
                + "website TEXT, "
                + "caminhoFoto TEXT, "
                + "id TEXT"
                + ");";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        database.execSQL(sql);
        onCreate(database);
    }



    public void insere(User user) {
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("cpf", user.getCpf());
            contentValues.put("nome", user.getName());
            contentValues.put("telefone", user.getPhone());
            contentValues.put("email", user.getEmail());
            contentValues.put("companhia", user.getCompany());
            contentValues.put("aniversario", user.getBirthdate());
            contentValues.put("localizacao", user.getLocation());
            contentValues.put("website", user.getUrl());
            contentValues.put("caminhoFoto", user.getPhoto());
            contentValues.put("id", user.get_id());

            getWritableDatabase().insert(TABELA, null, contentValues);
        }catch (Exception e){
            Log.i("DAO", "Usuário já cadastrado: " + e.getMessage());
        }
    }

    public void insereLista(List<User> users){
        for (User user : users){
            insere(user);
        }
    }

    public List<User> getLista() {

        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " ORDER BY nome;";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()){
            User user = new User();
            user.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            user.setName(cursor.getString(cursor.getColumnIndex("nome")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("telefone")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setCompany(cursor.getString(cursor.getColumnIndex("companhia")));
            user.setBirthdate(cursor.getString(cursor.getColumnIndex("aniversario")));
            user.setLocation(cursor.getString(cursor.getColumnIndex("localizacao")));
            user.setUrl(cursor.getString(cursor.getColumnIndex("website")));
            user.setPhoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
            user.set_id(cursor.getString(cursor.getColumnIndex("id")));

            users.add(user);
        }
        cursor.close();
        return users;
    }

    public boolean cpfCadastrado(User user){
        String sql = "SELECT cpf FROM " + TABELA + " WHERE cpf=" + user.getCpf() +";";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() > 0){
            cursor.close();
            return true;
        } else{
            cursor.close();
            return false;
        }
    }

    public void deletar(User user) {
        String[] args = {user.getCpf()};
        getWritableDatabase().delete(TABELA, "cpf=?", args);
    }



    public void atualizar(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cpf", user.getCpf());
        contentValues.put("nome", user.getName());
        contentValues.put("telefone", user.getPhone());
        contentValues.put("email", user.getEmail());
        contentValues.put("companhia", user.getCompany());
        contentValues.put("aniversario", user.getBirthdate());
        contentValues.put("localizacao", user.getLocation());
        contentValues.put("website", user.getUrl());
        contentValues.put("caminhoFoto", user.getPhoto());
        contentValues.put("id", user.get_id());

        String[] args = {user.getCpf()};
        getWritableDatabase().update(TABELA, contentValues, "cpf=?", args);
    }


    public void atualizar_id(User user, String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);

        getWritableDatabase().update(TABELA, contentValues, "cpf=?", new String[]{user.getCpf()});
    }

}
