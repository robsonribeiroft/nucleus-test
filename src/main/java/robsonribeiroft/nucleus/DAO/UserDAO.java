package robsonribeiroft.nucleus.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;

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
                + "cpf TEXT PRYMARY KEY, "
                + "nome TEXT NOT NULL, "
                + "telefone TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "companhia TEXT, "
                + "aniversario  TEXT, "
                + "localizacao TEXT, "
                + "website TEXT, "
                + "caminhoFoto TEXT"
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
        ContentValues contentValues = new ContentValues();
        contentValues.put("cpf", user.getCpf());
        contentValues.put("nome", user.getNome());
        contentValues.put("telefone", user.getTelefone());
        contentValues.put("email", user.getEmail());
        contentValues.put("companhia", user.getCompanhia());
        contentValues.put("aniversario", user.getAniversario());
        contentValues.put("localizacao", user.getLocalizacao());
        contentValues.put("website", user.getWebsite());
        contentValues.put("caminhoFoto", user.getCaminhoFoto());

        getWritableDatabase().insert(TABELA, null, contentValues);
    }

    public List<User> getLista() {

        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " ORDER BY nome;";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()){
            User user = new User();
            user.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            user.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            user.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setCompanhia(cursor.getString(cursor.getColumnIndex("companhia")));
            user.setAniversario(cursor.getString(cursor.getColumnIndex("aniversario")));
            user.setLocalizacao(cursor.getString(cursor.getColumnIndex("localizacao")));
            user.setWebsite(cursor.getString(cursor.getColumnIndex("website")));
            user.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            users.add(user);
        }
        //cursor.close();
        return users;
    }

    public boolean cpfCadastrado(User user){

        String sql = "SELECT * FROM " + TABELA + ";";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()){
            if (cursor.getCount()>0){
                return true;
            }
        }
        return false;
    }

    public void deletar(User user) {
        String[] args = {user.getCpf()};
        getWritableDatabase().delete(TABELA, "cpf=?", args);
    }



    public void atualizar(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cpf", user.getCpf());
        contentValues.put("nome", user.getNome());
        contentValues.put("telefone", user.getTelefone());
        contentValues.put("email", user.getEmail());
        contentValues.put("companhia", user.getCompanhia());
        contentValues.put("aniversario", user.getAniversario());
        contentValues.put("localizacao", user.getLocalizacao());
        contentValues.put("website", user.getWebsite());
        contentValues.put("caminhoFoto", user.getCaminhoFoto());

        String[] args = {user.getCpf()};
        getWritableDatabase().update(TABELA, contentValues, "cpf=?", args);
    }

}
