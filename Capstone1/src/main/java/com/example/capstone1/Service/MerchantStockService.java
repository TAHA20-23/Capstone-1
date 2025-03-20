package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
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
    public MerchantStock getMerchantStockByProductAndMerchant(int productId, int merchantId) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId() == productId && stock.getMerchantId() == merchantId) {
                return stock;
            }
        }
        return null;
    }

    // 6. Add additional stock to a product for a specific merchant
    public boolean addStock(int productId, int merchantId, int additionalStock) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId() == productId && stock.getMerchantId() == merchantId) {
                stock.setStock(stock.getStock() + additionalStock);
                return true;
            }
        }
        return false;
    }

    // 8. get by id
    public MerchantStock getMerchantStockById(int id) {
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId() == id) {
                return merchantStock;
            }
        }
        return null; // Return null if no matching stock is found
    }




    // 7. Reduce stock and send alert if stock is low
    public boolean reduceStock(int productId, int merchantId, int quantity) {
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductId() == productId && stock.getMerchantId() == merchantId) {
                if (stock.getStock() >= quantity) {
                    stock.setStock(stock.getStock() - quantity); // ← تصحيح، ينقص العدد المطلوب
                    return true;
                }
            }
        }
        return false;
    }


    // 9. Send low stock alert to merchant
    private void sendLowStockAlert(int merchantId, int productId, int currentStock) {
        System.out.println("⚠️ ALERT: Merchant ID " + merchantId +
                " has low stock for Product ID " + productId +
                ". Current stock: " + currentStock);
    }
}