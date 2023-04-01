package com.example.wmandroid.Fragment;

import static com.example.wmandroid.Utils.orderStatus.foodTypeDessert;
import static com.example.wmandroid.Utils.orderStatus.foodTypeMain;
import static com.example.wmandroid.Utils.orderStatus.foodTypeStarter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBindings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.Adapter.FoodSninperAdapter;
import com.example.wmandroid.Adapter.RealVenueAdapter;
import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.ServiceDTO;
import com.example.wmandroid.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingDetailFragment newInstance(String param1, String param2) {
        BookingDetailFragment fragment = new BookingDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    View view;
    OrderDTO myOrder;
    List<FoodDTO> foodList;
    private FragmentManager mFragmentManager;
    Spinner foodSpinerStater;
    Spinner foodSpinerMain1;
    Spinner foodSpinerMain2;
    Spinner foodSpinerMain3;
    Spinner foodSpinerMain4;
    Spinner foodSpinerMain5;
    Spinner foodSpinerDessert;
    Button btnAddFood;
    EditText txtTableAmount;
    Integer min ;
    Integer max;
//    Spinner foodSpinner;
//    Spinner service;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_booking_detail, container, false);
        // Retrieve the data from the arguments Bundle
        String orderId = getArguments().getString("orderId");

        init();
        //get data:
//            getData(orderId);
//        LinearLayout foodContainer = view.findViewById(R.id.foodSpinnerContainer);
        HomeService homeService=ApiClient.createService(HomeService.class);
        try {

            homeService.getOnebyId(orderId).enqueue(new Callback<OrderDTO>() {
                @Override
                public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {
                    if(response.isSuccessful()&&response!=null){
                    myOrder=response.body();
                        min=myOrder.getVenues().getMinPeople()/10;
                        max=myOrder.getVenues().getMaxPeople()/10;
                    Gson gson=new Gson();
                    String jsonString = gson.toJson(foodList);
                    Log.i("response", jsonString);
                }
                }

                @Override
                public void onFailure(Call<OrderDTO> call, Throwable t) {

                }
            });


            homeService.getAllFood().enqueue(new Callback<List<FoodDTO>>() {
                @Override
                public void onResponse(Call<List<FoodDTO>> call, Response<List<FoodDTO>> response) {
                    if (response.isSuccessful()) {
                        foodList = response.body();
                        FoodDTO defaultFood=new FoodDTO();
                        defaultFood.setFoodName("none");
                        defaultFood.setId(0);
                        defaultFood.setPrice(0.0);
                        List<FoodDTO> foodListStarter=foodList.stream().filter(foodDTO ->foodDTO.getFoodType().equalsIgnoreCase(foodTypeStarter)).collect(Collectors.toList());
                        foodListStarter.add(0,defaultFood);
                        List<FoodDTO> foodListMain=foodList.stream().filter(foodDTO ->foodDTO.getFoodType().equalsIgnoreCase(foodTypeMain)).collect(Collectors.toList());
                        foodListMain.add(0,defaultFood);
                        List<FoodDTO> foodListDessert=foodList.stream().filter(foodDTO ->foodDTO.getFoodType().equalsIgnoreCase(foodTypeDessert)).collect(Collectors.toList());
                        foodListDessert.add(0,defaultFood);
                        // Continue to the next step
                        // Create the spinner adapter for each food type
                        Gson gson=new Gson();
                        FoodSninperAdapter adapterStarter = new FoodSninperAdapter(getContext(), R.layout.food_detail_snipper, foodListStarter);foodSpinerStater.setAdapter(adapterStarter);
                        foodSpinerStater.setAdapter(adapterStarter);
                        FoodSninperAdapter adapterMain = new FoodSninperAdapter(getContext(), R.layout.food_detail_snipper, foodListMain);
                        foodSpinerMain1.setAdapter(adapterMain);
                        foodSpinerMain2.setAdapter(adapterMain);
                        foodSpinerMain3.setAdapter(adapterMain);
                        foodSpinerMain4.setAdapter(adapterMain);
                        foodSpinerMain5.setAdapter(adapterMain);

                        FoodSninperAdapter adapterDessert = new FoodSninperAdapter(getContext(), R.layout.food_detail_snipper, foodListDessert);
                        foodSpinerDessert.setAdapter(adapterDessert);
                        HashMap<Integer, String> previousSelectedValues = new HashMap<>();
                          List<String> foodList=new ArrayList<>();
// Set the onItemSelectedListener for each spinner using the appropriate list


                        foodSpinerStater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // Get the ID of the spinner
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerStater.setSelection(0);

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);
                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                                Log.i("foodName",foodListStarter.get(position).getFoodName());
                                Toast.makeText(getContext(), food.getFoodName(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });



                        foodSpinerMain1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // check if the selected value is already present in the list
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerMain1.setSelection(0);
                                    Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);
                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                                Gson gson=new Gson();
                                String jsonString = gson.toJson(foodList);
                                Log.i("response", jsonString);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        foodSpinerMain5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // check if the selected value is already present in the list
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerMain5.setSelection(0);
                                    Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);
                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        foodSpinerMain4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // check if the selected value is already present in the list
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerMain4.setSelection(0);
                                    Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);
                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        foodSpinerMain2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // check if the selected value is already present in the list
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerMain2.setSelection(0);
                                    Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);
                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        foodSpinerMain3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // check if the selected value is already present in the list
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerMain3.setSelection(0);
                                    Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);
                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        foodSpinerDessert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                FoodDTO food = (FoodDTO) parent.getItemAtPosition(position);
                                String strId= String.valueOf(food.getId());

                                // check if the selected value is already present in the list
                                int spinnerId = parent.getId();
//                                add value
                                String previousValue = previousSelectedValues.get(spinnerId);

                                // check if the selected value is already present in the list
                                if (foodList.contains(strId)) {
                                    // set the Spinner value to "none"

                                    foodSpinerDessert.setSelection(0);

                                } else {
                                    if(previousValue!=null){
                                        foodList.remove(previousValue);

                                    }
                                    foodList.add(strId);
                                    previousSelectedValues.put(spinnerId, strId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        btnAddFood.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String table= txtTableAmount.getText().toString();
                                if(!table.isEmpty()&&Integer.parseInt(table)>=min && Integer.parseInt(table)<=max &&foodList.size()>=6) {
//                                    foodList.remove("0");
                                    //remove "0"
                                    Iterator<String> iterator = foodList.iterator();
                                    while (iterator.hasNext()) {
                                        String value = iterator.next();
                                        if (value.equals("0")) {
                                            iterator.remove();
                                        }
                                    }

                                    Map myMap = new HashMap();
                                    myMap.put("orderId", orderId);
                                    myMap.put("foodList", foodList);
                                    myMap.put("table", table);
                                    String jsonString = gson.toJson(foodList);
                                    String map = gson.toJson(myMap);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("data", map);
                                    ServiceDetailFragment serviceDetailFragment = new ServiceDetailFragment();
                                    // Set the arguments for the new fragment
                                    serviceDetailFragment.setArguments(bundle);


                                    Log.i("response", jsonString);
                                    Log.i("map", map);

                                    mFragmentManager.popBackStack(ProfileFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frame_layout_navigate,serviceDetailFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                                else{

                                    Toast.makeText(getContext(), "Table must from "+ min +"to"+max+"!And more than 6 dics!", Toast.LENGTH_SHORT).show();
                                    Log.i("table error","Table must from "+min +"to"+max );
                                }
                            }
                        });


                        String jsonString = gson.toJson(foodList);
                        Log.i("response", jsonString);
                    }

                     else {
                        // Handle error or retry
                    Log.i("err","loi food");
                    }
                }


                @Override
                public void onFailure(Call<List<FoodDTO>> call, Throwable t) {

                }
            });


        }
        catch (Exception e)
        {
            Log.i("error",e.getMessage());
        }
//            getData(orderId);


//        adapter.notifyDataSetChanged();

        return view;

    }

    public void init()
    {
        foodSpinerStater = view.findViewById(R.id.foodSpinnerStarter);
        foodSpinerMain1=view.findViewById(R.id.foodSpinnerMain1);
        foodSpinerMain2=view.findViewById(R.id.foodSpinnerMain2);
         foodSpinerMain3=view.findViewById(R.id.foodSpinnerMain3);
         foodSpinerMain4=view.findViewById(R.id.foodSpinnerMain4);
         foodSpinerMain5=view.findViewById(R.id.foodSpinnerMain5);
         foodSpinerDessert=view.findViewById(R.id.foodSpinnerDessert);
        btnAddFood=view.findViewById(R.id.btnAddFoodDetail);
        txtTableAmount=view.findViewById(R.id.txtTableAmount);

    }







}
