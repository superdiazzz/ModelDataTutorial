package com.example.android.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.data.database.ItemTables;

import java.util.UUID;

/**
 * Created by User on 04/05/2017.
 */

public class DataItem implements Parcelable {

    private String itemID;
    private String itemName;
    private String description;
    private String category;
    private int sortPotition;
    private double price;
    private String image;


    public DataItem() {
    }

    public DataItem(String itemID, String itemName, String description, String category, int sortPotition, double price, String image) {

        if(itemID == null){
            itemID = UUID.randomUUID().toString();
        }

        this.itemID = itemID;
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.sortPotition = sortPotition;
        this.price = price;
        this.image = image;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSortPotition() {
        return sortPotition;
    }

    public void setSortPotition(int sortPotition) {
        this.sortPotition = sortPotition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ContentValues toContentValues(){
        ContentValues contentValues = new ContentValues(7);
        contentValues.put(ItemTables.COLUMN_ID, itemID);
        contentValues.put(ItemTables.COLUMN_NAME, itemName);
        contentValues.put(ItemTables.COLUMN_CATEGORY,category);
        contentValues.put(ItemTables.COLUMN_DESCRIPTION, description);
        contentValues.put(ItemTables.COLUMN_PRICE, price);
        contentValues.put(ItemTables.COLUMN_POSITION, sortPotition);
        contentValues.put(ItemTables.COLUMN_IMAGE, image);
        return contentValues;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "itemID='" + itemID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", sortPotition=" + sortPotition +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemID);
        dest.writeString(this.itemName);
        dest.writeString(this.description);
        dest.writeString(this.category);
        dest.writeInt(this.sortPotition);
        dest.writeDouble(this.price);
        dest.writeString(this.image);
    }

    protected DataItem(Parcel in) {
        this.itemID = in.readString();
        this.itemName = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.sortPotition = in.readInt();
        this.price = in.readDouble();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}
