package com.example.online_shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class OnlineShopping_DB extends SQLiteOpenHelper {
    private static String databaseName="App_Database";
    SQLiteDatabase App_Database;

    public OnlineShopping_DB(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customers(CID integer primary key Autoincrement, Name text not null , Email text not null , Password text not null , Phone text not null ,Job text not null , Birthdate text not null ,Gender text not null, SQuestion text  , QAnswer text ) ");
        db.execSQL("create table Orders(OrdID integer primary key Autoincrement, Name text not null )" );
        db.execSQL("create table Categories(CatID integer primary key Autoincrement, CatName text not null )" );
        db.execSQL("create table Products(ProID integer primary key Autoincrement," +
        " ProName text not null ,ProDesc text, Price int not null , Quantity integer not null , CatID integer ,FOREIGN KEY(CatID) REFERENCES Categories(CatID))");
        db.execSQL("create table OrderDetails(OrdID integer , ProID integer ,primary Key (OrdID,ProID) ,FOREIGN KEY(ProID) REFERENCES Products(ProID) ,FOREIGN KEY(OrdId) REFERENCES Orders(OrdID))" );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists OrdersDetils");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        onCreate(db);
    }

    public void CreateNewCustomer(Customer customer )
    {
        try {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",customer.getName());
        contentValues.put("Email",customer.getEmail());
        contentValues.put("Password",customer.getPassword());
        contentValues.put("Phone",customer.getPhone());
        contentValues.put("Job",customer.getName());
        contentValues.put("Birthdate",customer.getBirthday());
        contentValues.put("Gender",customer.getGender());
        contentValues.put("SQuestion",customer.getSQuestion());
        contentValues.put("QAnswer",customer.getSAnswer());
        App_Database =getWritableDatabase();
        App_Database.insert("Customers",null,contentValues);
        App_Database.close();
        }
        catch (Exception e)
        {

        }
    }
    public void CreateNewOrder(String order )
    {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("OrdID",order);
            App_Database =getWritableDatabase();
            App_Database.insert("OrderDetails",null,contentValues);
            App_Database.close();
        }
        catch (Exception e)
        {

        }
    }
    public void CreateNewOrderDetails(String orderid , List<Product> p )
    {
        try {
            ContentValues contentValues = new ContentValues();
            App_Database =getWritableDatabase();

            for (int i = 0 ; i < p.size() ; i++)
            {
                contentValues.put("OrdID",orderid);
                contentValues.put("ProID",p.get(i).getId());
                App_Database.insert("Orders",null,contentValues);
            }

            App_Database.close();
        }
        catch (Exception e)
        {

        }
    }
    public String Retrive_orderid(String name)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select OrdID from Orders where Name='"+name+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                String val  = cursor.getString(0);
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return "";
            }
        }
        return "";
    }
    public int Login_Validation(String username , String password)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select CID from Customers where Email='"+username+"' and Password='"+password+"'",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                int val  = Integer.parseInt(cursor.getString(0));
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return 0;
            }
        }
       return 0;
    }
    public void CreateCategories()
    {
        try {

            App_Database =getWritableDatabase();
            ContentValues row1 = new ContentValues();
            row1.put("CatName","Supermarket");
            App_Database.insert("Categories",null,row1);

            App_Database =getWritableDatabase();
            ContentValues row2 = new ContentValues();
            row2.put("CatName","Electronics");
            App_Database.insert("Categories",null,row2);

            App_Database =getWritableDatabase();
            ContentValues row3 = new ContentValues();
            row3.put("CatName","Home & Kitchen");
            App_Database.insert("Categories",null,row3);

            App_Database =getWritableDatabase();
            ContentValues row4 = new ContentValues();
            row4.put("CatName","Health And Beauty");
            App_Database.insert("Categories",null,row4);

            App_Database =getWritableDatabase();
            ContentValues row5 = new ContentValues();
            row5.put("CatName","Mom & Babies");
            App_Database.insert("Categories",null,row5);

            App_Database =getWritableDatabase();
            ContentValues row6 = new ContentValues();
            row6.put("CatName","Kids & Toys");
            App_Database.insert("Categories",null,row6);


            App_Database =getWritableDatabase();
            ContentValues row7 = new ContentValues();
            row7.put("CatName","Sports");
            App_Database.insert("Categories",null,row7);

            App_Database =getWritableDatabase();
            ContentValues row8 = new ContentValues();
            row8.put("CatName","Clothes");
            App_Database.insert("Categories",null,row8);


            App_Database.close();
        }
        catch (Exception e) { }
    }

    public void CreateProducts()
    {
        try {
            App_Database =getWritableDatabase();
            ContentValues row1 = new ContentValues();
            row1.put("ProName","Red Off Shoulder T-Shirt");
            row1.put("ProDesc", "Cotton t-shirt found in 3 different sizes");
            row1.put("Price",99);
            row1.put("Quantity",30);
            row1.put("CatID",8);
            App_Database.insert("Products",null,row1);
            App_Database =getWritableDatabase();
            ContentValues row2 = new ContentValues();
            row2.put("ProName","White Slim T-Shirt");
            row2.put("ProDesc", "not printed t-shirt found in 3 different sizes");
            row2.put("Price",45);
            row2.put("Quantity",20);
            row2.put("CatID",8);
            App_Database.insert("Products",null,row2);
            App_Database =getWritableDatabase();
            ContentValues row3 = new ContentValues();
            row3.put("ProName","Traditional Blue T-Shirt");
            row3.put("ProDesc", "t-shirt found in 3 different sizes");
            row3.put("Price",77);
            row3.put("Quantity",27);
            row3.put("CatID",8);
            App_Database.insert("Products",null,row3);
            App_Database =getWritableDatabase();
            ContentValues row4 = new ContentValues();
            row4.put("ProName","Grey Short Dress");
            row4.put("ProDesc", "Glitered Dress littel transparent from the top");
            row4.put("Price",200);
            row4.put("Quantity",40);
            row4.put("CatID",8);
            App_Database.insert("Products",null,row4);
            App_Database =getWritableDatabase();
            ContentValues row5 = new ContentValues();
            row5.put("ProName","Mini Brown Dress");
            row5.put("ProDesc", "Silk Dress with thin shoulders");
            row5.put("Price",269);
            row5.put("Quantity",30);
            row5.put("CatID",8);
            App_Database.insert("Products",null,row5);
            App_Database =getWritableDatabase();
            ContentValues row6 = new ContentValues();
            row6.put("ProName","Black Dress");
            row6.put("ProDesc", "Black Lined Dress");
            row6.put("Price",202);
            row6.put("Quantity",33);
            row6.put("CatID",8);
            App_Database.insert("Products",null,row6);
            App_Database =getWritableDatabase();
            ContentValues row7 = new ContentValues();
            row7.put("ProName","Coffe Trouser");
            row7.put("ProDesc", "pop wide leg Coffe Trouser");
            row7.put("Price",198);
            row7.put("Quantity",27);
            row7.put("CatID",8);
            App_Database.insert("Products",null,row7);
            App_Database =getWritableDatabase();
            ContentValues row8 = new ContentValues();
            row8.put("ProName","White Trouser");
            row8.put("ProDesc", "White Slim leg Trouser");
            row8.put("Price",178);
            row8.put("Quantity",22);
            row8.put("CatID",8);
            App_Database.insert("Products",null,row8);
            App_Database =getWritableDatabase();
            ContentValues row9 = new ContentValues();
            row9.put("ProName","Black Trouser");
            row9.put("ProDesc", "Black Slim leg Trouser");
            row9.put("Price",188);
            row9.put("Quantity",42);
            row9.put("CatID",8);
            App_Database.insert("Products",null,row9);
            App_Database =getWritableDatabase();
            ContentValues row10 = new ContentValues();
            row10.put("ProName","Black Madison Suit");
            row10.put("ProDesc", "Found in 5 Different Sizes");
            row10.put("Price",608);
            row10.put("Quantity",12);
            row10.put("CatID",8);
            App_Database.insert("Products",null,row10);
            App_Database =getWritableDatabase();
            ContentValues row11 = new ContentValues();
            row11.put("ProName","Grey Suit");
            row11.put("ProDesc", "Found in 5 Different Sizes");
            row11.put("Price",428);
            row11.put("Quantity",32);
            row11.put("CatID",8);
            App_Database.insert("Products",null,row11);
            App_Database =getWritableDatabase();
            ContentValues row12 = new ContentValues();
            row12.put("ProName","Green Socks");
            row12.put("ProDesc", "long cotton socks");
            row12.put("Price",30);
            row12.put("Quantity",48);
            row12.put("CatID",8);
            App_Database.insert("Products",null,row12);
            App_Database =getWritableDatabase();
            App_Database =getWritableDatabase();
            ContentValues row13 = new ContentValues();
            row13.put("ProName","Grey Shoes");
            row13.put("ProDesc", "grey and blue colored sporty shoes");
            row13.put("Price",179);
            row13.put("Quantity",28);
            row13.put("CatID",8);
            App_Database.insert("Products",null,row13);
            App_Database =getWritableDatabase();
            ContentValues row14 = new ContentValues();
            row14.put("ProName","White Shoes");
            row14.put("ProDesc", "White sporty shoes");
            row14.put("Price",140);
            row14.put("Quantity",48);
            row14.put("CatID",8);
            App_Database.insert("Products",null,row14);
            App_Database =getWritableDatabase();
            ContentValues row15 = new ContentValues();
            row15.put("ProName","Molifex Diapers");
            row15.put("ProDesc", "Cotton Comfortable diapers for your baby");
            row15.put("Price",150);
            row15.put("Quantity",34);
            row15.put("CatID",5);
            App_Database.insert("Products",null,row15);
            App_Database =getWritableDatabase();
            ContentValues row16 = new ContentValues();
            row16.put("ProName","Pink Baby Car");
            row16.put("ProDesc", "Moving baby car with control hand ");
            row16.put("Price",350);
            row16.put("Quantity",29);
            row16.put("CatID",5);
            App_Database.insert("Products",null,row16);
            App_Database =getWritableDatabase();
            ContentValues row17 = new ContentValues();
            row17.put("ProName","Grey Baby Chair");
            row17.put("ProDesc", "Grey rocking baby chair");
            row17.put("Price",336);
            row17.put("Quantity",19);
            row17.put("CatID",5);
            App_Database.insert("Products",null,row17);
            App_Database =getWritableDatabase();
            ContentValues row18 = new ContentValues();
            row18.put("ProName","Pampers Diapers");
            row18.put("ProDesc", "soften diapers ");
            row18.put("Price",188);
            row18.put("Quantity",59);
            row18.put("CatID",5);
            App_Database.insert("Products",null,row18);
            App_Database =getWritableDatabase();
            ContentValues row19 = new ContentValues();
            row19.put("ProName","Brown Hig Heels");
            row19.put("ProDesc", "Comfortable heels");
            row19.put("Price",130);
            row19.put("Quantity",37);
            row19.put("CatID",8);
            App_Database.insert("Products",null,row19);
            App_Database =getWritableDatabase();
            ContentValues row20 = new ContentValues();
            row20.put("ProName","Samsung Refrigerator");
            row20.put("ProDesc", "Full option Refregirator with water holder");
            row20.put("Price",60000);
            row20.put("Quantity",17);
            row20.put("CatID",2);
            App_Database.insert("Products",null,row20);
            App_Database =getWritableDatabase();
            ContentValues row21 = new ContentValues();
            row21.put("ProName","Ps5 Playstation");
            row21.put("ProDesc", "New Playstation with alot of features");
            row21.put("Price",5000);
            row21.put("Quantity",49);
            row21.put("CatID",2);
            App_Database.insert("Products",null,row21);
            App_Database =getWritableDatabase();
            ContentValues row22 = new ContentValues();
            row22.put("ProName","LG Refrigerator");
            row22.put("ProDesc", " ");
            row22.put("Price",50000);
            row22.put("Quantity",23);
            row22.put("CatID",2);
            App_Database.insert("Products",null,row22);
            App_Database =getWritableDatabase();
            ContentValues row23 = new ContentValues();
            row23.put("ProName","Samsung TV");
            row23.put("ProDesc", "");
            row23.put("Price",7000);
            row23.put("Quantity",26);
            row23.put("CatID",2);
            App_Database.insert("Products",null,row23);
            App_Database =getWritableDatabase();
            ContentValues row24 = new ContentValues();
            row24.put("ProName","Oppo Mobile Phone");
            row24.put("ProDesc", " ");
            row24.put("Price",7000);
            row24.put("Quantity",22);
            row24.put("CatID",2);
            App_Database.insert("Products",null,row24);
            App_Database =getWritableDatabase();
            ContentValues row25 = new ContentValues();
            row25.put("ProName","jio Mobile Phone");
            row25.put("ProDesc", "buttons small mobile phone");
            row25.put("Price",7000);
            row25.put("Quantity",22);
            row25.put("CatID",2);
            App_Database.insert("Products",null,row25);
            App_Database =getWritableDatabase();
            ContentValues row26 = new ContentValues();
            row26.put("ProName","avent  Baby pacifier");
            row26.put("ProDesc", "help your baby to have better teeth");
            row26.put("Price",55);
            row26.put("Quantity",32);
            row26.put("CatID",5);
            App_Database.insert("Products",null,row26);
            App_Database =getWritableDatabase();
            ContentValues row27 = new ContentValues();
            row27.put("ProName","Apple Mobile Phone");
            row27.put("ProDesc", " ");
            row27.put("Price",23000);
            row27.put("Quantity",22);
            row27.put("CatID",2);
            App_Database.insert("Products",null,row27);
             App_Database =getWritableDatabase();
            ContentValues row28 = new ContentValues();
            row28.put("ProName","LG TV");
            row28.put("ProDesc", "LG TV LG UHD 4K TV 86 Inch UN80 Series, Cinema Screen Design 4K Active HDR WebOS Smart AI ThinQ");
            row28.put("Price",10000);
            row28.put("Quantity",25);
            row28.put("CatID",2);
            App_Database.insert("Products",null,row28);
            App_Database =getWritableDatabase();
            ContentValues row29 = new ContentValues();
            row29.put("ProName","LG NanoCell TV");
            row29.put("ProDesc", "LG NanoCell TV 65 inch NANO79 Series, 4K Active HDR, WebOS Smart AI ThinQ");
            row29.put("Price",10000);
            row29.put("Quantity",25);
            row29.put("CatID",2);
            App_Database.insert("Products",null,row29);
            App_Database =getWritableDatabase();
            ContentValues row30 = new ContentValues();
            row30.put("ProName","avent baby bottel");
            row30.put("ProDesc", " ");
            row30.put("Price",100);
            row30.put("Quantity",55);
            row30.put("CatID",5);
            App_Database.insert("Products",null,row30);
            App_Database =getWritableDatabase();
            ContentValues row31 = new ContentValues();
            row31.put("ProName","Chanel Perfume");
            row31.put("ProDesc", " ");
            row31.put("Price",300);
            row31.put("Quantity",35);
            row31.put("CatID",4);
            App_Database.insert("Products",null,row31);
            App_Database =getWritableDatabase();
            ContentValues row32 = new ContentValues();
            row32.put("ProName","Sensualite Perfume");
            row32.put("ProDesc", " ");
            row32.put("Price",250);
            row32.put("Quantity",20);
            row32.put("CatID",4);
            App_Database.insert("Products",null,row32);
            App_Database =getWritableDatabase();
            ContentValues row33 = new ContentValues();
            row33.put("ProName","Artdeco Black Eyeliner");
            row33.put("ProDesc", "fixed black eyeliner");
            row33.put("Price",100);
            row33.put("Quantity",21);
            row33.put("CatID",4);
            App_Database.insert("Products",null,row33);
            App_Database =getWritableDatabase();
            ContentValues row34 = new ContentValues();
            row34.put("ProName","Mac Black Masckara");
            row34.put("ProDesc", "for Long and heavy eyelashces");
            row34.put("Price",140);
            row34.put("Quantity",27);
            row34.put("CatID",4);
            App_Database.insert("Products",null,row34);
            App_Database =getWritableDatabase();
            ContentValues row35 = new ContentValues();
            row35.put("ProName","Face hero face Wash");
            row35.put("ProDesc", "Go To Skin Care FaceHero FaceOil Hero");
            row35.put("Price",170);
            row35.put("Quantity",21);
            row35.put("CatID",4);
            App_Database.insert("Products",null,row35);
            App_Database =getWritableDatabase();
            ContentValues row36 = new ContentValues();
            row36.put("ProName","Grapefruit Face Wash");
            row36.put("ProDesc", "visibly clear pink grapefruit");
            row36.put("Price",120);
            row36.put("Quantity",24);
            row36.put("CatID",4);
            App_Database.insert("Products",null,row36);
            App_Database =getWritableDatabase();
            ContentValues row37 = new ContentValues();
            row37.put("ProName","Samsung Headphones");
            row37.put("ProDesc", " ");
            row37.put("Price",205);
            row37.put("Quantity",24);
            row37.put("CatID",2);
            App_Database.insert("Products",null,row37);
            App_Database =getWritableDatabase();
            ContentValues row38 = new ContentValues();
            row38.put("ProName","White Sofa");
            row38.put("ProDesc", "Long white comfortable sofa");
            row38.put("Price",490);
            row38.put("Quantity",54);
            row38.put("CatID",3);
            App_Database.insert("Products",null,row38);
            App_Database =getWritableDatabase();
            ContentValues row39 = new ContentValues();
            row39.put("ProName","Wooden Bed");
            row39.put("ProDesc", "163 X 163  comfortable Bed");
            row39.put("Price",6700);
            row39.put("Quantity",34);
            row39.put("CatID",3);
            App_Database.insert("Products",null,row39);
            App_Database =getWritableDatabase();
            ContentValues row40 = new ContentValues();
            row40.put("ProName","Wooden Combined Wardrobe");
            row40.put("ProDesc", " ");
            row40.put("Price",5000);
            row40.put("Quantity",25);
            row40.put("CatID",3);
            App_Database.insert("Products",null,row40);
            App_Database =getWritableDatabase();
            ContentValues row41 = new ContentValues();
            row41.put("ProName","Wooden Dining Tabel");
            row41.put("ProDesc", "long rectangel tabel with 4 chairs");
            row41.put("Price",5000);
            row41.put("Quantity",25);
            row41.put("CatID",3);
            App_Database.insert("Products",null,row41);
            App_Database =getWritableDatabase();
            ContentValues row42 = new ContentValues();
            row42.put("ProName","Modern Orange Kitchen");
            row42.put("ProDesc", "eligant and very modern");
            row42.put("Price",25000);
            row42.put("Quantity",23);
            row42.put("CatID",3);
            App_Database.insert("Products",null,row42);
            App_Database =getWritableDatabase();
            ContentValues row43 = new ContentValues();
            row43.put("ProName","Modern Orange Kitchen");
            row43.put("ProDesc", " ");
            row43.put("Price",2000);
            row43.put("Quantity",25);
            row43.put("CatID",3);
            App_Database.insert("Products",null,row43);
            App_Database =getWritableDatabase();
            ContentValues row44 = new ContentValues();
            row44.put("ProName","Blue Royal Chair");
            row44.put("ProDesc", " ");
            row44.put("Price",7000);
            row44.put("Quantity",30);
            row44.put("CatID",3);
            App_Database.insert("Products",null,row44);
            App_Database.close();
        }
        catch (Exception e)
        {
            System. out.println("In Exception block.");
        }
    }

    public Cursor RetriveProducts(String searchtext)
    {
        App_Database = getReadableDatabase();
        String[] namearray = {searchtext};
        Cursor cursor = App_Database.rawQuery("Select * from Products where ProName LIKE '%"+namearray[0]+"%'",null);
        if(cursor!=null)
            cursor.moveToFirst();
        App_Database.close();
        return cursor;
    }

    public Customer GetCustomerInfo(int id)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select * from Customers where CID ='"+id+"'",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                Customer c = new Customer(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getString(3),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(5),
                        cursor.getString(8),
                        cursor.getString(9)
                );
                App_Database.close();
                return c;
            }
            else {
                App_Database.close();
                return null;
            }
        }
        return null;
    }
    public void UpdateCustomerInfo(int id , Customer c)
    {
        App_Database=getWritableDatabase();
        ContentValues row =  new ContentValues();
        row.put("Name" , c.getName());
        row.put("Email" , c.getEmail());
        row.put("Password" , c.getPassword());
        row.put("Phone" , c.getPhone());
        row.put("Gender" , c.getGender());
        row.put("Birthdate" , c.getBirthday());
        row.put("Job" , c.getJob());
        App_Database.update("Customers",row,"CID ='"+id+"'",null);
        App_Database.close();
    }
    public void UpdatePassword(String username , String newpass)
    {
        App_Database=getWritableDatabase();
        //String Cid =String.valueOf(id);
        ContentValues row =  new ContentValues();
        row.put("Password" , newpass);
        App_Database.update("Customers",row,"Email ='"+username+"'",null);
        App_Database.close();
    }
    public int Retrive_id(String username)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select CID from Customers where Email='"+username+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                int val  = Integer.parseInt(cursor.getString(0));
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return 0;
            }
        }
        return 0;
    }
    public String Retrive_Email(int id)
    {
        App_Database = getReadableDatabase();
        String nid=String.valueOf(id) ;
        Cursor cursor = App_Database.rawQuery("Select Email from Customers where CID='"+nid+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                String val  = cursor.getString(0);
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return "";
            }
        }
        return "";
    }
    public String Retrive_Password(int id)
    {
        App_Database = getReadableDatabase();
        String nid=String.valueOf(id) ;
        Cursor cursor = App_Database.rawQuery("Select Password from Customers where CID='"+nid+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                String val  = cursor.getString(0);
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return "";
            }
        }
        return "";
    }
    public String Retrive_Security_Q(String username)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select SQuestion from Customers where Email='"+username+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                String val  = cursor.getString(0);
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return "";
            }
        }
        return "";
    }
    public String Retrive_Security_Q_question(String username)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select QAnswer from Customers where Email='"+username+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                String val  = cursor.getString(0);
                App_Database.close();
                return val;
            }
            else {
                App_Database.close();
                return "";
            }
        }
        return "";
    }
    public int Check_email(String username)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Select Email from Customers where Email='"+username+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                App_Database.close();
                return 1;
            }
            else {
                App_Database.close();
                return 0;
            }
        }
        return 0;
    }
    public int delete(String username)
    {
        App_Database = getReadableDatabase();
        Cursor cursor = App_Database.rawQuery("Delete from Customers where CID='"+username+"' ",null);
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                App_Database.close();
                return 1;
            }
            else {
                App_Database.close();
                return 0;
            }
        }
        return 0;
    }

}
