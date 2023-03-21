package com.example.wmandroid.Utils;

public class Regex {
    public static final String name_vietnamese = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s|_]+$";
    public static final String phone_vietnamese = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    public static final String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

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
    public static boolean isValidEmail(String email){
        if(email.matches(Regex.email)){
            return true;
        }
        return false;
    };
}
