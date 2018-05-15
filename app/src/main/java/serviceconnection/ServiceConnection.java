package serviceconnection;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.grocerybazzar.util.C;
import com.app.grocerybazzar.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by GAURAV on 7/8/2017.
 */

public class ServiceConnection {

    Utils utils=new Utils();
    public ServiceConnection() {
    }

    public void sendToServer(String baseurl,String method, final Map hashMap, String msg,final CompleteListener completeListener){
        utils.showDialog(msg,completeListener.getApplicationsContext());
        String REGISTER_URL= baseurl+method;
        Log.e("url==",REGISTER_URL);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utils.hideDialog();
                        completeListener.done(response);
                        //Toast.makeText(completeListener.getApplicationsContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utils.hideDialog();
                        Log.e("Error",error.toString());
                       // Toast.makeText(completeListener.getApplicationsContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                //  Map<String,String> params = new HashMap<String, String>();

                return hashMap;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Utils.getHeader();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(completeListener.getApplicationsContext());
        requestQueue.add(stringRequest);
    }
    public void sendToServerWithoutLoader(String baseurl,String method, final Map hashMap, String msg,final CompleteListener completeListener){
        String REGISTER_URL= baseurl+method;
        Log.e("url==",REGISTER_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        completeListener.done(response);
                        //Toast.makeText(completeListener.getApplicationsContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error",error.toString());
                        // Toast.makeText(completeListener.getApplicationsContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                //  Map<String,String> params = new HashMap<String, String>();

                return hashMap;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Utils.getHeader();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(completeListener.getApplicationsContext());
        requestQueue.add(stringRequest);
    }

    public void sendToServerForPincode(String baseurl,String method, final Map hashMap, String msg,final CompleteListener completeListener){
        utils.showDialog(msg,completeListener.getApplicationsContext());
        String REGISTER_URL= baseurl+method;
        Log.e("url==",REGISTER_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utils.hideDialog();
                        completeListener.done(response);
                        //Toast.makeText(completeListener.getApplicationsContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utils.hideDialog();
                        Log.e("Error",error.toString());
                        // Toast.makeText(completeListener.getApplicationsContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                //  Map<String,String> params = new HashMap<String, String>();

                return hashMap;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Utils.getHeader();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(completeListener.getApplicationsContext());
        requestQueue.add(stringRequest);
    }

    public void makeJsonObjectRequest(final  String method,String jsonBody, String msg,final CompleteListener completeListener) {

        try {
            Log.e("DEBUG","REQUEST="+jsonBody);
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            utils.showDialog(msg,completeListener.getApplicationsContext());
            String REGISTER_URL= C.BASE_URL_ORDERS+method;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    REGISTER_URL,obj,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    utils.hideDialog();

                    Log.e(TAG, response.toString());
                    completeListener.done(response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.getMessage());
                    utils.hideDialog();

                }

            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return Utils.getHeader();
                }
            };

            // Adding request to request queue
            // AppController.getInstance().addToRequestQueue(jsonObjReq);
            RequestQueue requestQueue = Volley.newRequestQueue(completeListener.getApplicationsContext());
            requestQueue.add(jsonObjReq);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void serviceRequest(final  String method,JSONObject jsonBody, final CompleteListener completeListener) {

        try {
//            Log.e("DEBUG","REQUEST="+jsonBody.toString());

            String REGISTER_URL= C.BASE_URL+method;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    REGISTER_URL,jsonBody,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {


                    Log.e(TAG, response.toString());
                    completeListener.done(response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: " + error.getMessage());


                }

            });

            // Adding request to request queue
            // AppController.getInstance().addToRequestQueue(jsonObjReq);
            RequestQueue requestQueue = Volley.newRequestQueue(completeListener.getApplicationsContext());
            requestQueue.add(jsonObjReq);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
