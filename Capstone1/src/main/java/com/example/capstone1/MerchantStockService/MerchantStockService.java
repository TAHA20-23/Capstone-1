package com.example.capstone1.MerchantStockService;

import com.example.capstone1.MerchantStockModel.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {

    private ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    // 1. Get all Merchant Stocks
    public ArrayList<MerchantStock> getAllMerchantStocks() {
        return merchantStocks;
    }

    // 2. Add a Merchant Stock
    public boolean addMerchantStock(MerchantStock merchantStock) {
        for (MerchantStock ms : merchantStocks) {
            if (ms.getId() == merchantStock.getId()) {
                return false;
            }
        }
        merchantStocks.add(merchantStock);
        return true;
    }

    // 3. Update a Merchant Stock
    public boolean updateMerchantStock(int id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == id) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    // 4. Delete a Merchant Stock
    public boolean deleteMerchantStock(int id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == id) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    // 5. Search a Merchant Stock by ID
    public MerchantStock getMerchantStockById(int id) {
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId() == id) {
                return merchantStock;
            }
        }
        return null;
    }


    //-------------------------------------------------------------------------

    // 6.  ِAdd additional stock to a product for a specific merchant
    // // Modify stock after a purchase
    public boolean addStock(int productId, int merchantId, int additionalStock) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId() == productId && stock.getMerchantId() == merchantId) {
                // Adding the additional stock to the current stock
                stock.setStock(stock.getStock() + additionalStock);
                return true;  // Stock updated successfully
            }
        }
        return false;  // Product or Merchant not found
    }

    // 7. Reduce stock
    public boolean reduceStock(int productId, int merchantId, int quantity) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId() == productId && stock.getMerchantId() == merchantId) {
                if (stock.getStock() >= quantity) {
                    stock.setStock(stock.getStock() - quantity);
                    return true;
                }
            }
        }
        return false;
    }


    // Extra 1
    //==============================================================



}
