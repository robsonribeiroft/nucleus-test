package userAPI;


import java.util.List;

import modelo.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {

    public static final String BASE_URL = "https://api-nucleus-user.herokuapp.com/api/";

    @GET("user/")
    Call<List<User>> getListaUsers();

    @POST("user")
    Call<User> postUser(@Body User user);

    @POST("user/{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user);

    @DELETE("user/{id}")
    Call<User> deleteUser(@Path("id") String id);



}
