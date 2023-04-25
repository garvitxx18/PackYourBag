package com.example.packyourbag.Data;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.packyourbag.Constants.MyConstants;
import com.example.packyourbag.Database.RoomDB;
import com.example.packyourbag.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {


    RoomDB database;
    String category;
    Context context;



    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;


    public AppData(RoomDB database) {
        this.database = database;
    }


    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }
    public List<Items> getBasicData() {

        category = "Basic Needs";
        List<Items> basicItem = new ArrayList<Items>();
        basicItem.clear();
        basicItem.add(new Items("Visa", category, false));
        basicItem.add(new Items("Passport", category, false));
        basicItem.add(new Items("Tickets", category, false));
        basicItem.add(new Items("Wallet", category, false));
        basicItem.add(new Items("Driving License", category, false));
        basicItem.add(new Items("Currency", category, false));
        basicItem.add(new Items("House Key", category, false));
        basicItem.add(new Items("Book", category, false));
        basicItem.add(new Items("Travel Pillow", category, false));
        basicItem.add(new Items("Eye Patch", category, false));
        basicItem.add(new Items("Umbrella", category, false));
        basicItem.add(new Items("Note Book", category, false));

        return basicItem;



    }

    public  List<Items> getPersonalCareData() {

        String[] data = {"Tooth-brush", "Tooth-paste", "Floss", "Mouthwash", "Shaving Cream", "Razor Blade", "Soap", "Fibre"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE,data);



    }


    public  List<Items> getClothingData() {

        String[] data = {"Stockings", "Underwear", "Pajamas", "T - shirts", "Casual Dress", "Evening Dress", "Shirt", "Vest"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE,data);



    }


    public  List<Items> getBabyNeedsData() {

        String[] data = {"Snapsuit", "Outfit", "Jumpsuit", "T - shirts", "Baby Socks", "Baby Hat", "Shirt", "Pajamas"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE,data);



    }




    public  List<Items> getHealthData() {

        String[] data = {"Aspirin", "Drugs Used", "Lens Solution", "First Aid Kit", "Pain Reliever", "Fever Reducer"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE,data);



    }

    public  List<Items> getTechnologyData() {

        String[] data = {"Mobile Phone", "E - Book Reader", "Camera", "Portable Speaker", "IPad", "Headphone", "Laptop", "Laptop Charger"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE,data);



    }

    public  List<Items> getFoodDataData() {

        String[] data = {"Snacks", "Sandwich", "Juice", "Tea Bags", "Coffee", "Water", "Biscuits"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE,data);



    }

    public  List<Items> getBeachSuppliesData() {

        String[] data = {"Sea Glasses", "Sun Cream", "Beach Umbrellla", "Beach Bag", "Beach Towel", "Beach Slippers", "Waterproof Clock"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE,data);



    }



    public  List<Items> getCarSuppliesData() {

        String[] data = {"Pump", "Car Jack", "Spare CAr Key", "Accident Record Set ", "Car Cover", "Window Sun Shades"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE,data);



    }

    public  List<Items> getNeedsData() {

        String[] data = {"Backpack", "Daily Bags ", "Laundary Bag", "Travel Lock", "Magazine", "Sports Equipment", "Shirt", "Vest"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE,data);



    }








    public  List<Items> prepareItemsList(String category, String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();;



        for(int i=0 ; i< list.size(); i++){



            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;

    }
    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodDataData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getNeedsData());
        return listOfAllItems;




    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for(List<Items> list: listOfAllItems){

            for(Items items: list){

                database.mainDao().saveItem(items);

            }


        }
        System.out.println("Data added.");

    }


    public void persistDataByCategory(String category, Boolean onlyDelete){


        try{

            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);
            if(!onlyDelete){

                for (Items item : list){
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context,category +  "Reset Successfully", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(context,category + "Reset Successfully", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception ex){

            ex.printStackTrace();
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();

        }

    }







    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete){

        if(onlyDelete){
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        }else{
            database.mainDao().deleteAllByCategory(category);

        }
        switch (category){
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();


            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();


            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();


            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();



            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodDataData();


            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();


            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();


            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();


            default:
                return new ArrayList<>();




        }



    }






}
