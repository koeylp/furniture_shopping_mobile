package com.bibon.furnitureshopping.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bibon.furnitureshopping.fragments.CanceledFragment;
import com.bibon.furnitureshopping.fragments.DeliveredFragment;
import com.bibon.furnitureshopping.fragments.ProcessingFragment;

public class ViewPageAdapter extends FragmentStateAdapter {


    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DeliveredFragment();
            case 1:
                return new ProcessingFragment();
            case 2:
                return new CanceledFragment();
            default:
                return new DeliveredFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
