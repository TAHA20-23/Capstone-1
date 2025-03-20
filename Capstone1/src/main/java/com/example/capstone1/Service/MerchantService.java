package com.example.capstone1.Service;


import com.example.capstone1.Model.Merchant;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {

    private ArrayList<Merchant> merchants = new ArrayList<>();

    private MerchantService merchantService;
    // 1. Get all Merchants
    public ArrayList<Merchant> getAllMerchants() {
        return merchants;
    }

    // 2. Add a Merchant
    public boolean addMerchant(Merchant merchant) {
        for (Merchant m : merchants) {
            if (m.getId() == merchant.getId()) {
                return false;
            }
        }
        if (merchantService.getMerchantById(merchant.getMerchantId())==null){
            return false;
        }
        merchants.add(merchant);
        return true;
    }

    // 3. Update a Merchant
    public boolean updateMerchant(int id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    // 4. Delete a Merchant
    public boolean deleteMerchant(int id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }

    // 5. Search a Merchant by ID
    public Merchant getMerchantById(int id) {
        for (Merchant merchant : merchants) {
            if (merchant.getId() == id) {
                return merchant;
            }
        }
        return null;
    }
}
