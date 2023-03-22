package com.example.wmandroid.Utils;

import android.text.TextUtils;
import android.widget.Toast;

public class Regex {
    public static final String name_vietnamese = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s|_]+$";
    public static final String phone_vietnamese = "^(0|(84)|(\\+84)?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    public static final String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\\s*$";





    public static boolean isValidPhone(String phone){
        if(phone.matches(Regex.phone_vietnamese)){
            return true;
        }
        return false;
    };

    public static boolean isValidName(String name){
        if(name.matches(Regex.name_vietnamese)){
            return true;
        }
        return false;
    };
    public static boolean isValidUsername(String username){
        if(username.length() >= 5 && username.length() <= 20 && !username.matches("[^a-zA-Z0-9]+")){
            return true;
        }
        return false;
    };

   public static boolean isValidPassword(String password){
       if(password.length() >= 5 && password.length() <= 20){
            return true;
       }
       return false;
   }

    public static boolean isValidEmail(String email){
        if(email.matches(Regex.email)){
            return true;
        }
        return false;
    };

//    public static boolean isGenderChecked()
}
