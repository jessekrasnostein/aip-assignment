package au.edu.uts.aip.pin.charge;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FullChargeResponse {
    
    private ChargeResponse response;

    public ChargeResponse getResponse() {
        return response;
    }

    public void setResponse(ChargeResponse response) {
        this.response = response;
    }
    
    
    
}
