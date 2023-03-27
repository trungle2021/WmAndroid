package com.example.wmandroid.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.Adapter.FoodSninperAdapter;
import com.example.wmandroid.Adapter.ServiceAdapter;
import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.ServiceDTO;
import com.example.wmandroid.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiceDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceDetailFragment newInstance(String param1, String param2) {
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;



    private FragmentManager mFragmentManager;
    Spinner serviceSpiner1;
    Spinner serviceSpiner2;
    Spinner serviceSpiner3;
    List<ServiceDTO> serviceList;
    Button btnAddService;

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
        view=inflater.inflate(R.layout.fragment_service_detail, container, false);
        init();
        String data = getArguments().getString("data");
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = gson.fromJson(data, type);

        Log.i("data",data);

        HomeService homeService= ApiClient.createService(HomeService.class);
        homeService.getAllService().enqueue(new Callback<List<ServiceDTO>>() {
            @Override
            public void onResponse(Call<List<ServiceDTO>> call, Response<List<ServiceDTO>> response) {
                //code
                if(response.isSuccessful()&& response.body()!=null)
                {
                   serviceList=response.body();
                    ServiceDTO defaultService=new ServiceDTO();
                    defaultService.setServiceName("none");
                    defaultService.setId(0);
                    defaultService.setPrice(0.0);
                    serviceList.add(0,defaultService);
                   ServiceAdapter adapter = new ServiceAdapter(getContext(), R.layout.service_detail_snipper, serviceList);
                    serviceSpiner1.setAdapter(adapter);
                    serviceSpiner2.setAdapter(adapter);
                    serviceSpiner3.setAdapter(adapter);

//                    {"orderId":74,"foodList":["2","3","4","6","8","10"],"serviceList":["2"],"table":"20"}
                    HashMap<Integer, String> previousSelectedValues = new HashMap<>();
                    List<String> serviceList=new ArrayList<>();
                    serviceSpiner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ServiceDTO service = (ServiceDTO) parent.getItemAtPosition(position);
                            String strId= String.valueOf(service.getId());

                            // Get the ID of the spinner
                            int spinnerId = parent.getId();
//                                add value
                            String previousValue = previousSelectedValues.get(spinnerId);

                            // check if the selected value is already present in the list
                            if (serviceList.contains(strId)) {
                                // set the Spinner value to "none"

                                serviceSpiner1.setSelection(0);
                                Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();


                            } else {
                                if(previousValue!=null){
                                    serviceList.remove(previousValue);
                                }
                                serviceList.add(strId);
                                previousSelectedValues.put(spinnerId, strId);
                            }
//                            Log.i("foodName",foodListStarter.get(position).getFoodName());
//                            Toast.makeText(getContext(), food.getFoodName(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    serviceSpiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ServiceDTO service = (ServiceDTO) parent.getItemAtPosition(position);
                            String strId= String.valueOf(service.getId());

                            // Get the ID of the spinner
                            int spinnerId = parent.getId();
//                                add value
                            String previousValue = previousSelectedValues.get(spinnerId);

                            // check if the selected value is already present in the list
                            if (serviceList.contains(strId)) {
                                // set the Spinner value to "none"

                                serviceSpiner2.setSelection(0);
                                Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();


                            } else {
                                if(previousValue!=null){
                                    serviceList.remove(previousValue);
                                }
                                serviceList.add(strId);
                                previousSelectedValues.put(spinnerId, strId);
                            }
//                            Log.i("foodName",foodListStarter.get(position).getFoodName());
//                            Toast.makeText(getContext(), food.getFoodName(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    //3
                    serviceSpiner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ServiceDTO service = (ServiceDTO) parent.getItemAtPosition(position);
                            String strId= String.valueOf(service.getId());

                            // Get the ID of the spinner
                            int spinnerId = parent.getId();
//                                add value
                            String previousValue = previousSelectedValues.get(spinnerId);

                            // check if the selected value is already present in the list
                            if (serviceList.contains(strId)) {
                                // set the Spinner value to "none"

                                serviceSpiner3.setSelection(0);
                                Toast.makeText(getContext(), "Duplicate!Try again", Toast.LENGTH_SHORT).show();


                            } else {
                                if(previousValue!=null){
                                    serviceList.remove(previousValue);
                                }
                                serviceList.add(strId);
                                previousSelectedValues.put(spinnerId, strId);
                            }
//                            Log.i("foodName",foodListStarter.get(position).getFoodName());
//                            Toast.makeText(getContext(), food.getFoodName(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    btnAddService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //code here
                            Iterator<String> iterator = serviceList.iterator();
                            while (iterator.hasNext()) {
                                String value = iterator.next();
                                if (value.equals("0")) {
                                    iterator.remove();
                                }
                            }
                            map.put("serviceList",serviceList);

                            String jsonString = gson.toJson(map);
                            Log.i("lastMap", jsonString);

                            //call API
                        }
                    });



                }

            }

            @Override
            public void onFailure(Call<List<ServiceDTO>> call, Throwable t) {

            }
        });








        return view;
    }



    public void init()
    {
        serviceSpiner1 = view.findViewById(R.id.serviceSpinner1);
        serviceSpiner1=view.findViewById(R.id.serviceSpinner2);
        serviceSpiner1=view.findViewById(R.id.serviceSpinner3);

        btnAddService=view.findViewById(R.id.btnAddService);

    }
}