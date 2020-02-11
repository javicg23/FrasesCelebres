package v.countero.frasescelebres.databases;

import android.provider.BaseColumns;

public class QuotationContract {
    private QuotationContract() {
    }
    public static class ContractBase implements BaseColumns {
        public static final String DB_NAME = "quotation_database";
        public static final int DB_VERSION = 1;
        public static final String ID = "_ID";
        public static final String AUTHOR_COLUMN = "author_column";
        public static final String QUOTE_COLUMN = "quote_column";
    }
}
