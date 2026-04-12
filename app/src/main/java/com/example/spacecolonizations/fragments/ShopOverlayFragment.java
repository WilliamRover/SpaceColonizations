package com.example.spacecolonizations.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.activities.ShopActivity;
import com.example.spacecolonizations.model.crewmate.Commander;
import com.example.spacecolonizations.model.crewmate.Crew;
import com.example.spacecolonizations.model.crewmate.Gunner;
import com.example.spacecolonizations.model.crewmate.Medic;
import com.example.spacecolonizations.model.crewmate.Navigator;
import com.example.spacecolonizations.model.crewmate.Technician;
import com.example.spacecolonizations.model.shop.Shop;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShopOverlayFragment extends Fragment {
    private TextView txtViewShopName1, txtViewShopJob1, txtViewCrewShopPrice1, txtViewPoor1;
    private TextView txtViewShopName2, txtViewShopJob2, txtViewCrewShopPrice2, txtViewPoor2;
    private TextView txtViewShopName3, txtViewShopJob3, txtViewCrewShopPrice3, txtViewPoor3;
    private ImageView showCrewImg1, showCrewImg2, showCrewImg3;
    private Button btnBuy1, btnBuy2, btnBuy3, btnExit;
    
    private Shop shop;
    private HashMap<Crew, Integer> shopableCrew;
    private List<TextView> shopNameArr;
    private List<TextView> shopJobArr;
    private List<TextView> shopCostArr;
    private List<Button> shopBuyBtnArr;
    private List<TextView> shopPoorArr;
    private List<ImageView> showCrewImgArr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.shop_overlay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
        generateShopCrew();
        setView();
        setupButton();
    }

    private void generateShopCrew() {
        shop = new Shop();
        shop.generateShopableCrew(); // Fixed: calling generateShopableCrew instead of non-existent setUp
        shopableCrew = shop.getShopableCrew();
    }

    private void setupView(View view) {
        txtViewShopName1 = view.findViewById(R.id.txtViewCrewShopName1);
        txtViewShopJob1 = view.findViewById(R.id.txtViewCrewShopJob1);
        txtViewCrewShopPrice1 = view.findViewById(R.id.txtViewCrewShopPrice1);
        txtViewPoor1 = view.findViewById(R.id.txtViewPoor1);
        btnBuy1 = view.findViewById(R.id.btnBuy1);

        txtViewShopName2 = view.findViewById(R.id.txtViewCrewShopName2);
        txtViewShopJob2 = view.findViewById(R.id.txtViewCrewShopJob2);
        txtViewCrewShopPrice2 = view.findViewById(R.id.txtViewCrewShopPrice2);
        txtViewPoor2 = view.findViewById(R.id.txtViewPoor2);
        btnBuy2 = view.findViewById(R.id.btnBuy2);

        txtViewShopName3 = view.findViewById(R.id.txtViewCrewShopName3);
        txtViewShopJob3 = view.findViewById(R.id.txtViewCrewShopJob3);
        txtViewCrewShopPrice3 = view.findViewById(R.id.txtViewCrewShopPrice3);
        txtViewPoor3 = view.findViewById(R.id.txtViewPoor3);
        btnBuy3 = view.findViewById(R.id.btnBuy3);

        btnExit = view.findViewById(R.id.btnExitShop);

        showCrewImg1 = view.findViewById(R.id.showCrewImg1);
        showCrewImg2 = view.findViewById(R.id.showCrewImg2);
        showCrewImg3 = view.findViewById(R.id.showCrewImg3);

        shopNameArr = List.of(txtViewShopName1, txtViewShopName2, txtViewShopName3);
        shopJobArr = List.of(txtViewShopJob1, txtViewShopJob2, txtViewShopJob3);
        shopCostArr = List.of(txtViewCrewShopPrice1, txtViewCrewShopPrice2, txtViewCrewShopPrice3);
        shopBuyBtnArr = List.of(btnBuy1, btnBuy2, btnBuy3);
        shopPoorArr = List.of(txtViewPoor1, txtViewPoor2, txtViewPoor3);
        showCrewImgArr = List.of(showCrewImg1, showCrewImg2, showCrewImg3);
    }

    private void setView() {
        if (shopableCrew == null || shopableCrew.isEmpty()) return;
        
        List<Crew> crews = new ArrayList<>(shopableCrew.keySet());
        for (int i = 0; i < Math.min(crews.size(), 3); i++) {
            Crew crew = crews.get(i);
            int cost = shopableCrew.get(crew);
            showCrewImgArr.get(i).setImageResource(getCrewImgRes(crew));
            shopNameArr.get(i).setText(crew.getName());
            shopJobArr.get(i).setText(getJobName(crew));
            shopCostArr.get(i).setText(String.format(Locale.US, "%d $", cost));
        }
    }

    private String getJobName(Crew crew) {
        if (crew instanceof Commander) return "Commander";
        if (crew instanceof Gunner) return "Gunner";
        if (crew instanceof Medic) return "Medic";
        if (crew instanceof Navigator) return "Navigator";
        if (crew instanceof Technician) return "Technician";
        return "Unknown";
    }

    private int getCrewImgRes(Crew crew) {
        if (crew instanceof Medic) {
            return R.drawable.medic;
        } else if (crew instanceof Gunner) {
            return R.drawable.gunner;
        } else if (crew instanceof Commander) {
            return R.drawable.commander;
        } else if (crew instanceof Navigator) {
            return R.drawable.navigator;
        } else if (crew instanceof Technician) {
            return R.drawable.technician;
        } else {
            return android.R.drawable.sym_def_app_icon;
        }
    }

    private void setupButton() {
        for (int i = 0; i < 3; i++) {
            final int index = i;
            shopBuyBtnArr.get(i).setOnClickListener(v -> {
                // Use the buyCrew logic from Shop class
                if (shop.buyCrew(index)) {
                    shopBuyBtnArr.get(index).setEnabled(false);
                    shopBuyBtnArr.get(index).setText("Bought");
                    shopPoorArr.get(index).setVisibility(View.GONE);
                    
                    // Update activity money display
                    if (getActivity() instanceof ShopActivity) {
                        ((ShopActivity) getActivity()).updateMoneyDisplay();
                    }
                } else {
                    shopPoorArr.get(index).setVisibility(View.VISIBLE);
                }
            });
        }

        btnExit.setOnClickListener(v -> {
            View overlay = getActivity().findViewById(R.id.shopOverlayFragmentContainer);
            if (overlay != null) {
                overlay.setVisibility(View.GONE);
            }
        });
    }
}