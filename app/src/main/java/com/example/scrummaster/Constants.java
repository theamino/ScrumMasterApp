package com.example.scrummaster;

public class Constants {
    // JSON Node names
    public static final String TAG_UNITS = "units";
    public static final String TAG_UNITID = "unitid";
    public static final String TAG_VOIPNUMS = "voipnums";
    public static final String TAG_USED = "used";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_UNIT = "unit";
    public static final String TAG_NUM = "num";
    public static final String TAG_VOIPNUM = "voipnum";
    public static final String TAG_OWNER = "owner";
    public static final String TAG_PASS = "pass";
    public static final String TAG_TIMEOUT = "timeout";
    public static final String TAG_ACCESS = "access";
    public static final String TAG_ACCESS2 = "access2";
    public static final String TAG_MOBVOIPNUM = "mobvoipnum";
    public static final String TAG_MOBTIMEOUT = "mobtimeout";


    //URLs
    //uri to create unit
    public static final String url_create_unit = "http://192.168.1.101/create_unit.php";
    //    public static final String url_create_unit = "http://cobacodoorbellpanel.local/create_unit.php";
    //url to get 2 free voipnum from server
    public static final String url_get_voipnum = "http://192.168.1.101/get_voip_num.php";
    //    public static final String url_get_voipnum = "http://cobacodoorbellpanel.local/get_voip_num.php";
    //url to delete unit
    public static final String url_delete_unit = "http://192.168.1.101/delete_unit.php";
    //    public static final String url_delete_unit = "http://cobacodoorbellpanel.local/delete_unit.php";
    //url to set voipnum status ("used" = 1 and "not used" = 0)
    public static final String url_update_voipnum = "http://192.168.1.101/update_voipnum.php";
    //    public static final String url_update_voipnum = "http://cobacodoorbellpanel.local/update_voipnum.php";
    // single unit url
    public static final String url_unit_detials = "http://192.168.1.101/get_unit_details.php";
    //    public static final String url_unit_detials = "http://cobacodoorbellpanel.local/get_unit_details.php";
    // url to update unit
    public static final String url_update_unit = "http://192.168.1.101/update_unit.php";
    //    public static final String url_update_unit = "http://cobacodoorbellpanel.local/update_unit.php";
    //url to load units list
    public static String url_all_units = "http://192.168.1.101/get_all_units.php";
    //    public static String url_all_units = "http://cobacodoorbellpanel.local/get_all_units.php";
    //url to authenticate
    public static String url_auth_unit = "http://192.168.1.101/auth_unit.php";
//    public static String url_auth_unit = "http://cobacodoorbellpanel.local/auth_unit.php";
}
