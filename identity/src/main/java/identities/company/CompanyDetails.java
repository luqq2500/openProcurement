package identities.company;

import identities.address.Address;

public class CompanyDetails {
    private String companyName;
    private String brn;
    private Structure structure;
    private String taxNumber;
    private Address address;
    public CompanyDetails(String companyName, Address address, String brn, Structure structure) {
        this.companyName = companyName;
        this.address = address;
        this.brn = brn;
        this.structure = structure;
    }
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    public void changeName(String newName){
        companyName = newName;
    }
    public void changeBrn(String newBrn){
        brn = newBrn;
    }
    public void changeStructure(Structure newStructure){
        structure = newStructure;
    }
    public void changeTaxNumber(String newTaxNumber){
        taxNumber = newTaxNumber;
    }
    public void changeAddress(Address address){
        this.address = address;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getBrn() {return brn;}

    public Structure getStructure() {
        return structure;
    }
    public String getTaxNumber() {
        return taxNumber;
    }
}
