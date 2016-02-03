
package victor.training.ws.responsibility.v1.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domainBID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userBID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "domainBID",
    "userBID"
})
@XmlRootElement(name = "getUserResponsibilityRequest")
public class GetUserResponsibilityRequest {

    @XmlElement(required = true)
    protected String domainBID;
    @XmlElement(required = true)
    protected String userBID;

    /**
     * Gets the value of the domainBID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainBID() {
        return domainBID;
    }

    /**
     * Sets the value of the domainBID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainBID(String value) {
        this.domainBID = value;
    }

    /**
     * Gets the value of the userBID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserBID() {
        return userBID;
    }

    /**
     * Sets the value of the userBID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserBID(String value) {
        this.userBID = value;
    }

}
