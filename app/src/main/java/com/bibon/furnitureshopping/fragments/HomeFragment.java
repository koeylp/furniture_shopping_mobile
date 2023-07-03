package com.bibon.furnitureshopping.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.ProductDetailActivity;
import com.bibon.furnitureshopping.adapters.ProductAdapter;
import com.bibon.furnitureshopping.models.Product;
import com.bibon.furnitureshopping.repositories.ProductRepository;
import com.bibon.furnitureshopping.services.ProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ArrayList<Product> productList;
    ProductAdapter productAdaper;
    ProductService productService;
    ListView listViewProduct;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewProduct = (ListView) getView().findViewById(R.id.listview_product);
        productService = ProductRepository.getProductService();
        getAllProducts();

        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product", productList.get(position));
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }

    private void getAllProducts() {
        this.productList = new ArrayList<>();
        try {
            Call<Product[]> call = productService.getAllProducts();
            System.out.println(call);
            call.enqueue(new Callback<Product[]>() {
                @Override
                public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                    Product[] products = response.body();
                    if (products == null) {
                        return;
                    }
                    for (Product product : products) {
                        System.out.println(product.get_id());
//                        System.out.println(product.getImg());
                        productList.add(new Product(product.get_id(), product.getProductName(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getImg()));
                    }

                    productAdaper = new ProductAdapter(getActivity(), R.layout.custom_product_list_view, productList);
                    listViewProduct.setAdapter(productAdaper);
                }

                @Override
                public void onFailure(Call<Product[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}