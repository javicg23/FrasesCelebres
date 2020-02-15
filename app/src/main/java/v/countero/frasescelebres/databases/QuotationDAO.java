package v.countero.frasescelebres.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import v.countero.frasescelebres.pojos.Quotation;

@Dao
public interface QuotationDAO {

    @Query("SELECT * FROM quotation_table")
    List<Quotation> getQuotations();

    @Query("SELECT * FROM quotation_table WHERE quoteColumn = :quotationText")
    Quotation getQuotationText(String quotationText);

    @Query("DELETE FROM quotation_table")
    void deleteAllQuotations();

    @Insert
    void insertQuotation(Quotation quotation);

    @Delete
    void deleteQuotation(Quotation quotation);
}
