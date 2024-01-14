package com.politecnicomalaga.NasdaqOilPrices.Control;

import com.politecnicomalaga.NasdaqOilPrices.Model.Price;
import com.politecnicomalaga.NasdaqOilPrices.View.MainActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainController {

    //SINGLETON Controller
    private static final String DATA_URL = "https://data.nasdaq.com/api/v3/datatables/QDL/OPEC.json?";
    private static MainController mySingleController;

    private List<Price> dataRequested;
    private List<Price> dataShowed;
    private static MainActivity activeActivity;
    private ArrayList<Double> preciosRes;
    //Comportamiento
    //Constructor
    private MainController() {

        dataRequested = new ArrayList<Price>();
        dataShowed = new ArrayList<Price>();
        preciosRes = new ArrayList<Double>();
    }

    //Get instance
    public static MainController getSingleton() {
        if (MainController.mySingleController == null) {
            mySingleController = new MainController();
        }
        return mySingleController;
    }

    //To send data to view
    public List<Price> getDataFromNasdaq() {
        return this.dataRequested;
    }

    //Called from the view
    public void requestDataFromNasdaq() {
        Peticion p = new Peticion();
        p.requestData(DATA_URL);
    }

    //Called when onResponse is OK
    public void setDataFromNasdaq(String json) {

        Respuesta answer = new Respuesta(json);
        dataRequested = answer.getData();
        dataShowed.clear();
        for(Price p:dataRequested) {
            dataShowed.add(p);
        }
        //Load data on the list
        this.preciosRes = answer.getPrecios();
        MainController.activeActivity.accessData();
    }

    public void setErrorFromNasdaq(String error) {

        //Load data on the list
        MainController.activeActivity.errorData(error);
    }


    public static void setActivity(MainActivity myAct) {
        activeActivity = myAct;
    }

    public List<Price> getList() {
        return this.dataShowed;
    }
    public ArrayList<Double> getPreciosRes() {
        return this.preciosRes;
    }


}
