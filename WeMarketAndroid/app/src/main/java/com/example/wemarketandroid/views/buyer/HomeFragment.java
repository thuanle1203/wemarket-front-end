package com.example.wemarketandroid.views.buyer;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wemarketandroid.R;
import com.example.wemarketandroid.databinding.FragmentBuyerHomeBinding;
import com.example.wemarketandroid.models.buyer.Food;
import com.example.wemarketandroid.models.buyer.User;
import com.example.wemarketandroid.viewmodels.buyer.HomeViewModel;
import com.example.wemarketandroid.viewmodels.buyer.RecyclerViewHelper;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentBuyerHomeBinding mViewBinding;
    private HomeViewModel mViewModel;
//    private NavController mNavController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewBinding = FragmentBuyerHomeBinding.inflate(inflater,container,false);
        View rootView = mViewBinding.getRoot();
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(HomeViewModel.class);
        // setups filters recycler view
        mViewBinding.recyclerBuyerFoodFiltersHome.setAdapter(mViewModel.getFilterViewHolderAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewHelper.addItemDivider(mViewBinding.recyclerBuyerFoodFiltersHome,LinearLayoutManager.HORIZONTAL);
        mViewBinding.recyclerBuyerFoodFiltersHome.setLayoutManager(layoutManager);
        // TODO: (if have time): add on item click event to send user to choose food page AND sort the list propriately

        // setups food promo recycler view
        RecyclerView.LayoutManager promoLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerViewHelper.addItemDivider(mViewBinding.recyclerBuyerHomePromo,LinearLayoutManager.HORIZONTAL);
        mViewBinding.recyclerBuyerHomePromo.setLayoutManager(promoLayoutManager);
        mViewBinding.recyclerBuyerHomePromo.setAdapter(mViewModel.getPromoViewHolderAdapter());
        // TODO: (if have time): add on item click event to send user to choose food page AND show the add food dialog

        Observer<List<Food>> foodListObserver = new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                mViewModel.getPromoViewHolderAdapter().submitList(foods);
            }
        };
        mViewModel.getmFoodLiveData().observe(getViewLifecycleOwner(),foodListObserver);
        // registers events
        mViewBinding.textBuyerSeeAll.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_chooseFoodFragment);
        });
        // registers user observer
        Observer<User> userObserver = new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mViewBinding.textBuyerTitle.setText(String.format(mViewBinding.textBuyerTitle.getText().toString(), user.getName()));
            }
        };
        mViewModel.getmUserLiveData().observe(getViewLifecycleOwner(),userObserver);

        return rootView;
    }

    // Template code
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mViewBinding = null;
    }
}