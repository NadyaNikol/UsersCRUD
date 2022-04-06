package com.example.usersCRUD.validations;

import com.example.usersCRUD.models.User;

public class UserValidation {
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String nameAndEmailFieldsCheck(User user){
        if(user.getName()==null){
            return "Error. Specify the name";
        } else if(user.getEmail()==null){
            return "Error. Specify the email";
        }
        return "ok";
    }

}
