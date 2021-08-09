package logic;

import common.ValidationException;
import dal.BloodDonationDAL;
import entity.BloodDonation;
import entity.BloodGroup;
import entity.RhesusFactor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.ObjIntConsumer;



/**
 *
 * @author sadia
 */
public class BloodDonationLogic extends GenericLogic<BloodDonation, BloodDonationDAL>  {
    public static final String BANK_ID = "bank_id";
    public static final String MILLILITERS = "milliliters";
    public static final String BLOOD_GROUP = "blood_group";
    public static final String RHESUS_FACTOR = "rhesus_factor";
    public static final String CREATED = "created";
    public static final String ID = "id";
   
    
    BloodDonationLogic() {
        super( new BloodDonationDAL() );
    }
    public List<BloodDonation> getAll() {
        return get( () -> dal().findAll()); 
    } 
   public BloodDonation getWithId(int id){
       return get( () -> dal().findById(id)); 
   } 
   public List<BloodDonation> getBloodDonationWithMilliliters(int milliliters) {
        return get( () -> dal().findByMilliliters(milliliters));
    }
    public List<BloodDonation> getBloodDonationWithBloodGroup(BloodGroup bloodGroup) {
        return get( () -> dal().findByBloodGroup(bloodGroup));
    }
    
    public List<BloodDonation> getBloodDonationWithCreated(Date created) {
        return get( () -> dal().findByCreated(created));
    }
    
    public List<BloodDonation> getBloodDonationsWithRhd(RhesusFactor rhd) {
        return get( () -> dal().findByRhd(rhd));
    }
     public List<BloodDonation> getBloodDonationsWithBloodBank(int bankId) {
          return get( () -> dal().findByBloodBank(bankId));
    }
     @Override
     public BloodDonation createEntity(Map<String, String[]> parameterMap) {
        Objects.requireNonNull(parameterMap, "ParameterMap cannot be null"); 
        
        BloodDonation entity = new BloodDonation();
        if (parameterMap.containsKey(ID)) {
            try {
                entity.setId(Integer.parseInt(parameterMap.get(ID)[0]));
            }
            catch (java.lang.NumberFormatException ex) {
                throw new ValidationException(ex);
            }
        }
         ObjIntConsumer<String> validator = (value, length) -> {

            if (value == null || value.trim().isEmpty() || value.length() > length) {
                String error = "";
                if (value == null || value.trim().isEmpty()) {
                    error = "Value cannot be null or empty: " + value;
                }
                if (value.length() > length) {
                    error = "string length is " + value.length() + " > " + length;
                }
                throw new ValidationException(error);
            }
        };
       
        String milliliters = parameterMap.get(MILLILITERS) [ 0 ];
        String bloodGroup = parameterMap.get(BLOOD_GROUP)[ 0 ];
      
        RhesusFactor rhesusFactor = this.convertToEntityAttribute(parameterMap.get(RHESUS_FACTOR)[0]);
 
        BloodGroup group = BloodGroup.valueOf(bloodGroup);
                        
        entity.setMilliliters(Integer.parseInt(milliliters));
        entity.setRhd(rhesusFactor); 
        entity.setBloodGroup(group);
         
        return entity;
    }
   @Override
   public List<String> getColumnNames() {
        return Arrays.asList ("ID", "Milliliters", "Blood Group", "Rhesus Factor", "Bank ID", "Created");
    }
     @Override
   public List<String> getColumnCodes() {
        return Arrays.asList(ID, MILLILITERS, BLOOD_GROUP, RHESUS_FACTOR, BANK_ID, CREATED);
    }
    @Override
    public List<?> extractDataAsList(BloodDonation e) {
        return Arrays.asList(e.getId(), e.getMilliliters(), e.getBloodGroup(), e.getRhd(), e.getBloodBank(), e.getCreated());
    }
    
      public RhesusFactor convertToEntityAttribute( String dbData ) {
        return RhesusFactor.getRhesusFactor( dbData );
    }
    
}
