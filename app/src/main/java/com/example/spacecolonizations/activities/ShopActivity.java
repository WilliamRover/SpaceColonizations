package com.example.spacecolonizations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolonizations.R;
import com.example.spacecolonizations.model.shop.Wallet;

import java.util.Locale;

public class ShopActivity extends AppCompatActivity {
    private TextView money;
    private View shopOverlay;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        
        money = findViewById(R.id.txtViewMoneyNum2);
        shopOverlay = findViewById(R.id.shopOverlayFragmentContainer);
        
        updateMoneyDisplay();
        setupButton();
    }
    
    public void updateMoneyDisplay() {
        money.setText(String.format(Locale.US, "%d $", Wallet.getInstance().getBalance()));
    }

    private void setupButton() {
        Button btnOpenShop = findViewById(R.id.btnOpenShop);
        btnOpenShop.setOnClickListener(v -> {
            if (shopOverlay != null) {
                shopOverlay.setVisibility(View.VISIBLE);
            }
        });

        Button btnExitToMap = findViewById(R.id.btnExitToMap);
        btnExitToMap.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
    }
}