package com.example.foursquare.nearby.ui.adapterr;

/**
 * Class representing callback functions from presenter to set data to set
 *
 * @author Shubham
 */
public interface IViewHolder {

    /*
     * Method to set venue name to ui
     *
     * @param name : name of venue
     * */
    void setName(String name);

    /*
     * Method to set venue image to ui
     *
     * @param url : image url of venue
     * */
    void setImageUrl(String url);

    /*
     * Method to set venue category name to ui
     *
     * @param name : name of venue category
     * */
    void setCategory(String catName);

    /*
     * Method to set venue city to ui
     *
     * @param city : city of venue
     * */
    void setCity(String city);

    /*
     * Method to set venue state to ui
     *
     * @param state : state of venue
     * */
    void setState(String state);

    /*
     * Method to set venue country to ui
     *
     * @param country : country of venue
     * */
    void setCountry(String country);

    /*
     * Method to set venue place to ui
     *
     * @param place : place of venue
     * */
    void setPlace(String place);
}
