package com.example.drivercab10.API;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.Interfaces.UniversalStringCallBack;
import com.example.drivercab10.util.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;


public class UserAPI {

    public void Register(final String first_name, final String last_name, final String email, final String password, final String mobile, final String name, final UniversalCallBack callBack) {
        String url = Constants.REGISTER;
        Log.d("Register: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSign responseObject = gson.fromJson(response.toString(), ResponseSign.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("login_by", "manual");
                params.put("device_type", Constants.ANDROID);
                params.put("password", password);
                params.put("name", name);
                params.put("mobile", mobile);
                params.put("lang", ApplicationController.langNum + "");
                params.put("plate_no", "9258");
                params.put("model", "Farrai");
                params.put("color", "white");
                params.put("service_type", "2");

                params.put("device_token", ApplicationController.getInstance().getDeviceToken());


                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void Login(final String email, final String password, final UniversalCallBack callBack) {
        String url = Constants.LOGIN;
        Log.d("LOGIN: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LOGIN: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSign responseObject = gson.fromJson(response.toString(), ResponseSign.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("login_by", "manual");
                params.put("device_type", Constants.ANDROID);
                params.put("password", password);
                params.put("lang", ApplicationController.langNum + "");
                params.put("device_token", ApplicationController.getInstance().getDeviceToken());


                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void Logout(final UniversalCallBack callBack) {
        String url = Constants.LOGOUT;
        Log.d("LOGOUT: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LOGOUT: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseLogout responseObject = gson.fromJson(response.toString(), ResponseLogout.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());


                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void TimeOrderTracking(final String link, final UniversalStringCallBack callBack) {
        Log.d("TimeOrderTracking: ", link);

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TimeOrderTracking: ", response);
                try {
                    callBack.onFinish();
//                    Gson gson = new Gson();
                    callBack.onResponse(response);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        });

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void getHistory(String URL, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("HISTORY: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseHistoryTrips responseObject = gson.fromJson(response.toString(), ResponseHistoryTrips.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                Log.e("HISTORY", ApplicationController.getInstance().getUser().getId() + "");
                Log.e("HISTORY", ApplicationController.getInstance().getUser().getToken());
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void RequestDetails(final String request_id, final UniversalCallBack callBack) {
        String url = Constants.REQUEST_DETAILS;
        Log.d("REQUEST_DETAILS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REQUEST_DETAILS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrderDetails responseObject = gson.fromJson(response.toString(), ResponseOrderDetails.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", request_id);

                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void FilterOrder(final String from, final String to, final UniversalCallBack callBack) {
        String url = Constants.FILTER_ORDER;
        Log.d("FILTER_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FILTER_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseBill responseObject = gson.fromJson(response.toString(), ResponseBill.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("from_date", from);
                params.put("to_date", to);

                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void serviceAccept(String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("serviceAccept: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseAccept responseObject = gson.fromJson(response.toString(), ResponseAccept.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void getOnlineStatus( final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, Constants.GET_ONLINE_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET_ONLINE_STATUS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOnLine responseObject = gson.fromJson(response.toString(), ResponseOnLine.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void setOnlineStatus(final String Online, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, Constants.SET_ONLINE_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("SET_ONLINE_STATUS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOnLine responseObject = gson.fromJson(response.toString(), ResponseOnLine.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("online",Online);


                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void checkStatus( final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, Constants.CHECK_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CHECK_STATUS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseCheckStatus responseObject ;
               try {
                   responseObject = gson.fromJson(response.toString(), ResponseCheckStatus.class);

               }catch (Exception e){
                   responseObject=new ResponseCheckStatus();
                   responseObject.setSuccess(false);
               }

                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                    Log.e("CHECK_STATUS",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void cancelServices(String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("cancelServices: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseCancel responseObject = gson.fromJson(response.toString(), ResponseCancel.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void providerStarted(String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("startServices: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseProviderStarted responseObject = gson.fromJson(response.toString(), ResponseProviderStarted.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void providerConfirm(String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("providerConfirm: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseProviderStarted responseObject = gson.fromJson(response.toString(), ResponseProviderStarted.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void startTravel(String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("startTravel: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseProviderStarted responseObject = gson.fromJson(response.toString(), ResponseProviderStarted.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }
    public void endTravel(String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("endTravel: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseProviderStarted responseObject = gson.fromJson(response.toString(), ResponseProviderStarted.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                params.put("time", "30");
                params.put("distance", "100");

                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void rateTravel(final String rating, String comment, String URL, final String RequestId, final UniversalCallBack callBack) {
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("rateTravel: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseProviderStarted responseObject = gson.fromJson(response.toString(), ResponseProviderStarted.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("id", ApplicationController.getInstance().getUser().getId() + "");
                params.put("token", ApplicationController.getInstance().getUser().getToken());
                params.put("request_id", RequestId);
                params.put("rating", rating);
                params.put("comment", "");

                return params;
            }


        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    private void showMessage(VolleyError error, UniversalCallBack callBack) {
        String message = null;
        Log.d("onErrorResponse", error.toString() + "");
        String json = null;
        Log.d("error.getMessage()", error.getMessage() + "");
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
            callBack.OnError(message);
        } else {
            try {
                Gson gson = new Gson();
                ResponseError ErrorMsg = gson.fromJson(error.getMessage(), ResponseError.class);
                callBack.onFailure(ErrorMsg);
            } catch (JsonSyntaxException e) {
                callBack.OnError("Server Connection error try again later");
            }
        }
    }

    private void showMessage(VolleyError error, UniversalStringCallBack callBack) {
        String message = null;
        Log.d("onErrorResponse", error.toString() + "");
        String json = null;
        Log.d("error.getMessage()", error.getMessage() + "");
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
            callBack.OnError(message);
        } else {
            try {
                Gson gson = new Gson();
                ResponseError ErrorMsg = gson.fromJson(error.getMessage(), ResponseError.class);
                callBack.onFailure(ErrorMsg.getMessage());
            } catch (JsonSyntaxException e) {
                callBack.OnError("Server Connection error try again later");
            }
        }
    }

}
