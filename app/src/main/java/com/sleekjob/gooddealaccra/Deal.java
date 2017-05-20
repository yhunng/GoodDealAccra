package com.sleekjob.gooddealaccra;

import android.os.Parcel;
import android.os.Parcelable;


public class Deal implements Parcelable {
    public String id;
    public String user_id;
    public String title;
    public String price;
    public int discount;
    public String image;
    public String location;
    public String description;
    public String contact;
    public String status;


    protected Deal(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        title = in.readString();
        price = in.readString();
        discount = in.readInt();
        image = in.readString();
        location = in.readString();
        description = in.readString();
        contact = in.readString();
        status = in.readString();
    }

    public static final Creator<Deal> CREATOR = new Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeInt(discount);
        dest.writeString(image);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(contact);
        dest.writeString(status);
    }
}
