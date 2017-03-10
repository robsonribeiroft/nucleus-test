package converte;


import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import modelo.User;

public class UserConverte {

    public String toJSON(List<User> users) throws JSONException {
        try{
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.object().key("lista").array().object().key("user").array();
            for (User user:users){
                jsonStringer.object();
                jsonStringer.key("cpf").value(user.getCpf());
                jsonStringer.key("name").value(user.getName());
                jsonStringer.key("phone").value(user.getPhone());
                jsonStringer.key("email").value(user.getEmail());
                jsonStringer.key("company").value(user.getCompany());
                jsonStringer.key("birthdate").value(user.getBirthdate());
                jsonStringer.key("url").value(user.getUrl());
                jsonStringer.key("location").value(user.getLocation());
                jsonStringer.key("photo").value(user.getPhoto());
                jsonStringer.endObject();
            }
            jsonStringer.endArray().endObject().endArray().endObject();
            return jsonStringer.toString();
        } catch (JSONException e){
            return null;
        }
    }
}
