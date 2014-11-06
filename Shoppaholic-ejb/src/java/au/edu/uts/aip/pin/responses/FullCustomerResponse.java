package au.edu.uts.aip.pin.responses;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FullCustomerResponse {
    
    private CustomerResponse response;

    public CustomerResponse getResponse() {
        return response;
    }

    public void setResponse(CustomerResponse response) {
        this.response = response;
    }
    
    
    
}
