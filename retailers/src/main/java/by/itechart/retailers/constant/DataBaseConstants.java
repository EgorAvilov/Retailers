package by.itechart.retailers.constant;

public class DataBaseConstants {

    public static final String ADDRESS_TABLE = "address";
    public static final String ADDRESS_STATE = "state_id";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_FIRST_ADDRESS_LINE = "first_address_line";
    public static final String ADDRESS_SECOND_ADDRESS_LINE = "second_address_line";

    public static final String APPLICATION_RECORD_TABLE = "application_record";
    public static final String APPLICATION_RECORD_PRODUCT = "product_id";
    public static final String APPLICATION_RECORD_AMOUNT = "amount";
    public static final String APPLICATION_RECORD_COST = "cost";

    public static final String BILL_TABLE = "bill";
    public static final String BILL_BILL_NUMBER = "bill_number";
    public static final String BILL_LOCATION = "location_id";
    public static final String BILL_SHOP_MANAGER = "manager_id";
    public static final String BILL_REGISTRATION_DATE_TIME = "registration_date_time";
    public static final String BILL_RECORD_LIST = "bill_id";
    public static final String BILL_TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public static final String BILL_TOTAL_PRODUCT_PRICE = "total_product_price";
    public static final String BILL_TOTAL_PRODUCT_COST = "total_product_cost";
    public static final String BILL_CUSTOMER = "customer_id";

    public static final String BILL_RECORD_TABLE = "bill_record";
    public static final String BILL_RECORD_PRODUCT = "product_id";
    public static final String BILL_RECORD_PRODUCT_AMOUNT = "product_amount";
    public static final String BILL_RECORD_PRODUCT_PRICE = "product_price";

    public static final String CATEGORY_PRODUCT_TABLE = "category";
    public static final String CATEGORY_PRODUCT_NAME = "name";
    public static final String CATEGORY_PRODUCT_CATEGORY_TAX = "category_tax";
    public static final String CATEGORY_PRODUCT_CUSTOMER = "customer_id";

    public static final String CUSTOMER_TABLE = "customer";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_EMAIL = "email";
    public static final String CUSTOMER_REGISTRATION_DATE = "registration_date";
    public static final String CUSTOMER_CUSTOMER_STATUS = "customer_status";

    public static final String INNER_APPLICATION_TABLE = "inner_applications";
    public static final String INNER_APPLICATION_APPLICATION_NUMBER = "application_number";
    public static final String INNER_APPLICATION_SOURCE_LOCATION = "source_location_id";
    public static final String INNER_APPLICATION_DESTINATION_LOCATION = "destination_location_id";
    public static final String INNER_APPLICATION_CREATOR = "creator_id";
    public static final String INNER_APPLICATION_UPDATER = "updater_id";
    public static final String INNER_APPLICATION_REGISTRATION_DATE_TIME = "registration_date_time";
    public static final String INNER_APPLICATION_UPDATING_DATE_TIME = "updating_date_time";
    public static final String INNER_APPLICATION_APPLICATION_STATUS = "application_status";
    public static final String INNER_APPLICATION_JOIN_TABLE_NAME = "inner_application_record";
    public static final String INNER_APPLICATION_JOIN_TABLE_JOIN_COLUMNS = "inner_application_id";
    public static final String INNER_APPLICATION_JOIN_TABLE_INVERSE_COLUMNS = "application_record_id";
    public static final String INNER_APPLICATION_TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public static final String INNER_APPLICATION_TOTAL_UNIT_NUMBER = "total_unit_number";

    public static final String LOCATION_TABLE = "location";
    public static final String LOCATION_IDENTIFIER = "identifier";
    public static final String LOCATION_CUSTOMER = "customer_id";
    public static final String LOCATION_ADDRESS = "address_id";
    public static final String LOCATION_TOTAL_CAPACITY = "total_capacity";
    public static final String LOCATION_AVAILABLE_CAPACITY = "available_capacity";
    public static final String LOCATION_LOCATION_TYPE = "location_type";
    public static final String LOCATION_LOCATION_TAX = "location_tax";
    public static final String LOCATION_STATUS = "deleted_status";

    public static final String LOCATION_PRODUCT_TABLE = "location_product";
    public static final String LOCATION_PRODUCT_COST = "cost";
    public static final String LOCATION_PRODUCT_AMOUNT = "amount";
    public static final String LOCATION_PRODUCT_PRODUCT = "product_id";
    public static final String LOCATION_PRODUCT_LOCATION = "location_id";

    public static final String PRODUCT_TABLE = "product";
    public static final String PRODUCT_UPC = "upc";
    public static final String PRODUCT_LABEL = "label";
    public static final String PRODUCT_CATEGORY = "category_id";
    public static final String PRODUCT_VOLUME = "volume";
    public static final String PRODUCT_CUSTOMER = "customer_id";
    public static final String PRODUCT_STATUS = "deleted_status";

    public static final String STATE_TABLE = "state";
    public static final String STATE_STATE_TAX = "state_tax";
    public static final String STATE_NAME = "name";

    public static final String SUPPLIER_TABLE = "supplier";
    public static final String SUPPLIER_FULL_NAME = "full_name";
    public static final String SUPPLIER_IDENTIFIER = "identifier";
    public static final String SUPPLIER_WARE_HOUSE_LIST = "supplier_id";
    public static final String SUPPLIER_CUSTOMER = "customer_id";
    public static final String SUPPLIER_SUPPLIER_STATUS = "supplier_status";

    public static final String SUPPLIER_APPLICATION_TABLE = "supplier_applications";
    public static final String SUPPLIER_APPLICATION_NUMBER = "application_number";
    public static final String SUPPLIER_APPLICATION_SUPPLIER_WAREHOUSE = "supplier_warehouse_id";
    public static final String SUPPLIER_APPLICATION_DESTINATION_LOCATION = "destination_location_id";
    public static final String SUPPLIER_APPLICATION_CREATOR = "creator_id";
    public static final String SUPPLIER_APPLICATION_UPDATER = "updater_id";
    public static final String SUPPLIER_APPLICATION_REGISTRATION_DATE_TIME = "registration_date_time";
    public static final String SUPPLIER_APPLICATION_UPDATING_DATE_TIME = "updating_date_time";
    public static final String SUPPLIER_APPLICATION_APPLICATION_STATUS = "application_status";
    public static final String SUPPLIER_APPLICATION_JOIN_TABLE_NAME = "supplier_application_record";
    public static final String SUPPLIER_APPLICATION_JOIN_TABLE_JOIN_COLUMNS = "supplier_application_id";
    public static final String SUPPLIER_APPLICATION_JOIN_TABLE_INVERSE_JOIN_COLUMNS = "application_record_id";
    public static final String SUPPLIER_APPLICATION_TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public static final String SUPPLIER_APPLICATION_TOTAL_UNIT_NUMBER = "total_unit_number";

    public static final String SUPPLIER_WAREHOUSE_TABLE = "supplier_warehouse";
    public static final String SUPPLIER_WAREHOUSE_NAME = "name";
    public static final String SUPPLIER_WAREHOUSE_ADDRESS = "address_id";
    public static final String SUPPLIER_WAREHOUSE_STATUS = "deleted_status";

    public static final String USER_TABLE = "users";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_ADDRESS = "address_id";
    public static final String USER_BIRTHDAY = "birthday";
    public static final String USER_COLLECTION_TABLE_NAME = "user_role";
    public static final String USER_COLLECTION_TABLE_JOIN_COLUMNS = "user_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_STATUS = "user_status";
    public static final String USER_LOCATION = "location_id";
    public static final String USER_CUSTOMER = "customer_id";
    public static final String USER_LOGIN = "login";

    public static final String WRITE_OFF_ACT_TABLE = "write_off_act";
    public static final String WRITE_OFF_ACT_NUMBER = "act_number";
    public static final String WRITE_OFF_ACT_RECORDS = "write_off_act_id";
    public static final String WRITE_OFF_ACT_ACT_DATE_TIME = "act_date_time";
    public static final String WRITE_OFF_ACT_TOTAL_PRODUCT_AMOUNT = "total_product_amount";
    public static final String WRITE_OFF_ACT_TOTAL_PRODUCT_SUM = "total_product_sum";
    public static final String WRITE_OFF_ACT_ACT_LOCATION = "location_id";
    public static final String WRITE_OFF_ACT_ACT_CUSTOMER = "customer_id";

    public static final String WRITE_OFF_ACT_RECORD_TABLE = "write_off_record";
    public static final String WRITE_OFF_ACT_RECORD_PRODUCT = "product_id";
    public static final String WRITE_OFF_ACT_RECORD_AMOUNT = "amount";
    public static final String WRITE_OFF_ACT_RECORD_REASON = "reason";
}
