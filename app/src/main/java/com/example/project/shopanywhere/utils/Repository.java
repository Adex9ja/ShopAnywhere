package com.example.project.shopanywhere.utils;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.shopanywhere.MainActivity;
import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.data.DatabaseContract;
import com.example.project.shopanywhere.model.Item;
import com.example.project.shopanywhere.model.Store;
import com.example.project.shopanywhere.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by project on 7/24/2017.
 */

public class Repository {
    private Context context;
    private DatabaseReference mDatabase;
    public Repository(Context vcontext)
    {
        context = vcontext;
    }
    public void uploadFileStore(Uri filePath, String filename, final Store store) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference("images");
            StorageReference riversRef = storageReference.child(filename);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
                            mDatabase.child("Store").child(store.getPicture()).setValue(store);
                            progressDialog.dismiss();
                            Toast.makeText(context, "Successfully Uploaded!", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploading "  + "...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    public void uploadFileItem(Uri filePath, String filename, final Item item) {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference("images");
            StorageReference riversRef = storageReference.child(filename);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
                            mDatabase.child("Item").child(item.getPicture()).setValue(item);
                            progressDialog.dismiss();
                            Toast.makeText(context, "Successfully Uploaded!", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();
                            //and displaying error message
                            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploading "  + "...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    public void downLoadImage(String filename,final ImageView imageView){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/"+ filename);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).error(R.drawable.logo).resize(400,400).centerCrop().into(imageView);
            }
        });
    }
    public static String getPixName(){
        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        return  ("TG"+hour+""+minute+""+second+""+day+""+(month+1)+""+year);
    }
    public void addStoreChangeListener() {
       // context.getContentResolver().delete(DatabaseContract.CONTENT_URI, null, null);
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("Store").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Store store = dataSnapshot.getValue(Store.class);
                if (store != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.StoreColumns.DESCRIPTION, store.getDescription());
                    values.put(DatabaseContract.StoreColumns.LOCATION, store.getLocation());
                    values.put(DatabaseContract.StoreColumns.STORENAME, store.getName());
                    values.put(DatabaseContract.StoreColumns.PHONENO, store.getPhoneno());
                    values.put(DatabaseContract.StoreColumns.PICTURE, store.getPicture());
                    context.getContentResolver().insert(DatabaseContract.CONTENT_URI, values);
                    MainActivity.mAdapter.swapCursor(getStores());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Store store = dataSnapshot.getValue(Store.class);
                if (store != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.StoreColumns.DESCRIPTION, store.getDescription());
                    values.put(DatabaseContract.StoreColumns.LOCATION, store.getLocation());
                    values.put(DatabaseContract.StoreColumns.STORENAME, store.getName());
                    values.put(DatabaseContract.StoreColumns.PHONENO, store.getPhoneno());
                    values.put(DatabaseContract.StoreColumns.PICTURE, store.getPicture());
                    context.getContentResolver().update(DatabaseContract.CONTENT_URI_ID(store.getPicture()),values,null,null);
                    MainActivity.mAdapter.swapCursor(getStores());
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Store store = dataSnapshot.getValue(Store.class);
                if (store != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.StoreColumns.DESCRIPTION, store.getDescription());
                    values.put(DatabaseContract.StoreColumns.LOCATION, store.getLocation());
                    values.put(DatabaseContract.StoreColumns.STORENAME, store.getName());
                    values.put(DatabaseContract.StoreColumns.PHONENO, store.getPhoneno());
                    values.put(DatabaseContract.StoreColumns.PICTURE, store.getPicture());
                    context.getContentResolver().delete(DatabaseContract.CONTENT_URI_ID(store.getPicture()),null,null);
                    MainActivity.mAdapter.swapCursor(getStores());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addSItemChangeListener() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("Item").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.ItemColumns.ITEMSTORENAME, item.getStorename());
                    values.put(DatabaseContract.ItemColumns.ITEMNAME, item.getItemname());
                    values.put(DatabaseContract.ItemColumns.COLOR, item.getColor());
                    values.put(DatabaseContract.ItemColumns.PRICE, item.getItemprice());
                    values.put(DatabaseContract.ItemColumns.ITEMSPICTURE, item.getPicture());
                    context.getContentResolver().insert(DatabaseContract.CONTENT_URI_ITEM, values);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.ItemColumns.ITEMSTORENAME, item.getStorename());
                    values.put(DatabaseContract.ItemColumns.ITEMNAME, item.getItemname());
                    values.put(DatabaseContract.ItemColumns.COLOR, item.getColor());
                    values.put(DatabaseContract.ItemColumns.PRICE, item.getItemprice());
                    values.put(DatabaseContract.ItemColumns.ITEMSPICTURE, item.getPicture());
                    context.getContentResolver().update(DatabaseContract.CONTENT_URI__ITEM_ID(item.getPicture()), values,null,null);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);
                if (item != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.ItemColumns.ITEMSTORENAME, item.getStorename());
                    values.put(DatabaseContract.ItemColumns.ITEMNAME, item.getItemname());
                    values.put(DatabaseContract.ItemColumns.COLOR, item.getColor());
                    values.put(DatabaseContract.ItemColumns.PRICE, item.getItemprice());
                    values.put(DatabaseContract.ItemColumns.ITEMSPICTURE, item.getPicture());
                    context.getContentResolver().delete(DatabaseContract.CONTENT_URI__ITEM_ID(item.getPicture()),null,null);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public Cursor getStores() {
        return context.getContentResolver().query(DatabaseContract.CONTENT_URI,DatabaseContract.storeProjection(),null,null,null);
    }
    public void DeleteStore(String picturename){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deleting...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("Store").child(picturename).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                progressDialog.dismiss();
                Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void DeleteItem(String picturename){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deleting...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("Item").child(picturename).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                progressDialog.dismiss();
                Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateStore(Store store){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Updating");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("Store").child(store.getPicture()).setValue(store).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(context, "Updated Successfully! ", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void UpdateItem(Item item){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Updating...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("Item").child(item.getPicture()).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(context, "Updated Successfully! ", Toast.LENGTH_LONG).show();

            }
        });
    }
    public void AddNewAdmin(User user) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        user.setUserId(getPixName());
        progressDialog.setTitle("Saving Data");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("User").child(user.getUserId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(context, "Successfully Added!", Toast.LENGTH_LONG).show();
            }
        });



    }
    public void addAdminChangeListener() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.UserColumns.USERNAME, user.getUsername());
                    values.put(DatabaseContract.UserColumns.USERPASSWORD, user.getPassword());
                    values.put(DatabaseContract.UserColumns.USERID, user.getUserId());
                    context.getContentResolver().insert(DatabaseContract.CONTENT_URI_USER, values);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.UserColumns.USERNAME, user.getUsername());
                    values.put(DatabaseContract.UserColumns.USERPASSWORD, user.getPassword());
                    values.put(DatabaseContract.UserColumns.USERID, user.getUserId());
                    context.getContentResolver().update(DatabaseContract.CONTENT_URI__USER_ID(user.getUserId()),values,null,null);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.UserColumns.USERNAME, user.getUsername());
                    values.put(DatabaseContract.UserColumns.USERPASSWORD, user.getPassword());
                    values.put(DatabaseContract.UserColumns.USERID, user.getUserId());
                    context.getContentResolver().delete(DatabaseContract.CONTENT_URI__USER_ID(user.getUserId()),null,null);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void UpdateUser(User user){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Updating");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("User").child(user.getUserId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(context, "Updated Successfully! ", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void DeleteUser(String userId){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Deleting...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Shop Anywhere");
        mDatabase.child("User").child(userId).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                progressDialog.dismiss();
                Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
