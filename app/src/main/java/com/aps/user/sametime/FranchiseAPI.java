package com.aps.user.sametime;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FranchiseAPI {


    @Multipart
    @POST("apssoftware-app/login.php")
    Call<loginBean> login(@Part("username") String username , @Part("password") String password,@Part("user_type") String user_type);

    @Multipart
    @POST("apssoftware-app/order_detail.php")
    Call<OrderBean> orders(@Part("userid") String userid);

    @Multipart
    @POST("apssoftware-app/order.php")
    Call<OrderDetailsBean> orderDetails(@Part("order_id") String orderid);

    @GET("apssoftware-app/country.php")
    Call<CountryBean> country();

    @Multipart
    @POST("apssoftware-app/states.php")
    Call<StateBean> getstate(@Part("country_id") String country_id);

    @Multipart
    @POST("apssoftware-app/signature_detail.php")
    Call<SignatureBean> getsignature(@Part("userid") String userid);

    @Multipart
    @POST("apssoftware-app/city.php")
    Call<CityBean> getcity(@Part("state_id") String state_id);

    @Multipart
    @POST("apssoftware-app/clientorder_detail.php")
    Call<ClientOrderDetailBeans> clientOrder(@Part("userid") String userid);

    @GET("apssoftware-app/project_list.php")
    Call<projNameBean> getProjName();

    @Multipart
    @POST("apssoftware-app/projectbyid.php")
    Call<projectDetailBean> getProjDetails(@Part("id") String userid);

    @Multipart
    @POST("apssoftware-app/add_order.php")
    Call<addOrderBean> addOrder(@Part("userid") String userId , @Part("email") String email , @Part("client_name") String cName , @Part("company_name") String compName , @Part("country") String country , @Part("state") String state , @Part("city") String city , @Part("location") String location , @Part("mobile") String mobile , @Part("project_name") String peojName , @Part("time_period") String time , @Part("currency") String currency , @Part("security_fee") String security , @Part("cur_inr") String curInr , @Part("total_amt") String total , @Part("slotdate") String slotDate , @Part("advance_amount") String advance , @Part("balance_amount") String balance , @Part("second_amount") String second, @Part("payment_details") String payment,@Part("payment_mode") String mode);

    @Multipart
    @POST("apssoftware-app/add_signature.php")
    Call<addSigBean> addSignature(@Part("userid") String userid , @Part MultipartBody.Part file);

    @Multipart
    @POST("apssoftware-app/update_signature.php")
    Call<UpdateSigBean> updateSig(@Part("userid") String userid , @Part MultipartBody.Part file , @Part("signatureid") String id);



    @Multipart
    @POST("apssoftware-app/update_order.php")
    Call<updateOrderBean> update(@Part("orderid") String orderid, @Part("bal_amount") String bal_amount, @Part("sec_amount") String sec_amount);



}




